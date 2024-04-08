package org.example;

import java.util.Map;

public class Record {
    private String id;
    private Map<String, Object> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Record(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", data=" + data +
                '}';
    }
}
