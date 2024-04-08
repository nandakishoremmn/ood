package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TableMangerImpl implements TableManger {
    private Map<String, Record> data;

    public TableMangerImpl() {
        this.data = new ConcurrentHashMap<>();
    }

    @Override
    public List<Record> executeQuery(Query query) {
        return query.getIds().stream()
                .filter(id -> data.containsKey(id))
                .map(id -> data.get(id))
                .collect(Collectors.toList());
    }

    @Override
    public void executeUpdate(DataCommand command) {
        if (command instanceof InsertDataCommand) {
            insert((InsertDataCommand) command);
        } else if (command instanceof UpdateDataCommand) {
            update((UpdateDataCommand) command);
        } else if (command instanceof DeleteDataCommand) {
            delete((DeleteDataCommand) command);
        } else {
            throw new RuntimeException("Unimplemented command");

        }
    }

    private void insert(InsertDataCommand command) {
        Map<String, Record> records = command.getRecords().stream()
                .collect(Collectors.toMap(Record::getId, e -> e));
        data.putAll(records);
    }

    private void update(UpdateDataCommand command) {
        Map<String, Record> records = command.getRecords().stream()
                .collect(Collectors.toMap(Record::getId, e -> e));
        if(!command.getRecords().stream()
                .map(Record::getId)
                .allMatch(id -> data.containsKey(id))) {
            throw new RuntimeException("Non existant id present");
        }

        data.putAll(records);
    }

    private void delete(DeleteDataCommand command) {
        command.getRecords().forEach(record -> {
            if(data.containsKey(record.getId())) {
                data.remove(record.getId());
            }
        });
    }
}
