package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.payload.Payload;

import java.time.LocalDateTime;

public class Issue extends Payload {
    long id;
    int number;
    boolean isOpen;
    LocalDateTime createdAt;
    LocalDateTime closedAt;

    public Issue(long id, int number){
        this.id = id;
        this.number = number;
    }

    public Issue(long id, int number, LocalDateTime createdAt) {
        this.id = id;
        this.number = number;
        isOpen = true;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", number=" + number +
                ", isOpen=" + isOpen +
                ", createdAt=" + createdAt +
                ", closedAt=" + closedAt +
                '}';
    }
}
