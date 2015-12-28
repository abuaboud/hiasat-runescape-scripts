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
public class OpenEquipmentTab implements Job {
    @Override
    public void run() {
        if (!Tab.EQUIPMENT.isOpen()) {
            Tab.EQUIPMENT.open();
            Time.sleep(200, 250);
            for (int i = 0; i < 20 && Methods.getState().equals(State.OPEN_EQUIPMENT_TAB); i++, Time.sleep(100, 150));
        } else {
            if (Widgets.get(387) != null) {
                WidgetChild widgetChild = Widgets.get(387, 17);
                if (widgetChild != null) {
                    widgetChild.click(true);
                    for (int i = 0; i < 20 && Methods.getState().equals(State.SHOW_EQUIPMENT_STATE); i++, Time.sleep(100, 150));
                }
            }
        }

    }

    @Override
    public boolean isActive() {

        return Methods.getState().equals(State.OPEN_EQUIPMENT_TAB) || Methods.getState().equals(State.SHOW_EQUIPMENT_STATE);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
