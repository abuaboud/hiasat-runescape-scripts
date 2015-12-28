package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 1:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClimbDownLaderHandler implements Job {
    @Override
    public void run() {
        Methods.interactObject("Ladder","Climb-down");
        for(int i = 0 ; i < 20 && Methods.getState().equals(State.ENTER_CAVE);i++, Time.sleep(100,150));
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.ENTER_CAVE);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
