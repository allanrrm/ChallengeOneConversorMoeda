package Comunicacao;

import Modelo.ExchangeRate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaCambio {

    public static ExchangeRate buscaCambio(String codigoMoedaBase){
        URI enderecoApi = URI.create("https://v6.exchangerate-api.com/v6/f783e8271bfab618d04eab44/latest/" + codigoMoedaBase);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(enderecoApi)
                .build();
        try {
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), ExchangeRate.class); //desserialização com a Biblioteca GSON

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("");
        }
    }

    public static JsonObject buscarNomeCambio(){
        URI enderecoApi = URI.create("https://openexchangerates.org/api/currencies.json");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(enderecoApi)
                .build();

        try{
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
                    return new Gson().fromJson(response.body(), JsonObject.class);
        } catch (IOException | InterruptedException e){
            throw new RuntimeException("");
        }
    }

}
