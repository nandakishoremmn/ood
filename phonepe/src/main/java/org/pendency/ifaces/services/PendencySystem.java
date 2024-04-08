package org.pendency.ifaces.services;

import java.util.List;

public interface PendencySystem {
    void startTracking (Integer id, List<String> hierarchicalTags);
    void stopTracking (Integer id);
    Integer getCounts (List<String> tags);
}
