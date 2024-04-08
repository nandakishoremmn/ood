package org.pendency.services;

import org.pendency.ifaces.helpers.TagTree;
import org.pendency.helpers.TagTreeImpl;
import org.pendency.ifaces.services.PendencySystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendencySystemImpl implements PendencySystem {
    Map<Integer, List<String>> entities;
//    EntityRepo entityRepo;
    TagTree tagTree;

    public PendencySystemImpl() {
        entities = new HashMap<>();
        tagTree = new TagTreeImpl();
    }

    @Override
    synchronized public void startTracking(Integer id, List<String> hierarchicalTags) {
        if(entities.containsKey(id)) {
            System.err.println("Entity already tracked");
            return;
        }
        if(hierarchicalTags.size() == 0){
            System.err.println("No tags found!");
        }
        entities.put(id, hierarchicalTags);
        tagTree.increment(hierarchicalTags, 1);
    }

    @Override
    public void stopTracking(Integer id) {
        if(!entities.containsKey(id)) {
            System.err.println("Entity not tracked already");
            return;
        }
        List<String> hierarchicalTags = entities.get(id);
        entities.remove(id);
        tagTree.decrement(hierarchicalTags, 1);
    }

    @Override
    public Integer getCounts(List<String> tags) {
        return tagTree.getCount(tags);
    }
}
