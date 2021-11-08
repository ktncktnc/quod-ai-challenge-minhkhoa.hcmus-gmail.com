package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Repository {
    public long id;

    public HashMap<Long, User> devs;
    public HashMap<Long, Push> pushes;

    public HashMap<Long, Issue> opendedIssues;
    public HashMap<Long, Issue> closedIssues;

    public HashMap<Long, PullRequest> openedPullRequests;
    public HashMap<Long, PullRequest> closedPullRequests;

    public ArrayList<String> branches;

    public LocalDateTime createdAt;

    public Repository(long id, LocalDateTime time) {
        this.id = id;
        this.createdAt = time;

        pushes = new HashMap<>();

        opendedIssues = new HashMap<>();
        closedIssues = new HashMap<>();

        openedPullRequests = new HashMap<>();
        closedPullRequests = new HashMap<>();

        branches = new ArrayList<>();
        devs = new HashMap<>();
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

    public void closePullRequest(long id){
        closedPullRequests.put(id, openedPullRequests.remove(id));
    }

    public void openIssue(Issue issue){
        opendedIssues.put(issue.id, issue);
    }

    public void closeIssue(Long id){
        closedIssues.put(id, opendedIssues.remove(id));
    }

    public void addDev(User dev){
        devs.put(dev.getId(), dev);
    }

    public void removeDev(long id){
        devs.remove(id);
    }
}
