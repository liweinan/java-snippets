package io.alchemystudio.workload;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class WeeklyPerPackageWorkloadSummary {
    private WeeklyTaskWorkloadSummary belongingWeeklyTaskWorkloadSummary;
    private Package belongingPackage;
    private User packageAssignee;
    private long workloadInMinutes;

    public WeeklyTaskWorkloadSummary getBelongingWeeklyTaskWorkloadSummary() {
        return belongingWeeklyTaskWorkloadSummary;
    }

    public void setBelongingWeeklyTaskWorkloadSummary(WeeklyTaskWorkloadSummary belongingWeeklyTaskWorkloadSummary) {
        this.belongingWeeklyTaskWorkloadSummary = belongingWeeklyTaskWorkloadSummary;
    }

    public Package getBelongingPackage() {
        return belongingPackage;
    }

    public void setBelongingPackage(Package belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    public User getPackageAssignee() {
        return packageAssignee;
    }

    public void setPackageAssignee(User packageAssignee) {
        this.packageAssignee = packageAssignee;
    }

    public long getWorkloadInMinutes() {
        return workloadInMinutes;
    }

    public void setWorkloadInMinutes(long workloadInMinutes) {
        this.workloadInMinutes = workloadInMinutes;
    }
}
