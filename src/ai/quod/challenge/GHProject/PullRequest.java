package ai.quod.challenge.GHProject;

import java.time.LocalDateTime;

public class PullRequest {
    public long id;
    boolean locked;
    boolean isOpen;
    int number;
    LocalDateTime openedTime;
    LocalDateTime closedTime;

    public PullRequest(long id, boolean locked, int number, LocalDateTime openedTime) {
        this.id = id;
        this.locked = locked;
        this.number = number;
        this.openedTime = openedTime;
        isOpen = true;
    }

    public PullRequest(long id, boolean locked, int number) {
        this.id = id;
        this.locked = locked;
        this.number = number;
        isOpen = true;
    }

    public void setOpen(LocalDateTime time){
        isOpen = true;
        this.openedTime = time;
    }

    public void setClosed(LocalDateTime closedTime){
        isOpen = false;
        this.closedTime = closedTime;
    }

    public boolean getIsOpen(){
        return this.isOpen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getOpenedTime() {
        return openedTime;
    }

    public void setOpenedTime(LocalDateTime openedTime) {
        this.openedTime = openedTime;
    }

    public LocalDateTime getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(LocalDateTime closedTime) {
        this.closedTime = closedTime;
    }

    @Override
    public String toString() {
        return "PullRequest{" +
                "id=" + id +
                ", locked=" + locked +
                ", isOpen=" + isOpen +
                ", number=" + number +
                ", openedTime=" + openedTime +
                ", closedTime=" + closedTime +
                '}';
    }
}
