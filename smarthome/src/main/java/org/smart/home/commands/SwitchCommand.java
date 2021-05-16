package org.smart.home.commands;

import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.enums.Power;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;
import org.smart.home.interfaces.device.property.Brightness;
import org.smart.home.interfaces.device.property.Switch;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SwitchCommand implements Command {
    Power status;
    private Appliance appliance;

    @Override
    public CommandStatus execute() {
        if(appliance instanceof Switch) {
            return ((Switch) appliance).updatePower(status);
        } else {
            return new CommandStatus(ExecutionStatus.FAILED, appliance.getName() + " does not support brightnes update");
        }
    }

    @Override
    public Command init(Matcher matcher) {
        String value = matcher.group("power");
        if (value.toLowerCase().equals("on")) {
            status = Power.ON;
        } else if (value.toLowerCase().equals("off")) {
            status = Power.OFF;
        } else {
            throw new RuntimeException("Unidentified power status");
        }
        return this;
    }

    @Override
    public Command setAppliance(Appliance appliance) {
        this.appliance = appliance;
        return this;
    }
}
