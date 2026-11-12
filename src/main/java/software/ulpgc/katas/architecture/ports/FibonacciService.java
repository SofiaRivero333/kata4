package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.architecture.domain.Series;

public interface FibonacciService {
    Series findSeries(int n);
}