package org.smart.home.interfaces;

import org.smart.home.data.CommandStatus;

import java.util.regex.Matcher;

public interface Command {
    CommandStatus execute();
    Command init(Matcher matcher);
    Command setAppliance(Appliance appliance);
}
