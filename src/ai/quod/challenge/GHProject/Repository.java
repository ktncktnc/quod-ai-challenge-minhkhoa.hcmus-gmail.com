package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.RepoInfo;
import ai.quod.challenge.GHArchiver.User;
import ai.quod.challenge.Metrics.PullMetric;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository {
    public long id;

    public RepoInfo info;

    public HashMap<Long, User> devs;
    public HashMap<Long, Push> pushes;

    public HashMap<Long, Issue> opendedIssues;
    public HashMap<Long, Issue> closedIssues;

    public HashMap<Long, PullRequest> openedPullRequests;
    public HashMap<Long, PullRequest> closedPullRequests;

    public ArrayList<String> branches;

    public LocalDateTime createdAt;

    public Repository(long id, LocalDateTime time, RepoInfo info, User actor) {
        this.id = id;
        this.createdAt = time;
        this.info = info;

        pushes = new HashMap<>();

        opendedIssues = new HashMap<>();
        closedIssues = new HashMap<>();

        openedPullRequests = new HashMap<>();
        closedPullRequests = new HashMap<>();

        branches = new ArrayList<>();
        devs = new HashMap<>();

        devs.put(actor.getId(), actor);
    }

    public void addPush(Push push){
        pushes.put(push.id, push);
    }

    public void addBranch(String branch){
        if (!branches.contains(branch)){
            branches.add(branch);
        }
    }

    public void removeBranch(String branch){
        branches.remove(branch);
    }

    public void openPullRequest(PullRequest pullRequest){
        openedPullRequests.put(pullRequest.id, pullRequest);
    }

    public void closePullRequest(long id, LocalDateTime time){
        PullRequest pullRequest = openedPullRequests.remove(id);

        if (pullRequest != null){
            pullRequest.setClosed(time);
            closedPullRequests.put(id, pullRequest);
        }
    }

    public void openIssue(Issue issue){
        opendedIssues.put(issue.id, issue);
    }

    public void closeIssue(Long id, LocalDateTime closedTime){
        Issue issue = opendedIssues.remove(id);
        if (issue != null){
            issue.setClosedAt(closedTime);
            closedIssues.put(id, issue);
        }
    }

    public void addDev(User dev){
        devs.put(dev.getId(), dev);
    }

    public void removeDev(long id){
        devs.remove(id);
    }
    
    public int totalCommits(){
        int size = 0;

        for(Map.Entry<Long, Push> entry : pushes.entrySet()){
            size += entry.getValue().commitSize();
        }

        return size;
    }

    public int totalDevs(){
        return devs.size();
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", info=" + info +
                ", devs=" + devs +
                ", pushes=" + pushes +
                ", opendedIssues=" + opendedIssues +
                ", closedIssues=" + closedIssues +
                ", openedPullRequests=" + openedPullRequests +
                ", closedPullRequests=" + closedPullRequests +
                ", branches=" + branches +
                ", createdAt=" + createdAt +
                '}';
    }
}
