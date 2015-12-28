package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class WearArmorHandler implements Job {
    @Override
    public void run() {
        Inventory.getItem("Bronze sword").interact("Wield");
        Time.sleep(300,500);
        Inventory.getItem("Wooden shield").interact("Wield");
        for(int i = 0 ; i < 20 && Methods.getState().equals(State.UNQUIP_ITEMS);i++, Time.sleep(100,150));
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.UNQUIP_ITEMS);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
