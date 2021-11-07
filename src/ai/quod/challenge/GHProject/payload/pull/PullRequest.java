package ai.quod.challenge.GHProject.payload.pull;

public class PullRequest {
    long id;
    boolean locked;
    int number;

    public PullRequest(long id, boolean locked, int number) {
        this.id = id;
        this.locked = locked;
        this.number = number;
    }
}
