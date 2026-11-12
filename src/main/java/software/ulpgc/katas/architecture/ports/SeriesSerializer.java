package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.architecture.domain.Series;

public interface SeriesSerializer {
    String serialize(Series series);
}