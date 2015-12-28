package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/6/14
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProsepectHandler implements Job {
    @Override
    public void run() {
        GameObject[] gameObjects = GameEntities.getAll("Rocks");
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(GameObject gameObject : gameObjects){
            if(gameObject !=null && !list.contains(gameObject.getId())){
                list.add(gameObject.getId());
            }
        }
        int id = Methods.getState().equals(State.PROSPECT_TIN) ? Collections.max(list) : Collections.min(list);
        Methods.interactObject(id,"Prospect");
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.PROSPECT_TIN) || Methods.getState().equals(State.PROSPECT_COPPER);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
