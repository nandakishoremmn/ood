package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataManagerImpl implements DataManager {
    private Map<String, TableManger> tables;

    public DataManagerImpl() {
        this.tables = new ConcurrentHashMap<>();
    }

    @Override
    public TableManger getTableManger(TableCommand command) {
        if(!tables.containsKey(command.getTableName())) {
            throw new RuntimeException("Table not found");
        }
        return tables.get(command.getTableName());
    }

    @Override
    public void createTable(String tableName) {
        tables.computeIfAbsent(tableName, (key) -> TableMangerFactory.get());

//        if(tables.containsKey(tableName)) {
//            throw new RuntimeException("Table exists already");
//        } else {
//            tables.put(tableName, new TableMangerImpl());
//        }
    }

    @Override
    public void deleteTable(String tableName) {
        if(!tables.containsKey(tableName)) {
            throw new RuntimeException("Table not found");
        } else {
            tables.remove(tableName);
        }
    }

    @Override
    public Set<String> showTables() {
        return tables.keySet();
    }
}
