package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.Tile;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 1:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClimbUpLadderHandler implements Job {

    final Tile tile = new Tile(3111,9526);

    @Override
    public void run() {
        Methods.interactObject(GameEntities.getAt(tile).getId(),"Climb-up");
        for(int i = 0 ; i < 20 && Methods.getState().equals(State.CLIMB_UP_LADDER);i++, Time.sleep(100,150));
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.CLIMB_UP_LADDER);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
