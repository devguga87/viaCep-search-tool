package services;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import entities.Endereco;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;

public class ViaCepService {
    public Endereco getEndereco(String cep) throws IOException, InterruptedException {
        String enderecoUrl = "https://viacep.com.br/ws/" + cep + "/json/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(enderecoUrl))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Erro na requisição: status " + response.statusCode());
            return null;
        }

        String json = response.body();

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (jsonObject.has("erro") && jsonObject.get("erro").getAsBoolean()) {
            return null; // CEP inválido
        }

        Gson gson = new Gson();
        return gson.fromJson(json, Endereco.class);
    }
}
