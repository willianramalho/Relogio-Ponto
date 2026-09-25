# PontoFlex — Design System (para o Claude Code)

> Documento gerado a partir do design system criado no Claude (Cowork) para o
> projeto PontoFlex. Repositório fonte: `Relogio Ponto`, hoje apenas API
> (Java/Spring Boot) — sem front-end ainda. Estes tokens são o ponto de
> partida visual para quando a UI começar a ser construída; **não foram
> extraídos do código**, foram definidos para o domínio do produto (sistema
> de ponto eletrônico / RH) com a direção pedida pelo Willian: visual leve e
> agradável, em tons de verde escuro e verde claro.

## Personalidade

Leve e agradável, não burocrático: mesmo sendo uma ferramenta de RH/operações
onde clareza importa, a proposta é fugir do visual denso e frio de ERP
tradicional (TOTVS, SAP, Senior). Verde escuro como cor de marca e verde
claro como tom de apoio — paleta orgânica que remete a crescimento, saldo
positivo, jornada em dia. Cantos generosamente arredondados e bastante
espaço em branco.

## Tokens de cor

Dois temas, `light` e `dark`. Todos os pares texto/fundo mantêm contraste
mínimo de 4.5:1 (3:1 para elementos grandes/bordas) nos dois temas.

| Token | Light | Dark | Uso |
|---|---|---|---|
| `surface-100` | `#f4f8f4` | `#0f1a14` | Fundo de página |
| `surface-200` | `#ffffff` | `#16261d` | Fundo de cards, tabelas e painéis |
| `surface-300` | `#e6f0e7` | `#1e3327` | Fundo de campos, linhas alternadas de tabela, hover sutil |
| `border` | `#cfe0d1` | `#2c4634` | Bordas de cards, tabelas e divisores |
| `ink` | `#12261a` | `#e6f2e9` | Texto principal sobre surface-100/200 |
| `ink-muted` | `#4c6356` | `#9fb8a8` | Texto secundário, legendas, timestamps |
| `brand` | `#1f6b45` | `#6fcf97` | Verde escuro (marca) — ações principais, links, ícones ativos |
| `brand-strong` | `#154d32` | `#8fdcae` | Hover/active de elementos brand, texto sobre brand claro |
| `brand-light` | `#a9dcb8` | `#3a5c47` | Verde claro — realces suaves, fundos de destaque, ilustrações |
| `success` | `#1f8a4c` | `#4ade80` | Jornada conforme, batida validada, saldo positivo de banco de horas |
| `warning` | `#a06a1f` | `#f0b13d` | Batida de baixa confiança (GPS impreciso), pendências de revisão |
| `danger` | `#b0442f` | `#f2856c` | Não conformidade CLT, batida rejeitada, erro de validação |
| `focus-ring` | = `brand` | = `brand` | Anel de foco em elementos interativos (teclado) |

`success` é intencionalmente próximo de `brand`: reforça que "verde" é o
estado normal/bom do sistema.

## Tipografia

- **Família padrão (`sans`)**: fonte do sistema operacional —
  `-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif`.
  Sem arquivo de fonte próprio ainda; trocar aqui quando uma fonte de marca
  for escolhida.
- **Família de dados (`mono`)**: `"SFMono-Regular", Menlo, Consolas, "Liberation Mono", monospace`
  — reservada para dados tabulares (horários, IDs, coordenadas de geofence),
  comuns nas telas de espelho de ponto.

| Estilo | Tamanho | Altura de linha | Peso | Família |
|---|---|---|---|---|
| `display` | 28px | 34px | 600 | sans |
| `heading-lg` | 22px | 28px | 600 | sans |
| `heading-md` | 17px | 24px | 600 | sans |
| `body` | 14px | 21px | 400 | sans |
| `body-strong` | 14px | 21px | 600 | sans |
| `caption` | 12px | 17px | 400 | sans |
| `data-mono` | 13px | 19px | 400 | mono |

## Espaçamento

| Token | Valor | Uso |
|---|---|---|
| `space-2` | 8px | Espaçamento entre ícone e rótulo, padding vertical compacto |
| `space-4` | 16px | Padding padrão de cards e campos de formulário |
| `space-6` | 24px | Espaçamento entre seções dentro de um painel |
| `space-8` | 32px | Espaçamento entre blocos principais da página |

## Raio de borda

Raios mais generosos que o padrão de ERP corporativo, para reforçar o
visual leve e amigável pedido.

| Token | Valor | Uso |
|---|---|---|
| `radius-sm` | 6px | Badges, chips de status (conforme/pendente/não conforme) |
| `radius-md` | 12px | Botões, campos de formulário, cards |
| `radius-lg` | 20px | Painéis e modais |

## tokens.json (formato bruto)

Bloco completo, caso o Claude Code prefira consumir os tokens como dado
estruturado (ex.: para gerar CSS custom properties ou um tema Tailwind):

