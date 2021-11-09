package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Issue;
import ai.quod.challenge.GHProject.PullRequest;
import ai.quod.challenge.GHProject.Repository;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class IssueMetric {
    public static HashMap<Long, Float> averageClosedTime = new HashMap<>();
    public static void processData(){
        HashMap<Long, Repository> repositories = Database.getInstance().repositories;

        for(Map.Entry<Long, Repository> entry : repositories.entrySet()){
            Repository repository = entry.getValue();
            int totalPull = repository.closedIssues.size() + repository.opendedIssues.size();

            if (totalPull == 0){
                averageClosedTime.put(entry.getKey(), 0.0f);
                continue;
            }

            long totalTime = 0;

            for(Map.Entry<Long, Issue> issueEntry : repository.closedIssues.entrySet()){
                Issue issue = issueEntry.getValue();
                totalTime += Duration.between(issue.getCreatedAt(), issue.getClosedAt()).getSeconds();
            }

            averageClosedTime.put(entry.getKey(), (float)totalTime/(float)totalPull );
        }
    }
}
