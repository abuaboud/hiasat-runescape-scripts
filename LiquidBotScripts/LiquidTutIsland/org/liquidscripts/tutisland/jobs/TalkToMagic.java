package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.NPCs;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Log;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.NPC;
import org.liquidbot.osrs.api.wrapper.Tile;
import org.liquidbot.osrs.api.wrapper.Widget;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class TalkToMagic implements Job {

    final Tile tile = new Tile(3139,3086);

    @Override
    public void run() {
        NPC npc = NPCs.getNearest("Magic Instructor");
        Widget parent = Widgets.get(228);
        if (parent != null && parent.isValid() && parent.getChild(1) != null && parent.getChild(1).isVisible()) {
            parent.getChild(1).click(true);
            for (int i = 0; i < 30 && (parent != null && parent.getChild(1) != null && parent.getChild(1).isVisible()); i++, Time.sleep(100, 150))
                ;
        } else {
            if (npc != null) {
                Methods.handleTalk("Magic instructor");
            } else {
                Walking.walkTo(tile);
            }
        }

    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.TALK_TO_MAGIC);
    }
}
