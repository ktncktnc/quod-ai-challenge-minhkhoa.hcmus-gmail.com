package ai.quod.challenge.GHProject;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Database {
    private static Database instance = null;
    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public HashMap<Long, Repository> repositories;
    private Database() {
        repositories = new HashMap<>();
    }

    public Repository getRepo(long repoID, LocalDateTime time){
        Repository repo = repositories.get(repoID);

        if (repo == null){
            repo = new Repository(repoID, time);
            repositories.put(repoID, repo);
        }

        return repo;
    }

    public void createRepo(long repoID, LocalDateTime time){
        repositories.put(repoID, new Repository(repoID, time));
    }

    public boolean deleteRepo(long repoID){
        Repository repository =  repositories.remove(repoID);
        return repository != null;
    }
}
