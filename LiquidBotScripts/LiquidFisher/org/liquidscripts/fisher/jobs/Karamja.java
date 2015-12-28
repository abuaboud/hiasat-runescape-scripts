package org.liquidscripts.fisher.jobs;

import org.liquid.automation.osrs.api.methods.data.Bank;
import org.liquid.automation.osrs.api.methods.data.DepositBox;
import org.liquid.automation.osrs.api.methods.data.Game;
import org.liquid.automation.osrs.api.methods.data.Inventory;
import org.liquid.automation.osrs.api.methods.data.movement.Camera;
import org.liquid.automation.osrs.api.methods.data.movement.Walking;
import org.liquid.automation.osrs.api.methods.interactive.GameEntities;
import org.liquid.automation.osrs.api.methods.interactive.NPCs;
import org.liquid.automation.osrs.api.methods.interactive.Players;
import org.liquid.automation.osrs.api.methods.interactive.Widgets;
import org.liquid.automation.osrs.api.util.Random;
import org.liquid.automation.osrs.api.util.Time;
import org.liquid.automation.osrs.api.wrapper.*;
import org.liquidscripts.fisher.Storage;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/28/14
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Karamja extends Action {

    private Path PATH_DOCK_TO_SHIP = new Path(Storage.KARAMJA_PATH_TO_SHIP);

    private Path PATH_SHIP_TO_DOCK = PATH_DOCK_TO_SHIP.getReversedPath();

    private Path PATH_SHIP_TO_BOX = new Path(Storage.KARAMJA_PATH_TO_DEPOSIT_BOX);

    private Path PATH_BOX_TO_SHIP = PATH_SHIP_TO_BOX.getReversedPath();

    private int WIDGET_CHAT = 228, WIDGET_CHAT_SEARCH = 230;

    @Override
    public void run() {
        setStatus("Karamja ");
        GameObject crossPlank = GameEntities.getNearest("Gangplank");
        if (Game.getPlane() == 1 && crossPlank != null) {
            if (crossPlank.isOnScreen() && crossPlank.distanceTo() < 8) {
                crossPlank.click();
                for (int i = 0; i < 30 && Game.getPlane() == 1; i++, Time.sleep(100, 150)) ;
            } else {
                Camera.turnTo(crossPlank);
                Walking.walkTo(crossPlank);

            }
        } else if (Storage.KARAMJA_AREA.contains(Players.getLocal().getLocation())) {
            if (Inventory.isFull()) {
                NPC customOfficer = NPCs.getNearest("Customs officer");
                if (customOfficer != null) {
                    if (customOfficer.isOnScreen()) {
                        WidgetChild widgetChild = Widgets.get(WIDGET_CHAT, 1);
                        WidgetChild widgetChildSearch = Widgets.get(WIDGET_CHAT_SEARCH, 2);
                        if (widgetChildSearch != null && widgetChildSearch.isVisible()) {
                            widgetChildSearch.click();
                            for (int i = 0; i < 20 && (widgetChildSearch != null && widgetChildSearch.isVisible()); i++, Time.sleep(100, 150))
                                ;
                        } else if (widgetChild != null && widgetChild.isVisible()) {
                            widgetChild.click();
                            for (int i = 0; i < 20 && (widgetChild != null && widgetChild.isVisible()); i++, Time.sleep(100, 150))
                                ;
                        } else if (Widgets.canContinue()) {
                            Widgets.clickContinue();
                        } else {
                            if(Camera.getPitch() < 80){
                                Camera.setPitch(Random.nextInt(81,90));
                            }
                            customOfficer.interact("Pay-Fare");
                            for (int i = 0; i < 10 && !Widgets.canContinue(); i++, Time.sleep(100, 150)) ;
                        }
                    } else {
                        if (customOfficer.distanceTo() < 7) {
                            Camera.turnTo(customOfficer);
                        } else {
                            Walking.walkTo(customOfficer);
                        }
                    }
                } else
                    PATH_DOCK_TO_SHIP.traverse();
            } else {

                PATH_SHIP_TO_DOCK.traverse();
            }
        } else {
            if (Inventory.isFull()) {

                GameObject gameObject = GameEntities.getNearest("Bank deposit Box");
                if (gameObject != null) {
                    if (DepositBox.isOpen()) {
                        setStatus("Banking");
                        if (Inventory.isFull()) {

                            ArrayList<Integer> tools = new ArrayList<Integer>();
                            for (int i = 0; i < Storage.fishToCaught.length; i++) {
                                for (int x : Storage.fishToCaught[i].getTools()) {
                                    tools.add(x);
                                }
                            }
                            tools.add(995);

                            for (int i = 0; i < 10; i++) {
                                for (Item item : Inventory.getAllItems()) {
                                    if (item != null && !tools.contains(item.getId())) {
                                        DepositBox.deposit(item.getId(), 999);

                                        break;
                                    }
                                }
                            }
                        } else {
                            DepositBox.close();
                        }
                    } else {
                        setStatus("Opening Bank");
                        if (gameObject.distanceTo() > 10) {
                            PATH_SHIP_TO_BOX.traverse();
                        } else {
                            if (gameObject.isOnScreen()) {
                                if (gameObject.distanceTo() > 4) {
                                    Walking.walkTo(gameObject);
                                }
                                if(Camera.getPitch() < 80){
                                    Camera.setPitch(Random.nextInt(81,90));
                                }
                                gameObject.interact("Deposit");
                                for (int i = 0; i < 20 && !DepositBox.isOpen(); i++, Time.sleep(100, 150)) ;
                            } else {
                                if (gameObject.distanceTo() < 7) {
                                    Camera.turnTo(gameObject);
                                } else {
                                    Walking.walkTo(gameObject);
                                }
                            }
                        }
                    }
                } else {
                    PATH_SHIP_TO_BOX.traverse();
                }
            } else {
                NPC seamanLorris = NPCs.getNearest("Seaman Lorris");
                if (seamanLorris != null) {
                    if (seamanLorris.isOnScreen()) {
                        WidgetChild widgetChild = Widgets.get(WIDGET_CHAT, 1);
                        if (widgetChild != null && widgetChild.isVisible()) {
                            widgetChild.click();
                            for (int i = 0; i < 20 && (widgetChild != null && widgetChild.isVisible()); i++, Time.sleep(100, 150))
                                ;
                        } else if (Widgets.canContinue()) {
                            Widgets.clickContinue();
                        } else {
                            if(Camera.getPitch() < 80){
                                Camera.setPitch(Random.nextInt(81,90));
                            }
                            seamanLorris.interact("Pay-Fare");
                            for (int i = 0; i < 10 && !Widgets.canContinue(); i++, Time.sleep(100, 150)) ;
                        }
                    } else {
                        if (seamanLorris.distanceTo() < 7) {
                            Camera.turnTo(seamanLorris);
                        } else {
                            Walking.walkTo(seamanLorris);
                        }
                    }
                } else {
                    PATH_BOX_TO_SHIP.traverse();
                }
            }
        }
    }

    @Override
    public void setup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
