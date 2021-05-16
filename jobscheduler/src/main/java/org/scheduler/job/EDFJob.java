package org.scheduler.job;

public class EDFJob extends Job implements Comparable<Job> {
    public EDFJob(Job job) {
        super(job);
    }

    @Override
    public int compareTo(Job o) {
        if(!this.getDeadline().equals(o.getDeadline())){
            return this.getDeadline().compareTo(o.getDeadline());
        }
        if(!this.getPriority().equals(o.getPriority())){
            return this.getPriority().compareTo(o.getPriority());
        }
        return this.getDuration().compareTo(o.getDuration());
    }
}
