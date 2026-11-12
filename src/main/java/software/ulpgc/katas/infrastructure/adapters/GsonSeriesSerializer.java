package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.domain.Series;
import software.ulpgc.katas.architecture.ports.SeriesSerializer;

public class GsonSeriesSerializer implements SeriesSerializer {
    @Override
    public String serialize(Series series) {
        return new Gson().toJson(series.values());
    }
}