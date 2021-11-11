package ai.quod.challenge.Metrics;

import ai.quod.challenge.GHProject.Database;
import ai.quod.challenge.GHProject.Repository;
import ai.quod.challenge.Utils.Utils;

import javax.rmi.CORBA.Util;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CommitMetric {
    public static HashMap<Long, Integer> totalCommits = new HashMap<>();

    public static HashMap<Long, Float> commitsPerDay = new HashMap<>();
    public static HashMap<Long, Float> commitsPerDev = new HashMap<>();

    public static int maxCommitsCount = 0;
    public static int minCommitsCount = 1000000000;

    public static float maxCommitsPerDay = 0.0f;
    public static float maxCommitsPerDev = 0.0f;

    public static void processData(int totalDays){
        HashSet<Long> repositories = Database.getInstance().repoIds;

        for(Long repoID : repositories){
            Repository repository = Database.getInstance().fromID(repoID);
            int count = repository.totalCommits();

            if (count > maxCommitsCount)
                maxCommitsCount = count;
            if (count < minCommitsCount)
                minCommitsCount = count;

            totalCommits.put(repoID, count);

            //Per days
            float perDay = (float)count/(float)totalDays;
            commitsPerDay.put(repoID, perDay);

            if (perDay - maxCommitsPerDay > Utils.EPSILON)
                maxCommitsPerDay = perDay;

            //Per devs
            float perDev = (float)count/(float)repository.totalDevs();
            commitsPerDev.put(repoID, perDev);

            if (perDev - maxCommitsPerDev > Utils.EPSILON)
                maxCommitsPerDev = perDev;
        }
    }


}
