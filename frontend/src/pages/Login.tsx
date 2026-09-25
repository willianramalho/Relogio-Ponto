import { useState, type FormEvent } from 'react'
import { AuthLayout } from '../components/auth/AuthLayout'
import { Alert } from '../components/ui/Alert'
import { Button } from '../components/ui/Button'
import { TextField } from '../components/ui/TextField'
import { ApiError } from '../lib/api'
import { login, type Sessao } from '../lib/auth'

type LoginProps = {
  onEntrar: (sessao: Sessao) => void
}

export function Login({ onEntrar }: LoginProps) {
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [erro, setErro] = useState<string | null>(null)
  const [enviando, setEnviando] = useState(false)

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setErro(null)
    setEnviando(true)
    try {
      onEntrar(await login(email.trim(), senha))
    } catch (e) {
      setErro(e instanceof ApiError ? e.message : 'Não foi possível entrar. Tente novamente.')
    } finally {
      setEnviando(false)
    }
  }

  return (
    <AuthLayout
      titulo="Entrar"
      subtitulo="Acesse sua conta para registrar o ponto."
      rodape={
        <>
          Ainda não tem conta?{' '}
          <a href="#/cadastro" className="text-brand font-semibold hover:underline">
            Cadastre-se
          </a>
        </>
      }
    >
      <form onSubmit={handleSubmit} className="flex flex-col gap-4" noValidate>
        {erro && <Alert>{erro}</Alert>}
        <TextField
          label="Email"
          type="email"
          autoComplete="email"
          required
          autoFocus
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <TextField
          label="Senha"
          type="password"
          autoComplete="current-password"
          required
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
        <Button type="submit" disabled={enviando || !email || !senha}>
          {enviando ? 'Entrando…' : 'Entrar'}
        </Button>
      </form>
    </AuthLayout>
  )
}
