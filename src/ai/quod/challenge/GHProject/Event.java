package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHProject.payload.Payload;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Event {
    public enum Type {
        PushEvent, CreateEvent, DeleteEvent, PullRequestEvent
    }
    public static HashMap<String, Type> eventTypes = new HashMap<>();
    static{
        eventTypes.put("PushEvent", Type.PushEvent);
        eventTypes.put("CreateEvent", Type.CreateEvent);
        eventTypes.put("DeleteEvent", Type.DeleteEvent);
        eventTypes.put("PullRequestEvent", Type.PullRequestEvent);
    }

    public static Type getEventType(String eventStr){
        return eventTypes.get(eventStr);
    }

    long id;
    Type type;
    User actor;
    Repo repo;
    Payload payLoad;
    Boolean isPublic;
    LocalDateTime time;

    public Event(long id, Type type, User actor, Repo repo, Payload payLoad, Boolean isPublic, LocalDateTime time) {
        this.id = id;
        this.type = type;
        this.actor = actor;
        this.repo = repo;
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

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public long getRepoID(){
        return this.repo.getId();
    }

    public Payload getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(Payload payLoad) {
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
}
