import clsx from 'clsx';
import React from 'react';

type CardProps = React.ComponentPropsWithoutRef<'div'>;

const baseClasses = 'bg-surface-200 border border-border rounded-md p-4';

export const Card = ({ className, ...props }: CardProps) => {
  return <div className={clsx(baseClasses, className)} {...props} />;
};
