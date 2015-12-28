package org.liquidscripts.fisher.jobs;

import org.liquid.automation.osrs.api.methods.data.*;
import org.liquid.automation.osrs.api.methods.input.Mouse;
import org.liquid.automation.osrs.api.util.Random;
import org.liquid.automation.osrs.api.util.Time;
import org.liquid.automation.osrs.api.wrapper.Item;
import org.liquidscripts.fisher.Storage;

import java.awt.*;
import java.awt.Menu;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/3/14
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Drop extends Action {

    private Item itemToDrop;
    private int[] tools;

    @Override
    public void run() {
        Item[] items = Inventory.getAllItems();
            for (int j = 0; j < items.length; j++) {
                itemToDrop = items[j];
                boolean found = false;
                for (int x = 0; x < Storage.fishToCaught.length; x++) {
                    tools = Storage.fishToCaught[x].getTools();
                    for (int a = 0; a < tools.length; a++) {
                        if (tools[a] == itemToDrop.getId()) {
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    itemToDrop.interact("Drop", itemToDrop.getName());
                    Time.sleep(200, 400);
                }
            }
    }

    @Override
    public void setup() {
        setName("Drop Action");
        setStatus("Dropping Fishes");
    }


}
