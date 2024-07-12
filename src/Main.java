import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.ConversionHistory;
import models.Currency;
import models.HttpHandler;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HttpHandler http = new HttpHandler("API_KEY");
        Gson gson = new GsonBuilder().create();
        ConversionHistory history = new ConversionHistory();

        System.out.println("Bienvenido!!");
        while(true) {
            System.out.println("Por favor seleccione una opcion:");
            System.out.println("1. Convertir moneda");
            System.out.println("2. Ver Historial de conversiones");
            System.out.println("3. Salir");
            try {
                int op = sc.nextInt(); sc.nextLine();
                if(op == 3) {
                    break;
                }
                if(op == 2) {
                    System.out.println("Historial de conversiones");
                    System.out.println(history);
                    System.out.println("Presione cualquier tecla para continuar...");
                    System.in.read();
                    continue;
                }
                if(op != 1) {
                    System.out.println("Opción inválida");
                    continue;
                }
                System.out.println("Por favor ingrese el código de la moneda base.");
                System.out.println("Ejemplo: USD, EUR, MXN, etc.");
                String base_code = sc.nextLine();

                System.out.println("Por favor ingrese el código de la moneda objetivo.");
                System.out.println("Ejemplo: USD, EUR, MXN, etc.");
                String target_code = sc.nextLine();

                System.out.println("Por favor ingrese la cantidad a convertir.");
                double amount = sc.nextDouble();

                String response = http.apiRequest(base_code, target_code, amount);
                Currency currency = gson.fromJson(response, Currency.class);
                double conversion_result = currency.conversion_result();

                System.out.println("Resultado: " + conversion_result);
                System.out.println("Presione cualquier tecla para continuar...");
                history.saveConversion(base_code, target_code, amount, conversion_result);

                System.in.read();
            } catch(InputMismatchException e) {
                System.out.println("Error, por favor ingrese un número valido.");
                sc.next();
            } catch(IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch(Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        System.out.println("Gracias por usar el conversor de monedas.");
    }
}