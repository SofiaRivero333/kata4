package software.ulpgc.katas;

import software.ulpgc.katas.architecture.ports.FibonacciService;
import software.ulpgc.katas.infrastructure.adapters.HttpFibonacciService;

import java.util.Arrays;

public class Main {
    private static final String BASE_URL = "http://localhost:8080";

    public static void main(String[] args) {
        try {
            int n = args.length == 0 ? 8 : Integer.parseInt(args[0]);
            show(new HttpFibonacciService(BASE_URL), n);
        } catch (NumberFormatException e) {
            System.err.println("Error: n debe ser un entero.");
            System.exit(1);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void show(FibonacciService service, int n) {
        System.out.println("Serie de Fibonacci: " + Arrays.toString(service.findSeries(n).values()));
    }
}