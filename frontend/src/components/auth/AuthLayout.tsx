import type { ReactNode } from 'react'
import { Card } from '../ui/Card'
import { Text } from '../ui/Text'
import { ThemeToggle } from '../ui/ThemeToggle'

type AuthLayoutProps = {
  titulo: string
  subtitulo: string
  children: ReactNode
  rodape: ReactNode
}

export function AuthLayout({ titulo, subtitulo, children, rodape }: AuthLayoutProps) {
  return (
    <div className="min-h-screen bg-surface-100 flex flex-col">
      <header className="flex items-center justify-between px-4 py-3 sm:px-8">
        <Text variant="heading-md" as="span" className="text-brand">
          PontoFlex
        </Text>
        <ThemeToggle />
      </header>
      <main className="flex flex-1 items-center justify-center px-4 py-8">
        <div className="w-full max-w-md flex flex-col gap-4">
          <div className="flex flex-col gap-1 text-center">
            <Text variant="display" as="h1">
              {titulo}
            </Text>
            <Text muted>{subtitulo}</Text>
          </div>
          <Card className="p-6">{children}</Card>
          <Text muted className="text-center">
            {rodape}
          </Text>
        </div>
      </main>
    </div>
  )
}
