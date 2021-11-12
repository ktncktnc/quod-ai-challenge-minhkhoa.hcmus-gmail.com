package ai.quod.challenge.Utils;

import ai.quod.challenge.GHArchiver.HealthScore;
import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileHandler {
    public static final String home_path = System.getProperty("user.dir");
    public static final String data_url = "https://data.gharchive.org/";

    public static final String download_path = home_path + "\\download\\";
    public static final String db_path = home_path + "\\db\\";
    public static final String output_path = home_path + "\\output\\";
    public static final String output_name = output_path + "health_scores.csv";

    public static final String fileName_format = "%04d-%02d-%02d-%d";

    public static ArrayList<String> processInputFiles(ArrayList<String> fileNames){
        ArrayList<String> jsonFiles = new ArrayList<>();
        for(String fileName : fileNames){

            String file_url = data_url + fileName + ".json.gz";
            String json_file_path = download_path + fileName + ".json";
            String gz_file_path = json_file_path + ".gz";

            try{
                jsonFiles.add(json_file_path);

                File file = new File(json_file_path);
                if(file.exists()) continue;

                System.out.println("Download " + file_url);
                Utils.downloadFile(file_url, gz_file_path);
                System.out.println("Unzipping to " + json_file_path);
                Utils.unzipGzFile(gz_file_path, json_file_path);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }


        }
        System.out.println("Download and Unzip done!");
        return jsonFiles;
    }

    public static void scoresToCsv(List<HealthScore> scores, String filePath) {
        try {
            File file = new File(filePath);

            System.out.println(file);

            file.getParentFile().mkdirs();
            file.createNewFile();

            PrintWriter writer = new PrintWriter(file);

            for (String str : HealthScore.titles) {
                writer.append(str);
                writer.append(",");
            }
            writer.append("\n");

            for (HealthScore healthScore : scores) {
                StringBuilder builder = new StringBuilder();

                //Get repo
                long id = healthScore.id;
                Repository repository = Database.getInstance().get(id);

                //Get org and repo names
                String name = repository.info.getName();
                int index = name.indexOf("/");

                String org = name.substring(0, index);
                String repo = name.substring(index + 1);

                //Get number commits
                int numCommits = repository.totalCommits();

                //Get health score values
                ArrayList<Float> values = healthScore.toArray();

                builder.append(org);
                builder.append(",");
                builder.append(repo);
                builder.append(",");
                builder.append(values.get(0).toString());
                builder.append(",");
                builder.append(numCommits);
                builder.append(",");
                builder.append(values.get(1).toString());
                builder.append(",");
                builder.append(values.get(2).toString());
                builder.append(",");
                builder.append(values.get(3).toString());
                builder.append(",");
                builder.append(values.get(4).toString());
                builder.append("\n");

                writer.write(builder.toString());
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
