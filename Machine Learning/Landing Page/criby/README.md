# Company Profile PT. Mega Nusa Indonesia

Company Profile for PT. Mega Nusa Indonesia made by Etech.

## Getting Started

1. Clone this repo.
2. Run `yarn install` to install dependencies.
3. Go to the project directory.
4. Run `yarn start`.

## Scripts

-   `yarn dev` - Run the application
-   `yarn build` - Build the application
-   `yarn lint` - Run [ESLint](https://eslint.org/)
-   `yarn lint:fix` - Fix codes from ESLint errors
-   `yarn format` - Format codes using Prettier

## File Structure

```raw
.
├── 📂 public/                  Public files (e.g. favicon).
├── 📂 src/
│   ├── ⚛️ app/                 Next Js page components.
│   ├── ⚛️ components/
│   │   ├── ⚛️ commons/          Common components.
│   │   ├── ⚛️ layouts/          Components which has specific location in a page and cannot just be placed anywhere (e.g. header footer).
│   │   └── ⚛️ sections/         Components which create a block/section of a page.
│   ├── 📂 constant/
│   └── 📂 styles/
│   │   ├── 📂 components/       SCSS files for components (e.g. button, table, n input form).
│   │   ├── 📂 layouts/          SCSS files for specific location in a page and cannot just be placed anywhere (e.g. header, sidebar, footer).
│   │   ├── _base.scss           Base styles.
│   │   ├── _utilities.scss      Contains utility classes.
│   │   └── index.scss           Contains @import statements to merge all SCSS files.
├── .eslintignore
├── .eslintrc.cjs                ESLint configuration.
├── .gitignore
├── .prettierrc                  Prettier configuration.
├── package.json
├── postcss.config.js
├── README.md                    Information about the application.
├── tailwind.config.js           The configuration TailwindCss for the application.
├── tsconfig.json                The configuration Typescript for the application.
├── tsconfig.node.json           The configuration Typescript compiler for Node.js server code.
└── yarn.lock                    Ensure consistency version-dependencies.

Notes:
📂: Folder
⚛️: React-related folder
```

## Learn More

To learn more about Next.js, take a look at the following resources:

-   [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
-   [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js/) - your feedback and contributions are welcome!
