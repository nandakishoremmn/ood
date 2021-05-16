package org.smart.home.interfaces.device.property;

import org.smart.home.data.CommandStatus;

public interface Brightness extends ApplianceType {
    CommandStatus updateBrightness(Integer brightness);
}
