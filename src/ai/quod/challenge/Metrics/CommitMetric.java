package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Repository;
import ai.quod.challenge.Utils.Utils;

import javax.rmi.CORBA.Util;
import java.util.HashMap;
import java.util.Map;

public class CommitMetric {
    public static HashMap<Long, Integer> totalCommits = new HashMap<>();
    public static HashMap<Long, Float> commitsPerDay = new HashMap<>();
    public static HashMap<Long, Float> commitsPerDev = new HashMap<>();

    public static int maxCommitsCount = 0;
    public static float maxCommitsPerDay = 0.0f;
    public static float maxCommitsPerDev = 0.0f;

    public static void processData(int totalDays){
        HashMap<Long, Repository> repositories = Database.getInstance().repositories;

        for(Map.Entry<Long, Repository> entry : repositories.entrySet()){
            int count = entry.getValue().totalCommits();
            if (count > maxCommitsCount)
                maxCommitsCount = count;

            totalCommits.put(entry.getKey(), count);

            //Per days
            float perDay = (float)count/(float)totalDays;
            commitsPerDay.put(entry.getKey(), perDay);

            if (perDay - maxCommitsPerDay > Utils.EPSILON)
                maxCommitsPerDay = perDay;

            //Per devs
            float perDev = (float)count/(float)entry.getValue().totalDevs();
            commitsPerDev.put(entry.getKey(), perDev);

            if (perDev - maxCommitsPerDev > Utils.EPSILON)
                maxCommitsPerDev = perDev;
        }
    }




}
