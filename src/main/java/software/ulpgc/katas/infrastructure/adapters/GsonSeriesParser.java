package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.*;
import software.ulpgc.katas.architecture.domain.Series;

public class GsonSeriesParser {
    private final Gson gson = new GsonBuilder()
            .setStrictness(Strictness.STRICT)
            .create();

    public Series parse(String body) {
        try {
            JsonArray array = gson.fromJson(body, JsonArray.class);
            if (array == null) throw new JsonParseException("Falta el array JSON.");

            int[] values = new int[array.size()];
            for (int i = 0; i < values.length; i++) {
                JsonElement value = array.get(i);
                if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
                    throw new JsonParseException("Se esperaba un entero.");
                }
                values[i] = Integer.parseInt(value.getAsString());
            }
            return new Series(values);
        } catch (JsonParseException | NumberFormatException e) {
            throw new IllegalStateException("Respuesta JSON inválida.", e);
        }
    }
}