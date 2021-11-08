package ai.quod.challenge.GHArchiver.payload;

import ai.quod.challenge.GHProject.PullRequest;

import java.util.HashMap;

public class PullRequestPayload extends Payload{
    public enum Type {
        Opened, Closed
    }
    public static HashMap<String, Type> pullTypes = new HashMap<>();
    static{
        pullTypes.put("opened", Type.Opened);
        pullTypes.put("closed", Type.Closed);
    }

    public static Type getPullType(String str){
        return pullTypes.get(str);
    }

    Type type;
    int number;
    PullRequest request;

    public PullRequestPayload(Type type, int number, PullRequest request) {
        this.type = type;
        this.number = number;
        this.request = request;
    }

    public static HashMap<String, Type> getPullTypes() {
        return pullTypes;
    }

    public static void setPullTypes(HashMap<String, Type> pullTypes) {
        PullRequestPayload.pullTypes = pullTypes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PullRequest getRequest() {
        return request;
    }

    public void setRequest(PullRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "PullPayload{" +
                "type=" + type +
                ", number=" + number +
                ", request=" + request +
                '}';
    }
}
