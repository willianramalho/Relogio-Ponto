import { useCallback, useState } from 'react'

export type Theme = 'light' | 'dark'
const STORAGE_KEY = 'pontoflex-theme'

export function getInitialTheme(): Theme {
  return document.documentElement.classList.contains('dark') ? 'dark' : 'light'
}

export function applyTheme(theme: Theme): void {
  document.documentElement.classList.toggle('dark', theme === 'dark')
  localStorage.setItem(STORAGE_KEY, theme)
}

export function useTheme(): [Theme, () => void] {
  const [theme, setThemeState] = useState<Theme>(getInitialTheme)

  const toggleTheme = useCallback(() => {
    const next: Theme = theme === 'dark' ? 'light' : 'dark'
    applyTheme(next)
    setThemeState(next)
  }, [theme])

  return [theme, toggleTheme]
}
