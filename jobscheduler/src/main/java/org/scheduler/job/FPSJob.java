package org.scheduler.job;

public class FPSJob extends Job implements Comparable<Job> {
    public FPSJob(Job job) {
        super(job);
    }

    @Override
    public int compareTo(Job o) {
        if (!this.getPriority().equals(o.getPriority())) {
            return this.getPriority().compareTo(o.getPriority());
        }
        if (!this.getUserType().equals(o.getUserType())) {
            return this.getUserType().compareTo(o.getUserType());
        }

        return o.getDuration().compareTo(this.getDuration());
    }
}
