package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.RepoInfo;
import ai.quod.challenge.GHArchiver.User;
import ai.quod.challenge.Utils.FileHandler;
import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;

public class Database {
    private static Database instance = null;
    public HashSet<Long> repoIds;

    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        repoIds = new HashSet<>();
    }

    public void createRepo(long repoID, LocalDateTime time, RepoInfo info, User actor){
        Repository repository = new Repository(repoID, time, info, actor);
        saveRepositoryToFile(repository);

        repoIds.add(repoID);
    }

    public Repository createAndGetRepo(long repoID, LocalDateTime time, RepoInfo info, User actor){
        Repository repository = new Repository(repoID, time, info, actor);
        saveRepositoryToFile(repository);

        repoIds.add(repoID);
        return repository;
    }

    public void deleteRepo(long id){
        String file_name = id + ".json";
        String file_path = FileHandler.db_path + file_name;

        File file = new File(file_path);
        try{
            file.delete();
            repoIds.remove(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public Repository fromID(long id){
        if (!repoIds.contains(id)) return null;

        String file_name = id + ".json";
        String file_path = FileHandler.db_path + file_name;

        try {
            BufferedReader bf = new BufferedReader(new FileReader(file_path));
            String content = bf.readLine();

            Gson gson = new Gson();

            Repository repository = gson.fromJson(content, Repository.class);
            return repository;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void saveRepositoryToFile(Repository repository){
        String file_name = repository.id + ".json";
        String file_path = FileHandler.db_path + file_name;

        Gson gson = new Gson();
        String json = gson.toJson(repository);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_path));
            writer.write(json);
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();;
        }
    }


}
