package org.liquidscripts.tutisland.jobs;

import org.liquidscripts.tutisland.jobs.Job;
import org.liquidbot.osrs.api.methods.data.Bank;
import org.liquidbot.osrs.api.methods.data.Inventory;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.wrapper.Tile;
import org.liquidscripts.tutisland.Methods;
import org.liquidscripts.tutisland.State;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenDoorHandler implements Job {

    final Tile tile = new Tile(3073, 3090);
    final Tile adviser_door = new Tile(3125, 3124);
    final Tile next_adivser = new Tile(3130, 3124);

    @Override
    public void run() {
        if (Bank.isOpen()) {
            Bank.close();
        } else if (Methods.getState().equals(State.NEXT_DOOR)) {
            Methods.interactObject(GameEntities.getAt(next_adivser).getId(), "Open");
        } else if (adviser_door.distanceTo() < 10) {
            Methods.interactObject(GameEntities.getAt(adviser_door).getId(), "Open");
        } else if (tile.distanceTo() > 3 && tile.distanceTo() < 10 && Inventory.contains("Bread")) {
            Walking.walkTo(tile);
        } else {
            Methods.interactObject("Door", "Open");
        }
    }

    @Override
    public boolean isActive() {
        return Methods.getState().equals(State.OPEN_DOOR) || Methods.getState().equals(State.NEXT_DOOR);
    }
}
