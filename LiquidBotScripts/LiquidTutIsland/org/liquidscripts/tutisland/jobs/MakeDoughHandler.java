package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class MakeDoughHandler implements Job {

    @Override
    public void run() {
        if (!Tab.INVENTORY.isOpen()) {
            Tab.INVENTORY.open();
        }
        Inventory.getItem("Bucket of water").interact("Use");
        Time.sleep(200,240);
        Inventory.getItem("Pot of flour").interact("Use");
        for(int i = 0 ; i < 30 && Inventory.getItem("Bucket of water") !=null;i++, Time.sleep(100,150));
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.MAKE_DOUGH);
    }
}
