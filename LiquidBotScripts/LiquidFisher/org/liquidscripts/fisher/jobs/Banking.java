package org.liquidscripts.fisher.jobs;

import org.liquid.automation.osrs.api.methods.data.Bank;
import org.liquid.automation.osrs.api.methods.data.Inventory;
import org.liquid.automation.osrs.api.methods.data.movement.Camera;
import org.liquid.automation.osrs.api.methods.data.movement.Walking;
import org.liquid.automation.osrs.api.methods.interactive.GameEntities;
import org.liquid.automation.osrs.api.methods.interactive.NPCs;
import org.liquid.automation.osrs.api.methods.interactive.Players;
import org.liquid.automation.osrs.api.util.Filter;
import org.liquid.automation.osrs.api.util.Random;
import org.liquid.automation.osrs.api.util.Time;
import org.liquid.automation.osrs.api.wrapper.GameObject;
import org.liquid.automation.osrs.api.wrapper.NPC;
import org.liquidscripts.fisher.Storage;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/4/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Banking extends Action {

	private ArrayList<Integer> tools;
	private int[] Fishingtool;


	@Override
	public void run() {
		final NPC npc = NPCs.getNearest("Banker");
		GameObject[] gameObjects = GameEntities.getAll(new Filter<GameObject>() {
			@Override
			public boolean accept(GameObject gameObject) {
				return gameObject != null
						&& gameObject.getName() != null
						&& gameObject.getName().equals("Bank booth")
						&& (npc == null || (npc.distanceTo(gameObject) < 3));
			}
		});

		GameObject gameObject = gameObjects != null && gameObjects.length > 0 ? gameObjects[0] : null;
		if (gameObjects != null && gameObjects.length > 0)
			gameObject = gameObjects[Random.nextInt(0, gameObjects.length)];


		if (Storage.chosenLocation.getBankArea().contains(Players.getLocal().getLocation())) {
			if (Bank.isOpen()) {
				setStatus("Banking");
				if (Inventory.isFull()) {
					tools = new ArrayList<Integer>();
					for (int i = 0; i < Storage.fishToCaught.length; i++) {
						for (int x : Storage.fishToCaught[i].getTools()) {
							tools.add(x);
						}
					}
					Fishingtool = new int[tools.size()];
					for (int i = 0; i < tools.size(); i++) {
						Fishingtool[i] = (int) tools.get(i);
					}
					Bank.depositAllExcept(Fishingtool);
				} else {
					Bank.close();
				}
			} else {
				setStatus("Opening Bank");
				if (gameObject != null) {
					Camera.turnTo(gameObject);
				}
				Bank.open();
			}
		} else {
			if (gameObject != null) {
				setStatus("Walking To Bank");
				Walking.walkTo(gameObject);
				for (int i = 0; i < 20 && Players.getLocal().isMoving(); i++, Time.sleep(300, 350)) ;
			} else {
				setStatus("Traversing Bank Path");
				Storage.chosenLocation.getTilesToBank().traverse();
				if (!Walking.isRunning() && Walking.getRunEnergy() > 50) {
					Walking.setRun(true);
				}
			}
		}

	}

	@Override
	public void setup() {
		setName("Banking");
	}
}
