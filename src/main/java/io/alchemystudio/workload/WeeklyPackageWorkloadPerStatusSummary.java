package io.alchemystudio.workload;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class WeeklyPackageWorkloadPerStatusSummary {

    private WeeklyPerPackageWorkloadSummary belongingWeeklyPerPackageWorkloadSummary;
    private Status belongingStatus;
    private User packageAssignee;
    private long workloadPerStatusInMinutes;

    public WeeklyPerPackageWorkloadSummary getBelongingWeeklyPerPackageWorkloadSummary() {
        return belongingWeeklyPerPackageWorkloadSummary;
    }

    public void setBelongingWeeklyPerPackageWorkloadSummary(WeeklyPerPackageWorkloadSummary belongingWeeklyPerPackageWorkloadSummary) {
        this.belongingWeeklyPerPackageWorkloadSummary = belongingWeeklyPerPackageWorkloadSummary;
    }

    public Status getBelongingStatus() {
        return belongingStatus;
    }

    public void setBelongingStatus(Status belongingStatus) {
        this.belongingStatus = belongingStatus;
    }

    public User getPackageAssignee() {
        return packageAssignee;
    }

    public void setPackageAssignee(User packageAssignee) {
        this.packageAssignee = packageAssignee;
    }

    public long getWorkloadPerStatusInMinutes() {
        return workloadPerStatusInMinutes;
    }

    public void setWorkloadPerStatusInMinutes(long workloadPerStatusInMinutes) {
        this.workloadPerStatusInMinutes = workloadPerStatusInMinutes;
    }
}
