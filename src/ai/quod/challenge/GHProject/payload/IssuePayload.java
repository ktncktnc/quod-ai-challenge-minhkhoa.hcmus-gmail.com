package ai.quod.challenge.GHProject.payload;

import java.util.HashMap;

public class IssuePayload {
    enum Type{
        Closed, Opened
    }
    static HashMap<String, IssuePayload.Type> events;
    static{
        events.put("opened", Type.Opened);
        events.put("closed", Type.Closed);
    }

    IssuePayload.Type type;


}
