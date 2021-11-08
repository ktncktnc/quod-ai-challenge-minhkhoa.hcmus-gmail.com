package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.payload.Payload;
import ai.quod.challenge.GHProject.Commit;

import java.util.ArrayList;

public class Push extends Payload {
    long id;
    int size;
    ArrayList<Commit> commits;
    public String ref;

    public Push(long id, int size, String ref, ArrayList<Commit> commits) {
        this.id = id;
        this.size = size;
        this.ref = ref;
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
