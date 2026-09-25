import type { ReactNode } from 'react'
import { Button } from '../components/ui/Button'
import { Card } from '../components/ui/Card'
import { StatusChip } from '../components/ui/Badge'
import { Text } from '../components/ui/Text'
import { ThemeToggle } from '../components/ui/ThemeToggle'

type TextVariant =
  | 'display'
  | 'heading-lg'
  | 'heading-md'
  | 'body'
  | 'body-strong'
  | 'caption'
  | 'data-mono'

type ColorSwatch = {
  token: string
  cssVar: string
  light: string
  dark: string
}

type ColorGroup = {
  title: string
  swatches: ColorSwatch[]
}

const colorGroups: ColorGroup[] = [
  {
    title: 'Surfaces',
    swatches: [
      { token: 'surface-100', cssVar: '--color-surface-100', light: '#f4f8f4', dark: '#0f1a14' },
      { token: 'surface-200', cssVar: '--color-surface-200', light: '#ffffff', dark: '#16261d' },
      { token: 'surface-300', cssVar: '--color-surface-300', light: '#e6f0e7', dark: '#1e3327' },
    ],
  },
  {
    title: 'Border',
    swatches: [{ token: 'border', cssVar: '--color-border', light: '#cfe0d1', dark: '#2c4634' }],
  },
  {
    title: 'Text',
    swatches: [
      { token: 'ink', cssVar: '--color-ink', light: '#12261a', dark: '#e6f2e9' },
      { token: 'ink-muted', cssVar: '--color-ink-muted', light: '#4c6356', dark: '#9fb8a8' },
    ],
  },
  {
    title: 'Brand',
    swatches: [
      { token: 'brand', cssVar: '--color-brand', light: '#1f6b45', dark: '#6fcf97' },
      { token: 'brand-strong', cssVar: '--color-brand-strong', light: '#154d32', dark: '#8fdcae' },
      { token: 'brand-light', cssVar: '--color-brand-light', light: '#a9dcb8', dark: '#3a5c47' },
    ],
  },
  {
    title: 'Status',
    swatches: [
      { token: 'success', cssVar: '--color-success', light: '#1f8a4c', dark: '#4ade80' },
      { token: 'warning', cssVar: '--color-warning', light: '#a06a1f', dark: '#f0b13d' },
      { token: 'danger', cssVar: '--color-danger', light: '#b0442f', dark: '#f2856c' },
    ],
  },
  {
    title: 'Focus',
    swatches: [
      {
        token: 'focus-ring',
        cssVar: '--color-focus-ring',
        light: '#1f6b45 (= brand)',
        dark: '#6fcf97 (= brand)',
      },
    ],
  },
]

const typeRows: { variant: TextVariant; sample: string; spec: string }[] = [
  {
    variant: 'display',
    sample: 'PontoFlex — Design System',
    spec: '28px / 34px / peso 600 / sans',
  },
  {
    variant: 'heading-lg',
    sample: 'Resumo da jornada',
    spec: '22px / 28px / peso 600 / sans',
  },
  {
    variant: 'heading-md',
    sample: 'Batidas do dia',
    spec: '17px / 24px / peso 600 / sans',
  },
  {
    variant: 'body',
    sample: 'Texto padrão de parágrafos, descrições e conteúdo de formulários.',
    spec: '14px / 21px / peso 400 / sans',
  },
  {
    variant: 'body-strong',
    sample: 'Texto padrão em destaque, para rótulos e valores importantes.',
    spec: '14px / 21px / peso 600 / sans',
  },
  {
    variant: 'caption',
    sample: 'Legenda auxiliar, timestamps e textos de apoio.',
    spec: '12px / 17px / peso 400 / sans',
  },
  {
    variant: 'data-mono',
    sample: '08:02:14 · -23.5505, -46.6333',
    spec: '13px / 19px / peso 400 / mono',
  },
]

const spacingRows: { token: string; className: string; px: string }[] = [
  { token: 'space-2', className: 'w-2 h-2', px: '8px' },
  { token: 'space-4', className: 'w-4 h-4', px: '16px' },
  { token: 'space-6', className: 'w-6 h-6', px: '24px' },
  { token: 'space-8', className: 'w-8 h-8', px: '32px' },
]

const radiusRows: { token: string; className: string; px: string; usage: string }[] = [
  { token: 'radius-sm', className: 'rounded-sm', px: '6px', usage: 'Badges, chips de status' },
  {
    token: 'radius-md',
    className: 'rounded-md',
    px: '12px',
    usage: 'Botões, campos de formulário, cards',
  },
  { token: 'radius-lg', className: 'rounded-lg', px: '20px', usage: 'Painéis e modais' },
]

function Section({
  title,
  children,
}: {
  title: string
  children: ReactNode
}) {
  return (
    <section className="flex flex-col gap-4">
      <Text as="h2" variant="heading-lg">
        {title}
      </Text>
      {children}
    </section>
  )
}

