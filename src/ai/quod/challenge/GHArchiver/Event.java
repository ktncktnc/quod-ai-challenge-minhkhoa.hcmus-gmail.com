package ai.quod.challenge.GHArchiver;

import ai.quod.challenge.GHArchiver.payload.*;
import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.PullRequest;
import ai.quod.challenge.GHProject.Push;
import ai.quod.challenge.GHProject.Repository;
import ai.quod.challenge.utils.Parser;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Event {
    public enum Type {
        PushEvent, CreateEvent, DeleteEvent, PullRequestEvent, IssuesEvent, WatchEvent, ForkEvent,
        PullRequestReviewCommentEvent, PullRequestReviewEvent, IssueCommentEvent, GollumEvent, ReleaseEvent,
        PublicEvent, MemberEvent, CommitCommentEvent
    }
    public static HashMap<String, Type> eventTypes = new HashMap<>();
    static{
        eventTypes.put("PushEvent", Type.PushEvent);
        eventTypes.put("CreateEvent", Type.CreateEvent);
        eventTypes.put("DeleteEvent", Type.DeleteEvent);
        eventTypes.put("PullRequestEvent", Type.PullRequestEvent);
        eventTypes.put("IssuesEvent", Type.IssuesEvent);
        eventTypes.put("WatchEvent", Type.WatchEvent);
        eventTypes.put("ForkEvent", Type.ForkEvent);
        eventTypes.put("PullRequestReviewCommentEvent", Type.PullRequestReviewCommentEvent);
        eventTypes.put("PullRequestReviewEvent", Type.PullRequestReviewEvent);
        eventTypes.put("IssueCommentEvent", Type.IssueCommentEvent);
        eventTypes.put("GollumEvent", Type.GollumEvent);
        eventTypes.put("ReleaseEvent", Type.ReleaseEvent);
        eventTypes.put("PublicEvent", Type.PublicEvent);
        eventTypes.put("MemberEvent", Type.MemberEvent);
        eventTypes.put("CommitCommentEvent", Type.CommitCommentEvent);
    }

    public static Type getEventType(String eventStr){
        return eventTypes.get(eventStr);
    }

    long id;
    Type type;
    User actor;
    RepoInfo repoInfo;
    JSONObject payLoad;
    Boolean isPublic;
    LocalDateTime time;

    public Event(long id, Type type, User actor, RepoInfo repoInfo, JSONObject payLoad, Boolean isPublic, LocalDateTime time) {
        this.id = id;
        this.type = type;
        this.actor = actor;
        this.repoInfo = repoInfo;
        this.payLoad = payLoad;
        this.isPublic = isPublic;
        this.time = time;
    }

    public static HashMap<String, Type> getEventTypes() {
        return eventTypes;
    }

    public static void setEventTypes(HashMap<String, Type> eventTypes) {
        Event.eventTypes = eventTypes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public RepoInfo getRepo() {
        return repoInfo;
    }

    public void setRepo(RepoInfo repoInfo) {
        this.repoInfo = repoInfo;
    }

    public long getRepoID(){
        return this.repoInfo.getId();
    }

    public JSONObject getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(JSONObject payLoad) {
        this.payLoad = payLoad;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void handle(){
        switch (type){
            case PushEvent:
                handlePushEvent();
                break;
            case CreateEvent:
                handleCreateEvent();
                break;
            case DeleteEvent:
                handleDeleteEvent();
                break;
            case PullRequestEvent:
                handlePullRequestEvent();
                break;
            case IssuesEvent:
                handleIssuesEvent();
                break;
            case MemberEvent:
                handleMemberEvent();
                break;
        }
    }

    private Repository getRepositoryFromDB(){
        long repoID = getRepoID();
        Database database = Database.getInstance();
        return database.getRepo(repoID, time);
    }

    private void handlePushEvent(){
        Repository repository = getRepositoryFromDB();

        Push push = Parser.parsePush(getPayLoad());
        repository.addPush(push);
    }

    private void handleCreateEvent(){
        CreatePayload create = Parser.parseCreate(getPayLoad());

        if (create.getRefType() == CreatePayload.Type.Repository){
            Database.getInstance().createRepo(getRepoID(), time);
        }
        else if (create.getRefType() == CreatePayload.Type.Branch){
            Repository repository = getRepositoryFromDB();
            repository.addBranch(create.getRef());
        }
    }

    private void handleDeleteEvent(){
        DeletePayload delete = Parser.parseDelete(getPayLoad());

        if (delete.getRefType() == DeletePayload.Type.Repository){
            Database.getInstance().deleteRepo(getRepoID());
        }
        else if (delete.getRefType() == DeletePayload.Type.Branch){
            Repository repository = getRepositoryFromDB();
            repository.removeBranch(delete.getRef());
        }
    }

    private void handlePullRequestEvent(){
        Repository repository = getRepositoryFromDB();
        PullRequestPayload pull = Parser.parsePull(getPayLoad());

        if (pull.getType() == PullRequestPayload.Type.Opened){
            PullRequest request = pull.getRequest();
            request.setOpen(time);
            repository.openPullRequest(request);
        }
        else if (pull.getType() == PullRequestPayload.Type.Closed){
            repository.closePullRequest(pull.getRequest().id);
        }
    }

    private void handleIssuesEvent(){
        Repository repository = getRepositoryFromDB();
        IssuePayload issue = Parser.parseIssuePayload(getPayLoad());

        if (issue.getType() == IssuePayload.Type.Opened){
            repository.openIssue(issue.getIssue());
        }
        else if (issue.getType() == IssuePayload.Type.Closed){
            repository.closeIssue(issue.getIssue().getId());
        }
    }

    private void handleMemberEvent(){
        Repository repository = getRepositoryFromDB();

        MemberPayload member = Parser.parseMemberEvent(getPayLoad());

        if(member.getAction() == MemberPayload.Type.Added){
            repository.addDev(member.getMember());
        }
    }
}