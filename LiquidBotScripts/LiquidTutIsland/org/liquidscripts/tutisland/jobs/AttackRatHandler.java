package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AttackRatHandler implements Job {
    @Override
    public void run() {
        if (!Players.getLocal().isInCombat()) {
            Methods.interactNPC("Giant Rat", "Attack");
            for (int i = 0; i < 20 && Methods.getState().equals(State.ATTACK_RAT); i++, Time.sleep(100, 150)) ;
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.ATTACK_RAT);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
