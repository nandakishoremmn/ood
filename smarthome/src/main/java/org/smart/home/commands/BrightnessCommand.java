package org.smart.home.commands;

import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;
import org.smart.home.interfaces.device.property.Brightness;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrightnessCommand implements Command {
    private Integer brightness;
    private Appliance appliance;

    @Override
    public CommandStatus execute() {
        if(appliance instanceof Brightness) {
            return ((Brightness) appliance).updateBrightness(brightness);
        } else {
            return new CommandStatus(ExecutionStatus.FAILED, appliance.getName() + " does not support brightnes update");
        }
    }

    @Override
    public Command init(Matcher matcher) {
        brightness = Integer.parseInt(matcher.group("brightness"));
        return this;
    }

    @Override
    public Command setAppliance(Appliance appliance) {
        this.appliance = appliance;
        return this;
    }
}
