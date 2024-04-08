package org.example;

import java.util.List;
import java.util.Map;

public class QueryBuilder {
    private String tableName;
    private List<String> ids;
    private Map<String, String> filters;
    QueryBuilder withTable(String tableName) {
        this.tableName = tableName;
        return this;
    }
    QueryBuilder withIds(List<String> ids) {
        this.ids = ids;
        return this;
    }
    QueryBuilder withFilter(String columnName, String value) {
        this.filters.put(columnName, value);
        return this;
    }
    Query build() {
        return new Query(tableName, ids, filters);
    }
}
