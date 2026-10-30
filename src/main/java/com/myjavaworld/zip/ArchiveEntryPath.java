package com.myjavaworld.zip;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Locale;

/** Portable lexical ZIP-name policy. This component never opens an entry path. */
final class ArchiveEntryPath {
    private ArchiveEntryPath() { }

    static Path resolve(Path root, String name, boolean directory) throws IOException {
        if (name == null || name.length() == 0 || name.startsWith("/") || name.indexOf('\\') >= 0) {
            throw unsafe(name, "a forward-slash relative path is required");
        }
        String relative = directory && name.endsWith("/") ? name.substring(0, name.length() - 1) : name;
        String[] parts = relative.split("/", -1);
        for (String part : parts) {
            if (part.length() == 0 || part.equals(".") || part.equals("..")
                    || part.endsWith(".") || part.endsWith(" ")) {
                throw unsafe(name, "empty, dot and ambiguous trailing components are forbidden");
            }
            for (int i = 0; i < part.length(); i++) {
                char c = part.charAt(i);
                if (c < 32 || "<>:\"|?*".indexOf(c) >= 0) {
                    throw unsafe(name, "a reserved filename character is present");
                }
            }
            String base = part.split("\\.", 2)[0].toUpperCase(Locale.ROOT);
            if (base.equals("CON") || base.equals("PRN") || base.equals("AUX") || base.equals("NUL")
                    || base.equals("CONIN$") || base.equals("CONOUT$") || base.equals("CLOCK$")
                    || base.matches("(?:COM|LPT)[1-9¹²³]")) {
                throw unsafe(name, "a reserved device name is present");
            }
        }
        try {
            Path result = root.resolve(relative).normalize();
            if (!result.startsWith(root) || result.equals(root)) {
                throw unsafe(name, "destination is outside the extraction folder");
            }
            return result;
        } catch (InvalidPathException error) {
            throw unsafe(name, "the filesystem cannot represent this path");
        }
    }

    static IOException unsafe(String name, String reason) {
        return new IOException("Unsafe ZIP entry '" + printable(name) + "': " + reason);
    }

    static String printable(String name) {
        if (name == null) return "<missing>";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c < 32 || c == '<' || c == '>' || c == '&' || c == '\'' || c == '"') {
                result.append(String.format(Locale.ROOT, "\\u%04x", (int) c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
