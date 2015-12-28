package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class CutTreeHandler implements Job {

    @Override
    public void run() {
       Methods.interactObject("Tree","Chop down");
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.CUT_TREE);
    }
}
