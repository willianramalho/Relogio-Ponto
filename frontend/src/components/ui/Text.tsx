import type { ComponentPropsWithoutRef, ElementType } from 'react'
import clsx from 'clsx'

type TextVariant =
  | 'display'
  | 'heading-lg'
  | 'heading-md'
  | 'body'
  | 'body-strong'
  | 'caption'
  | 'data-mono'

type TextProps = ComponentPropsWithoutRef<'p'> & {
  variant?: TextVariant
  as?: ElementType
  muted?: boolean
}

// Tailwind's static content scanner needs literal class names in the source -
// a template literal like `text-${variant}` is invisible to it, so the
// variant classes are spelled out here instead of interpolated.
const VARIANT_CLASSES: Record<TextVariant, string> = {
  display: 'text-display',
  'heading-lg': 'text-heading-lg',
  'heading-md': 'text-heading-md',
  body: 'text-body',
  'body-strong': 'text-body-strong',
  caption: 'text-caption',
  'data-mono': 'text-data-mono font-mono',
}

export function Text({
  variant = 'body',
  as,
  muted = false,
  className,
  children,
  ...rest
}: TextProps) {
  const Component = as ?? 'p'

  return (
    <Component
      className={clsx(
        VARIANT_CLASSES[variant],
        muted ? 'text-ink-muted' : 'text-ink',
        className,
      )}
      {...rest}
    >
      {children}
    </Component>
  )
}
