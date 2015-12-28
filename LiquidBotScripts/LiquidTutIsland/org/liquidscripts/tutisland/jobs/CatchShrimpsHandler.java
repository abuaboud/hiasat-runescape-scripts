package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CatchShrimpsHandler implements Job {
    @Override
    public void run() {
        if (Players.getLocal().getAnimation() == -1) {
            Methods.interactNPC("Fishing spot", "Net");
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.CATCH_SHRIMPS);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
