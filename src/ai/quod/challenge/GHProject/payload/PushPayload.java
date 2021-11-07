package ai.quod.challenge.GHProject.payload;

import ai.quod.challenge.GHProject.payload.push.Commit;

import java.util.ArrayList;

public class PushPayload extends Payload {
    long id;
    int size;
    ArrayList<Commit> commits;

    public PushPayload(long id, int size, ArrayList<Commit> commits) {
        this.id = id;
        this.size = size;
        this.commits = commits;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
                "id=" + id +
                ", size=" + size +
                ", commits=" + commits +
                '}';
    }
}
