package org.scheduler;

import org.scheduler.enums.Algorithm;
import org.scheduler.job.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.scheduler.enums.Algorithm.*;

public class Scheduler {
    private static final Map<Algorithm, Function<Job, Job>> algoMap = new HashMap<Algorithm, Function<Job, Job>>() {{
        put(EDF, EDFJob::new);
        put(SJF, SJFJob::new);
        put(FCFS, FCFSJob::new);
        put(FPS, FPSJob::new);
    }};

    public void schedule(List<Job> jobs, int nThreads, Algorithm alogrithm) {
        Queue<Job> jobQueue = jobs.stream()
                .map(algoMap.get(alogrithm))
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));

        simulate(nThreads, alogrithm, jobQueue);

    }

    private void simulate(int nThreads, Algorithm alogrithm, Queue<Job> jobQueue) {
        int time;
        List<Thread> threads = IntStream.range(0, nThreads).
                mapToObj(i -> new Thread(i + 1))
                .collect(Collectors.toList());

        while (!jobQueue.isEmpty()) {
            Thread thread = threads.stream().min(Thread::compareTo).get();
            Job job = jobQueue.remove();
            time = thread.getEndTime();
            if (job.getDeadline() <= time) {
                continue;
            }
            thread.updateJob(job.getName(), time + job.getDuration());
        }

        System.out.println(alogrithm);
        threads.forEach(System.out::println);
        System.out.println();
    }

}
