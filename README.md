EASTER_EGG_URLS

# HtmlAnalyzer

HtmlAnalyzer é um programa Java que analisa o conteúdo HTML de uma página web e encontra o texto mais profundo na estrutura do HTML.

---

## Como usar

1. Certifique-se de ter o Java instalado.
2. Compile o código:
   ```bash
   javac HtmlAnalyzer.java
3. Execute o programa com uma URL:
	java HtmlAnalyzer http://hiring.axreng.com/internship/example1.html

O que ele faz?
Baixa o HTML: O programa acessa a URL fornecida e baixa o conteúdo HTML.

Encontra o texto mais profundo: Ele analisa o HTML e retorna o texto que está dentro da estrutura mais aninhada de tags.

Verifica erros: Se o HTML estiver mal-formado (tags não fechadas ou incorretas), ele avisa com mensagens específicas.

Exemplos de Saída
HTML bem-formado:
Texto mais profundo: Este é um exemplo de texto.

HTML mal-formado:
Erro: Tag de fechamento '</div>' sem tag de abertura correspondente.

Requisitos
Java 8 ou superior.

Conexão com a internet.

Limitações
Não funciona com JavaScript ou conteúdo dinâmico.

Só analisa HTML estático e bem-formado.

Autor
Lucas Unzer

Licença
Código aberto. Sinta-se à vontade para usar e compartilhar!