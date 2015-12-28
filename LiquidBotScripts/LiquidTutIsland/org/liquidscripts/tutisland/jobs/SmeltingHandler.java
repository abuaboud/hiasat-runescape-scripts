package org.liquidscripts.tutisland.jobs;
import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmeltingHandler implements Job {

    private final MiningOreHandler miningOreHandler = new MiningOreHandler();

    @Override
    public void run() {
        GameObject furnace = GameEntities.getNearest("Furnace");
        if (furnace != null) {
            if (furnace.isOnScreen()) {
                if (!Tab.INVENTORY.isOpen()) {
                    Tab.INVENTORY.open();
                }
                if (Tab.INVENTORY.isOpen() && (Inventory.getItem("Copper Ore") == null || Inventory.getItem("tin Ore") == null)) {
                    miningOreHandler.run();
                } else if (Inventory.getItem("Copper Ore") != null) {
                    Inventory.getItem("Copper Ore").interact("Use");
                    Time.sleep(200, 250);
                    furnace.interact("Use");
                    for (int i = 0; i < 50 && Methods.getState().equals(State.SMELT_BAR); i++, Time.sleep(100, 150)) ;
                }
            } else {
                Walking.walkTo(furnace.getLocation());
            }
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.SMELT_BAR);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
