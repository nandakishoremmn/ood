package org.pendency;

import org.pendency.ifaces.services.PendencySystem;
import org.pendency.factories.PendencySystemFactory;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        PendencySystem pendencySystem = PendencySystemFactory.get();

        pendencySystem.startTracking(1112, Arrays.asList("UPI", "Karnataka", "Bangalore"));
        pendencySystem.startTracking(2451, Arrays.asList("UPI", "Karnataka", "Mysore"));
        pendencySystem.startTracking(3421, Arrays.asList("UPI", "Rajasthan", "Jaipur"));
        pendencySystem.startTracking(1221, Arrays.asList("Wallet", "Karnataka", "Bangalore"));

        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI")));   // represents the counts of all pending "UPI" transaction)s
//        Output: 3
        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI", "Karnataka")));  // represents the counts of all pending "UPI" transactions in "Karnataka)"
//        Output: 2
        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI", "Karnataka", "Bangalore"))); // represents the counts of all pending "UPI" transactions in "Karnataka" and "Bangalore)"
//        Output: 1

        System.out.println(pendencySystem.getCounts(Arrays.asList("Bangalore"))); // Does not represent a valid hierarchy in the sampl)e
//        Output: 0

        pendencySystem.startTracking(4221, Arrays.asList("Wallet", "Karnataka", "Bangalore"));
        pendencySystem.stopTracking(1112);
        pendencySystem.stopTracking(2451);

        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI")));
//        Output: 1
        System.out.println(pendencySystem.getCounts(Arrays.asList("Wallet")));
//        Output: 2
        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI", "Karnataka", "Bangalore")));
//        Output: 0

        pendencySystem.startTracking(1112, Arrays.asList("UPI", "Maharastra", "Mumbai", "another tag"));

        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI", "Karnataka", "Bangalore")));
//        Output: 0
        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI", "Maharastra", "Mumbai", "another tag", "another tag2", "another tag3")));
//        Output: 1
        System.out.println(pendencySystem.getCounts(Arrays.asList("UPI", "Maharastra", "Mumbai")));
//        Output: 1
    }
}
