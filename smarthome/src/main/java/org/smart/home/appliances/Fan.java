package org.smart.home.appliances;

import lombok.ToString;
import org.smart.home.data.CommandStatus;
import org.smart.home.enums.ExecutionStatus;
import org.smart.home.enums.Power;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.appliance.property.FanSpeed;
import org.smart.home.interfaces.appliance.property.Switch;

@ToString
public class Fan implements Appliance, FanSpeed, Switch {
    private Integer speed = 1;
    private final String name;
    private Power power = Power.OFF;

    public Fan(String name) {
        this.name = name;
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