function ColorSwatchCard({ swatch }: { swatch: ColorSwatch }) {
  return (
    <div className="flex flex-col gap-2">
      <div
        className="h-16 w-full rounded-md border border-border"
        style={{ backgroundColor: `var(${swatch.cssVar})` }}
      />
      <Text variant="body-strong">{swatch.token}</Text>
      <Text variant="caption" muted>
        Light {swatch.light} · Dark {swatch.dark}
      </Text>
    </div>
  )
}

export function StyleGuide() {
  return (
    <div className="min-h-screen bg-surface-100 text-ink p-8">
      <div className="mx-auto flex max-w-5xl flex-col gap-8">
        {/* a. Header */}
        <header className="flex items-start justify-between gap-4">
          <div className="flex flex-col gap-2">
            <Text as="h1" variant="display">
              PontoFlex — Design System
            </Text>
            <Text variant="body" muted>
              Cores, tipografia, espaçamento, raio e componentes de UI usados no PontoFlex.
            </Text>
          </div>
          <ThemeToggle className="shrink-0" />
        </header>

        {/* b. Colors */}
        <Section title="Cores">
          <div className="flex flex-col gap-6">
            {colorGroups.map((group) => (
              <div key={group.title} className="flex flex-col gap-3">
                <Text variant="body-strong">{group.title}</Text>
                <div className="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4">
                  {group.swatches.map((swatch) => (
                    <ColorSwatchCard key={swatch.token} swatch={swatch} />
                  ))}
                </div>
              </div>
            ))}
          </div>
        </Section>

        {/* c. Typography */}
        <Section title="Tipografia">
          <div className="flex flex-col gap-4">
            {typeRows.map((row) => (
              <div
                key={row.variant}
                className="flex flex-col gap-1 border-b border-border pb-4 last:border-b-0 last:pb-0"
              >
                <Text variant={row.variant}>{row.sample}</Text>
                <Text variant="caption" muted>
                  {row.variant} — {row.spec}
                </Text>
              </div>
            ))}
          </div>
        </Section>

        {/* d. Spacing */}
        <Section title="Espaçamento">
          <div className="flex flex-col gap-6">
            <div className="flex flex-wrap items-end gap-6">
              {spacingRows.map((row) => (
                <div key={row.token} className="flex flex-col items-center gap-2">
                  <div className={`${row.className} rounded-sm bg-brand`} />
                  <Text variant="caption" muted>
                    {row.token} ({row.px})
                  </Text>
                </div>
              ))}
            </div>
            <Card className="p-4">
              <Text variant="body-strong">Exemplo de Card</Text>
              <Text variant="body" muted>
                Este card usa o padding padrão do componente (p-4 = space-4 = 16px) para
                demonstrar o token em contexto.
              </Text>
            </Card>
          </div>
        </Section>

        {/* e. Radius */}
        <Section title="Raio de borda">
          <div className="flex flex-wrap gap-6">
            {radiusRows.map((row) => (
              <div key={row.token} className="flex flex-col items-center gap-2">
                <div
                  className={`h-20 w-20 border border-border bg-surface-300 ${row.className}`}
                />
                <Text variant="body-strong">{row.token}</Text>
                <Text variant="caption" muted>
                  {row.px} — {row.usage}
                </Text>
              </div>
            ))}
          </div>
        </Section>

        {/* f. Components */}
        <Section title="Componentes">
          <div className="flex flex-col gap-6">
            <div className="flex flex-col gap-3">
              <Text variant="body-strong">Botões</Text>
              <div className="flex flex-wrap items-center gap-3">
                <Button variant="primary">Primário</Button>
                <Button variant="secondary">Secundário</Button>
                <Button variant="danger">Perigo</Button>
                <Button variant="primary" disabled>
                  Primário desabilitado
                </Button>
              </div>
            </div>

            <div className="flex flex-col gap-3">
              <Text variant="body-strong">Card</Text>
              <Card className="max-w-sm">
                <Text variant="heading-md">Jornada de hoje</Text>
                <Text variant="body" muted>
                  Conteúdo de exemplo dentro de um Card, ilustrando padding, borda e raio
                  padrão do componente.
                </Text>
              </Card>
            </div>

            <div className="flex flex-col gap-3">
              <Text variant="body-strong">Chips de status</Text>
              <div className="flex flex-wrap items-center gap-3">
                <StatusChip status="conforme" />
                <StatusChip status="pendente" />
                <StatusChip status="nao-conforme" />
              </div>
            </div>
          </div>
        </Section>

        {/* g. Footer */}
        <footer className="flex flex-col gap-2 border-t border-border pt-6">
          <Text variant="caption" muted>
            Fonte da verdade dos tokens: docs/design-system.md (raiz do repositório).
          </Text>
          <Text variant="caption" muted>
            A tabela de espelho de ponto e o formulário de registro de ponto por
            geolocalização são trabalho futuro documentado e não foram construídos neste
            ciclo porque o backend ainda não os suporta.
          </Text>
        </footer>
      </div>
    </div>
  )
}

export default StyleGuide
