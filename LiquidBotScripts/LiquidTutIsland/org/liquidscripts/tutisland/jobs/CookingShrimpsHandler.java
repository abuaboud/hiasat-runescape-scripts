package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidbot.osrs.api.util.Log;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CookingShrimpsHandler implements Job {
    @Override
    public void run() {
        if(!Tab.INVENTORY.isOpen()){
            Tab.INVENTORY.open();
            Time.sleep(200,250);
        }
        if (Inventory.contains("Raw shrimps")) {
            GameObject gameObject = GameEntities.getNearest("Fire");
            if (gameObject != null) {
                if (gameObject.isOnScreen()) {
                    Inventory.getItem("Raw shrimps").interact("Use");
                    Time.sleep(200,240);
                    gameObject.interact("Use","Raw shrimps -> Fire");
                    for (int i = 0; i < 20 && Inventory.contains("Raw shrimps"); i++, Time.sleep(100, 150)) ;
                } else {
                    Walking.walkTo(gameObject);
                }
            } else {
                if (Inventory.contains("Logs")) {
                    Inventory.getItem("Logs").interact("Use");
                    Time.sleep(200,240);
                    Inventory.getItem("Tinderbox").interact("Use");
                    for(int i = 0 ; i < 30 && Inventory.getItem("Logs") !=null;i++, Time.sleep(100,150));
                } else if (Players.getLocal().getAnimation() == -1) {
                    Methods.interactObject("Tree", "Chop down");
                }
            }
        } else {
            if (Players.getLocal().getAnimation() == -1) {
                Methods.interactNPC("Fishing spot", "Net");
            }
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.COOK_SHRIMPS);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
