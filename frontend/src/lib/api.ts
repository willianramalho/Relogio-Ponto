export type CampoInvalido = {
  campo: string
  mensagem: string
}

type ErroResponse = {
  codigo: string
  mensagem: string
  camposInvalidos?: CampoInvalido[]
}

export class ApiError extends Error {
  readonly status: number
  readonly codigo: string
  readonly camposInvalidos: CampoInvalido[]

  constructor(status: number, erro: ErroResponse) {
    super(erro.mensagem)
    this.status = status
    this.codigo = erro.codigo
    this.camposInvalidos = erro.camposInvalidos ?? []
  }
}

export async function post<T>(path: string, body: unknown): Promise<T> {
  let response: Response
  try {
    response = await fetch(path, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    })
  } catch {
    throw new ApiError(0, {
      codigo: 'SERVIDOR_INDISPONIVEL',
      mensagem: 'Não foi possível conectar ao servidor. Verifique se a API está rodando.',
    })
  }

  const data = await response.json().catch(() => null)
  if (!response.ok) {
    throw new ApiError(
      response.status,
      data ?? { codigo: 'ERRO_INESPERADO', mensagem: 'Ocorreu um erro inesperado. Tente novamente.' },
    )
  }
  return data as T
}
