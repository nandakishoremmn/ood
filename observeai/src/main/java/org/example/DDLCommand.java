package org.example;

public interface DDLCommand extends TableCommand{
    DDLCommand withTableName(String tableName);

}
