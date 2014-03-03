package io.wildbee.workload;

import java.util.Date;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PackageWorkloadEntry {
    private User assignee;
    private Status belongingStatus;
    private Date startTime;
    private Date endTime;
    private Package belongingPackage;
    private WeeklyTaskWorkloadSummary belongingWeeklyTaskWorkloadSummary;


    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Status getBelongingStatus() {
        return belongingStatus;
    }

    public void setBelongingStatus(Status belongingStatus) {
        this.belongingStatus = belongingStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Package getBelongingPackage() {
        return belongingPackage;
    }

    public void setBelongingPackage(Package belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    public WeeklyTaskWorkloadSummary getBelongingWeeklyTaskWorkloadSummary() {
        return belongingWeeklyTaskWorkloadSummary;
    }

    public void setBelongingWeeklyTaskWorkloadSummary(WeeklyTaskWorkloadSummary belongingWeeklyTaskWorkloadSummary) {
        this.belongingWeeklyTaskWorkloadSummary = belongingWeeklyTaskWorkloadSummary;
    }
}
