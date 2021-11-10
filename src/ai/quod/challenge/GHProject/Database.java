package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.RepoInfo;
import ai.quod.challenge.GHArchiver.User;

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

    public Repository getRepo(long repoID){
        return repositories.get(repoID);
    }

    public void createRepo(long repoID, LocalDateTime time, RepoInfo info, User actor){
        repositories.put(repoID, new Repository(repoID, time, info, actor));
    }

    public Repository createAndGetRepo(long repoID, LocalDateTime time, RepoInfo repo, User actor){
        Repository repository = new Repository(repoID, time, repo, actor);
        repositories.put(repoID, repository);

        return repository;
    }

    public boolean deleteRepo(long repoID){
        Repository repository =  repositories.remove(repoID);
        return repository != null;
    }
}
