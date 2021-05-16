package org.scheduler.job;


import lombok.Getter;
import org.scheduler.enums.Priority;
import org.scheduler.enums.UserType;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Job implements Comparable<Job> {
    private Integer id;
    private String name;
    private Integer duration;
    private Integer priority;
    private Integer deadline;
    private Integer userType;

    private static AtomicInteger counter = new AtomicInteger(0);

    public Job(Job job) {
        this.id = job.id;
        this.name = job.name;
        this.duration = job.duration;
        this.priority = job.priority;
        this.deadline = job.deadline;
        this.userType = job.userType;
    }

    public Job(String name, int duration, Priority priority, int deadline, UserType userType) {
        this.id = counter.getAndIncrement();
        this.name = name;
        this.duration = duration;
        this.priority = priority.ordinal();
        this.deadline = deadline;
        this.userType = userType.ordinal();
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", priority=" + priority +
                ", deadline=" + deadline +
                ", userType=" + userType +
                '}';
    }

    @Override
    public int compareTo(Job o) {
        return 0;
    }
}
