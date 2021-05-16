package org.scheduler;

import org.scheduler.enums.Algorithm;
import org.scheduler.job.Job;

import java.util.Arrays;
import java.util.List;

import static org.scheduler.enums.Priority.*;
import static org.scheduler.enums.UserType.*;

public class Driver {
    public static void main(String[] args) {
        List<Job> jobs = Arrays.asList(
                new Job("J1", 10, P0, 10, ROOT),
                new Job("J2", 20, P0, 40, ADMIN),
                new Job("J3", 15, P2, 40, ROOT),
                new Job("J4", 30, P1, 40, USER),
                new Job("J5", 10, P2, 30, USER)
        );

        Scheduler scheduler = new Scheduler();
        Arrays.stream(Algorithm.values())
                .forEach(algorithm -> scheduler.schedule(jobs, 2, algorithm));
    }
}
