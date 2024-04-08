package org.example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TableMangerFactory {
    public static TableManger get() {
        return new TableMangerImpl();
    }
}
