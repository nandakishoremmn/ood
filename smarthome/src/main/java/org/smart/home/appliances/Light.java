package org.smart.home.appliances;

import lombok.ToString;
import org.smart.home.commands.BrightnessCommand;
import org.smart.home.interfaces.Command;
import org.smart.home.commands.SwitchCommand;
import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.enums.Power;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.device.property.Brightness;
import org.smart.home.interfaces.device.property.Switch;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Light implements Appliance, Switch, Brightness {
    private final String name;
    private Integer brightness = 1;
    private Power power = Power.OFF;

    public Light(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public CommandStatus updatePower(Power status) {
        power = status;
        return new CommandStatus(ExecutionStatus.SUCCESS, name + " turned " + power);
    }

    @Override
    public CommandStatus updateBrightness(Integer brightness) {
        if (power.equals(Power.OFF)) {
            return new CommandStatus(ExecutionStatus.FAILED, "Fan is off");
        }
        if (1 <= brightness && brightness <= 5) {
            this.brightness = brightness;
            return new CommandStatus(ExecutionStatus.SUCCESS, name + " brightness changed to " + brightness);
        } else {
            return new CommandStatus(ExecutionStatus.FAILED, "Fan speed must be between 1-5");
        }    }
}
