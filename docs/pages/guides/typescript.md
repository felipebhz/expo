---
title: TypeScript
---

import { Terminal } from '~/ui/components/Snippet';

> Example project: [with-typescript](https://github.com/expo/examples/tree/master/with-typescript)

Expo has first-class support for [TypeScript](https://www.typescriptlang.org/). The JavaScript interface of the Expo SDK is completely written in TypeScript.

To get started, create a **tsconfig.json** in your project root:

<Terminal cmd={['$ touch tsconfig.json']} />

Running `expo start` will prompt you to install the required dependencies (`typescript`, `@types/react`, `@types/react-native`), and automatically configure your **tsconfig.json**.

Rename files to convert them to TypeScript. For example, you would rename **App.js** to **App.tsx**. Use the **.tsx** extension if the file includes React components (JSX). If the file did not include any JSX, you can use the **.ts** file extension.

<Terminal cmd={['$ mv App.js App.tsx']} />

You can now run `yarn tsc` or `npx tsc` to typecheck the project.

## Base configuration

> You can disable the TypeScript setup in Expo CLI with the environment variable `EXPO_NO_TYPESCRIPT_SETUP=1`

An Expo app's **tsconfig.json** should extend the `expo/tsconfig.base` by default. This sets the following default [compiler options][tsc-compileroptions] (which can be overwritten in your project's **tsconfig.json**):

- `"allowJs"`: `true`
  - Allow JavaScript files to be compiled. If you project requires more strictness, you can disable this.
- `"esModuleInterop"`: `true`
  - Improve Babel ecosystem compatibility. This also sets `allowSyntheticDefaultImports` to `true`, allowing default imports from modules with no default export.
- [`"jsx"`][tsc-jsx]: `"react-native"`
  - Preserve JSX, and converts the `jsx` extension to `js`. This is optimized for bundlers that transform the JSX internally (like Metro).
- `"lib"`: `["DOM", "ESNext"]`
  - Allow using the latest [ECMAScript proposed features and libraries](https://github.com/tc39/proposals).
- [`"moduleResolution"`][tsc-moduleresolution]: `"node"`
  - Emulate how Metro and Webpack resolve modules.
- `"noEmit"`: `true`
  - Only use the TypeScript compiler (TSC) to check the code. The Metro bundler is responsible for compiling TypeScript to JavaScript.
- `"resolveJsonModule"`: `true`
  - Enables importing **.json** files. Metro's default behavior is to allow importing json files as JS objects.
- `"skipLibCheck"`: `true`
  - Skip type checking of all declaration files (`*.d.ts`).
- `"target"`: `"ESNext"`
  - Compile to the latest version of ECMAScript.

[tsc-jsx]: https://www.typescriptlang.org/docs/handbook/jsx.html
[tsc-compileroptions]: https://www.typescriptlang.org/docs/handbook/compiler-options.html
[tsc-moduleresolution]: https://www.typescriptlang.org/docs/handbook/module-resolution.html

## Project configuration

Expo CLI will automatically modify your **tsconfig.json** to the preferred default which is optimized for universal React development:

```json
{
  "extends": "expo/tsconfig.base",
  "compilerOptions": {}
}
```

The default configuration is forgiving and makes it easier to adopt TypeScript. If you'd like to opt-in to more strict type checking, you can add `"strict": true` to the `compilerOptions`. We recommend enabling this to minimize the chance of introducing runtime errors.

Certain language features may require additional configuration, for example if you'd like to use decorators you will need to add the `experimentalDecorators` option. For more information on the available properties see the [TypeScript compiler options documentation](https://www.typescriptlang.org/docs/handbook/compiler-options.html) documentation.

## Starting from scratch: using a TypeScript template

<Terminal cmd={['$ npx create-expo-app -t expo-template-blank-typescript']} cmdCopy="npx create-expo-app -t expo-template-blank-typescript" />

The easiest way to get started is to initialize your new project using a TypeScript template, then run `yarn tsc` or `npx tsc` to "typecheck" the project.

When you create new source files in your project you should use the **.ts** extension or the **.tsx** if the file includes React components.

## TypeScript for config files

You may find that you want to use TypeScript for the config files in your project, like the **webpack.config.js**, **metro.config.js**, or **app.config.js**. These will require a little extra setup. You can utilize the [`ts-node` require hook](https://github.com/TypeStrong/ts-node#programmatic) to _import_ TypeScript files into your JS config file, meaning any import can be TypeScript, but the root file will still need to be JavaScript.

<Terminal cmd={['$ yarn add -D ts-node typescript']} />

### webpack.config.js

> You may need to install the `@expo/webpack-config` package.

**webpack.config.js**

```js
require('ts-node/register');
module.exports = require('./webpack.config.ts');
```

**webpack.config.ts**

```ts
import createExpoWebpackConfigAsync from '@expo/webpack-config/webpack';
import { Arguments, Environment } from '@expo/webpack-config/webpack/types';

module.exports = async function (env: Environment, argv: Arguments) {
  const config = await createExpoWebpackConfigAsync(env, argv);
  // Customize the config before returning it.
  return config;
};
```

### metro.config.js

> Metro doesn't ship types or have a definitely typed package.

**metro.config.js**

```js
require('ts-node/register');
module.exports = require('./metro.config.ts');
```

**metro.config.ts**

```ts
import { getDefaultConfig } from '@expo/metro-config';

const defaultConfig = getDefaultConfig(__dirname);

module.exports = defaultConfig;
```

### app.config.js

Technically **app.config.ts** is supported by default, but it doesn't support external TypeScript modules, or **tsconfig.json** customization. You can use the following approach to get a more comprehensive TypeScript setup.

**app.config.js**

```js
require('ts-node/register');
module.exports = require('./app.config.ts');
```

**app.config.ts**

```ts
import { ExpoConfig } from '@expo/config-types';

const config: ExpoConfig = {
  name: 'my-app',
};

export default config;
```

## Learning how to use TypeScript

A good place to start learning TypeScript is the official [TypeScript Handbook](https://www.typescriptlang.org/docs/handbook/basic-types.html).

### TypeScript and React components

We recommend reading over and referring to the [React TypeScript CheatSheet](https://github.com/typescript-cheatsheets/react) to learn how to type your React components in a variety of common situations.

### Advanced types

If you would like to go deeper and learn how to create more expressive and powerful types, we recommend the [Advanced Static Types in TypeScript course](https://egghead.io/courses/advanced-static-types-in-typescript) (this requires an egghead.io subscription).
