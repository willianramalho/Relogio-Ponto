import type { Config } from 'tailwindcss'

// Spacing is intentionally NOT overridden - Tailwind's default numeric scale already equals the doc exactly (space-2=8px -> p-2, space-4=16px -> p-4, space-6=24px -> p-6, space-8=32px -> p-8).
export default {
  darkMode: 'class',
  content: ['./index.html', './src/**/*.{ts,tsx}'],
  theme: {
    extend: {
      colors: {
        surface: {
          100: 'var(--color-surface-100)',
          200: 'var(--color-surface-200)',
          300: 'var(--color-surface-300)',
        },
        border: 'var(--color-border)',
        ink: {
          DEFAULT: 'var(--color-ink)',
          muted: 'var(--color-ink-muted)',
        },
        brand: {
          DEFAULT: 'var(--color-brand)',
          strong: 'var(--color-brand-strong)',
          light: 'var(--color-brand-light)',
        },
        success: 'var(--color-success)',
        warning: 'var(--color-warning)',
        danger: 'var(--color-danger)',
        'focus-ring': 'var(--color-focus-ring)',
      },
      fontFamily: {
        sans: [
          '-apple-system', 'BlinkMacSystemFont', '"Segoe UI"', 'Roboto',
          '"Helvetica Neue"', 'Arial', 'sans-serif',
        ],
        mono: ['"SFMono-Regular"', 'Menlo', 'Consolas', '"Liberation Mono"', 'monospace'],
      },
      fontSize: {
        display: ['28px', { lineHeight: '34px', fontWeight: '600' }],
        'heading-lg': ['22px', { lineHeight: '28px', fontWeight: '600' }],
        'heading-md': ['17px', { lineHeight: '24px', fontWeight: '600' }],
        body: ['14px', { lineHeight: '21px', fontWeight: '400' }],
        'body-strong': ['14px', { lineHeight: '21px', fontWeight: '600' }],
        caption: ['12px', { lineHeight: '17px', fontWeight: '400' }],
        'data-mono': ['13px', { lineHeight: '19px', fontWeight: '400' }],
      },
      borderRadius: {
        sm: '6px',
        md: '12px',
        lg: '20px',
      },
    },
  },
  plugins: [],
} satisfies Config
