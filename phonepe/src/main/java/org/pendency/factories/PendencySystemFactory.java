package org.pendency.factories;

import org.pendency.ifaces.services.PendencySystem;
import org.pendency.services.PendencySystemImpl;

public class PendencySystemFactory {
    private static PendencySystem INSTANCE = null;


    public static PendencySystem get() {
        if(INSTANCE == null) {
            INSTANCE = new PendencySystemImpl();
        }
        return INSTANCE;
    }
}
