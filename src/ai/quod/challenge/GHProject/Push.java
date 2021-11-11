package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.payload.Payload;
import ai.quod.challenge.GHProject.Commit;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Push {
    long id;
    int size;
    ArrayList<Commit> commits;
    public String ref;
    LocalDateTime time;

    public Push(long id, int size, String ref, ArrayList<Commit> commits, LocalDateTime time) {
        this.id = id;
        this.size = size;
        this.ref = ref;
        this.commits = commits;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Push{" +
                "id=" + id +
                ", size=" + size +
                ", commits=" + commits +
                ", ref='" + ref + '\'' +
                ", time=" + time +
                '}';
    }

    public int commitSize(){
        return commits.size();
    }
}
