package org.example;

import java.util.List;
import java.util.Map;

public class Query implements TableCommand{
    private String tableName;
    private List<String> ids;
    private Map<String, String> filters;

    public Query(String tableName, List<String> ids, Map<String, String> filters) {
        this.tableName = tableName;
        this.ids = ids;
        this.filters = filters;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    public List<String> getIds() {
        return ids;
    }

    public Map<String, String> getFilters() {
        return filters;
    }
}
