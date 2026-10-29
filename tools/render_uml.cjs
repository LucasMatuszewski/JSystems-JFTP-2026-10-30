#!/usr/bin/env node
// Render checked-in Mermaid blocks in a real headless browser; no application code runs.
const fs = require('node:fs');
const path = require('node:path');
const crypto = require('node:crypto');
const root = path.resolve(__dirname, '..');
const modules = path.join(root, '.local', 'uml-qa', 'node_modules');
const {chromium} = require(path.join(modules, 'playwright'));
const source = path.join(root, 'docs', 'uml', 'README.md');
const target = path.join(root, 'docs', 'uml', 'rendered');
const names = ['01-core-classes', '02-persistence-and-tls', '03-connection-sequence',
               '04-transfer-activity', '05-session-states', '06-deployment'];
const hash = data => crypto.createHash('sha256').update(data).digest('hex');
(async () => {
  if (process.argv.length !== 3 || !['--write', '--check'].includes(process.argv[2])) {
    throw new Error('Usage: node tools/render_uml.cjs --write|--check');
  }
  const write = process.argv[2] === '--write';
  const raw = fs.readFileSync(source);
  const blocks = [...raw.toString('utf8').matchAll(/```mermaid\r?\n([\s\S]*?)```/g)].map(m => m[1]);
  if (blocks.length !== names.length) throw new Error('Expected exactly six documented diagrams');
  const browser = await chromium.launch({executablePath: process.env.JFTP_CHROME ||
    'C:/Program Files/Google/Chrome/Application/chrome.exe', headless: true});
  const diagrams = [];
  try {
    const page = await browser.newPage({viewport: {width: 1800, height: 1000}});
    const errors = [];
    page.on('pageerror', error => errors.push(error.message));
    await page.setContent('<!doctype html><html lang="en"><body></body></html>');
    await page.addScriptTag({path: path.join(modules, 'mermaid', 'dist', 'mermaid.min.js')});
    await page.evaluate(() => mermaid.initialize({startOnLoad: false, securityLevel: 'strict',
      theme: 'neutral', deterministicIds: true, deterministicIDSeed: 'jftp-baseline'}));
    if (write) fs.mkdirSync(target, {recursive: true});
    for (let i = 0; i < blocks.length; i++) {
      const rendered = await page.evaluate(async ({code, id}) => {
        document.body.innerHTML = '';
        const {svg} = await mermaid.render(id, code);
        document.body.innerHTML = svg;
        const element = document.querySelector('svg');
        const box = element.getBoundingClientRect();
        return {svg, width: box.width, height: box.height, labels: element.textContent.length};
      }, {code: blocks[i], id: `jftp-uml-${i + 1}`});
      if (rendered.width <= 0 || rendered.height <= 0 || rendered.labels < 20) {
        throw new Error(`Empty diagram: ${names[i]}`);
      }
      const filename = `${names[i]}.svg`;
      if (write) fs.writeFileSync(path.join(target, filename), rendered.svg + '\n');
      else if (!fs.existsSync(path.join(target, filename))) throw new Error(`Missing ${filename}`);
      diagrams.push({id: names[i], source_sha256_lf: hash(blocks[i].replace(/\r\n/g, '\n')),
        svg_sha256: hash(fs.readFileSync(path.join(target, filename))),
        rendered_width: rendered.width, rendered_height: rendered.height});
      if (write && i === 0) await page.screenshot({path: path.join(target, 'preview.png'), fullPage: true});
    }
    if (errors.length) throw new Error(errors.join('\n'));
    const auxiliary = [];
    const architectureSources = ['docs/PRD.md', ...['ADR', 'prd'].flatMap(directory =>
      fs.readdirSync(path.join(root, 'docs', directory)).filter(name => name.endsWith('.md'))
        .sort().map(name => `docs/${directory}/${name}`))];
    for (const relative of architectureSources) {
      const content = fs.readFileSync(path.join(root, relative), 'utf8');
      const extra = [...content.matchAll(/```mermaid\r?\n([\s\S]*?)```/g)];
      for (let i = 0; i < extra.length; i++) {
        await page.evaluate(async ({id, code}) => {
          document.body.innerHTML = '';
          await mermaid.render(id, code);
        }, {id: `aux-${auxiliary.length}-${i}`, code: extra[i][1]});
      }
      auxiliary.push({path: relative, diagrams: extra.length,
        source_sha256_lf: hash(content.replace(/\r\n/g, '\n'))});
    }
    if (errors.length) throw new Error(errors.join('\n'));
    const receipt = {application_revision: '14e62ceba4e371c2a0b955604b10f065f46f4f7d',
      mermaid_version: require(path.join(modules, 'mermaid', 'package.json')).version,
      source_sha256_lf: hash(raw.toString('utf8').replace(/\r\n/g, '\n')),
      diagrams, auxiliary_sources: auxiliary, browser_errors: errors};
    const receiptPath = path.join(root, 'docs', 'uml', 'validation.json');
    if (write) fs.writeFileSync(receiptPath, JSON.stringify(receipt, null, 2) + '\n');
    else {
      const saved = JSON.parse(fs.readFileSync(receiptPath, 'utf8'));
      if (saved.source_sha256_lf !== receipt.source_sha256_lf ||
          JSON.stringify(saved.auxiliary_sources) !== JSON.stringify(auxiliary) ||
          saved.diagrams.some((d, i) => d.svg_sha256 !== diagrams[i].svg_sha256 ||
            d.source_sha256_lf !== diagrams[i].source_sha256_lf)) throw new Error('Stale UML snapshot');
    }
    console.log(JSON.stringify({passed: true, diagrams: diagrams.length,
      auxiliary_diagrams: auxiliary.reduce((n, x) => n + x.diagrams, 0), browser_errors: errors.length}));
  } finally { await browser.close(); }
})().catch(error => {console.error(error); process.exitCode = 1;});
