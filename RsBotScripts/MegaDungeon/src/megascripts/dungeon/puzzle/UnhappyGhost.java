package megascripts.dungeon.puzzle;

import java.awt.Color;


import megascripts.api.ulits;
import megascripts.api.myplayer.MyGroundItems;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class UnhappyGhost extends Puzzle{

	public static int Ring = 19879;
	public static int Coffin[] = { 54593, 54571, 40181 };
	public static int OPENED_COFFIN[] = { 54572, 55451 };
	public static int Damged_Wall[] = {54580,54602, 55457 };
	public static int Jewely_Box[] = { 54598, 54576, 55453 };
	public static int Broken_Pot[] = { 54599, 54577, 55455 };

    @Override
	public String getName() {
		return "Unhappy Ghost";
	}
    @Override
	public String getAuthor() {
		return "Arthurr";
	}
    @Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}
    @Override
	public boolean isValid() {
		return There(Coffin) || There(Damged_Wall) || There(Jewely_Box)
				|| There(Broken_Pot) || There(OPENED_COFFIN);
	}
    @Override
	public boolean isSolved() {
		return !isValid();
	}


	private static boolean There(int[] coffin2) {
		SceneObject n = SceneEntities.getNearest(coffin2);
		if (n == null) {
			return false;
		}
		return ulits.tileinroom(n.getLocation());
	}
    @Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		if (There(Jewely_Box)) {
			if (MyItems.contains(Ring)) {
				Interact(Jewely_Box, "Fill");
			} else {
				loot(Ring);
			}
		} else if (There(Coffin)) {
			Interact(Coffin, "Unlock");
		} else if (There(Damged_Wall)) {
			Interact(Damged_Wall, "Repair");
		} else if (There(Broken_Pot)) {
			Interact(Broken_Pot, "Repair");
		} else if (There(OPENED_COFFIN)) {
			Interact(OPENED_COFFIN, "Bless-remain");
		}
	}

	public void Interact(int[] id, String Action) {
		SceneObject n = SceneEntities.getNearest(id);
		if (n != null && n.validate()) {
			if (MyObjects.isOnScreen(n)) {
				if (Calculations.distanceTo(n) > 5) {
					Camera.turnTo(n);
				}
				if(n.interact(Action)){
				Task.sleep(1000, 1500);
				if(Action.equals("Bless-remain")){
					ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
				}
				ShadowDungeon.SleepTillStop();
				}
			} else {
				if (!Players.getLocal().isMoving()) {
					Walking.walk(n);
					Camera.turnTo(n);
				}
			}
		}
	}

	public void loot(int item) {
		GroundItem loot = GroundItems.getNearest(item);
		if (loot != null) {
			if (MyGroundItems.isOnScreen(loot)) {
				if (loot.getLocation().canReach()) {
					Camera.turnTo(loot);
					String name = loot.getGroundItem().getName();
					loot.interact("Take", name);
					Task.sleep(500, 650);
					if (Players.getLocal().isMoving()) {
						while (Players.getLocal().isMoving()) {
							Task.sleep(10, 30);
						}
					}
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}

	}
}
