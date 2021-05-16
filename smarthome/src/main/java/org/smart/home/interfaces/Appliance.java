package org.smart.home.interfaces;

import java.util.Map;
import java.util.regex.Pattern;

public interface Appliance {
    Map<Pattern, Command> getCommandMap();
    String getName();
}
