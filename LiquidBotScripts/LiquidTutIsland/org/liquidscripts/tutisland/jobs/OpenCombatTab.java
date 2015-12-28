package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.WidgetChild;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class OpenCombatTab implements Job {
    @Override
    public void run() {
        if (!Tab.COMBAT.isOpen()) {
            Tab.COMBAT.open();
            Time.sleep(200, 250);
            for (int i = 0; i < 20 && Methods.getState().equals(State.OPEN_COMBAT); i++, Time.sleep(100, 150));
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.OPEN_COMBAT);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
