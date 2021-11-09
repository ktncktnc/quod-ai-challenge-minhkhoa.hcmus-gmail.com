package ai.quod.challenge.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class Utils {
    public static final float EPSILON = 0.00001f;

    /**
     * @param urlStr: url of file
     * @param path: path to save
     * @return:
     * long: -1: error
     *      >= 0: status of Files.copy func
     */
    public static long downloadFile(String urlStr, String path) throws Exception{
        try {
            URL url = new URL(urlStr);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream in = urlc.getInputStream();
            return Files.copy(in, Paths.get(path));
        }
        catch (Exception e){
            throw e;
        }
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

    public static ArrayList<String> fileNamesFromTimeRange(LocalDateTime start, LocalDateTime end){
        ArrayList<String> fileNames = new ArrayList<>();

        LocalDateTime currentTime = start;
        while(currentTime.isBefore(end)){
            String fileName = String.format(FileHandler.fileName_format,
                    currentTime.getYear(),
                    currentTime.getMonth().getValue(),
                    currentTime.getDayOfMonth(),
                    currentTime.getHour());

            fileNames.add(fileName);
            currentTime = currentTime.plusHours(1);
        }

        return fileNames;
    }
}
