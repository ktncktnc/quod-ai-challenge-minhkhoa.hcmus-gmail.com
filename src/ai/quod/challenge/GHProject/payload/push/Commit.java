package ai.quod.challenge.GHProject.payload.push;

public class Commit {
    String sha;
    String message;

    public Commit(String sha, String message) {
        this.sha = sha;
        this.message = message;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "sha='" + sha + '\'' +
                '}';
    }
}
