package io.alchemystudio.workload;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class TaskWorkloadSummary {
    private Task belongingTask;
    private int totalNumberOfPackages;
    private Map<Status, Long> workloadPerStatusInMinutes = new HashMap();


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
