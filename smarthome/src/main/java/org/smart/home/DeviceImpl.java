package org.smart.home;

import lombok.SneakyThrows;
import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.factory.CommandFactory;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;
import org.smart.home.interfaces.Device;
import org.smart.home.interfaces.Home;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceImpl implements Device {
    private final String name;
    private final String activator;
    private final Map<String, Appliance> appliances = new HashMap<>();
    private final Home home;
    private final Map<Pattern, Command> patterns = new HashMap<>();

    public DeviceImpl(String name, String activator, Home home) {
        this.name = name;
        this.activator = activator.substring(13).toLowerCase();
        this.home = home;
    }

    @SneakyThrows
    @Override
    public boolean sendCommand(String rawCommand) {
        if (!rawCommand.startsWith(activator)) {
            return false;
        }
        String applianceCommand = rawCommand.substring(activator.length() + 1);
        Command command = CommandFactory.create(applianceCommand, appliances);

        if (command == null) {
            System.out.println("Sorry, no appliance support this command [" + rawCommand + "]");
            return false;
        } else {
            CommandStatus status = command.execute();
            if (status.getStatus().equals(ExecutionStatus.SUCCESS)) {
                System.out.println(status.getMessage());
            } else {
                System.out.println(status.getMessage());
            }
            return true;
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
