package org.scheduler.job;

public class SJFJob extends Job implements Comparable<Job> {
    public SJFJob(Job job) {
        super(job);
    }

    @Override
    public int compareTo(Job o) {
        if(this.getDuration().equals(o.getDuration())){
            return this.getPriority().compareTo(o.getPriority());
        }
        return this.getDuration().compareTo(o.getDuration());
    }
}
