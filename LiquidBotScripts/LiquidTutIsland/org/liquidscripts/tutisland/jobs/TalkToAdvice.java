package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.util.Log;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class TalkToAdvice implements Job {

    @Override
    public void run() {
       Methods.handleTalk("Financial advisor");
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.TALK_TO_ADVICE);
    }
}