```json
{
  "name": "PontoFlex",
  "version": 1,
  "color": {
    "themes": [
      { "id": "light", "name": "Light" },
      { "id": "dark", "name": "Dark" }
    ],
    "tokens": [
      { "name": "surface-100", "value": { "light": "#f4f8f4", "dark": "#0f1a14" }, "usage": "Fundo de página." },
      { "name": "surface-200", "value": { "light": "#ffffff", "dark": "#16261d" }, "usage": "Fundo de cards, tabelas e painéis." },
      { "name": "surface-300", "value": { "light": "#e6f0e7", "dark": "#1e3327" }, "usage": "Fundo de campos, linhas alternadas de tabela, hover sutil." },
      { "name": "border", "value": { "light": "#cfe0d1", "dark": "#2c4634" }, "usage": "Bordas de cards, tabelas e divisores." },
      { "name": "ink", "value": { "light": "#12261a", "dark": "#e6f2e9" }, "usage": "Texto principal sobre surface-100/200." },
      { "name": "ink-muted", "value": { "light": "#4c6356", "dark": "#9fb8a8" }, "usage": "Texto secundário, legendas, timestamps." },
      { "name": "brand", "value": { "light": "#1f6b45", "dark": "#6fcf97" }, "usage": "Verde escuro (marca) — ações principais, links, ícones ativos." },
      { "name": "brand-strong", "value": { "light": "#154d32", "dark": "#8fdcae" }, "usage": "Hover/active de elementos brand, texto sobre brand claro." },
      { "name": "brand-light", "value": { "light": "#a9dcb8", "dark": "#3a5c47" }, "usage": "Verde claro — realces suaves, fundos de destaque, ilustrações." },
      { "name": "success", "value": { "light": "#1f8a4c", "dark": "#4ade80" }, "usage": "Jornada conforme, batida validada, saldo positivo de banco de horas." },
      { "name": "warning", "value": { "light": "#a06a1f", "dark": "#f0b13d" }, "usage": "Batida de baixa confiança (GPS impreciso), pendências de revisão." },
      { "name": "danger", "value": { "light": "#b0442f", "dark": "#f2856c" }, "usage": "Não conformidade CLT, batida rejeitada, erro de validação." },
      { "name": "focus-ring", "value": "{brand}", "usage": "Anel de foco em elementos interativos (teclado)." }
    ]
  },
  "type": {
    "fonts": [],
    "families": {
      "sans": "-apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif",
      "mono": "\"SFMono-Regular\", Menlo, Consolas, \"Liberation Mono\", monospace"
    },
    "groups": [
      { "name": "Display", "family": "sans", "styles": [
        { "name": "display", "fontSize": "28px", "lineHeight": "34px", "fontWeight": 600 }
      ]},
      { "name": "Heading", "family": "sans", "styles": [
        { "name": "heading-lg", "fontSize": "22px", "lineHeight": "28px", "fontWeight": 600 },
        { "name": "heading-md", "fontSize": "17px", "lineHeight": "24px", "fontWeight": 600 }
      ]},
      { "name": "Text", "family": "sans", "styles": [
        { "name": "body", "fontSize": "14px", "lineHeight": "21px", "fontWeight": 400 },
        { "name": "body-strong", "fontSize": "14px", "lineHeight": "21px", "fontWeight": 600 },
        { "name": "caption", "fontSize": "12px", "lineHeight": "17px", "fontWeight": 400 }
      ]},
      { "name": "Data", "family": "mono", "styles": [
        { "name": "data-mono", "fontSize": "13px", "lineHeight": "19px", "fontWeight": 400 }
      ]}
    ]
  },
  "spacing": {
    "tokens": [
      { "name": "space-2", "value": "8px", "usage": "Espaçamento entre ícone e rótulo, padding vertical compacto." },
      { "name": "space-4", "value": "16px", "usage": "Padding padrão de cards e campos de formulário." },
      { "name": "space-6", "value": "24px", "usage": "Espaçamento entre seções dentro de um painel." },
      { "name": "space-8", "value": "32px", "usage": "Espaçamento entre blocos principais da página." }
    ]
  },
  "radius": {
    "tokens": [
      { "name": "radius-sm", "value": "6px", "usage": "Badges, chips de status (conforme/pendente/não conforme)." },
      { "name": "radius-md", "value": "12px", "usage": "Botões, campos de formulário, cards." },
      { "name": "radius-lg", "value": "20px", "usage": "Painéis e modais." }
    ]
  }
}
```

## O que ainda falta (próximos passos)

Este sistema **não foi extraído do repositório `Relogio Ponto` (PontoFlex)**
— nada nele tem CSS, tokens, fontes, logos ou componentes ainda; o projeto
hoje é apenas a API REST (`src/main/java/com/pontoflex/{domain,application,
infrastructure,web}`). Quando o front-end começar a ser construído, dá para
usar este documento (ou o design system original no Claude) como base e, se
quiser, re-sincronizar depois com o código real (tokens de CSS/Tailwind,
fontes da marca, logo do PontoFlex, componentes como chips de status
conforme/pendente/não conforme, tabela de espelho de ponto e formulário de
registro de ponto por geolocalização).

---

Design system completo (com a capa visual) disponível em:
https://claude.ai/artifact/QCo6w5UH8hDgLpgCZXMHQ3
