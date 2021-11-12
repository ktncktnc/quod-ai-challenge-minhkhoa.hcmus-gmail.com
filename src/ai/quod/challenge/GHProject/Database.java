package ai.quod.challenge.GHProject;

import ai.quod.challenge.GHArchiver.RepoInfo;
import ai.quod.challenge.GHArchiver.User;
import ai.quod.challenge.Utils.FileHandler;
import com.google.gson.Gson;

import java.sql.*;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Database {
    private static Database instance = null;
    public static String dbName = FileHandler.db_path + "gharchiver.db";

    public static String create_table_sql =
                        "CREATE TABLE IF NOT EXISTS repositories (\n" +
                                "id long PRIMARY KEY,\n" +
                                "repoInfo text NOT NULL\n"
            +   ");";

    public static String insert_data_sql =
            "INSERT OR REPLACE INTO repositories(id, content) VALUES(?, ?)";

    public static String select_data_sql =
            "SELECT id, content FROM repositories WHERE id = ?";

    public static String delete_data_sql =
            "DELETE FROM repositories where id = ?";

    public HashSet<Long> repoIds;

    public int curFileID;
    public int curLine;
    public Connection connection;

     public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    private Database() {
         repoIds = new HashSet<>();
         curFileID = 0;
         curLine = 0;

        connect();
    }

    public void connect(){
         try{
             connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
         }
         catch (SQLException e){
             e.printStackTrace();
         }
         finally {
             try {
                 if (connection != null){
                     Statement statement = connection.createStatement();

                     statement.execute(create_table_sql);
                     connection.setAutoCommit(false);

                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    }

    public void close(){
        try {
            if (connection != null){
                connection.close();
                connection = null;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void commit(){
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(Repository repository){
        try{

            if (connection != null){
                PreparedStatement statement = connection.prepareStatement(insert_data_sql);

                Gson gson = new Gson();
                String content = gson.toJson(repository);

                statement.setLong(1, repository.id);
                statement.setString(2, content);

                statement.executeUpdate();

                repoIds.add(repository.id);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Repository insert(long repoID, LocalDateTime time, RepoInfo info, User actor){
        Repository repository = new Repository(repoID, time, info, actor);

        insert(repository);

        return repository;
    }

    public Repository get(long id){
        try {
            if (connection == null) connect();

            PreparedStatement statement = connection.prepareStatement(select_data_sql);

            statement.setLong(1, id);

            ResultSet set = statement.executeQuery();
            if (!set.next()) return null;

            Gson gson = new Gson();
            Repository repository = gson.fromJson(set.getString("content"), Repository.class);
            return repository;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return  null;
    }




//    public void updateRepo(Repository repository){
//         try {
//             Gson gson = new Gson();
//             String content = gson.toJson(repository);
//
//             PreparedStatement pstmt = connection.prepareStatement(update_data_sql);
//             pstmt.setString(1, content);
//             pstmt.setLong(2, repository.id);
//
//             pstmt.executeUpdate();
//         }
//         catch (SQLException e){
//             e.printStackTrace();
//         }
//    }

    public void delete(long id){
        try{
            PreparedStatement pstmt = connection.prepareStatement(delete_data_sql);
            pstmt.setLong(1, id);

            pstmt.executeUpdate();

            repoIds.remove(id);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
