package megascripts.dungeon.puzzle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Door;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.door.Back;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Leavers extends Puzzle{

	public static int[] Pull_Leaver = { 49381, 49382 };
	public static int[] Pulled_Leaver = { 49384, 49385 };
	public static Tile step;

	@Override
	public String getName() {
		return "Leavers";
	}
	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public boolean isValid() {
		SceneObject Leave = SceneEntities.getNearest(Pull_Leaver);
		if (Leave == null) {
			step = null;
			return false;
		}
		return Calc.Reach(Leave);
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	@Override
	public void solve() {
		Mouse.setSpeed(Speed.VERY_FAST);
		if (MyActions.ThreNPC()) {
			Room_Job.Kill();
		} else {
			ShadowDungeon.setStatus(getStatus());
			SceneObject Leave = SceneEntities.getNearest(Pull_Leaver);
			step = getFirstLeveaerTile();

			if (Leave != null) {
				Pull();
			}
		}
	}

	private  Tile getFirstLeveaerTile() {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<SceneObject> objects = new ArrayList<SceneObject>();
		Dist.clear();
		objects.clear();
		final Door back = new Back();
		SceneObject door = Back.getDoor();
		for (SceneObject x : SceneEntities.getLoaded(Pull_Leaver)) {
			if (door != null && x != null && ulits.tileinroom(x.getLocation())) {
				int e = (int) Calculations.distance(x, door);
				Dist.add(e);
				objects.add(x);
			}
		}
		if (Dist.isEmpty()) {
			return null;
		}
		int m = Collections.min(Dist);
		int r = Dist.indexOf(m);
		if (objects.get(r) == null) {
			return null;
		}
		return objects.get(r).getLocation();
	}



	private void Pull() {
	
		SceneObject Leave = SceneEntities.getNearest(Pull_Leaver);
		SceneObject[] Pulled = SceneEntities.getLoaded(Pulled_Leaver);
		if (Leave != null) {
			if (step != null && !Leave.getLocation().equals(step)
					&& Pulled.length == 0) {
				Walking.walk(step);
				Camera.turnTo(step);
			} else {
				Leave = SceneEntities.getNearest(Pull_Leaver);
		         if (Leave != null) {
		             if (MyObjects.isOnScreen(Leave))
		            	 Leave.interact("Pull");
		             else
		            	 Leave.getLocation().clickOnMap();
		         }

			}
		}
		if (isSolved()) {
			ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
		}
	}

}
