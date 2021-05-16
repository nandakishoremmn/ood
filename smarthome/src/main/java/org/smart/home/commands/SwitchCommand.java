package org.smart.home.commands;

import org.smart.home.data.CommandStatus;
import org.smart.home.enums.Power;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;
import org.smart.home.interfaces.device.property.Switch;

import java.util.regex.Matcher;

public class SwitchCommand implements Command {
    Power status;
    private Switch appliance;

    @Override
    public CommandStatus execute() {
        return appliance.updatePower(status);
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
        this.appliance = (Switch) appliance;
        return this;
    }
}
