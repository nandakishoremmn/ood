package org.smart.home.appliances;

import lombok.ToString;
import org.smart.home.interfaces.Command;
import org.smart.home.commands.SwitchCommand;
import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.enums.Power;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.device.property.Switch;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@ToString
public class Light implements Appliance, Switch {
    private String name;
    private Power power = Power.OFF;
    @ToString.Exclude
    private Map<Pattern, Command> commandMap;

    public Light(String name) {
        this.name = name;
        commandMap = new HashMap<>();
        commandMap.put(
                Pattern.compile("turn (?<power>on|off) " + name, Pattern.CASE_INSENSITIVE),
                new SwitchCommand().setAppliance(this)
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
    public CommandStatus updatePower(Power status) {
        power = status;
        return new CommandStatus(ExecutionStatus.SUCCESS, name + " turned " + power);
    }
}
