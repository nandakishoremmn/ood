package org.smart.home.interfaces;

import org.smart.home.data.CommandStatus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    CommandStatus execute();
    Command init(Matcher matcher);
    Command setAppliance(Appliance appliance);
    static Pattern getPattern(List<String> names) {
        throw new NotImplementedException();
    }
}
