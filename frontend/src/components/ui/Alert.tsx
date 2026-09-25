import clsx from 'clsx'
import type { ReactNode } from 'react'

type AlertProps = {
  tipo?: 'erro' | 'sucesso'
  children: ReactNode
}

export function Alert({ tipo = 'erro', children }: AlertProps) {
  return (
    <div
      role={tipo === 'erro' ? 'alert' : 'status'}
      className={clsx(
        'rounded-md border px-3 py-2 text-body',
        tipo === 'erro' ? 'border-danger text-danger' : 'border-success text-success',
      )}
    >
      {children}
    </div>
  )
}
