import clsx from 'clsx'
import { useId, type ComponentPropsWithoutRef } from 'react'

type TextFieldProps = ComponentPropsWithoutRef<'input'> & {
  label: string
  erro?: string
  ajuda?: string
}

export function TextField({ label, erro, ajuda, className, id, ...props }: TextFieldProps) {
  const gerado = useId()
  const inputId = id ?? gerado
  const descricaoId = `${inputId}-descricao`
  const descricao = erro ?? ajuda

  return (
    <div className={clsx('flex flex-col gap-1', className)}>
      <label htmlFor={inputId} className="text-body-strong text-ink">
        {label}
      </label>
      <input
        id={inputId}
        aria-invalid={erro ? true : undefined}
        aria-describedby={descricao ? descricaoId : undefined}
        className={clsx(
          'rounded-md border bg-surface-200 px-3 py-2 text-body text-ink placeholder:text-ink-muted',
          'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-focus-ring',
          erro ? 'border-danger' : 'border-border',
        )}
        {...props}
      />
      {descricao && (
        <span id={descricaoId} className={clsx('text-caption', erro ? 'text-danger' : 'text-ink-muted')}>
          {descricao}
        </span>
      )}
    </div>
  )
}
