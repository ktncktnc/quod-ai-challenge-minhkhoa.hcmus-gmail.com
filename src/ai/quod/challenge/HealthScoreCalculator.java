package ai.quod.challenge;

import ai.quod.challenge.GHArchiver.Archiver;
import ai.quod.challenge.Metrics.CommitMetric;
import ai.quod.challenge.Metrics.HealthMetrics;
import ai.quod.challenge.Metrics.IssueMetric;
import ai.quod.challenge.Metrics.PullMetric;
import ai.quod.challenge.Utils.FileHandler;
import ai.quod.challenge.Utils.Utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.time.Instant;
import java.time.ZoneOffset;

public class HealthScoreCalculator {

    public static void main(String[] args) {
        LocalDateTime start, end;

        //Parameter processing
        if (args.length == 0){
            Instant instant = Instant.now();
            start = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).minusHours(2);
            end = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).minusHours(1);
        }
        else if (args.length == 2){
            start = Utils.timeStringToLocalDateTime(args[0]);
            end = Utils.timeStringToLocalDateTime(args[1]);
        }
        else{
            System.out.println("Error parameters");
            return;
        }

        //Connect to database
        Database.getInstance().connect();

        //Get file list from parameters.
        ArrayList<String> fileNames = Utils.fileNamesFromTimeRange(start, end);
        ArrayList<String> jsonFiles = FileHandler.processInputFiles(fileNames);

        //Create an archiver to store event data.
        Archiver archiver = new Archiver();

        for(String jsonFile : jsonFiles){
            System.out.println("parsing file " + jsonFile);
            archiver.fromFile(jsonFile);
            archiver.events.clear();
        }

        //Get day range
        int dayRange = (int) ChronoUnit.DAYS.between(start, end);

        //Calculate metrics
        CommitMetric.processData(dayRange);
        IssueMetric.processData();
        PullMetric.processData();

        HealthMetrics.process();

        //Save result to csv
        FileHandler.scoresToCsv(HealthMetrics.sortToList(), FileHandler.output_name);

        //Close database.
        Database.getInstance().close();

    }
}
