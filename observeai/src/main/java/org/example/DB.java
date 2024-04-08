package org.example;

import java.util.List;
import java.util.Set;

public interface DB {
    void executeDDL(DDLCommand command);
    List<Record> executeQuery(Query query);
    void executeUpdate(DataCommand command);
    Set<String> showTables();
}
