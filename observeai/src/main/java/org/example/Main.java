package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DB db = new DBImpl();

        db.executeDDL(new CreateDDLCommand().withTableName("test"));
        db.executeDDL(new CreateDDLCommand().withTableName("test1"));
        System.out.println(db.showTables());
        db.executeDDL(new DeleteDDLCommand().withTableName("test"));
        System.out.println(db.showTables());

        db.executeUpdate(new InsertDataCommand().withTable("test1")
                .withIds(Arrays.asList(
                        new Record("1", new HashMap<String, Object>() {{
                            put("name", "name1");
                        }}),
                        new Record("2", new HashMap<String, Object>() {{
                            put("name", "name2");
                        }})

                ))
        );

        List<Record> records = db.executeQuery(new QueryBuilder()
                .withTable("test1")
                .withIds(Arrays.asList("1", "2", "3"))
                .build()
        );
        System.out.println(records);

        db.executeUpdate(new DeleteDataCommand()
                .withTable("test1")
                .withIds(records.subList(0, 1))
        );

        List<Record> records2 = db.executeQuery(new QueryBuilder()
                .withTable("test1")
                .withIds(Arrays.asList("1", "2", "3"))
                .build()
        );
        System.out.println(records2);


        db.executeUpdate(new UpdateDataCommand()
                .withTable("test1")
                .withIds(Arrays.asList(
                        new Record("2", new HashMap<String, Object>() {{
                            put("name", "name2mod");
                        }})

                ))
        );

        List<Record> records3 = db.executeQuery(new QueryBuilder()
                .withTable("test1")
                .withIds(Arrays.asList("1", "2", "3"))
                .build()
        );
        System.out.println(records3);
    }
}