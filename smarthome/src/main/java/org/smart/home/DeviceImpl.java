package org.smart.home;

import lombok.SneakyThrows;
import org.smart.home.data.CommandStatus;
import org.smart.home.textprocessor.RegexTextProcessor;
import org.smart.home.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class DeviceImpl implements Device {
    private final String name;
    private final String activator;
    private final Map<String, Appliance> appliances = new HashMap<>();
    private final Home home;
    private final TextProcessor textProcessor = new RegexTextProcessor();

    public DeviceImpl(String name, String activator, Home home) {
        this.name = name;
        this.activator = activator.substring("activated by ".length()).toLowerCase();
        this.home = home;
    }

    @SneakyThrows
    @Override
    public void sendCommand(String rawCommand) {
        if (!rawCommand.startsWith(activator)) {
            return;
        }
        String applianceCommand = rawCommand.substring(activator.length() + 1);
        Command command = textProcessor.parse(applianceCommand, appliances);

        if (command == null) {
            System.out.println("Sorry, no appliance support this command [" + rawCommand + "]");
        } else {
            CommandStatus status = command.execute();
            System.out.println(status.getStatus() + " : " + status.getMessage());
        }
    }

    @Override
    public boolean addAppliance(String command) {
        if (!command.startsWith(activator)) {
            return false;
        }
        String applianceName = command.substring(activator.length() + 12);
        try {
            appliances.put(applianceName, home.getAppliance(applianceName));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void print() {
        System.out.println("\n==============");
        System.out.println(name);
        System.out.println("==============");
        appliances.values().forEach(System.out::println);
        System.out.println("==============\n");

    }
}
