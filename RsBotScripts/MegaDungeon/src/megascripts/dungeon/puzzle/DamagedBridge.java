package megascripts.dungeon.puzzle;

import java.awt.Color;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.Flood;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class DamagedBridge extends Puzzle {

	public static int MINING_ROCK[] = { 54183, 54184 };
	public static int DAMAGED_BRIDGE[] = { 54187, 54188, 54189, 54011, 54010,54012 };
	public static int AGILTY_BRIDGE[] = { 54011, 54010, 54012 };
	public static int Stone = 19660;
	public static int Craved_Stone = 19661;
	public static int WOOD = 19651;
	public static int WOOD_BOX[] = { 54023 };

	@Override
	public String getName() {
		return "Damaged Bridge";
	}

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		SceneObject n = SceneEntities.getNearest(DAMAGED_BRIDGE);
		if (n == null) {
			return false;
		}
		return ulits.tileinroom(n.getLocation());
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		Combat.TurnRet(false);
		SceneObject bridge = SceneEntities.getNearest(AGILTY_BRIDGE);
		if (bridge != null && ulits.MatchID(bridge.getId(), AGILTY_BRIDGE)) {
			if (Inventory.getCount(WOOD) == 5) {
				SceneObject n = SceneEntities.getNearest(AGILTY_BRIDGE);
				if (n != null && n.validate()) {
					if (MyObjects.isOnScreen(n)) {
						if(n.interact("Repair")){
						Task.sleep(1000, 1500);
						ShadowDungeon.SleepTillStop();
						ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
						}
					} else {
						MyPlayer.WalkTo(n.getLocation());
					}
				}
			} else if (Calc.Reach(SceneEntities.getNearest(WOOD_BOX))) {
				Interact(WOOD_BOX, "Raid");
			} else {
				Interact(AGILTY_BRIDGE, "Jump");
			}
			ShadowDungeon.SleepTillStop();
		} else {
			if (MyItems.contains(Craved_Stone)) {
				Interact(DAMAGED_BRIDGE, "Repair");
				if (MyItems.contains(Stone)) {
					MyItems.Interact(Stone, "Drop");
				}
			} else if (MyItems.contains(Stone)) {
				MyItems.Interact(Stone, "Carve");
			} else {
				Interact(MINING_ROCK, "Mine");
			}
			ShadowDungeon.SleepTillStop();
		}

	}

	public void Interact(int[] id, String Action) {
		SceneObject n = SceneEntities.getNearest(id);
		if (n != null && n.validate()) {
			if (MyObjects.isOnScreen(n)) {
				if (Calculations.distanceTo(n) > 5) {
					Camera.turnTo(n);
				}
				n.interact(Action);
				Task.sleep(1000, 1500);
				ShadowDungeon.SleepTillStop();
				if (isSolved()) {
					ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
				}
			} else {
				if (!Players.getLocal().isMoving()) {
					Walking.walk(n);
					Camera.turnTo(n);
				}
			}
		}
	}
}
