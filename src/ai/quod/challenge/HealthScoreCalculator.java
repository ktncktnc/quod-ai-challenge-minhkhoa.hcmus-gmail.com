package ai.quod.challenge;

import ai.quod.challenge.GHArchiver.Archiver;
import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Repository;
import ai.quod.challenge.Metrics.CommitMetric;
import ai.quod.challenge.Metrics.HealthMetrics;
import ai.quod.challenge.Metrics.IssueMetric;
import ai.quod.challenge.Metrics.PullMetric;
import ai.quod.challenge.Utils.FileHandler;
import ai.quod.challenge.Utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class HealthScoreCalculator {

    public static void main(String[] args) {
        LocalDateTime start, end;

        if (args.length == 0){
            start = LocalDateTime.now().minusHours(1);
            end = LocalDateTime.now();
        }
        else if (args.length == 2){
            start = Utils.timeStringToLocalDateTime(args[0]);
            end = Utils.timeStringToLocalDateTime(args[1]);
        }
        else{
            System.out.println("Error parameters");
            return;
        }

        Database.getInstance().connect();

        ArrayList<String> fileNames = Utils.fileNamesFromTimeRange(start, end);

        ArrayList<String> jsonFiles = FileHandler.processInputFiles(fileNames);

        Archiver archiver = new Archiver();

        for(String jsonFile : jsonFiles){
            System.out.println("parsing file " + jsonFile);
            archiver.fromFile(jsonFile);
            archiver.events.clear();
        }
        CommitMetric.processData(7);
        IssueMetric.processData();
        PullMetric.processData();

        HealthMetrics.process();

        FileHandler.scoresToCsv(HealthMetrics.sortToList(), FileHandler.output_name);

        Database.getInstance().close();

    }
}
