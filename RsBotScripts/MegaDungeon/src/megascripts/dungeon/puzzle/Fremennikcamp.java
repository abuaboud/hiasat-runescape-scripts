package megascripts.dungeon.puzzle;

import java.awt.Color;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class Fremennikcamp extends Puzzle {

	public static final int[] Bar_Box = { 49529, 49530 };
	public static final int[] Fish_Box = { 49523, 49524 };
	public static final int[] Wooden_Box = { 49535, 49536 };

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getName() {
		return "Fremennikcamp";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		return Check(Bar_Box) || Check(Fish_Box) || Check(Wooden_Box);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		SceneObject Bar = SceneEntities.getNearest(Bar_Box);
		SceneObject Fish = SceneEntities.getNearest(Fish_Box);
		SceneObject Wood = SceneEntities.getNearest(Wooden_Box);
		if (Bar != null) {
			Interact(Bar, "Smith-battleaxes");
		} else if (Fish != null) {
			Interact(Fish, "Cook-fish");
		} else if (Wood != null) {
			Interact(Wood, "Fletch-bows");
		}
	}

	private void Interact(SceneObject n, String Action) {
		if (n != null && n.validate()) {
			if (MyObjects.isOnScreen(n)) {
				if (Calculations.distanceTo(n) > 5) {
					Camera.turnTo(n);
				}
				n.interact(Action);
				Task.sleep(1000, 1500);
				ShadowDungeon.SleepTillStop();
				if (n.getId() == SceneEntities.getNearest(Wooden_Box).getId()) {
					ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
				}
				Task.sleep(2000, 3500);
			} else {
				if (!Players.getLocal().isMoving()) {
					Walking.walk(n);
					Camera.turnTo(n);
				}
			}
		}
	}

	private boolean Check(int[] barBox) {
		SceneObject box = SceneEntities.getNearest(barBox);
		if (box == null) {
			return false;
		}
		return Calc.Reach(box);
	}

}
