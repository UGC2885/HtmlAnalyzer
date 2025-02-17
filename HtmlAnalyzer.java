import java.io.BufferedReader; /* A classe BufferedReader serve para ler dados de forma eficiente.
Ele armazena os dados em um buffer (memória temporária), o que melhora o desempenho da leitura.
*/
import java.io.InputStreamReader; // Converte um fluxo de bytes em um fluxo de caracteres, sendo útil para ler dados vindos de um site.
import java.net.HttpURLConnection; // Classe que permite fazer requisições HTTP. Usada para estabelecer uma conexão com o servidor web e obter dados GET ou POST 
import java.net.URL; // Usado para apresentar endereços na web. A classe fornece meios para abrir uma conexão com recursos da internet
import java.util.Stack; // Usado para montar a pilha de tags HTML abertas, garantindo que todas tenham uma tag de fechamento.

public class HtmlAnalyzer {
    public static void main(String[] args) { // Verifica se o usuário colocou realmente uma URL real
        if (args.length != 1) {
            System.out.println("Uso: java HtmlAnalyzer <URL>");
            return;
        }
        //Se não mostra a mensagem de erro e encerra.

        // Baixa o conteudo HTML
        try {
            String htmlContent = downloadHtml(args[0]);
            System.out.println(findDeepestText(htmlContent));
        } catch (Exception e) {
            System.out.println("Erro ao conectar à URL");
        }
        // Se houver um erro, mostra o erro
    }

    // Esse método baixa o conteúdo HTML da página.
    private static String downloadHtml(String urlString) throws Exception {
        URL url = new URL(urlString);
        // Criado um objeto para representar a URL

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Aqui abre a conexão HTTP
        connection.setRequestMethod("GET");
        // Definido o método GET para requisição dos conteúdos da página

        // Aqui verifica se está tudo ok
        if (connection.getResponseCode() != 200) throw new Exception();
        // Caso aconteça um erro, é lançado um erro

        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
        // Explicação do BufferedReader::: Quando estabelecemos a conexão com a URL, BufferedReader lê a resposta da página de forma otimizada.
        // Explicação do InputStreamReader::: o InputStreamReader converte os bytes em caracteres. fornecendo informação para connection.getInputStream() que vai fornecer um fluxo de entrada contendo a resposta do servidor.
        
        // Aqui será lido linha por linha e depois armazenado o conteúdo da página
        StringBuilder html = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) html.append(line).append("\n");
        
        return html.toString();
        // Depois que tudo ocorreu bem, é mostrado o HTMl completo da página.
    }

    private static String findDeepestText(String html) {
        // Criação da pilha e variaveis de controle
        Stack<String> stack = new Stack<>();
        String deepest = "";
        int maxDepth = -1;

        // A Stack armazena as tags abertas
        // deepest guarda o texto mais produndo
        // maxDepth acompanha a produndidade alcançada

        // Aqui percorremos o HTML linha por linha
        for (String line : html.split("\n")) {
            line = line.trim();
            if (line.isEmpty()) continue;
            // Caso esteja vazio, é ignorado

            
            if (line.startsWith("<") && !line.startsWith("</")) {
                stack.push(line);
                // Verifica se é uma tag de abertura, se for, é empilhado
            } else if (line.startsWith("</")) {
                if (stack.isEmpty()) {
                    return "Erro: Tag de fechamento '" + line + "' sem tag de abertura correspondente.";
                }
                String openedTag = stack.pop();
                String expectedClosingTag = "</" + openedTag.substring(1); // Remove '<' e adiciona '</'
                // Verifica se é uma tag de fechamento, remove a ultima tag da pilha

                if (!line.equals(expectedClosingTag)) {
                    return "Erro: Tag de fechamento '" + line + "' nao corresponde a tag de abertura '" + openedTag + "'.";
                }
            } else {
                // É um texto fora de tags
                if (stack.size() > maxDepth) {
                    maxDepth = stack.size();
                    deepest = line;
                }
                // Se o texto estiver na maior profundidade, salvamos ele.
            }
        }

        // Verifica se há tags não fechadas
        if (!stack.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Erro: As seguintes tags nao foram fechadas:\n");
            while (!stack.isEmpty()) {
                errorMessage.append("- ").append(stack.pop()).append("\n");
            }
            return errorMessage.toString();
        }

        return deepest.isEmpty() ? "Nenhum texto encontrado." : deepest;
    }
}