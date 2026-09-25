import { useCallback, useState } from 'react'
import { post } from './api'

export type Sessao = {
  token: string
  email: string
  roles: string[]
  expiraEm: number
}

type AutenticacaoResponse = {
  token: string
  tipo: string
  expiraEmSegundos: number
  email: string
  roles: string[]
}

export type RegistroRequest = {
  nome: string
  cpf: string
  matricula: string
  dataAdmissao: string
  email: string
  senha: string
}

const STORAGE_KEY = 'pontoflex-sessao'

function lerSessao(): Sessao | null {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    if (!raw) return null
    const sessao = JSON.parse(raw) as Sessao
    return sessao.expiraEm > Date.now() ? sessao : null
  } catch {
    return null
  }
}

export async function login(email: string, senha: string): Promise<Sessao> {
  const resposta = await post<AutenticacaoResponse>('/api/auth/login', { email, senha })
  const sessao: Sessao = {
    token: resposta.token,
    email: resposta.email,
    roles: resposta.roles,
    expiraEm: Date.now() + resposta.expiraEmSegundos * 1000,
  }
  localStorage.setItem(STORAGE_KEY, JSON.stringify(sessao))
  return sessao
}

export function registrar(request: RegistroRequest): Promise<unknown> {
  return post('/api/auth/registrar', request)
}

export function useSessao(): [Sessao | null, (sessao: Sessao) => void, () => void] {
  const [sessao, setSessao] = useState<Sessao | null>(lerSessao)

  const sair = useCallback(() => {
    localStorage.removeItem(STORAGE_KEY)
    setSessao(null)
  }, [])

  return [sessao, setSessao, sair]
}
