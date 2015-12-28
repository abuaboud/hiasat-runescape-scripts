package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenGateHandler implements Job {
    @Override
    public void run() {
        if (!Players.getLocal().isMoving())
            Methods.interactObject("Gate", "Open");
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.OPEN_GATE);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
