package org.example;

import java.util.List;
import java.util.Set;

public class DBImpl implements DB {
    private DataManager dataManager;

    public DBImpl() {
        this.dataManager = new DataManagerImpl();
    }

    @Override
    public void executeDDL(DDLCommand command) {
        if(command instanceof CreateDDLCommand) {
            dataManager.createTable(command.getTableName());
        } else if (command instanceof DeleteDDLCommand) {
            dataManager.deleteTable(command.getTableName());
        } else {
            throw new RuntimeException("Unimplemented command");
        }
    }

    @Override
    public List<Record> executeQuery(Query query) {
        TableManger tableManger = dataManager.getTableManger(query);
        return tableManger.executeQuery(query);
    }

    @Override
    public void executeUpdate(DataCommand command) {
        TableManger tableManger = dataManager.getTableManger(command);
        tableManger.executeUpdate(command);
    }

    @Override
    public Set<String> showTables() {
        return dataManager.showTables();
    }
}
