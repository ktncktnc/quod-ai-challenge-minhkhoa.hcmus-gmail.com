package ai.quod.challenge;

import ai.quod.challenge.GHArchiver.Archiver;
import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Repository;
import ai.quod.challenge.Metrics.CommitMetric;
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

        ArrayList<String> fileNames = Utils.fileNamesFromTimeRange(start, end);

        ArrayList<String> jsonFiles = FileHandler.processInputFiles(fileNames);

        Archiver archiver = new Archiver();

        for(String jsonFile : jsonFiles){
            System.out.println("parsing file " + jsonFile);
            archiver.fromFile(jsonFile);
        }

        System.out.println("archiver = " + archiver);
        System.out.println("db size = " + Database.getInstance().repositories.size());

        Repository repository = Database.getInstance().getRepo(423173519);
        if (repository != null)
        System.out.println("push size = " + repository.pushes.size());

        CommitMetric.processData(7);

        IssueMetric.processData();
        //System.out.println("pull" + IssueMetric.averageClosedTime);

    }
}
