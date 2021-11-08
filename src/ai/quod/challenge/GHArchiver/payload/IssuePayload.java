package ai.quod.challenge.GHArchiver.payload;

import ai.quod.challenge.GHProject.Issue;

import java.util.HashMap;

public class IssuePayload {
    public enum Type {
        Opened, Closed
    }
    public static HashMap<String, Type> types = new HashMap<>();
    static{
        types.put("opened", Type.Opened);
        types.put("closed", Type.Closed);
    }

    public static Type getType(String str){
        return types.get(str);
    }

    Type type;
    Issue issue;

    public IssuePayload(Type type, Issue issue) {
        this.type = type;
        this.issue = issue;
    }

    public static HashMap<String, Type> getTypes() {
        return types;
    }

    public static void setTypes(HashMap<String, Type> types) {
        IssuePayload.types = types;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
