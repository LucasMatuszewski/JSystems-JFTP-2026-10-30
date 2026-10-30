"""Real loopback-only FTP fixture; serves only an owned synthetic directory."""
import argparse
import importlib.metadata
import json
import logging
import os
import datetime
import ipaddress
from pathlib import Path

from pyftpdlib.authorizers import DummyAuthorizer
from pyftpdlib.handlers import FTPHandler
from pyftpdlib.servers import FTPServer

ROOT = Path(__file__).resolve().parents[1]


def tls_handler(directory):
    from cryptography import x509
    from cryptography.hazmat.primitives import hashes, serialization
    from cryptography.hazmat.primitives.asymmetric import rsa
    from cryptography.x509.oid import NameOID, ExtendedKeyUsageOID
    from OpenSSL import SSL
    from pyftpdlib.handlers import TLS_FTPHandler

    now = datetime.datetime.now(datetime.timezone.utc)
    ca_key = rsa.generate_private_key(public_exponent=65537, key_size=2048)
    ca_name = x509.Name([x509.NameAttribute(NameOID.COMMON_NAME, "JFTP synthetic fixture CA")])
    ca = (x509.CertificateBuilder().subject_name(ca_name).issuer_name(ca_name)
          .public_key(ca_key.public_key()).serial_number(x509.random_serial_number())
          .not_valid_before(now - datetime.timedelta(days=1)).not_valid_after(now + datetime.timedelta(days=7))
          .add_extension(x509.BasicConstraints(ca=True, path_length=0), critical=True)
          .add_extension(x509.SubjectKeyIdentifier.from_public_key(ca_key.public_key()), critical=False)
          .add_extension(x509.AuthorityKeyIdentifier.from_issuer_public_key(ca_key.public_key()), critical=False)
          .add_extension(x509.KeyUsage(digital_signature=False, content_commitment=False,
              key_encipherment=False, data_encipherment=False, key_agreement=False,
              key_cert_sign=True, crl_sign=True, encipher_only=False, decipher_only=False), critical=True)
          .sign(ca_key, hashes.SHA256()))
    unrelated_key = rsa.generate_private_key(public_exponent=65537, key_size=2048)
    unrelated_name = x509.Name([x509.NameAttribute(NameOID.COMMON_NAME, "Unrelated synthetic CA")])
    unrelated = (x509.CertificateBuilder().subject_name(unrelated_name).issuer_name(unrelated_name)
        .public_key(unrelated_key.public_key()).serial_number(x509.random_serial_number())
        .not_valid_before(now - datetime.timedelta(days=1)).not_valid_after(now + datetime.timedelta(days=7))
        .add_extension(x509.BasicConstraints(ca=True, path_length=0), critical=True)
        .add_extension(x509.SubjectKeyIdentifier.from_public_key(unrelated_key.public_key()), critical=False)
        .add_extension(x509.AuthorityKeyIdentifier.from_issuer_public_key(unrelated_key.public_key()), critical=False)
        .add_extension(x509.KeyUsage(digital_signature=False, content_commitment=False,
            key_encipherment=False, data_encipherment=False, key_agreement=False,
            key_cert_sign=True, crl_sign=True, encipher_only=False, decipher_only=False), critical=True)
        .sign(unrelated_key, hashes.SHA256()))
    key = rsa.generate_private_key(public_exponent=65537, key_size=2048)
    leaf = (x509.CertificateBuilder()
            .subject_name(x509.Name([x509.NameAttribute(NameOID.COMMON_NAME, "127.0.0.1")]))
            .issuer_name(ca_name).public_key(key.public_key()).serial_number(x509.random_serial_number())
            .not_valid_before(now - datetime.timedelta(days=1)).not_valid_after(now + datetime.timedelta(days=7))
            .add_extension(x509.BasicConstraints(ca=False, path_length=None), critical=True)
            .add_extension(x509.SubjectKeyIdentifier.from_public_key(key.public_key()), critical=False)
            .add_extension(x509.AuthorityKeyIdentifier.from_issuer_public_key(ca_key.public_key()), critical=False)
            .add_extension(x509.KeyUsage(digital_signature=True, content_commitment=False,
                key_encipherment=True, data_encipherment=False, key_agreement=False,
                key_cert_sign=False, crl_sign=False, encipher_only=False, decipher_only=False), critical=True)
            .add_extension(x509.SubjectAlternativeName([x509.DNSName("localhost"),
                x509.IPAddress(ipaddress.ip_address("127.0.0.1"))]), critical=False)
            .add_extension(x509.ExtendedKeyUsage([ExtendedKeyUsageOID.SERVER_AUTH]), critical=False)
            .sign(ca_key, hashes.SHA256()))
    (directory / "ca.pem").write_bytes(ca.public_bytes(serialization.Encoding.PEM))
    (directory / "unrelated-ca.pem").write_bytes(unrelated.public_bytes(serialization.Encoding.PEM))
    (directory / "server.pem").write_bytes(leaf.public_bytes(serialization.Encoding.PEM))
    (directory / "server-key.pem").write_bytes(key.private_bytes(serialization.Encoding.PEM,
        serialization.PrivateFormat.PKCS8, serialization.NoEncryption()))

    class Handler(TLS_FTPHandler):
        def handle_ssl_established(self):
            super().handle_ssl_established()
            logging.info("JFTP fixture negotiated control protocol=%s", self.socket.get_protocol_version_name())

    Handler.certfile = str(directory / "server.pem")
    Handler.keyfile = str(directory / "server-key.pem")
    Handler.tls_control_required = True
    Handler.tls_data_required = True
    Handler.ssl_context = SSL.Context(SSL.TLS_SERVER_METHOD)
    Handler.ssl_context.use_certificate_chain_file(Handler.certfile)
    Handler.ssl_context.use_privatekey_file(Handler.keyfile)
    Handler.ssl_context.set_min_proto_version(SSL.TLS1_2_VERSION)
    return Handler


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", required=True, type=Path)
    parser.add_argument("--tls", action="store_true", help="Require explicit TLS using a fresh synthetic certificate")
    args = parser.parse_args()
    root = args.root.resolve(strict=True)
    if not root.is_dir() or not root.is_relative_to(ROOT / ".local" / "ftp-integration"):
        parser.error("Serve only a synthetic directory under .local/ftp-integration")
    version = importlib.metadata.version("pyftpdlib")
    if version != "2.2.0":
        parser.error("Install the pinned tools/ftp-fixture-requirements.txt")
    logging.basicConfig(level=logging.DEBUG, format="%(asctime)s %(levelname)s %(message)s")
    authorizer = DummyAuthorizer()
    authorizer.add_user("fixture-user", "fixture-password", str(root), perm="elradfmwMT")

    if args.tls:
        Handler = tls_handler(root.parent)
    else:
        class Handler(FTPHandler):
            pass

    Handler.authorizer = authorizer
    Handler.banner = "JFTP synthetic loopback integration fixture"
    server = FTPServer(("127.0.0.1", 0), Handler)
    server.max_cons = 16
    server.max_cons_per_ip = 16
    ready = {"pid": os.getpid(), "parent_pid": os.getppid(), "host": "127.0.0.1", "port": server.socket.getsockname()[1],
             "pyftpdlib": version, "root": str(root), "tls": args.tls}
    (root.parent / "ready.json").write_text(json.dumps(ready, indent=2) + "\n", encoding="utf-8")
    def stop_when_requested():
        if (root.parent / "STOP").exists():
            server.close_all()
    server.ioloop.call_every(0.1, stop_when_requested)
    try:
        # Bound the first idle socket wait so the STOP timer runs without a client.
        server.serve_forever(timeout=0.1)
    finally:
        server.close_all()


if __name__ == "__main__":
    main()
