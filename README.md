# Back-end do site Yasmim & Júnior

Back-end em **Java (Spring Boot)** para o site de casamento. Ele:

- Serve o próprio site (o HTML/CSS/JS ficam em `src/main/resources/static/index.html`);
- Guarda **fotos da galeria**, **lista de presentes** e **confirmações de presença (RSVP)** em um banco de dados local (H2, um arquivo em `./data/`);
- Exige **login (usuário e senha)** para adicionar/remover fotos, criar/editar/excluir presentes e ver a lista de quem confirmou presença;
- Deixa público (sem login) apenas: ver o site, ver a galeria, ver a lista de presentes e enviar uma confirmação de presença.

## Pré-requisitos

- **Java 17 ou superior** instalado (`java -version` no PowerShell para conferir).
- **Maven** instalado (`mvn -v` para conferir). Se não tiver, baixe em https://maven.apache.org/download.cgi e adicione ao PATH, ou abra o projeto em uma IDE (IntelliJ IDEA, VS Code com extensão Java, Eclipse) que já vem com Maven embutido.

## Como rodar

Na pasta `casamento-backend`, no PowerShell:

```powershell
mvn spring-boot:run
```

Depois abra no navegador: **http://localhost:8080**

O site já abre funcionando, com a galeria e a lista de presentes carregadas do banco.

## Login do admin (o casal)

Usuário e senha padrão (defina os seus antes de publicar o site!):

- Usuário: `noivos`
- Senha: `trocar123`

Para trocar, você tem duas opções:

**Opção 1 — variáveis de ambiente (recomendado, não fica gravado no código):**

```powershell
$env:ADMIN_USERNAME = "seu_usuario"
$env:ADMIN_PASSWORD = "sua_senha_forte"
mvn spring-boot:run
```

**Opção 2 — editar direto o arquivo** `src/main/resources/application.properties`:

```properties
app.admin.username=${ADMIN_USERNAME:seu_usuario}
app.admin.password=${ADMIN_PASSWORD:sua_senha_forte}
```

No site, clique em **"Entrar"** no canto superior direito, informe usuário e senha. Depois de logado aparecem:

- Botões de **+ Adicionar foto** e um **×** em cada foto da galeria;
- Botões **Editar/Excluir** em cada presente e **+ Adicionar presente**;
- Um link **"Painel admin"** no menu, com a lista de todas as confirmações de presença recebidas (nome, acompanhantes, resposta, recado e data), com opção de excluir.

### Pagamento dos presentes (Pix / cartão de crédito)

Ao editar ou criar um presente, você pode colar dois links, cada um gerado no site/app do seu banco:

- **Link de pagamento via Pix**
- **Link de pagamento no cartão de crédito**

Os dois campos são opcionais e independentes — se só tiver o link de um deles pronto, deixe o outro em branco. Os links precisam começar com `https://` (o back-end recusa qualquer outro formato).

Quando um convidado clica em **"Presentear"** em um presente, abre um modal perguntando se ele quer pagar via Pix ou cartão; cada opção abre, em uma nova aba, o link correspondente que você cadastrou. Se nenhum dos dois links estiver configurado ainda, o convidado vê uma mensagem avisando para falar diretamente com vocês.

Clique em **"Sair"** para encerrar a sessão.

## Onde ficam os dados

- `./data/wedding.mv.db` — banco de dados (fotos cadastradas, presentes, confirmações). Não apague se não quiser perder as informações.
- `./uploads/gallery/` — os arquivos de imagem enviados pela galeria.

Essas duas pastas são geradas automaticamente na primeira execução e estão no `.gitignore` (não sobem para o Git).

