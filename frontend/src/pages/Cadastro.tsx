import { useState, type FormEvent } from 'react'
import { AuthLayout } from '../components/auth/AuthLayout'
import { Alert } from '../components/ui/Alert'
import { Button } from '../components/ui/Button'
import { TextField } from '../components/ui/TextField'
import { ApiError } from '../lib/api'
import { login, registrar, type RegistroRequest, type Sessao } from '../lib/auth'

type CadastroProps = {
  onEntrar: (sessao: Sessao) => void
}

type Campos = RegistroRequest & { confirmacaoSenha: string }
type Erros = Partial<Record<keyof Campos, string>>

const VAZIO: Campos = {
  nome: '',
  cpf: '',
  matricula: '',
  dataAdmissao: '',
  email: '',
  senha: '',
  confirmacaoSenha: '',
}

function somenteDigitos(valor: string): string {
  return valor.replace(/\D/g, '').slice(0, 11)
}

function formatarCpf(valor: string): string {
  const d = somenteDigitos(valor)
  return d
    .replace(/^(\d{3})(\d)/, '$1.$2')
    .replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3')
    .replace(/\.(\d{3})(\d{1,2})$/, '.$1-$2')
}

function hojeIso(): string {
  const agora = new Date()
  return new Date(agora.getTime() - agora.getTimezoneOffset() * 60000).toISOString().slice(0, 10)
}

// Espelha as validacoes de RegistroRequest no backend para dar retorno imediato.
function validar(campos: Campos): Erros {
  const erros: Erros = {}
  if (!campos.nome.trim()) erros.nome = 'Informe seu nome completo.'
  if (somenteDigitos(campos.cpf).length !== 11) erros.cpf = 'O CPF deve ter 11 dígitos.'
  if (!campos.matricula.trim()) erros.matricula = 'Informe sua matrícula.'
  if (!campos.dataAdmissao) erros.dataAdmissao = 'Informe a data de admissão.'
  else if (campos.dataAdmissao > hojeIso()) erros.dataAdmissao = 'A data de admissão não pode ser futura.'
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(campos.email.trim())) erros.email = 'Informe um email válido.'
  if (campos.senha.length < 8) erros.senha = 'A senha deve ter pelo menos 8 caracteres.'
  if (campos.confirmacaoSenha !== campos.senha) erros.confirmacaoSenha = 'As senhas não coincidem.'
  return erros
}

export function Cadastro({ onEntrar }: CadastroProps) {
  const [campos, setCampos] = useState<Campos>(VAZIO)
  const [erros, setErros] = useState<Erros>({})
  const [erroGeral, setErroGeral] = useState<string | null>(null)
  const [enviando, setEnviando] = useState(false)

  function atualizar<K extends keyof Campos>(campo: K, valor: Campos[K]) {
    setCampos((atual) => ({ ...atual, [campo]: valor }))
    setErros((atual) => ({ ...atual, [campo]: undefined }))
  }

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setErroGeral(null)

    const errosLocais = validar(campos)
    setErros(errosLocais)
    if (Object.keys(errosLocais).length > 0) return

    const request: RegistroRequest = {
      nome: campos.nome.trim(),
      cpf: somenteDigitos(campos.cpf),
      matricula: campos.matricula.trim(),
      dataAdmissao: campos.dataAdmissao,
      email: campos.email.trim(),
      senha: campos.senha,
    }

    setEnviando(true)
    try {
      await registrar(request)
      onEntrar(await login(request.email, request.senha))
    } catch (e) {
      if (e instanceof ApiError && e.camposInvalidos.length > 0) {
        setErros(Object.fromEntries(e.camposInvalidos.map((c) => [c.campo, c.mensagem])))
        setErroGeral('Corrija os campos destacados.')
      } else {
        setErroGeral(e instanceof ApiError ? e.message : 'Não foi possível concluir o cadastro.')
      }
    } finally {
      setEnviando(false)
    }
  }

  return (
    <AuthLayout
      titulo="Criar conta"
      subtitulo="Cadastre-se como colaborador para começar a registrar o ponto."
      rodape={
        <>
          Já tem conta?{' '}
          <a href="#/login" className="text-brand font-semibold hover:underline">
            Entrar
          </a>
        </>
      }
    >
      <form onSubmit={handleSubmit} className="flex flex-col gap-4" noValidate>
        {erroGeral && <Alert>{erroGeral}</Alert>}
        <TextField
          label="Nome completo"
          autoComplete="name"
          autoFocus
          maxLength={150}
          value={campos.nome}
          erro={erros.nome}
          onChange={(e) => atualizar('nome', e.target.value)}
        />
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
          <TextField
            label="CPF"
            inputMode="numeric"
            placeholder="000.000.000-00"
            value={formatarCpf(campos.cpf)}
            erro={erros.cpf}
            onChange={(e) => atualizar('cpf', somenteDigitos(e.target.value))}
          />
          <TextField
            label="Matrícula"
            maxLength={30}
            value={campos.matricula}
            erro={erros.matricula}
            onChange={(e) => atualizar('matricula', e.target.value)}
          />
        </div>
        <TextField
          label="Data de admissão"
          type="date"
          max={hojeIso()}
          value={campos.dataAdmissao}
          erro={erros.dataAdmissao}
          onChange={(e) => atualizar('dataAdmissao', e.target.value)}
        />
        <TextField
          label="Email"
          type="email"
          autoComplete="email"
          maxLength={150}
          value={campos.email}
          erro={erros.email}
          onChange={(e) => atualizar('email', e.target.value)}
        />
        <TextField
          label="Senha"
          type="password"
          autoComplete="new-password"
          maxLength={100}
          ajuda="Mínimo de 8 caracteres."
          value={campos.senha}
          erro={erros.senha}
          onChange={(e) => atualizar('senha', e.target.value)}
        />
        <TextField
          label="Confirmar senha"
          type="password"
          autoComplete="new-password"
          maxLength={100}
          value={campos.confirmacaoSenha}
          erro={erros.confirmacaoSenha}
          onChange={(e) => atualizar('confirmacaoSenha', e.target.value)}
        />
        <Button type="submit" disabled={enviando}>
          {enviando ? 'Cadastrando…' : 'Criar conta'}
        </Button>
      </form>
    </AuthLayout>
  )
}
