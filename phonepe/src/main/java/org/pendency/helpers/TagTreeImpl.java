package org.pendency.helpers;

import org.pendency.ifaces.helpers.TagTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TagTreeImpl implements TagTree {
    private Integer count;
    private final Map<String, TagTreeImpl> children;

    public TagTreeImpl() {
        this.count = 0;
        this.children = new ConcurrentHashMap<>();
    }

    @Override
    public void increment(List<String> hierarchicalTags, int count) {
        increment(hierarchicalTags, count, 0);
    }

    public void increment(List<String> hierarchicalTags, int count, int pos) {
        this.count += count;
        if (hierarchicalTags.size() <= pos) {
            return;
        }
        String tag = hierarchicalTags.get(pos);
        if (!children.containsKey(tag)) {
            children.put(tag, new TagTreeImpl());
        }
        children.get(tag).increment(hierarchicalTags, count, pos + 1);
    }

    @Override
    public void decrement(List<String> hierarchicalTags, int count) {
        decrement(hierarchicalTags, count, 0);
    }

    public void decrement(List<String> hierarchicalTags, int count, int pos) {
        this.count -= count;
        if (hierarchicalTags.size() == pos) {
            return;
        }
        TagTreeImpl tagTree = children.get(hierarchicalTags.get(pos));
        tagTree.decrement(hierarchicalTags, count, pos + 1);
    }

    @Override
    public Integer getCount(List<String> tags) {
        if (tags.size() == 0) {
            return this.count;
        }
        return getCount(tags, 0);
    }

    public Integer getCount(List<String> tags, int pos) {
        if (pos == tags.size()) {
            return this.count;
        } else {
            if (children.containsKey(tags.get(pos))) {
                return children.get(tags.get(pos)).getCount(tags, pos + 1);
            } else {
                return 0;
            }
        }
    }
}
