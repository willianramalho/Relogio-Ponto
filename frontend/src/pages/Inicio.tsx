import { Button } from '../components/ui/Button'
import { Card } from '../components/ui/Card'
import { Text } from '../components/ui/Text'
import { ThemeToggle } from '../components/ui/ThemeToggle'
import type { Sessao } from '../lib/auth'

type InicioProps = {
  sessao: Sessao
  onSair: () => void
}

export function Inicio({ sessao, onSair }: InicioProps) {
  return (
    <div className="min-h-screen bg-surface-100">
      <header className="flex items-center justify-between gap-2 border-b border-border bg-surface-200 px-4 py-3 sm:px-8">
        <Text variant="heading-md" as="span" className="text-brand">
          PontoFlex
        </Text>
        <div className="flex items-center gap-2">
          <ThemeToggle />
          <Button variant="secondary" onClick={onSair}>
            Sair
          </Button>
        </div>
      </header>
      <main className="mx-auto flex max-w-3xl flex-col gap-4 px-4 py-8">
        <Text variant="heading-lg" as="h1">
          Olá!
        </Text>
        <Card className="flex flex-col gap-1">
          <Text muted variant="caption">
            Você está conectado como
          </Text>
          <Text variant="body-strong">{sessao.email}</Text>
        </Card>
        <Text muted>O registro de ponto ainda está em construção.</Text>
      </main>
    </div>
  )
}
