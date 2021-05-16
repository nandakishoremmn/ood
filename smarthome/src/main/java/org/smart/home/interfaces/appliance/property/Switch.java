package org.smart.home.interfaces.appliance.property;

import org.smart.home.data.CommandStatus;
import org.smart.home.enums.Power;

public interface Switch extends ApplianceType{
    CommandStatus updatePower(Power status);
}
