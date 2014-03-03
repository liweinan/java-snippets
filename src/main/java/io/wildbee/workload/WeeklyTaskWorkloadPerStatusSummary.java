package io.wildbee.workload;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class WeeklyTaskWorkloadPerStatusSummary {

    private WeeklyTaskWorkloadSummary belongingWeeklyTaskWorkloadSummary;
    private Status belongingStatus;
    private long workloadPerStatusInMinutes;

    public WeeklyTaskWorkloadSummary getBelongingWeeklyTaskWorkloadSummary() {
        return belongingWeeklyTaskWorkloadSummary;
    }

    public void setBelongingWeeklyTaskWorkloadSummary(WeeklyTaskWorkloadSummary belongingWeeklyTaskWorkloadSummary) {
        this.belongingWeeklyTaskWorkloadSummary = belongingWeeklyTaskWorkloadSummary;
    }

    public Status getBelongingStatus() {
        return belongingStatus;
    }

    public void setBelongingStatus(Status belongingStatus) {
        this.belongingStatus = belongingStatus;
    }

    public long getWorkloadPerStatusInMinutes() {
        return workloadPerStatusInMinutes;
    }

    public void setWorkloadPerStatusInMinutes(long workloadPerStatusInMinutes) {
        this.workloadPerStatusInMinutes = workloadPerStatusInMinutes;
    }
}
