package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHArchiver.HealthScore;
import ai.quod.challenge.Database;
import java.util.*;

public class HealthMetrics {
    public static HashMap<Long, HealthScore> healthScores = new HashMap<>();

    public static void process(){
        HashSet<Long> repositories = Database.getInstance().repoIds;

        for(Long repoID : repositories){
            //Commit rate
            float commitsPerDay = CommitMetric.commitsPerDay.get(repoID)/CommitMetric.maxCommitsPerDay;
            float commitsPerDev = CommitMetric.commitsPerDev.get(repoID)/CommitMetric.maxCommitsPerDev;

            //Time rate
            float averageMergedTime = PullMetric.pullScores.get(repoID);
            float averageClosedTime = IssueMetric.issueScores.get(repoID);

            HealthScore healthScore = new HealthScore(repoID, commitsPerDay, commitsPerDev, averageMergedTime, averageClosedTime);
            healthScores.put(repoID, healthScore);
        }
    }

    //Sort health scores list
    public static ArrayList<HealthScore> sortToList(){
        ArrayList<HealthScore> result = new ArrayList<>(healthScores.values());
        Collections.sort(result, Collections.reverseOrder());

        return result;
    }
}
