package Modelo;

import Comunicacao.ConsultaCambio;

import java.util.Map;

public record ExchangeRate(String base_code, Map<String, Double> conversion_rates) {

    public void exibirMoedasApi()
    {
        for (String key : conversion_rates.keySet()) {
            System.out.print(key + " | ");
        }
    }

}
