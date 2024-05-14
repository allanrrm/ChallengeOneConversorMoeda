package Modelo;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Moeda {

    private String moedaBase;
    private String moedaConversao;
    private double taxaConversao;


    public Moeda(String moedaBase, String moedaConversao,double taxaConversao) {
        this.moedaBase = moedaBase;
        this.moedaConversao = moedaConversao;
        this.taxaConversao = taxaConversao;
    }

    public String getMoedaBase() {
        return moedaBase;
    }

    public String getMoedaConversao() {
        return moedaConversao;
    }

    public double getTaxaConversao() {
        return taxaConversao;
    }

    public double conversao(double valor){
        double total = this.taxaConversao * valor;
        return total;
    }
}

