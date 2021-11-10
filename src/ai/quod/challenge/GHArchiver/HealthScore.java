package ai.quod.challenge.GHArchiver;

import ai.quod.challenge.Metrics.HealthMetrics;
import ai.quod.challenge.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HealthScore implements Comparable<HealthScore> {
    public long id;
    public float health;
    public float commitsPerDay;
    public float commitsPerDev;
    public float averageTimeMergedPull;
    public float averageTimeClosedIssue;

    public static ArrayList<String> titles = new ArrayList<>();
    static {
        titles.add("org");
        titles.add("repo_name");
        titles.add("health_score");
        titles.add("num_commits");
        titles.add("commits_per_day");
        titles.add("commits_per_dev");
        titles.add("average_time_merged_pull");
        titles.add("average_time_closed_issue");
    }

    public HealthScore(long id, float commitsPerDay, float commitsPerDev, float averageTimeMergedPull, float averageTimeClosedIssue) {
        this.id = id;

        this.commitsPerDay = commitsPerDay;
        this.commitsPerDev = commitsPerDev;
        this.averageTimeMergedPull = averageTimeMergedPull;
        this.averageTimeClosedIssue = averageTimeClosedIssue;

        this.health = commitsPerDay + commitsPerDev + averageTimeClosedIssue + averageTimeMergedPull;
    }

    public ArrayList<Float> toArray(){
        ArrayList<Float> result = new ArrayList<>();
        result.add(health);
        result.add(commitsPerDay);
        result.add(commitsPerDev);
        result.add(averageTimeMergedPull);
        result.add(averageTimeClosedIssue);

        return result;
    }

    @Override
    public String toString() {
        return "HealthScore{" +
                "id=" + id +
                ", health=" + health +
                ", commitsPerDay=" + commitsPerDay +
                ", commitsPerDev=" + commitsPerDev +
                ", averageTimeMergedPull=" + averageTimeMergedPull +
                ", averageTimeClosedIssue=" + averageTimeClosedIssue +
                '}';
    }

    @Override
    public int compareTo(HealthScore o) {
        if (this.health - o.health > Utils.EPSILON) return 1;
        if (Math.abs(this.health - o.health) < Utils.EPSILON) return 0;
        return -1;
    }
}
