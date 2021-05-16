package org.smart.home.interfaces;

import org.smart.home.data.CommandStatus;
import org.smart.home.interfaces.appliance.property.ApplianceType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    CommandStatus execute();
    Command init(Matcher matcher);
    Command setAppliance(Appliance appliance);
}
