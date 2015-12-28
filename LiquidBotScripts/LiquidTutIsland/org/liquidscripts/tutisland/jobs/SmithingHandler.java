package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.methods.data.Calculations;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.input.Mouse;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Random;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmithingHandler implements Job {

    Rectangle interact = new Rectangle(10, 45, 36, 36);

    @Override
    public void run() {
        if (Widgets.get(312) != null && Widgets.get(312).isValid()  && Widgets.get(312, 2) != null) {
            Widgets.get(312, 2).interact("Smith 1");
            for (int i = 0; i < 20 && Methods.getState().equals(State.SMITH_DAGGER); i++, Time.sleep(100, 150)) ;
        } else {
            GameObject anvil = GameEntities.getNearest("Anvil");
            if (anvil != null) {
                if (anvil.isOnScreen()) {
                    if (!Tab.INVENTORY.isOpen()) {
                        Tab.INVENTORY.open();
                    }
                    if (Inventory.getItem("Bronze Bar") != null) {
                        Inventory.getItem("Bronze Bar").interact("Use");
                        Time.sleep(200, 250);
                        anvil.interact("Use");
                        for (int i = 0; i < 20 && !(Widgets.get(312) != null && Widgets.get(312).isValid() && Widgets.get(312, 121) != null); i++, Time.sleep(100, 150))
                            ;
                    }
                } else {
                    Walking.walkTo(anvil.getLocation());
                }
            }
        }

    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.SMITH_DAGGER);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
