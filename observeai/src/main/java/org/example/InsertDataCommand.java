package org.example;

import java.util.List;

public class InsertDataCommand implements DataCommand {
    private List<Record> records;
    private String tableName;

    @Override
    public DataCommand withIds(List<Record> records) {
        if (records == null) {
            throw new RuntimeException("Recoreds is null");
        }

        this.records    = records;

        return this;
    }
    @Override
    public DataCommand withTable(String tableName) {
        if (tableName == null) {
            throw new RuntimeException("Recoreds is null");
        }

        this.tableName    = tableName;

        return this;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public List<Record> getRecords() {
        return records;
    }
}
