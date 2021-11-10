package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.PullRequest;
import ai.quod.challenge.GHProject.Repository;

import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PullMetric {
    public static HashMap<Long, Float> averageMergedTime = new HashMap<>();
    public static HashMap<Long, Float> pullScores = new HashMap<>();

    public static float minimumMergedTime = Float.MAX_VALUE;
    private static float maxMergedTime = -1f;

    public static void processData(){
        HashMap<Long, Repository> repositories = Database.getInstance().repositories;

        for(Map.Entry<Long, Repository> entry : repositories.entrySet()){
            Repository repository = entry.getValue();
            int totalPull = repository.closedPullRequests.size() + repository.openedPullRequests.size();
            if (totalPull == 0){
                averageMergedTime.put(entry.getKey(), 0.0f);
                continue;
            }

            long totalTime = 0;

            for(Map.Entry<Long, PullRequest> pullEntry : repository.closedPullRequests.entrySet()){
                PullRequest pullRequest = pullEntry.getValue();
                totalTime += Duration.between(pullRequest.getOpenedTime(), pullRequest.getClosedTime()).getSeconds();
            }

            float time = (float)totalTime/(float)totalPull;
            if (time > 0f){
                if(time < minimumMergedTime)
                    minimumMergedTime = time;
                else if (time > maxMergedTime)
                    maxMergedTime = time;

            }

            averageMergedTime.put(entry.getKey(), time);
        }

        calculateScores();
    }

    public static void calculateScores(){
        float max = maxMergedTime / minimumMergedTime;

        for(Map.Entry<Long, Float> entry : averageMergedTime.entrySet()){
            float value = entry.getValue();

            if(value == 0) {
                pullScores.put(entry.getKey(), 0f);
                continue;
            }

            value = (max - value / minimumMergedTime)/(max - 1f);

            pullScores.put(entry.getKey(), value);
        }
    }
}
