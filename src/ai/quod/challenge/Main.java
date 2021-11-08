package ai.quod.challenge;

import ai.quod.challenge.GHArchiver.Archiver;
import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.utils.Parser;
import ai.quod.challenge.utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) {
        String url = "https://data.gharchive.org/2021-10-31-15.json.gz";
        String gzFileName = Utils.getFileNameFromURL(url);
        gzFileName = "download/" + gzFileName;

        String jsonFilePath = gzFileName.substring(0, gzFileName.length() - 3);

        //long returnCode = Utils.downloadFile(url, gzFileName);

        //Utils.unzipGzFile(gzFileName, jsonFilePath);

        Archiver archiver = new Archiver();
        archiver.fromFile(jsonFilePath);
        System.out.println("archiver = " + archiver);
        System.out.println("db size = " + Database.getInstance().repositories.size());

    }
}
