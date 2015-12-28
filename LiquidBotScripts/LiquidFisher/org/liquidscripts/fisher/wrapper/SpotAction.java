package org.liquidscripts.fisher.wrapper;

import org.liquid.automation.osrs.api.wrapper.NPC;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/3/14
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpotAction {

    private Fish fish;
    private NPC npc;

    public SpotAction(Fish fish,NPC npc){
        this.fish = fish;
        this.npc = npc;
    }

    public Fish getFish(){
        return fish;
    }

    public NPC getNpc(){
        return npc;
    }

}
