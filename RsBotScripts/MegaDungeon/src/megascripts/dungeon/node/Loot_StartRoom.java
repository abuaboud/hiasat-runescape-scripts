package megascripts.dungeon.node;

import java.awt.Color;
import java.awt.Point;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;


import megascripts.api.Calc;
import megascripts.api.Shop;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyArmor;
import megascripts.api.myplayer.MyGroundItems;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.dungeon.*;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class Loot_StartRoom extends Node {

	public static int FEATHER = 17796;

	@Override
	public boolean activate() {
		return MyActions.InDungeon()
				&& MyActions.AtStartRoom()
				&& (!MyActions.ThereStartRoom() || MyActions.ThereGroupStone()
						|| MyActions.ThereFish() || MyActions.ThereKey());
	}

	@Override
	public void execute() {
		if (Camera.getPitch() <= 80) {
			Camera.setPitch(90);
		}
	
		if (Constants.DUNGEONEERING_LEVEL == 0 || Constants.PRAYER_LEVEL == 0 ||Constants.COOKING_LEVEL == 0) {
			if (!Tabs.STATS.isOpen()) {
				Tabs.STATS.open();
			}
				Constants.PRAYER_LEVEL = Integer.parseInt(Widgets.get(320, 57).getText());
				Constants.DUNGEONEERING_LEVEL = Integer.parseInt(Widgets.get(320, 119).getText());
				Constants.COOKING_LEVEL = Integer.parseInt(Widgets.get(320, 51).getText());
				Constants.ATTACK_LEVEL = Integer.parseInt(Widgets.get(320, 149).getText());
				Constants.DEFENCE_LEVEL = Integer.parseInt(Widgets.get(320, 21).getText());
				Constants.STRENGTH_LEVEL = Integer.parseInt(Widgets.get(320, 8).getText());
				Tabs.INVENTORY.open();
			
		}else if(MyArmor.CHECKARMOR()){
			MyArmor.getCurrentArmor();
		}else if(MyArmor.ThereGoodArmor()){
			MyArmor.UPGRAGE();
		}else{

		Constants.MAX_FLOOR = ShadowDungeon.getMaxFloor();
		if (Constants.DungeonStarted
				&& Inventory.contains(Constants.DUNGEON_RING)) {
			MyItems.Interact(Constants.DUNGEON_RING, "Wear");
			Task.sleep(300, 700);
			Constants.DungeonStarted = false;
		}
		if (MyActions.ThereGroupStone()) {
			Loot_StartRoom.loot(Constants.GROUP_STONE, false);
		} else if (MyActions.ThereFish()) {
			ShadowDungeon.setStatus("Looting Fish...");
			lootTable(Constants.FOODTOEAT);
		} else if (MyActions.ThereKey()) {
			ShadowDungeon.setStatus("Looting Keys...");
			loot(ulits.convertInt(Constants.ALL_KEYS), true);
		} else if (!MyActions.ThereStartRoom()) {
			ShadowDungeon.setStatus("Buyying Supplies...");
			for (int o = 0; o < 25; o++) {
				if (!MyItems.contains(FEATHER)) {
					Shop.buy(FEATHER, 4, 6);
				} else if (!MyItems.contains(Constants.POSION_POTION)) {
					Shop.buy(Constants.POSION_POTION, 1, 200);
				}
			}
			if (MyItems.contains(FEATHER)
					&& MyItems.contains(Constants.POSION_POTION)) {
				Constants.BUY_SUPPLIES = false;
			}
		}
		if (MyActions.ThereStartRoom()) {
			Shop.close();
			}
		}
	}

	public static void lootTable(int[] FishesID) {
		GroundItem items = GroundItems.getNearest(FishesID);
		if (!Inventory.isFull()) {
			if (items != null) {
				if (MyGroundItems.isOnScreen(items)) {
					Point p = getOnScreenPoint(items);
					if (p != null) {
						Mouse.click(p, false);
					}
					Menu.select("Take", items.getGroundItem().getName());
					Task.sleep(500, 800);
					for (int x = 0; x < 40
							&& (Players.getLocal().isMoving() || Players
									.getLocal().getAnimation() != -1); x++, Task
							.sleep(100, 150))
						;
				} else {
					Camera.turnTo(items.getLocation());
					Walking.walk(items.getLocation());

				}
			}
		}
	}

	private static Point getOnScreenPoint(GroundItem gi) {
		if (gi == null || !gi.validate() || !MyGroundItems.isOnScreen(gi))
			return null;
		final int surfaceHeight = 500;
		SceneObject surface = SceneEntities.getAt(gi.getLocation());
		return (surface != null && surface.validate() && surface.getType() == SceneEntities.TYPE_INTERACTIVE) ? gi
				.getLocation().getPoint(.5d, .5d, -surfaceHeight) : gi
				.getCentralPoint();
	}

	public static void loot(int item[], boolean Keya) {
		GroundItem loot = GroundItems.getNearest(item);
		if (loot != null) {
			if (MyGroundItems.isOnScreen((loot))
					&& Calculations.distanceTo(loot) < 5) {
				if (loot.getLocation().canReach()) {
					String name = loot.getGroundItem().getName();
					Mouse.move(loot.getCentralPoint());
					if (Menu.contains("Take")) {
						Menu.select("Take", name);
					} else {
						loot.interact("Take", name);
					}
					Task.sleep(500, 650);
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
		if (Keya) {
			Constants.Inevntory_KEY.add(loot.getId());
		}
	}

	public static void loot(int item, boolean Keya) {
		GroundItem loot = GroundItems.getNearest(item);
		if (loot != null) {
			if (MyGroundItems.isOnScreen((loot))
					&& Calculations.distanceTo(loot) < 5) {
				if (loot.getLocation().canReach()) {
					String name = loot.getGroundItem().getName();
					Mouse.move(loot.getCentralPoint());
					if (Menu.contains("Take")) {
						Menu.select("Take", name);
					} else {
						loot.interact("Take", name);
					}
					Task.sleep(500, 650);
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
		if (Keya) {
			Constants.Inevntory_KEY.add(loot.getId());
		}
	}

}
