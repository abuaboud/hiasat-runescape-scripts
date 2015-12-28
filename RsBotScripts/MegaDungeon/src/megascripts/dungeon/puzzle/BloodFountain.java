package megascripts.dungeon.puzzle;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class BloodFountain extends Puzzle {

	public static final int[] RUBBLE = { 37232, 54130, 54131, 54132, 54133,
			54134, 541325, 54138, 54139, 54140, 54141, 54142 };
	public static final int[] PILLARS = { 54118, 54122, 54123, 54124, 54125,
			54126, 37207 };

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getName() {
		return "Blood Fountain";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		return There(RUBBLE) || There(PILLARS);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		if (!Players.getLocal().isMoving()) {
			if (There(PILLARS)) {
				SceneObject p = SceneEntities.getNearest(PILLARS);
				if (p != null) {
					if (MyObjects.isOnScreen(p)) {
						if (Calculations.distanceTo(p) > 6) {
							Camera.turnTo(p);
						}
						p.interact("Fix");
						ShadowDungeon.SleepTillStop();
					} else {
						Walking.walk(p);
						Camera.turnTo(p);
					}
				}
			} else if (There(RUBBLE)) {
				SceneObject r = SceneEntities.getNearest(RUBBLE);
				if (r != null) {
					if (MyObjects.isOnScreen(r)) {
						if (Calculations.distanceTo(r) > 6) {
							Camera.turnTo(r);
						}
						if (r.interact("Mine")) {
							ShadowDungeon.SleepTillStop();
							ShadowDungeon.SleepTillStop();
							if (MyObjects.getCount(RUBBLE) == 0) {
								ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
							}
						}
						if (ShadowDungeon.Message.toLowerCase().contains("level of")) {
							Constants.Break_Puzzle = true;
							ShadowDungeon.Message = null;
						}
					} else {
						Walking.walk(r);
						Camera.turnTo(r);
					}
				}
			}
		} else {
			Task.sleep(50, 150);
		}
	}

	private boolean There(int[] p2) {
		SceneObject obj = SceneEntities.getNearest(p2);
		if (obj == null) {
			return false;
		}
		return Calc.Reach(obj);
	}
}
