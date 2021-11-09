package ai.quod.challenge.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPInputStream;

public class Utils {
    public static final float EPSILON = 0.00001f;

    /**
     * @param url: url of file
     * @param path: path to save
     * @return:
     * long: -1: error
     *      >= 0: status of Files.copy func
     */
    public static long downloadFile(String url, String path){
        try (InputStream in = URI.create(url).toURL().openStream()){
            return Files.copy(in, Paths.get(path));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param url: url of gz file
     * @return: String: filename
     */
    public static String getFileNameFromURL(String url){
        int index = url.lastIndexOf("/");

        if (index < 0) return "";
        return url.substring(index + 1);
    }


    /**
     * @param sourceStr: path of source file (.gz)
     * @param targetStr: path of destination file (.json)
     */
    public static void unzipGzFile(String sourceStr, String targetStr){
        Path source = Paths.get(sourceStr);
        Path target = Paths.get(targetStr);

        try{
            GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(source.toFile()));
            FileOutputStream outputStream = new FileOutputStream(target.toFile());

            byte[] buffer = new byte[1024];
            int len;

            while((len = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, len);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * @param time: ISO 8601 time string
     * @return: LocalDateTime: day time
     */
    public static LocalDateTime timeStringToLocalDateTime(String time){
        time = "+" + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("+yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.of("UTC"));
        LocalDateTime date = LocalDateTime.parse(time, formatter);
        return date;
    }
}
