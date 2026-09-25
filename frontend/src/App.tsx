import { useEffect } from 'react'
import { useSessao } from './lib/auth'
import { navegar, useRota } from './lib/router'
import { Cadastro } from './pages/Cadastro'
import { Inicio } from './pages/Inicio'
import { Login } from './pages/Login'
import { StyleGuide } from './pages/StyleGuide'

function App() {
  const rota = useRota()
  const [sessao, entrar, sair] = useSessao()

  // Redireciona conforme o estado de autenticacao: logado nao ve login/cadastro, deslogado nao ve /inicio.
  useEffect(() => {
    if (sessao && (rota === '/login' || rota === '/cadastro')) navegar('/inicio')
    if (!sessao && rota === '/inicio') navegar('/login')
  }, [sessao, rota])

  if (rota === '/design-system') return <StyleGuide />
  if (sessao) return <Inicio sessao={sessao} onSair={sair} />
  if (rota === '/cadastro') return <Cadastro onEntrar={entrar} />
  return <Login onEntrar={entrar} />
}

export default App
