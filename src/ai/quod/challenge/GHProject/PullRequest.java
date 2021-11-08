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
}
