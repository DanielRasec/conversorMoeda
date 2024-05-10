import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ConversorMoeda {

    private static final String API_KEY = "b866b6d350719c57caa46537";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/b866b6d350719c57caa46537/latest/USD";

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                exibirMenu();
                String escolha = reader.readLine();
                if (escolha.equals("7")) {
                    System.out.println("Obrigado por utilizar o conversor de moeda. Até mais!");
                    break;
                }
                switch (escolha) {
                    case "1":
                        converterMoeda("USD");
                        break;
                    case "2":
                        converterMoeda("EUR");
                        break;
                    case "3":
                        converterMoeda("GBP");
                        break;
                    case "4":
                        converterMoeda("JPY");
                        break;
                    case "5":
                        converterMoeda("CAD");
                        break;
                    case "6":
                        converterMoeda("AUD");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exibirMenu() {
        System.out.println("=== Conversor de Moeda ===");
        System.out.println("Selecione a moeda base:");
        System.out.println("1. USD - Dólar Americano");
        System.out.println("2. EUR - Euro");
        System.out.println("3. GBP - Libra Esterlina");
        System.out.println("4. JPY - Iene Japonês");
        System.out.println("5. CAD - Dólar Canadense");
        System.out.println("6. AUD - Dólar Australiano");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void converterMoeda(String moedaBase) {
        try {
            URL url = new URL(API_URL + moedaBase + "&access_key=" + API_KEY);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            int respostaCode = conexao.getResponseCode();
            if (respostaCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    resposta.append(linha);
                }
                reader.close();

                JSONObject json = new JSONObject(resposta.toString());
                JSONObject taxas = json.getJSONObject("rates");

                System.out.println("Taxas de conversão em relação a " + moedaBase + ":");
                for (String moeda : taxas.keySet()) {
                    System.out.println(moeda + ": " + taxas.getDouble(moeda));
                }
            } else {
                System.out.println("Erro ao obter as taxas de câmbio. Código de resposta: " + respostaCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
