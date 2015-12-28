package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.methods.input.Mouse;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.WidgetChild;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class WieldingWeaponsHandler implements Job {

    @Override
    public void run() {
        if (Widgets.get(465) != null && Widgets.get(465, 70) != null && Widgets.get(465, 70).isVisible()) {
            WidgetChild widgetChild = Widgets.get(465, 70);
            widgetChild.interact("Close");
        }
        if (!Tab.INVENTORY.isOpen()) {
            Tab.INVENTORY.open();
            Time.sleep(200, 250);
        }
        if (Inventory.getItem("Bronze dagger") != null)
            Mouse.click(Inventory.getItem("Bronze dagger").getCenteralPoint(), true);
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.WEAR_DAGGER);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
