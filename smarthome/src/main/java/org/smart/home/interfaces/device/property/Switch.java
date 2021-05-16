package org.smart.home.interfaces.device.property;

import org.smart.home.data.CommandStatus;
import org.smart.home.enums.Power;
import org.smart.home.interfaces.Command;

import java.util.regex.Pattern;

public interface Switch extends ApplianceType{
    CommandStatus updatePower(Power status);
}
