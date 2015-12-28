package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.enums.Tab;
import org.liquidbot.osrs.api.methods.data.movement.Camera;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.NPCs;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.NPC;
import org.liquidbot.osrs.api.wrapper.Tile;
import org.liquidbot.osrs.api.wrapper.Widget;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AttackChickdenHandler implements Job {

    final Tile attackTile = new Tile(3140, 3090);

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.ATTACK_CHICKEN);
    }

    @Override
    public void run() {
        NPC chicken = NPCs.getNearest("Chicken");
        if (chicken != null) {
            if (chicken.isOnScreen()) {
                if (Tab.MAGIC.isOpen()) {
                    if (attackTile.distanceTo() > 2) {
                        Walking.walkTo(attackTile);
                    } else {
                        Widget magic = Widgets.get(192);
                        if (magic != null && magic.isValid() && magic.getChild(1) != null && magic.getChild(1).isVisible()) {
                            magic.getChild(1).click(true);
                            Time.sleep(300, 350);
                            chicken.interact("Cast");
                            for (int i = 0; i < 30 && Methods.getState().equals(State.ATTACK_CHICKEN); i++, Time.sleep(100, 150))
                                ;
                        }
                    }
                } else {
                    Tab.MAGIC.open();
                    Time.sleep(200, 300);
                }
            } else {
                Camera.turnTo(chicken);
            }
        }
    }
}
