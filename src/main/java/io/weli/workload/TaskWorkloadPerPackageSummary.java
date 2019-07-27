package io.weli.workload;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 *
 * TrackTime
 * ------
id            | integer | not null default nextval('track_times_id_seq'::regclass)
status_id     | integer |
package_id    | integer |
time_consumed | integer |
 */
public class TaskWorkloadPerPackageSummary {

    private TaskWorkloadSummary belongingTaskWorkloadSummary;
    private Package belongingPackage;
//    private Map<Status, Long> workloadPerStatusInMinutes = new HashMap();
    private Status belongingStatus;
    private long workloadInMinutes;


    public TaskWorkloadSummary getBelongingTaskWorkloadSummary() {
        return belongingTaskWorkloadSummary;
    }

    public void setBelongingTaskWorkloadSummary(TaskWorkloadSummary belongingTaskWorkloadSummary) {
        this.belongingTaskWorkloadSummary = belongingTaskWorkloadSummary;
    }

    public Package getBelongingPackage() {
        return belongingPackage;
    }

    public void setBelongingPackage(Package belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    public Status getBelongingStatus() {
        return belongingStatus;
    }

    public void setBelongingStatus(Status belongingStatus) {
        this.belongingStatus = belongingStatus;
    }

    public long getWorkloadInMinutes() {
        return workloadInMinutes;
    }

    public void setWorkloadInMinutes(long workloadInMinutes) {
        this.workloadInMinutes = workloadInMinutes;
    }
}