## Principais endpoints (API)

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/api/auth/login` | Público | Faz login (`{username, password}`) |
| POST | `/api/auth/logout` | Público | Encerra a sessão |
| GET | `/api/auth/status` | Público | Diz se a sessão atual está logada |
| GET | `/api/gallery` | Público | Lista as fotos |
| POST | `/api/gallery` | **Admin** | Envia uma foto (`multipart/form-data`, campo `photo`) |
| DELETE | `/api/gallery/{id}` | **Admin** | Remove uma foto |
| GET | `/api/gifts` | Público | Lista os presentes |
| POST | `/api/gifts` | **Admin** | Cria um presente |
| PUT | `/api/gifts/{id}` | **Admin** | Edita um presente |
| DELETE | `/api/gifts/{id}` | **Admin** | Remove um presente |
| POST | `/api/rsvp` | Público | Envia uma confirmação de presença |
| GET | `/api/rsvp` | **Admin** | Lista todas as confirmações |
| DELETE | `/api/rsvp/{id}` | **Admin** | Remove uma confirmação |

## Publicando o site (produção) — passo a passo no Railway

O projeto já vem pronto para isso: tem um `Dockerfile` (usado pela plataforma para buildar o Java sozinha) e o `application.properties` lê a porta e os caminhos de banco/uploads de variáveis de ambiente, para funcionar com um disco persistente.

### 1. Colocar o código no GitHub

O Railway builda a partir de um repositório Git. Dentro da pasta `casamento-backend`:

```powershell
git init
git add .
git commit -m "Site do casamento com backend"
```

Depois crie um repositório vazio em https://github.com/new (pode ser privado) e siga as instruções que o próprio GitHub mostra para "…or push an existing repository from the command line" (algo como):

```powershell
git remote add origin https://github.com/SEU_USUARIO/NOME_DO_REPO.git
git branch -M main
git push -u origin main
```

### 2. Criar o projeto no Railway

1. Crie uma conta em https://railway.app (dá para entrar com a conta do GitHub).
2. Clique em **New Project → Deploy from GitHub repo** e escolha o repositório que você acabou de criar.
3. O Railway detecta o `Dockerfile` e builda sozinho. Aguarde o primeiro deploy terminar (alguns minutos).

### 3. Adicionar um volume persistente (para não perder fotos e RSVPs)

1. Dentro do serviço criado, vá em **Settings → Volumes** (ou o botão de adicionar volume) e crie um volume, montando-o, por exemplo, no caminho `/data`.
2. Isso garante que o conteúdo de `/data` sobrevive a reinicializações e a novos deploys — sem isso, qualquer atualização apaga fotos e confirmações.

### 4. Configurar as variáveis de ambiente

Ainda no serviço, vá em **Variables** e adicione:

| Variável | Valor sugerido |
|---|---|
| `ADMIN_USERNAME` | um usuário só seu (não deixe o padrão `noivos`) |
| `ADMIN_PASSWORD` | uma senha forte (não deixe o padrão `trocar123`) |
| `DB_PATH` | `/data/db/wedding` |
| `UPLOAD_DIR` | `/data/uploads` |

(A variável `PORT` o próprio Railway já define sozinho — não precisa mexer nela.)

Depois de salvar as variáveis, o Railway reinicia o serviço automaticamente.

### 5. Acessar o site

O Railway gera uma URL pública sozinho, algo como `https://seu-projeto.up.railway.app` (em **Settings → Networking → Generate Domain**, caso ainda não tenha uma). Ela já vem com HTTPS pronto, sem você precisar configurar nada.

Se quiser usar seu próprio domínio (ex. `yasmimejunior.com.br`), em **Settings → Networking → Custom Domain** o Railway te dá um registro CNAME para cadastrar no painel onde você comprou o domínio; o certificado HTTPS é emitido automaticamente depois disso.

### E se eu preferir o Render em vez do Railway?

O processo é praticamente o mesmo: conectar o repositório do GitHub, deixar o Render detectar o `Dockerfile`, adicionar um **Disk** (equivalente ao volume do Railway, em **Settings → Disks**, montado por exemplo em `/data`) e cadastrar as mesmas variáveis de ambiente (`ADMIN_USERNAME`, `ADMIN_PASSWORD`, `DB_PATH=/data/db/wedding`, `UPLOAD_DIR=/data/uploads`). O Render também define `PORT` sozinho e gera HTTPS automático na URL pública.

### Atualizando o site depois de publicado

Sempre que quiser mudar algo no código, é só:

```powershell
git add .
git commit -m "descrição da mudança"
git push
```

O Railway/Render detectam o push e fazem o novo deploy sozinhos — como o banco e os uploads estão no volume/disk persistente, nada se perde.

## Observações

- O texto fixo do site (nomes, data, endereços da cerimônia/recepção, dress code) continua editável diretamente no arquivo `src/main/resources/static/index.html`, como no HTML original — não foi incluído no painel admin, pois não foi pedido. Se quiser que isso também vire editável pelo login, é só avisar.
- O arquivo original `casamento_front_casar_style.html` (na pasta `Projetos`) não foi alterado — a versão com login e integração ao back-end é a `src/main/resources/static/index.html` deste projeto.
