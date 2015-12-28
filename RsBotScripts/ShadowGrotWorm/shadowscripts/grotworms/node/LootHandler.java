package shadowscripts.grotworms.node;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;

import shadowscripts.grotworms.api.calc;
import shadowscripts.grotworms.ulits.Ulits;
import shadowscripts.grotworms.Constants;

public class LootHandler extends Node {

	@Override
	public boolean activate() {
		return thereLoot() && !BankHandler.reqBank();
	}

	@Override
	public void execute() {
		for (int r = 0; r < 4; r++) {
			GroundItem loot = GroundItems.getNearest(Ulits
					.convertIntegers(Constants.DROPS));
			if (Inventory.contains(Constants.NATURE_RUNE)
					&& Inventory.contains(Constants.FIRE_RUNE)
					&& Inventory.getCount(Constants.ALCH_ITEM) > 0
					&& Constants.ALCH_LOW_PROFIT) {
				if(Widgets.get(1092 , 59).visible()){
					Widgets.get(1092 , 59).click(true);
					for (int i = 0; i < 20 && Widgets.get(1092 , 59).visible(); i++, Task.sleep(100, 150));
				}else{
				Item alch = Inventory.getItem(Constants.ALCH_ITEM);
				Constants.STATUS = "Alching " + alch.getName()
						+ " for profit...";
				if (Menu.contains("Cast")) {
					int count = Inventory.getCount(Ulits
							.convertIntegers(Constants.DROPS));
					Menu.select("Cast");
					Ulits.SleepTillStop(2);
					for (int j = 0; j < 25
							&& Inventory.getCount(Ulits
									.convertIntegers(Constants.DROPS)) == count; j++, sleep(
							100, 150))
						;
				} else {
					Mouse.hop((int) alch.getWidgetChild().getCentralPoint()
							.getX(), (int) alch.getWidgetChild()
							.getCentralPoint().getY());
					Mouse.move(alch.getWidgetChild().getCentralPoint());
					Keyboard.sendText("9", false);
				}
				}
			} else if (Inventory.isFull()) {
				Constants.STATUS = "Eating Food for Space...";
				Inventory.getItem(Constants.CURRENT_FOOD).getWidgetChild()
						.interact("Eat");
				Task.sleep(800, 1200);
			} else if (loot.isOnScreen()
					&& calc.isPointOnScreen(loot.getCentralPoint()) && calc.distanceTo(loot.getLocation()) < 7) {
				Constants.STATUS = "Looting " + loot.getGroundItem().getName()
						+ "...";
				if (!Players.getLocal().isMoving()) {
					Walking.walk(loot);
				}
				Mouse.hop((int) loot.getCentralPoint().getX(), (int) loot
						.getCentralPoint().getY());
				if (loot.interact("Take", loot.getGroundItem().getName())) {
					Ulits.SleepTillStop(5);
					break;
				}
			} else {
				Constants.STATUS = "Looting " + loot.getGroundItem().getName()
						+ "...";
				Walking.walk(loot);
				Ulits.turnTo(loot);
				for (int i = 0; i < 10 && !loot.isOnScreen(); i++, Task.sleep(
						100, 150))
					;
			}
		}
	}

	public static boolean thereLoot(){
		return GroundItems.getNearest(Ulits.convertIntegers(Constants.DROPS)) !=null && AttackHandler.AtGrotWorm();
	}
}
