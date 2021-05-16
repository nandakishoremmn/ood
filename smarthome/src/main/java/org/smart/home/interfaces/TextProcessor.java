package org.smart.home.interfaces;

import java.util.Map;

public interface TextProcessor {
    Command parse(String rawCommand, Map<String, Appliance> appliances);
}
