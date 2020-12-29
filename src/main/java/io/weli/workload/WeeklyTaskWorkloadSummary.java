package io.weli.workload;

import java.util.Date;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class WeeklyTaskWorkloadSummary {
    private Task belongingTask;
    private Date startOfWeek;
    private Date endOfWeek;
    private int packageCount;

    public Task getBelongingTask() {
        return belongingTask;
    }

    public void setBelongingTask(Task belongingTask) {
        this.belongingTask = belongingTask;
    }

    public Date getStartOfWeek() {
        return startOfWeek;
    }

    public void setStartOfWeek(Date startOfWeek) {
        this.startOfWeek = startOfWeek;
    }

    public Date getEndOfWeek() {
        return endOfWeek;
    }

    public void setEndOfWeek(Date endOfWeek) {
        this.endOfWeek = endOfWeek;
    }

    public int getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }
}
