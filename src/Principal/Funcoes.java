package Principal;

import Comunicacao.ConsultaCambio;
import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Funcoes {

    //Utilizado uma variavel global pois o caminho não será alterado, somente por decisão de projeto
    public static String caminhoJson = "src/Principal/conversoes.json";

    public static ArrayList<Integer> chamarMenu() throws FileNotFoundException {
        ArrayList<Integer> listaOpcoes = new ArrayList<Integer>();
        JsonArray jsonArray = lerArquivoJson(caminhoJson);
        int contador = 0;
        System.out.println("*****************************************************************************************");
        System.out.println("Seja Bem-vindo ao conversor de Moeda");
        System.out.println("Por Favor selecione uma das opções abaixo selecionando o numero");
        System.out.println();
        JsonObject jsonObjectNomeCambio = ConsultaCambio.buscarNomeCambio();

        for(JsonElement moeda : jsonArray){
            JsonObject jsonObject = moeda.getAsJsonObject();
            contador ++;
            String moedaBase = converterSimboloParaNome(jsonObject.get("moedaBase").toString().replace("\"", ""), jsonObjectNomeCambio);
            String moedaConversao = converterSimboloParaNome(jsonObject.get("moedaConversao").toString().replace("\"", ""), jsonObjectNomeCambio);


            System.out.println(contador + ") " + moedaBase.replace("\"", "") + " => "
                    + moedaConversao.replace("\"", ""));
        }
        contador ++;
        System.out.println(contador + ") Adicionar Conversão");
        listaOpcoes.add(contador);
        contador ++;
        System.out.println(contador +") Sair" );
        listaOpcoes.add(contador);
        return listaOpcoes;
    }

    public static void adicionarConversao(String moedaBase, String moedaConversao) throws IOException {

        JsonArray jsonArray = lerArquivoJson(caminhoJson);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject novoObjeto = new JsonObject();
        novoObjeto.addProperty("moedaBase", moedaBase);
        novoObjeto.addProperty("moedaConversao", moedaConversao);
        jsonArray.add(novoObjeto);
        String novoJson = gson.toJson(jsonArray);
        System.out.println("Nova conversão adicionada com sucesso");

        salvarArquivoJson(caminhoJson, novoJson);

    }

    // Método para salvar um arquivo Json ao passar um caminho e uma string Json
    private static void salvarArquivoJson(String caminho, String conteudo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write(conteudo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    public static JsonArray lerArquivoJson(String caminhoJson) throws FileNotFoundException {
        Reader reader = new FileReader(caminhoJson);
        JsonArray jsonArray = new Gson().fromJson(reader, JsonArray.class);
        return jsonArray;
    }

    public static void apertarParaContinuar(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pressione Enter para continuar");
        scanner.nextLine();
    }

    public static String converterSimboloParaNome(String codigoMoeda, JsonObject jsonObjectNomeCambio){
        String nomeMoeda = jsonObjectNomeCambio.get(codigoMoeda).toString().replace("\"", "");
        return nomeMoeda;
    }

    //SobreEscrita do método anterior para utilização na alteração dos nomes na conversão da taxa
    public static String converterSimboloParaNome(String codigoMoeda){
        JsonObject jsonObjectNomeCambio = ConsultaCambio.buscarNomeCambio();
        String nomeMoeda = jsonObjectNomeCambio.get(codigoMoeda).toString().replace("\"", "");
        return nomeMoeda;
    }
}
