package org.smart.home.commands;

import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Command;
import org.smart.home.interfaces.appliance.property.Brightness;

import java.util.regex.Matcher;

public class BrightnessCommand implements Command {
    private Integer brightness;
    private Appliance appliance;

    @Override
    public CommandStatus execute() {
        if(appliance instanceof Brightness) {
            return ((Brightness) appliance).updateBrightness(brightness);
        } else {
            return new CommandStatus(ExecutionStatus.FAILED, appliance.getName() + " does not support brightness update");
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
