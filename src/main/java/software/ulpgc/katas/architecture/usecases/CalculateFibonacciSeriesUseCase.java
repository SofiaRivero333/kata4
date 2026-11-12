package software.ulpgc.katas.architecture.usecases;

import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.domain.Series;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.Output;
import software.ulpgc.katas.architecture.ports.SeriesSerializer;

public class CalculateFibonacciSeriesUseCase implements UseCase {
    private final SeriesSerializer serializer;

    public CalculateFibonacciSeriesUseCase(SeriesSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Output execute(Input input) {
        Series series = calculate(input.get("n").asInteger());
        return () -> serializer.serialize(series);
    }

    private Series calculate(int n) {
        Series series = new Series();
        long a = 0;
        long b = 1;
        for (int i = 0; i < n; i++) {
            series = series.put((int) a);
            a = b;
            b += series.last();
        }
        return series;
    }
}