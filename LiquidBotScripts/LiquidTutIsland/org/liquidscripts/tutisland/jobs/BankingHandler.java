package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidbot.osrs.api.wrapper.Widget;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 9:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankingHandler implements Job {
    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.TALK_TO_BANKER);
    }

    @Override
    public void run() {
        Widget parent = Widgets.get(228);
        if (parent != null && parent.isValid() && parent.getChild(1) != null && parent.getChild(1).isVisible()) {
            parent.getChild(1).click(true);
            for (int i = 0; i < 30 && (parent != null && parent.getChild(1) != null && parent.getChild(1).isVisible()); i++, Time.sleep(100, 150))
                ;
        } else if (Widgets.canContinue()) {
            Widgets.clickContinue();
        } else {
            Methods.interactObject("Bank booth", "Use");
        }
    }
}
