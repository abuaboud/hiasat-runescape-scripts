package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.Tile;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class WalkToQuestHandler implements Job {

    final Tile doorTile = new Tile(3086, 3127);

    @Override
    public void run() {
        if (!Players.getLocal().isMoving()) {
            if (doorTile.distanceTo() > 3) {
                Walking.walkTo(doorTile);
            } else {
                Methods.interactObject("Door", "Open");
                for(int i = 0 ; i < 20 &&  Methods.getState().equals(State.DO_RUN_TO_QUEST);i++, Time.sleep(100, 150));
            }
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.DO_RUN_TO_QUEST);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
