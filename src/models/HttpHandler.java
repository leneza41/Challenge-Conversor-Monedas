package models;

import Exceptions.IllegalCurrencyException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpHandler extends DataValidator {
    private String baseURI;
    private String apiKey;

    public HttpHandler(String apiKey) {
        this.apiKey = apiKey;
        this.baseURI = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/";
    }
    private String getURI(String base_code, String target_code, double amount) {
        if (validateCode(base_code) && validateCode(target_code) && validateAmount(amount)) {
            return baseURI + base_code + "/" + target_code + "/" + amount;
        }
        List<String> invalid = getInputIssues(base_code, target_code, amount);
        throw new IllegalArgumentException("Entrada inválida de: " + invalid);
    }
    public String apiRequest(String base_code, String target_code, double amount) {
        base_code = base_code.trim().toUpperCase();
        target_code = target_code.trim().toUpperCase();
        String json;
        String requestURI = getURI(base_code, target_code, amount);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestURI))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            json = response.body();
            if(invalidResponse(json)) {
                throw new IllegalCurrencyException("Los códigos de moneda no son válidos");
            }
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
