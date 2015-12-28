package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class OpenIgnoreTab implements Job {
    @Override
    public void run() {
        if (Tab.IGNORE_LIST.isOpen()) {
            Tab.IGNORE_LIST.open();
        }
        Tab.IGNORE_LIST.open();
        Time.sleep(200, 250);
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.OPEN_IGNORE_TAB);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
