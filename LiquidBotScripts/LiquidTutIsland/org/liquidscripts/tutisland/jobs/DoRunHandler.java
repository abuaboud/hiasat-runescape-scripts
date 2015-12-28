package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class DoRunHandler implements Job {

    @Override
    public void run() {
        if (Walking.isRunning()) {
            Walking.setRun(false);
        }
        Walking.setRun(true);
        for(int i = 0 ; i < 20 &&  Methods.getState().equals(State.DO_RUN);i++,Time.sleep(100,150));
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.DO_RUN);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
