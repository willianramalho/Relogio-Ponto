import clsx from 'clsx';
import React from 'react';

type ButtonProps = React.ComponentPropsWithoutRef<'button'> & {
  variant?: 'primary' | 'secondary' | 'danger';
};

const baseClasses =
  'rounded-md px-4 py-2 text-body-strong transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-focus-ring focus-visible:ring-offset-2 focus-visible:ring-offset-surface-100 disabled:opacity-50 disabled:pointer-events-none';

const variantClasses: Record<NonNullable<ButtonProps['variant']>, string> = {
  primary: 'bg-brand text-surface-200 hover:bg-brand-strong',
  secondary: 'bg-surface-200 text-ink border border-border hover:bg-surface-300',
  danger: 'bg-danger text-surface-200 hover:brightness-90',
};

export const Button = ({ variant = 'primary', className, ...props }: ButtonProps) => {
  return (
    <button className={clsx(baseClasses, variantClasses[variant], className)} {...props} />
  );
};
