package ai.quod.challenge.Utils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FileHandler {
    public static final String home_path = System.getProperty("user.dir");
    public static final String data_url = "https://data.gharchive.org/";

    public static final String download_path = home_path + "\\download\\";
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
}
