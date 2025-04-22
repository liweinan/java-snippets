package io.weli.workload;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TaskWorkloadSummary {
    private Task belongingTask;
    private int totalNumberOfPackages;
    private Map<Status, Long> workloadPerStatusInMinutes = new HashMap<>();

    public static void main(String[] args) {
        Map<Status, Long> workload = new HashMap<>();
        workload.put(Status.PENDING, 5L);
        workload.put(Status.RUNNING, 3L);
        workload.put(Status.COMPLETED, 10L);
        
        System.out.println("Workload summary: " + workload);
    }
    
    enum Status {
        PENDING,
        RUNNING,
        COMPLETED
    }

    public Task getBelongingTask() {
        return belongingTask;
    }

    public void setBelongingTask(Task belongingTask) {
        this.belongingTask = belongingTask;
    }

    public int getTotalNumberOfPackages() {
        return totalNumberOfPackages;
    }

    public void setTotalNumberOfPackages(int totalNumberOfPackages) {
        this.totalNumberOfPackages = totalNumberOfPackages;
    }
}
