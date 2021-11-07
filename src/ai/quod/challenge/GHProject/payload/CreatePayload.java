package ai.quod.challenge.GHProject.payload;

import java.util.HashMap;

public class CreatePayload extends Payload {
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
    String masterBranch;

    public CreatePayload(String ref, Type refType, String masterBranch) {
        this.ref = ref;
        this.refType = refType;
        this.masterBranch = masterBranch;
    }

    public static HashMap<String, Type> getCreateTypes() {
        return createTypes;
    }

    public static void setCreateTypes(HashMap<String, Type> createTypes) {
        CreatePayload.createTypes = createTypes;
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

    public String getMasterBranch() {
        return masterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        this.masterBranch = masterBranch;
    }

    @Override
    public String toString() {
        return "CreatePayLoad{" +
                "ref='" + ref + '\'' +
                ", refType=" + refType +
                ", masterBranch='" + masterBranch + '\'' +
                '}';
    }
}
