package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class TalkToSurvivalExpert implements Job {

    @Override
    public void run() {
       Methods.handleTalk("Survival Expert");
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.TALK_TO_SURVIVAL_EXPERT);
    }
}
