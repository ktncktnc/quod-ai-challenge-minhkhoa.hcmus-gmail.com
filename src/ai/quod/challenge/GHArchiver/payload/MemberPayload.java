package ai.quod.challenge.GHArchiver.payload;

import ai.quod.challenge.GHArchiver.User;

import java.util.HashMap;

public class MemberPayload {
    public enum Type {
        Added
    }
    public static HashMap<String, Type> types = new HashMap<>();
    static{
        types.put("added", Type.Added);
    }

    public static Type getType(String str){
        return types.get(str);
    }

    Type action;
    User member;

    public MemberPayload(Type action, User member) {
        this.action = action;
        this.member = member;
    }

    public Type getAction() {
        return action;
    }

    public void setAction(Type action) {
        this.action = action;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "MemberPayload{" +
                "action=" + action +
                ", member=" + member +
                '}';
    }
}
