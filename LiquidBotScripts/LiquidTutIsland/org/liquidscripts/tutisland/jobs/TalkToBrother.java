package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.methods.interactive.NPCs;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidbot.osrs.api.wrapper.NPC;
import org.liquidbot.osrs.api.wrapper.Tile;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TalkToBrother implements Job {

    final Tile doorTile =  new Tile(3129,3107);
    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.BROTHER_BRACE);
    }

    @Override
    public void run() {
        GameObject large = GameEntities.getAt(doorTile);
        NPC npc = NPCs.getNearest("Brother brace");
        if (npc != null) {
            if (large == null) {
                if(npc.distanceTo() > 3){
                    Walking.walkTo(npc);
                }
                 Methods.handleTalk("Brother brace");
            } else {
                if (large != null && !Players.getLocal().isMoving()) {
                    Methods.interactObject(large.getId(), "Open");
                }
            }
        }else{
            Walking.walkTo(doorTile);
        }
    }
}
