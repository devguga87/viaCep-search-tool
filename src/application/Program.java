package application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Endereco;
import services.ViaCepService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);
        List<Endereco> listaDeEnderecos = new ArrayList<>();
        ViaCepService viaCep = new ViaCepService();

        try {
            while(true){
                System.out.println("Digite um cep para obter endereço ou q para sair: ");
                String cep= sc.nextLine();

                if(cep.equalsIgnoreCase("q")){
                    break;
                }

                if (!cep.matches("\\d{8}")) {
                    System.out.println("CEP inválido! O CEP deve conter 8 dígitos numéricos.");
                    continue;
                }


                Endereco end = viaCep.getEndereco(cep);
                if(end == null){
                    System.out.println("O endereço não existe!");
                } else {
                    System.out.println(end);
                    listaDeEnderecos.add(end);
                }
            }

            if (!listaDeEnderecos.isEmpty()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(listaDeEnderecos);
                try (FileWriter file = new FileWriter("enderecos.txt")) {
                    file.write(json);
                }
                System.out.println("Endereços salvos em enderecos.txt");
            } else {
                System.out.println("Nenhum endereço para salvar.");
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}
