package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.PullRequest;
import ai.quod.challenge.GHProject.Repository;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PullMetric {
    public static HashMap<Long, Float> averageMergedTime = new HashMap<>();
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

            averageMergedTime.put(entry.getKey(), (float)totalTime/(float)totalPull );
        }
    }
}
