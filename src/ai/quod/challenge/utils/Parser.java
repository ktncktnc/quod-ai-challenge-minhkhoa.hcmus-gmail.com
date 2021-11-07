package ai.quod.challenge.utils;

import ai.quod.challenge.GHProject.*;
import ai.quod.challenge.GHProject.payload.*;
import ai.quod.challenge.GHProject.payload.pull.PullRequest;
import ai.quod.challenge.GHProject.payload.push.Commit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Parser {


    public static Event parse(String data){
        JSONObject jsonObject = new JSONObject(data);

        long id = Long.parseLong(jsonObject.getString("id"));
        Event.Type type = Event.getEventType(jsonObject.getString("type"));

        boolean isPublic = jsonObject.getBoolean("public");
        LocalDateTime createdAt = Utils.timeStringToLocalDateTime(jsonObject.getString("created_at"));

        JSONObject actorJson = jsonObject.getJSONObject("actor");
        JSONObject repoJson = jsonObject.getJSONObject("repo");
        JSONObject payloadJson = jsonObject.getJSONObject("payload");

        User actor = parseActor(actorJson);
        Repo repo = parseRepo(repoJson);
        Payload payLoad = parsePayload(payloadJson, type);

        System.out.println(payLoad);

        return new Event(id, type, actor, repo, payLoad, isPublic, createdAt);

    }

    public static User parseActor(JSONObject data){
        long id = data.getLong("id");
        String username = data.getString("login");

        return new User(id, username);
    }

    public static Repo parseRepo(JSONObject data){
        long id = data.getLong("id");
        String name = data.getString("name");
        String url = data.getString("url");

        return new Repo(id, name, url);
    }

    public static Payload parsePayload(JSONObject payloadJson, Event.Type type){
        Payload payLoad = null;
        switch (type){
            case PushEvent:
                payLoad = parsePushPayload(payloadJson);
                break;
            case CreateEvent:
                payLoad = parseCreatePayload(payloadJson);
                break;
            case DeleteEvent:
                payLoad = parseDeletePayload(payloadJson);
                break;
            case PullRequestEvent:
                payLoad = parsePullPayload(payloadJson);
                break;
        }

        return payLoad;
    }

    public static PushPayload parsePushPayload(JSONObject payload){
        long id = payload.getLong("push_id");
        int size = payload.getInt("size");
        ArrayList<Commit> commits = new ArrayList<>();

        JSONArray commitsJson = payload.getJSONArray("commits");

        for(int i = 0; i < commitsJson.length(); i++){
            JSONObject commitJson = commitsJson.getJSONObject(i);

            Commit commit = parseCommit(commitJson);
            if (commit != null) commits.add(commit);
        }

        return new PushPayload(id, size, commits);
    }

    public static Commit parseCommit(JSONObject commit){
        String sha = commit.getString("sha");
        String message = commit.getString("message");
        return new Commit(sha, message);
    }

    public static CreatePayload parseCreatePayload(JSONObject payload){
        String ref = payload.getString("ref");
        CreatePayload.Type type = CreatePayload.getEventType(payload.getString("ref_type"));
        String masterBranch = payload.getString("master_branch");

        return new CreatePayload(ref, type, masterBranch);
    }

    public static DeletePayload parseDeletePayload(JSONObject payload){
        String ref = payload.getString("ref");
        DeletePayload.Type type = DeletePayload.getEventType(payload.getString("ref_type"));

        return new DeletePayload(ref, type);
    }

    public static PullPayload parsePullPayload(JSONObject payload){
        PullPayload.Type type = PullPayload.getPullType(payload.getString("action"));
        int number = payload.getInt("number");
        PullRequest request = parsePullRequest(payload.getJSONObject("pull_request"));

        return new PullPayload(type, number, request);
    }

    public static PullRequest parsePullRequest(JSONObject jsonObject){
        long id = jsonObject.getLong("id");
        int number = jsonObject.getInt("number");
        boolean locked = jsonObject.getBoolean("locked");

        return new PullRequest(id, locked, number);
    }

}
