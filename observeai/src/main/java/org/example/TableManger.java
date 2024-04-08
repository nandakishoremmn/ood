package org.example;

import java.util.List;

public interface TableManger {
    List<Record> executeQuery(Query query);

    void executeUpdate(DataCommand command);
}
