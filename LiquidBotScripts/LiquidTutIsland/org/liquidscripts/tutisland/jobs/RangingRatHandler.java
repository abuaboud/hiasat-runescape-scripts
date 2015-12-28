package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class RangingRatHandler implements Job {
    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.RANGE_RAT);
    }

    @Override
    public void run() {
        if (Inventory.contains("Bronze arrow")) {
            Inventory.getItem("Bronze arrow").interact("Wield");
            for (int i = 0; i < 40 && Inventory.contains("Bronze arrow"); i++, Time.sleep(100, 150)) ;
        } else if (Inventory.contains("Shortbow")) {
            Inventory.getItem("Shortbow").interact("Wield");
            for (int i = 0; i < 40 && Inventory.contains("Shortbow"); i++, Time.sleep(100, 150)) ;
        } else {
            if (!Players.getLocal().isInCombat() && Players.getLocal().getInteracting() ==null) {
                Methods.interactNPC("Giant Rat", "Attack");
                for (int i = 0; i < 20 && Methods.getState().equals(State.ATTACK_RAT); i++, Time.sleep(100, 150)) ;
            }
        }
    }
}
