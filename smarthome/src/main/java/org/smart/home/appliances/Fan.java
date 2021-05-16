package org.smart.home.appliances;

import lombok.ToString;
import org.smart.home.interfaces.Command;
import org.smart.home.commands.FanSpeedCommand;
import org.smart.home.commands.SwitchCommand;
import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.enums.Power;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.device.property.FanSpeed;
import org.smart.home.interfaces.device.property.Switch;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@ToString
public class Fan implements Appliance, FanSpeed, Switch {
    private Integer speed = 1;
    private String name;
    private Power power = Power.OFF;
    @ToString.Exclude
    private Map<Pattern, Command> commandMap;

    public Fan(String name) {
        this.name = name;
        this.commandMap = new HashMap<>();
        commandMap.put(
                Pattern.compile("turn (?<power>on|off) " + name, Pattern.CASE_INSENSITIVE),
                new SwitchCommand().setAppliance(this)
        );
        commandMap.put(
                Pattern.compile("set " + name + " speed to (?<speed>\\d)", Pattern.CASE_INSENSITIVE),
                new FanSpeedCommand().setAppliance(this)
        );
    }

    @Override
    public Map<Pattern, Command> getCommandMap() {
        return commandMap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CommandStatus updateFanSpeed(Integer speed) {
        if (power.equals(Power.OFF)) {
            return new CommandStatus(ExecutionStatus.FAILED, "Fan is off");
        }
        if (1 <= speed && speed <= 5) {
            this.speed = speed;
            return new CommandStatus(ExecutionStatus.SUCCESS, name + " speed changed to " + speed);
        } else {
            return new CommandStatus(ExecutionStatus.FAILED, "Fan speed must be between 1-5");
        }

    }

    @Override
    public CommandStatus updatePower(Power status) {
        power = status;
        return new CommandStatus(ExecutionStatus.SUCCESS, name + " turned " + power);
    }
}
