import { useEffect, useState } from 'react'

export type Rota = '/login' | '/cadastro' | '/inicio' | '/design-system'

const ROTAS: Rota[] = ['/login', '/cadastro', '/inicio', '/design-system']

function rotaAtual(): Rota {
  const hash = window.location.hash.replace(/^#/, '')
  return ROTAS.includes(hash as Rota) ? (hash as Rota) : '/login'
}

export function navegar(rota: Rota): void {
  window.location.hash = rota
}

export function useRota(): Rota {
  const [rota, setRota] = useState<Rota>(rotaAtual)

  useEffect(() => {
    const onChange = () => setRota(rotaAtual())
    window.addEventListener('hashchange', onChange)
    return () => window.removeEventListener('hashchange', onChange)
  }, [])

  return rota
}
