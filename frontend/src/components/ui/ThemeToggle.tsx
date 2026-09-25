import clsx from 'clsx'
import { useTheme } from '../../lib/theme'

type ThemeToggleProps = {
  className?: string
}

export function ThemeToggle({ className }: ThemeToggleProps) {
  const [theme, toggleTheme] = useTheme()
  const isDark = theme === 'dark'

  return (
    <button
      type="button"
      aria-pressed={isDark}
      onClick={toggleTheme}
      className={clsx(
        'rounded-md border border-border bg-surface-200 text-ink px-3 py-1.5 text-body hover:bg-surface-300',
        'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-focus-ring focus-visible:ring-offset-2',
        className,
      )}
    >
      {isDark ? 'Modo claro' : 'Modo escuro'}
    </button>
  )
}
