import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer conexão HTTPe buscar os top 250 filmes

        String imdbKey = System.getenv("IMDB_API_KEY");
        String url = "https://imdb-api.com/en/API/MostPopularMovies/" + imdbKey;
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam(titulo, poster, classificação)

        var parser = new jsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir e manipular os dados

        for (int i = 0; i < 7; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            System.out.println(
                    "\u001b[30m \u001b[43m 🎬 Titulo:\u001b[m" + "\u001b[37m \u001b[44m" + filme.get("title")
                            + "\u001b[m");
            System.out.println("\u001b[34m" + filme.get("image") + "\u001b[m");

            // pegar valor de nota e torna-lo inteiro
            double classificação = Double.parseDouble(filme.get("imDbRating"));
            int notaEstrela = (int) classificação;

            for (int n = 0; n < notaEstrela; n++) {
                if (notaEstrela <= 6) {
                    System.out.print("🍍");
                } else if (notaEstrela >= 7) {
                    System.out.print("🏆");
                }
                ;
            }
            System.out.println("\n");

        }
    }
}
