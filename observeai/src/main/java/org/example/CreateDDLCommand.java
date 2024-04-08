package org.example;

public class CreateDDLCommand implements DDLCommand {
    private String tableName;
    @Override
    public DDLCommand withTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
