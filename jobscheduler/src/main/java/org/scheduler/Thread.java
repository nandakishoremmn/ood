package org.scheduler;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Thread implements Comparable<Thread> {
    private String jobId;
    @Getter
    private Integer endTime = 0;
    private Integer threadId;
    private List<String> jobs = new ArrayList<>();

    public Thread(Integer threadId) {
        this.threadId = threadId;
    }

    public void updateJob(String jobId, Integer endTime) {
        this.jobId = jobId;
        this.endTime = endTime;
        jobs.add(jobId);
    }

    @Override
    public int compareTo(Thread o) {
        if(this.endTime.equals(o.endTime)){
            return this.threadId.compareTo(o.threadId);
        }
        return this.endTime.compareTo(o.endTime);
    }

    @Override
    public String toString() {
        return "Thread{" +
                "threadId=T" + threadId +
                ", jobs=" + jobs +
                '}';
    }
}
