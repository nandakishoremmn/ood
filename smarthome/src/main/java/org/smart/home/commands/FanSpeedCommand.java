package org.smart.home.commands;

import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;
import org.smart.home.interfaces.appliance.property.FanSpeed;

import java.util.regex.Matcher;

public class FanSpeedCommand implements Command {
    private Integer speed;
    private Appliance appliance;

    @Override
    public CommandStatus execute() {
        if(appliance instanceof FanSpeed) {
            return ((FanSpeed) appliance).updateFanSpeed(speed);
        } else {
            return new CommandStatus(ExecutionStatus.FAILED, appliance.getName() + " does not support fan speed update");
        }    }

    @Override
    public Command init(Matcher matcher) {
        speed = Integer.parseInt(matcher.group("speed"));
        return this;
    }

    @Override
    public Command setAppliance(Appliance appliance) {
        this.appliance = appliance;
        return this;
    }
}
