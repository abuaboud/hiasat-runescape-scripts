package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.methods.interactive.NPCs;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidbot.osrs.api.wrapper.NPC;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class TalkToCombat implements Job {

    boolean click = true;

    @Override
    public void run() {
        if (Widgets.get(84, 3) != null && click) {
            for (int x = 0; x < 2; x++) {
                Widgets.get(84, 3).click();
                for (int i = 0; i < 30 && Widgets.get(84, 3) == null; i++, Time.sleep(100, 150)) ;
            }
            click = false;
        } else {
            GameObject gameObject = GameEntities.getNearest("Gate");
            NPC npc = NPCs.getNearest("Combat Instructor");
            if (npc != null) {
                if (Walking.findPath(npc.getLocation()) !=null|| ((gameObject != null && gameObject.distanceTo() > npc.distanceTo()))) {
                    Methods.handleTalk("Combat Instructor");
                } else {
                    if (!Players.getLocal().isMoving())
                        Methods.interactObject("Gate", "Open");
                }
            }
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.TALK_TO_COMBAT) || (Widgets.get(84, 3) != null);
    }
}
