package org.smart.home.interfaces.appliance.property;

import org.smart.home.data.CommandStatus;

public interface FanSpeed extends ApplianceType {
    CommandStatus updateFanSpeed(Integer speed);
}
