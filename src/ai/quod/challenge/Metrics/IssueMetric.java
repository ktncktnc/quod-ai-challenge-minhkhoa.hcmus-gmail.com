package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Issue;
import ai.quod.challenge.GHProject.PullRequest;
import ai.quod.challenge.GHProject.Repository;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class IssueMetric {
    public static HashMap<Long, Float> averageClosedTime = new HashMap<>();
    public static HashMap<Long, Float> issueScores = new HashMap<>();

    public static float minimumClosedTime = Float.MAX_VALUE;
    private static float maximumClosedTime = -1f;

    public static void processData(){
        HashSet<Long> repositories = Database.getInstance().repoIds;

        for(Long repoID : repositories){
            Repository repository = Database.getInstance().get(repoID);

            int totalPull = repository.closedIssues.size() + repository.opendedIssues.size();

            if (totalPull == 0){
                averageClosedTime.put(repoID, 0.0f);
                continue;
            }

            long totalTime = 0;

            for(Map.Entry<Long, Issue> issueEntry : repository.closedIssues.entrySet()){
                Issue issue = issueEntry.getValue();
                totalTime += Duration.between(issue.getCreatedAt(), issue.getClosedAt()).getSeconds();
            }

            float time = (float)totalTime/(float)totalPull;

            if (time > 0f){
                if (time < minimumClosedTime)
                    minimumClosedTime = time;
                else if (time > maximumClosedTime)
                    maximumClosedTime = time;
            }
            averageClosedTime.put(repoID, time);
        }

        calculateScores();
    }

    public static void calculateScores(){
        float max = maximumClosedTime / minimumClosedTime;

        for(Map.Entry<Long, Float> entry : averageClosedTime.entrySet()){
            float value = entry.getValue();

            if(value == 0) {
                issueScores.put(entry.getKey(), 0f);
                continue;
            }

            value = (max - value / minimumClosedTime)/(max - 1f);

            issueScores.put(entry.getKey(), value);
        }
    }
}
