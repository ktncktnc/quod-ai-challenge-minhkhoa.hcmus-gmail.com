package ai.quod.challenge.Utils;

import ai.quod.challenge.GHArchiver.Event;
import ai.quod.challenge.GHProject.Org;
import ai.quod.challenge.GHProject.User;
import ai.quod.challenge.GHArchiver.payload.*;
import ai.quod.challenge.GHProject.Issue;
import ai.quod.challenge.GHProject.PullRequest;
import ai.quod.challenge.GHProject.Commit;
import ai.quod.challenge.GHProject.Push;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Parser {

    //Parse json to event data
    public static Event parse(String data){
        JSONObject jsonObject = new JSONObject(data);

        long id = Long.parseLong(jsonObject.getString("id"));
        Event.Type type = Event.getEventType(jsonObject.getString("type"));

        if (type == null)
            System.out.println("type = " + jsonObject.getString("type"));

        boolean isPublic = jsonObject.getBoolean("public");
        LocalDateTime createdAt = Utils.timeStringToLocalDateTime(jsonObject.getString("created_at"));

        JSONObject actorJson = jsonObject.getJSONObject("actor");
        JSONObject repoJson = jsonObject.getJSONObject("repo");
        JSONObject payloadJson = jsonObject.getJSONObject("payload");

        User actor = parseActor(actorJson);
        Org org = parseRepo(repoJson);

        return new Event(id, type, actor, org, payloadJson, isPublic, createdAt);
    }

    public static User parseActor(JSONObject data){
        long id = data.getLong("id");
        String username = data.getString("login");

        return new User(id, username);
    }

    public static Org parseRepo(JSONObject data){
        long id = data.getLong("id");
        String name = data.getString("name");
        String url = data.getString("url");

        return new Org(id, name, url);
    }

    //Push-------------

    public static Push parsePush(JSONObject payload, LocalDateTime time){
        long id = payload.getLong("push_id");
        int size = payload.getInt("size");
        String ref = payload.getString("ref");
        ArrayList<Commit> commits = new ArrayList<>();

        JSONArray commitsJson = payload.getJSONArray("commits");

        for(int i = 0; i < commitsJson.length(); i++){
            JSONObject commitJson = commitsJson.getJSONObject(i);

            Commit commit = parseCommit(commitJson);
            commits.add(commit);
        }

        return new Push(id, size, ref, commits, time);
    }

    public static Commit parseCommit(JSONObject commit){
        String sha = commit.getString("sha");
        String message = commit.getString("message");
        return new Commit(sha, message);
    }

    //Create------------

    public static CreatePayload parseCreate(JSONObject payload){
        String ref = null;
        if (!payload.isNull("ref"))
            ref = payload.getString("ref");

        CreatePayload.Type type = CreatePayload.getEventType(payload.getString("ref_type"));
        String masterBranch = payload.getString("master_branch");

        return new CreatePayload(ref, type, masterBranch);
    }

    //Delete------------

    public static DeletePayload parseDelete(JSONObject payload){
        String ref = payload.getString("ref");
        DeletePayload.Type type = DeletePayload.getEventType(payload.getString("ref_type"));

        return new DeletePayload(ref, type);
    }

    //Pull--------------

    public static PullRequestPayload parsePull(JSONObject payload){
        PullRequestPayload.Type type = PullRequestPayload.getPullType(payload.getString("action"));
        int number = payload.getInt("number");
        PullRequest request = parsePullRequest(payload.getJSONObject("pull_request"));

        return new PullRequestPayload(type, number, request);
    }

    public static PullRequest parsePullRequest(JSONObject jsonObject){
        long id = jsonObject.getLong("id");
        int number = jsonObject.getInt("number");
        boolean locked = jsonObject.getBoolean("locked");

        return new PullRequest(id, locked, number);
    }

    //Issue-------------

    public static IssuePayload parseIssuePayload(JSONObject payload){
        IssuePayload.Type type = IssuePayload.getType(payload.getString("action"));
        Issue issue = parseIssue(payload.getJSONObject("issue"));

        return new IssuePayload(type, issue);
    }

    public static Issue parseIssue(JSONObject jsonObject){
        long id = jsonObject.getLong("id");
        int number = jsonObject.getInt("number");
        LocalDateTime createdAt = Utils.timeStringToLocalDateTime(jsonObject.getString("created_at"));
        LocalDateTime closedAt = null;

        if (! jsonObject.isNull("closed_at"))
            closedAt = Utils.timeStringToLocalDateTime(jsonObject.getString("closed_at"));

        return new Issue(id, number, createdAt);
    }

    //Member------------

    public static MemberPayload parseMemberEvent(JSONObject payload){
        MemberPayload.Type type = MemberPayload.getType(payload.getString("action"));
        User user = parseUser(payload.getJSONObject("member"));

        return new MemberPayload(type, user);
    }

    public static User parseUser(JSONObject jsonObject){
        long id = jsonObject.getLong("id");
        String username = jsonObject.getString("login");

        return new User(id, username);
    }

}
