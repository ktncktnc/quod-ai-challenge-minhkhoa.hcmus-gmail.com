package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHArchiver.HealthScore;
import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Repository;

import java.util.*;

public class HealthMetrics {
    public static HashMap<Long, HealthScore> healthScores = new HashMap<>();

    public static void process(){
        HashMap<Long, Repository> repositories = Database.getInstance().repositories;

        for(Map.Entry<Long, Repository> entry : repositories.entrySet()){
            long id = entry.getValue().id;

            float commitsPerDay = CommitMetric.commitsPerDay.get(id)/CommitMetric.maxCommitsPerDay;
            float commitsPerDev = CommitMetric.commitsPerDev.get(id)/CommitMetric.maxCommitsPerDev;
            float averageMergedTime = PullMetric.pullScores.get(id);
            float averageClosedTime = IssueMetric.issueScores.get(id);

            HealthScore healthScore = new HealthScore(id, commitsPerDay, commitsPerDev, averageMergedTime, averageClosedTime);
            healthScores.put(id, healthScore);
        }
    }

    public static ArrayList<HealthScore> sortToList(){
        ArrayList<HealthScore> result = new ArrayList<>(healthScores.values());
        Collections.sort(result, Collections.reverseOrder());

        return result;
    }
}
