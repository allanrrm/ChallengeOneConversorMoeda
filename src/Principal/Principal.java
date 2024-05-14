package Principal;

import Comunicacao.ConsultaCambio;
import Modelo.ExchangeRate;
import Modelo.Moeda;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static Principal.Funcoes.caminhoJson;

public class Principal {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        JsonArray jsonArray = Funcoes.lerArquivoJson(caminhoJson);
        ExchangeRate exchangeRate = ConsultaCambio.buscaCambio("BRL");
        ArrayList<Integer> listaOpcoes;
        listaOpcoes = Funcoes.chamarMenu(); // O Método retorna os dois ultimos inteiros do contador para poder gerar o Menu Adicionar Conversão e Sair Corretamente.
        int opcao = scanner.nextInt();

        while (opcao != listaOpcoes.get(1)) {
            try {
                if (opcao == listaOpcoes.getFirst()) {
                    System.out.println("Digite o codigo da moeda base para a conversão que deseja criar: (Consulte o README " +
                            "para saber qual a moeda de cada codigo)");
                    exchangeRate.exibirMoedasApi();
                    System.out.println();
                    String moedaBase = scanner.next().toUpperCase();
                    if (exchangeRate.conversion_rates().containsKey(moedaBase)) {
                        System.out.println("Codigo de Moeda Base Válido!");
                        System.out.println("Por Favor, digite o código da moeda de destino");
                        exchangeRate.exibirMoedasApi();
                        System.out.println();
                        String moedaConversao = scanner.next().toUpperCase();
                        if (exchangeRate.conversion_rates().containsKey(moedaConversao)) {
                            Funcoes.adicionarConversao(moedaBase, moedaConversao);
                            jsonArray = Funcoes.lerArquivoJson(caminhoJson);
                        } else {
                            System.out.println("codigo inexistente!");
                            Funcoes.apertarParaContinuar();
                        }
                    } else {
                        System.out.println("codigo inexistente!");
                        Funcoes.apertarParaContinuar();
                    }
                }
                else if(--opcao<jsonArray.size() && opcao>=0){ //lembrar de eliminar opcao < 1
                    JsonElement jsonElementmoedaCodigo = jsonArray.get(opcao);
                    String moedabase = jsonElementmoedaCodigo.getAsJsonObject().get("moedaBase").toString().replace("\"", "");
                    String moedaConversao = jsonElementmoedaCodigo.getAsJsonObject().get("moedaConversao").toString().replace("\"", "");
                    exchangeRate = ConsultaCambio.buscaCambio(moedabase);
                    double taxaConversao = exchangeRate.conversion_rates().get(moedaConversao);
                    Moeda moeda = new Moeda(moedabase, moedaConversao, taxaConversao);
                    String nomeMoedaBase = Funcoes.converterSimboloParaNome(moeda.getMoedaBase());
                    String nomeMoedaConversa = Funcoes.converterSimboloParaNome(moeda.getMoedaConversao());
                    System.out.println("Qual valor você deseja converter de: " + nomeMoedaBase + " para " + nomeMoedaConversa);
                    double valor = scanner.nextDouble();
                    double valorConvertido = moeda.conversao(valor);
                    System.out.println("O valor de conversão é: " + valorConvertido);
                    Funcoes.apertarParaContinuar();

                }else {
                    System.out.println("Opção invalida!");
                    Funcoes.apertarParaContinuar();
                }
            } catch (Exception e) {
                e.getMessage();
            }
            listaOpcoes = Funcoes.chamarMenu();
            opcao = scanner.nextInt();
        }

    }


}

