package org.example;

import java.util.List;

public interface DataCommand extends TableCommand {
    DataCommand withIds(List<Record> records);

    DataCommand withTable(String tableName);
}
