import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    // Porta fixa para nao colidir com outros projetos Vite rodando na 5173.
    port: 5180,
    strictPort: true,
    // Encaminha /api para o backend Spring, evitando configurar CORS em desenvolvimento.
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})
