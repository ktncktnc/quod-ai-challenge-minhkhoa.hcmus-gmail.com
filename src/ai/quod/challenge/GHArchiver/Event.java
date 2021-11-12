package ai.quod.challenge.GHArchiver;

import ai.quod.challenge.Database;
import ai.quod.challenge.GHArchiver.payload.*;
import ai.quod.challenge.GHProject.*;
import ai.quod.challenge.Utils.Parser;
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
    Org org;
    JSONObject payLoad;
    Boolean isPublic;
    LocalDateTime time;

    public Event(long id, Type type, User actor, Org org, JSONObject payLoad, Boolean isPublic, LocalDateTime time) {
        this.id = id;
        this.type = type;
        this.actor = actor;
        this.org = org;
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

    public Org getRepo() {
        return org;
    }

    public void setRepo(Org org) {
        this.org = org;
    }

    public long getRepoID(){
        return this.org.getId();
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

    private Repository getRepositoryFromDBIfNull(){
        long repoID = getRepoID();
        Database database = Database.getInstance();

        Repository repository = database.get(repoID);

        if (repository == null){
            repository = Database.getInstance().insert(getRepoID(), time, org, actor);
        }

        return repository;
    }

    private void handlePushEvent(){
        Repository repository = getRepositoryFromDBIfNull();

        Push push = Parser.parsePush(getPayLoad(), getTime());
        repository.addPush(push);

        Database.getInstance().insert(repository);
    }

    private void handleCreateEvent(){
        CreatePayload create = Parser.parseCreate(getPayLoad());

        if (create.getRefType() == CreatePayload.Type.Repository){
            Database.getInstance().insert(getRepoID(), time, org, actor);
        }
        else if (create.getRefType() == CreatePayload.Type.Branch){
            Repository repository = getRepositoryFromDBIfNull();

            repository.addBranch(create.getRef());
            Database.getInstance().insert(repository);
        }

    }

    private void handleDeleteEvent(){
        DeletePayload delete = Parser.parseDelete(getPayLoad());

        if (delete.getRefType() == DeletePayload.Type.Repository){
            Database.getInstance().delete(getRepoID());
        }
        else if (delete.getRefType() == DeletePayload.Type.Branch){
            Repository repository = getRepositoryFromDBIfNull();
            repository.removeBranch(delete.getRef());

            Database.getInstance().insert(repository);
        }
    }

    private void handlePullRequestEvent(){
        Repository repository = getRepositoryFromDBIfNull();
        PullRequestPayload pull = Parser.parsePull(getPayLoad());

        if (pull.getType() == PullRequestPayload.Type.Opened){
            PullRequest request = pull.getRequest();
            request.setOpen(time);
            repository.openPullRequest(request);
        }
        else if (pull.getType() == PullRequestPayload.Type.Closed){
            repository.closePullRequest(pull.getRequest().id, getTime());
        }

        Database.getInstance().insert(repository);
    }

    private void handleIssuesEvent(){
        Repository repository = getRepositoryFromDBIfNull();
        IssuePayload issue = Parser.parseIssuePayload(getPayLoad());

        if (issue.getType() == IssuePayload.Type.Opened){
            repository.openIssue(issue.getIssue());
        }
        else if (issue.getType() == IssuePayload.Type.Closed){
            repository.closeIssue(issue.getIssue().getId(), getTime());
        }

        Database.getInstance().insert(repository);
    }

    private void handleMemberEvent(){
        Repository repository = getRepositoryFromDBIfNull();

        MemberPayload member = Parser.parseMemberEvent(getPayLoad());

        if(member.getAction() == MemberPayload.Type.Added){
            repository.addDev(member.getMember());
        }

        Database.getInstance().insert(repository);
    }
}
