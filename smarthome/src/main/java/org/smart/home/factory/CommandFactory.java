package org.smart.home.factory;

import org.smart.home.appliances.Fan;
import org.smart.home.appliances.Light;
import org.smart.home.commands.BrightnessCommand;
import org.smart.home.commands.FanSpeedCommand;
import org.smart.home.commands.SwitchCommand;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandFactory {
    public static Map<Class<? extends Command>, String> patternMap = new HashMap<Class<? extends Command>, String>() {{
        put(BrightnessCommand.class, "set (?<name>%s) brightness to (?<brightness>\\d)");
        put(FanSpeedCommand.class, "set (?<name>%s) speed to (?<speed>\\d)");
        put(SwitchCommand.class, "turn (?<power>on|off) (?<name>%s)");
    }};

    public static Command create(String rawCommand, Map<String, Appliance> appliances) {
        List<String> names = new ArrayList<>(appliances.keySet());
        return patternMap.entrySet().stream().map(e -> {
            String regexp = String.format(e.getValue(), String.join("|", names));
            Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(rawCommand);
            if (matcher.matches()) {
                Appliance appliance = appliances.get(matcher.group("name"));
                try {
                    return e.getKey().newInstance().setAppliance(appliance).init(matcher);
                } catch (InstantiationException | IllegalAccessException instantiationException) {
                    instantiationException.printStackTrace();
                }
            }
            return null;
        }).filter(Objects::nonNull).findFirst().orElse(null);
    }
}
