package org.smart.home;

import org.smart.home.interfaces.Home;

public class App {
    public static void main(String[] args) {
        Home home = new HomeImpl();

        home.createAppliance("hall fan");
        home.createAppliance("dining room fan");
        home.createAppliance("living room light");


        home.addDevice("Google Home", "activated by ok google");
        home.addDevice("Amazon Echo", "activated by alexa");

        home.addAppliance("ok google connect to hall fan");
        home.addAppliance("alexa connect to hall fan");
        home.addAppliance("ok google connect to living room light");
        home.addAppliance("alexa connect to living room light");

        home.printDevice("Google Home");

        home.sendCommand("ok google set hall fan speed to 4");
        home.sendCommand("ok google turn on hall fan");
        home.sendCommand("ok google set hall fan speed to 4");
        home.sendCommand("ok google set hall fan speed to 7");
        home.sendCommand("ok google turn on living room light");

        home.printDevice("Google Home");
        home.printDevice("Amazon Echo");

        home.sendCommand("alexa turn off living room light");

        home.printDevice("Amazon Echo");
        home.printDevice("Google Home");
    }
}
