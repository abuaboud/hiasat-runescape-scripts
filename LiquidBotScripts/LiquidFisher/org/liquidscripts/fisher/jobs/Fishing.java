package org.liquidscripts.fisher.jobs;

import org.liquid.automation.osrs.api.methods.data.movement.Camera;
import org.liquid.automation.osrs.api.methods.data.movement.Walking;
import org.liquid.automation.osrs.api.methods.interactive.Players;
import org.liquid.automation.osrs.api.util.Random;
import org.liquid.automation.osrs.api.util.Time;
import org.liquid.automation.osrs.api.wrapper.NPC;
import org.liquidscripts.fisher.Storage;
import org.liquidscripts.fisher.Utils;
import org.liquidscripts.fisher.wrapper.SpotAction;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/3/14
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Fishing extends Action {

    private SpotAction fishSpotAction;
    private NPC fishSpot;


    @Override
    public void run() {
        fishSpotAction = Utils.getFishSpot();
        if (fishSpotAction != null) {
            fishSpot = fishSpotAction.getNpc();
            if (fishSpot != null && fishSpot.distanceTo() < 20) {
                if (fishSpot.isOnScreen()) {
                    if(Camera.getPitch() < 80){
                        Camera.setPitch(Random.nextInt(80,90));
                    }
                    setStatus("Interacting with spot");
                    fishSpot.interact(fishSpotAction.getFish().getAction());
                    for (int i = 0; i < 20 && Players.getLocal().getAnimation() == -1; i++, Time.sleep(300, 350)) ;
                } else {
                    setStatus("Walking to Spot");
                    if (fishSpot.distanceTo() < 7) {
                        Walking.walkTo(fishSpot.getLocation());
                        Camera.turnTo(fishSpot);
                    } else {
                        Walking.walkTo(fishSpot.getLocation());
                    }
                    for (int i = 0; i < 7 && !Players.getLocal().isMoving(); i++, Time.sleep(300, 350)) ;
                }
            } else {
                if (Storage.chosenLocation.equals(Storage.KARAMJA)) {
                    Actions.run(Actions.KARAMJA);
                } else {
                    setStatus("Walking to Spot");
                    Storage.chosenLocation.getTilesToFish().traverse();
                }
                if (!Walking.isRunning() && Walking.getRunEnergy() > 50) {
                    Walking.setRun(true);
                }
            }
        } else {
            if (Storage.chosenLocation.equals(Storage.KARAMJA)) {
                Actions.run(Actions.KARAMJA);
            } else {
                setStatus("Walking to Spot");
                Storage.chosenLocation.getTilesToFish().traverse();
            }
            if (!Walking.isRunning() && Walking.getRunEnergy() > 50) {
                Walking.setRun(true);
            }
        }
    }

    @Override
    public void setup() {
        setName("Fishing");
        setStatus("Fishing");
    }
}
