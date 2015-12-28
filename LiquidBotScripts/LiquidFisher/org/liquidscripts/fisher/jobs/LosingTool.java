package org.liquidscripts.fisher.jobs;

import org.liquidscripts.fisher.LiquidFisher;
import org.liquid.automation.osrs.api.methods.data.Bank;
import org.liquid.automation.osrs.api.methods.data.Inventory;
import org.liquid.automation.osrs.api.methods.data.movement.Camera;
import org.liquid.automation.osrs.api.methods.data.movement.Walking;
import org.liquid.automation.osrs.api.methods.interactive.GameEntities;
import org.liquid.automation.osrs.api.methods.interactive.GroundItems;
import org.liquid.automation.osrs.api.methods.interactive.NPCs;
import org.liquid.automation.osrs.api.methods.interactive.Players;
import org.liquid.automation.osrs.api.util.Filter;
import org.liquid.automation.osrs.api.util.Random;
import org.liquid.automation.osrs.api.util.Time;
import org.liquid.automation.osrs.api.wrapper.GameObject;
import org.liquid.automation.osrs.api.wrapper.GroundItem;
import org.liquid.automation.osrs.api.wrapper.Item;
import org.liquid.automation.osrs.api.wrapper.NPC;
import org.liquidscripts.fisher.Storage;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/4/14
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class LosingTool extends Action {

    @Override
    public void run() {
        GroundItem groundItem = GroundItems.getNearest(Storage.fishToCaught[0].getTools());
        if (groundItem != null && !Inventory.contains(groundItem.getId()) && groundItem.distanceTo() < 30) {
            if (groundItem.isOnScreen()) {
                groundItem.interact("Take", groundItem.getName());
                for (int i = 0; i < 10; i++, Time.sleep(100, 150)) ;
            } else {
                Walking.walkTo(groundItem);

            }
        } else {
            GameObject[] gameObjects = GameEntities.getAll(new Filter<GameObject>() {
                @Override
                public boolean accept(GameObject gameObject) {
                    NPC npc = NPCs.getNearest("Banker");
                    return gameObject.getName() != null
                            && gameObject.getName().equals("Bank booth")
                            && (npc == null || (npc.distanceTo(gameObject) < 3));
                }
            });
            GameObject gameObject = gameObjects != null && gameObjects.length > 0 ? gameObjects[0] : null;
            if (gameObjects != null && gameObjects.length > 0)
                gameObject = gameObjects[Random.nextInt(0, gameObjects.length)];

            if (Storage.chosenLocation.getBankArea().contains(Players.getLocal().getLocation())) {
                if (Bank.isOpen()) {
                    for (int id : Storage.fishToCaught[0].getTools()) {
                        if (!Inventory.contains(id)) {
                            Item item = Bank.getItem(id);
                            if (Bank.getCount(id) == 0) {
                                LiquidFisher.stop();
                            }
                            if (item != null && (item.getName().equals("Feather") || item.getName().equals("Bait"))) {
                                Bank.withdraw(Bank.getItem(id).getName(), Bank.getCount(item.getId()));
                            } else {
                                if (Bank.getCount(id) > 0) {
                                    Bank.withdraw(Bank.getItem(id).getName(), 1);
                                } else {
                                    LiquidFisher.stop();
                                }
                            }
                            for (int i = 0; i < 20 && !Inventory.contains(id); i++, Time.sleep(100, 150)) ;
                        }
                    }
                } else {
                    if (gameObject != null) {
                        Camera.turnTo(gameObject);
                    }
                    Bank.open();
                }
            } else {

                if (gameObject != null) {
                    Walking.walkTo(gameObject);
                    for (int i = 0; i < 30 && Players.getLocal().isMoving(); i++, Time.sleep(100, 150)) ;
                } else {
                    Storage.chosenLocation.getTilesToBank().traverse();
                }
            }
        }
    }


    @Override
    public void setup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
