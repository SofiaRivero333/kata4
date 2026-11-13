package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.domain.Series;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpFibonacciService implements FibonacciService {
    private final String baseUrl;
    private final GsonSeriesParser parser = new GsonSeriesParser();

    public HttpFibonacciService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Series findSeries(int n) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/fibonacci?n=" + n))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IllegalStateException("El servicio respondió con HTTP " + response.statusCode() + ".");
            }
            return parser.parse(response.body());
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo consultar el servicio en " + baseUrl + ".", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Se interrumpió la petición.", e);
        }
    }
}