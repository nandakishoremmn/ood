package org.smart.home.commands;

import org.smart.home.data.CommandStatus;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;
import org.smart.home.interfaces.device.property.FanSpeed;

import java.util.regex.Matcher;

public class FanSpeedCommand implements Command {
    private Integer speed;
    private FanSpeed appliance;

    @Override
    public CommandStatus execute() {
        return appliance.updateFanSpeed(speed);
    }

    @Override
    public Command init(Matcher matcher) {
        speed = Integer.parseInt(matcher.group("speed"));
        return this;
    }

    @Override
    public Command setAppliance(Appliance appliance) {
        this.appliance = (FanSpeed) appliance;
        return this;
    }
}
