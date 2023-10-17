import path from 'node:path';
import fs from 'node:fs';

import cp from 'cpy';
import { deleteAsync } from 'del';

import { execa as exec } from 'execa';

import { createRequire } from 'node:module';

var dest = process.env.DISTRO_DIST || 'dist';

function resolve(module, sub) {
  var require = createRequire(import.meta.url);
  var pkg = require.resolve(module + '/package.json');

  return path.dirname(pkg) + sub;
}

async function run() {

  console.log('clean ' + dest);
  await deleteAsync(dest);

  console.log('mkdir -p ' + dest);
  fs.mkdirSync(dest, { recursive: true });

  console.log('copy diagram-js.css to ' + dest);
  await cp(resolve('diagram-js', '/assets/**'), dest + '/assets');

  console.log('building pre-packaged distributions');

  await exec('rollup', [ '-c', '--bundleConfigAsCjs' ], {
    stdio: 'inherit'
  });

  console.log('done.');
}

run().catch(e => {
  console.error('failed to build distribution', e);

  process.exit(1);
});