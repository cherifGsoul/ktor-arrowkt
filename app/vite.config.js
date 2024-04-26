import { defineConfig } from "vite"
import path from 'path';

const projectRootDir = path.resolve(__dirname);

export default defineConfig({
    build: {
        emptyOutDir: false,
        outDir: "./src/main/resources/static",
        manifest: true,
        sourceMap: true,
        rollupOptions: {
            // Use a custom non-html entry point
            input: path.resolve(projectRootDir, './assets/js/app.js'),
        }
    },
    resolve: {
        alias: [
          {
            find: 'app',
            replacement: path.resolve(projectRootDir, './assets/js'),
          },
        ],
      },
      esbuild: {},
      optimizeDeps: {
        include: [],
      }
})