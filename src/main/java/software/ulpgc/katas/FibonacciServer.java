package software.ulpgc.katas;

import io.javalin.Javalin;
import io.javalin.http.Context;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.Output;
import software.ulpgc.katas.architecture.usecases.CalculateFibonacciSeriesUseCase;
import software.ulpgc.katas.infrastructure.adapters.ContextInput;
import software.ulpgc.katas.infrastructure.adapters.GsonSeriesSerializer;

public class FibonacciServer {
    public static void main(String[] args) {
        Javalin.create(config -> config.routes.get("/fibonacci", FibonacciServer::fibonacci))
                .start("localhost", 8080);
    }

    private static void fibonacci(Context context) {
        Input input = new ContextInput(context);
        int n;
        try {
            n = input.get("n").asInteger();
        } catch (NumberFormatException e) {
            context.status(400).result("n debe ser un entero entre 0 y 47.");
            return;
        }

        if (n < 0 || n > 47) {
            context.status(400).result("n debe ser un entero entre 0 y 47.");
            return;
        }

        Output output = new CalculateFibonacciSeriesUseCase(new GsonSeriesSerializer()).execute(input);
        context.contentType("application/json").result(output.result());
    }
}