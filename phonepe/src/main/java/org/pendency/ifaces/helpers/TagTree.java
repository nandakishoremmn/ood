package org.pendency.ifaces.helpers;

import java.util.List;

public interface TagTree {

    void increment(List<String> hierarchicalTags, int count);

    void decrement(List<String> hierarchicalTags, int count);

    Integer getCount(List<String> tags);
}
