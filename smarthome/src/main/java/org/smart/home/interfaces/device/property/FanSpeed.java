package org.smart.home.interfaces.device.property;

import org.smart.home.data.CommandStatus;
import org.smart.home.interfaces.Command;

import java.util.regex.Pattern;

public interface FanSpeed {
    CommandStatus updateFanSpeed(Integer speed);
}