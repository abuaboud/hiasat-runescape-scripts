package megascripts.dungeon.puzzle;


import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class Collapsingroom extends Puzzle {

	public static int[] Rocks = { 49622, 49621, 49620, 49619, 49618, 49617,
			49616, 49615 };
	public static int[] Broken_Collapsing = { 49610, 49611, 54061 };

	@Override
	public String getName() {
		return "Collapsing room";
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
		return There(Rocks) || There(Broken_Collapsing);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		SceneObject Collapsing = SceneEntities
				.getNearest(new Filter<SceneObject>() {
					@Override
					public boolean accept(SceneObject obj) {
						return obj != null
								&& MatchID(obj.getId(), Broken_Collapsing)
								&& Calc.Reach(obj);
					}

				});

		SceneObject Rock = SceneEntities.getNearest(new Filter<SceneObject>() {
			@Override
			public boolean accept(SceneObject obj) {
				return obj != null && MatchID(obj.getId(), Rocks);
			}

		});
		if (Collapsing != null) {
			if (MyObjects.isOnScreen(Collapsing)) {
				Collapsing.interact("Repair");
				ShadowDungeon.SleepTillStop();
			} else {
				MyPlayer.WalkTo(Collapsing.getLocation());
			}
		} else if (Rock != null) {
			if (MyObjects.isOnScreen(Rock)) {
				Rock.interact("Destroy");
				ShadowDungeon.SleepTillStop();
			} else {
				MyPlayer.WalkTo(Rock.getLocation());
			}
		}
	}

	public static boolean MatchID(int e, int r[]) {
		for (int x : r) {
			if (x == e) {
				return true;
			}
		}
		return false;
	}

	public static boolean There(int[] e) {
		SceneObject d = SceneEntities.getNearest(e);
		if (d == null) {
			return false;
		}
		return Calc.Reach(d) || Flood.getArea().contains(d.getLocation());
	}
}
