package ai.quod.challenge.GHProject.payload;

import java.util.HashMap;

public class DeletePayload extends Payload {
    public enum Type {
        Branch, Repository
    }
    public static HashMap<String, Type> createTypes = new HashMap<>();
    static{
        createTypes.put("branch", Type.Branch);
        createTypes.put("repository", Type.Repository);
    }

    public static Type getEventType(String str){
        return createTypes.get(str);
    }

    String ref;
    Type refType;

    public DeletePayload(String ref, Type refType) {
        this.ref = ref;
        this.refType = refType;
    }

    public static HashMap<String, Type> getCreateTypes() {
        return createTypes;
    }

    public static void setCreateTypes(HashMap<String, Type> createTypes) {
        DeletePayload.createTypes = createTypes;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Type getRefType() {
        return refType;
    }

    public void setRefType(Type refType) {
        this.refType = refType;
    }

    @Override
    public String toString() {
        return "DeletePayload{" +
                "ref='" + ref + '\'' +
                ", refType=" + refType +
                '}';
    }
}
