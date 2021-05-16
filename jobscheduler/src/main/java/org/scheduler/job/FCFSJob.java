package org.scheduler.job;

public class FCFSJob extends Job implements Comparable<Job> {
    public FCFSJob(Job job) {
        super(job);
    }

    @Override
    public int compareTo(Job o) {
        return this.getId().compareTo(o.getId());
    }
}
