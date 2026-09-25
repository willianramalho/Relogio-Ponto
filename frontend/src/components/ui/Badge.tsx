import type { ComponentPropsWithoutRef } from 'react'
import clsx from 'clsx'

export type StatusChipStatus = 'conforme' | 'pendente' | 'nao-conforme'

export interface StatusChipProps extends ComponentPropsWithoutRef<'span'> {
  status: StatusChipStatus
  label?: string
}

const DEFAULT_LABELS: Record<StatusChipStatus, string> = {
  conforme: 'Conforme',
  pendente: 'Pendente',
  'nao-conforme': 'Não conforme',
}

// NOTE: bg-{success,warning,danger}/15 was verified against a production build
// (npm run build -> dist/assets/*.css) and produced no CSS rule at all - the
// tokens are plain `var(--color-x)` custom properties, not the RGB-channel
// pattern Tailwind needs to synthesize an opacity modifier, so the utility is
// silently dropped. Falling back to the solid token colors with light text
// per the design doc's documented fallback.
const STATUS_CLASSES: Record<StatusChipStatus, string> = {
  conforme: 'bg-success text-surface-200',
  pendente: 'bg-warning text-surface-200',
  'nao-conforme': 'bg-danger text-surface-200',
}

export function StatusChip({ status, label, className, ...props }: StatusChipProps) {
  return (
    <span
      className={clsx(
        'inline-flex items-center rounded-sm px-2 py-0.5 text-caption font-semibold',
        'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-focus-ring focus-visible:ring-offset-2',
        STATUS_CLASSES[status],
        className,
      )}
      {...props}
    >
      {label ?? DEFAULT_LABELS[status]}
    </span>
  )
}
