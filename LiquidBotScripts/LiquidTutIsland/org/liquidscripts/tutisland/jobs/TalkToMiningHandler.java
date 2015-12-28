package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.NPCs;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.NPC;
import org.liquidbot.osrs.api.wrapper.Tile;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class TalkToMiningHandler implements Job {

    final Tile location = new Tile(3081,9507);

    @Override
    public void run() {
        NPC npc = NPCs.getNearest("Mining Instructor");
        if(npc == null){
            Walking.walkTo(location);
        }
        Methods.handleTalk("Mining Instructor");
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.TALK_TO_MINING);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
