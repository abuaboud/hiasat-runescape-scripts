package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.Game;
import org.liquidbot.osrs.api.methods.input.Mouse;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Random;
import org.liquidbot.osrs.api.util.Time;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 8/25/13
 * Time: 1:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class JobsHandler {

    private static ArrayList<Job> jobs = new ArrayList<Job>();

    private static boolean clicked = false;

    public static Job[] jobs() {
        return jobs.toArray(new Job[jobs.size()]);
    }

    public static void addJob(Job job) {
        jobs.add(job);
    }

    public static int run() {
        if (Game.isLoggedIn()) {
            if (!clicked && Widgets.get(548, 120).isVisible() && Widgets.get(548, 120).getText() != null && Widgets.get(548, 120).getText().toLowerCase().contains("bar of bronze")) {
                Mouse.click(Widgets.get(548, 121).getCentralPoint(), true);
                Time.sleep(1000, 2000);
                clicked = true;
            } else {
                for (Job job : jobs) {
                    if (job != null && job.isActive()) {
                        job.run();
                    }
                }
            }
        }
        return Random.nextInt(150, 200);
    }
}
