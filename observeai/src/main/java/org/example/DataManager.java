package org.example;

import java.util.Set;

public interface DataManager {
    TableManger getTableManger(TableCommand command);
    void createTable(String tableName);
    void deleteTable(String tableName);

    Set<String> showTables();
}
