package megascripts.dungeon.puzzle;

import java.util.ArrayList;
import java.util.Collections;

import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.currRoom;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;



public class HunterFerret extends Puzzle {
	public static final int[] HOLES = { 49587, 49588, 54004 }, CORNER = {
			50663, 54785, 51762 };
	public static final int[] DRY_LOGS = { 17377 }, TRAPS = { 17378 },
			SET_TRAPS = { 49592 }, COMPLETED_TRAPS = { 49593 }, Tree = { 49590,
					49591, 54005 };

	public static Tile tile = null;
	
	@Override
	public String getName() {
		return "Hunter Ferret";
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
		SceneObject n = SceneEntities.getNearest(COMPLETED_TRAPS);
		return (MyObjects.There(n) || MyNpc.There("Ferret"))
				&& MyObjects.There(HOLES);

	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		NPC ferret = NPCs.getNearest("Ferret");
		if (MyObjects.getCount(SET_TRAPS) < 3) {
			if (Inventory.getCount(DRY_LOGS) + Inventory.getCount(TRAPS) < 3 - MyObjects
					.getCount(COMPLETED_TRAPS)) {
				SceneObject tree = SceneEntities.getNearest(Tree);
				if (tree != null) {
					if (tree.isOnScreen()) {
						if (tree.interact("Chop")) {
							ShadowDungeon.sleep(1000, 1500);
							ShadowDungeon.SleepTillStop();
						}
					} else {
						MyPlayer.WalkTo(tree.getLocation());
					}
				}
			} else if (MyItems.contains(TRAPS)) {
				SceneObject n = SceneEntities.getNearest(HOLES);
				if (n != null) {
					Tile e = ferret.getLocation();
					if (SceneEntities.getAt(e) != null) {
						getNearstTile(e);
					}
					for (int p = 0; p < 4; p++) {
						int d = (int) Calculations.distanceTo(e);
						if (d < 2) {
							MyItems.Interact(TRAPS, "Lay");
							ShadowDungeon.SleepTillStop();
							ShadowDungeon.sleep(2500, 3000);
						} else {
							if (currRoom.isOnScreen(e.getCentralPoint())) {
								e.click(true);
							} else {
								MyPlayer.WalkTo(e);
							}
							ShadowDungeon.sleep(500, 600);
							ShadowDungeon.SleepTillStop();
						}
					}
				}
			} else if (MyItems.contains(DRY_LOGS)) {
				MyItems.Interact(DRY_LOGS, "Make-trap");
				ShadowDungeon.sleep(500, 600);
			}
		}
		if (MyObjects.getCount(SET_TRAPS) == 3) {
			if (ferret != null) {
				if (ferret.isMoving()) {
					int ds = (int) Calculations.distanceTo(SceneEntities
							.getNearest(CORNER));
					if (ds > 2) {
						MyPlayer.WalkTo(SceneEntities.getNearest(CORNER)
								.getLocation());
					} else {
						Camera.turnTo(ferret);
					}
				} else {
					MyPlayer.WalkTo(ferret.getLocation());
				}
			}
		}
		SceneObject COMPLETEDTRAP = SceneEntities.getNearest(COMPLETED_TRAPS);
		if (COMPLETEDTRAP != null) {

			for (int x = 0; x < 3; x++) {
				if (MyObjects.isOnScreen(COMPLETEDTRAP)) {
					if (COMPLETEDTRAP.interact("Pick-up")) {
						ShadowDungeon.sleep(400, 600);
						ShadowDungeon.SleepTillStop();
						ShadowDungeon.sleep(400, 600);
						ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
						for (int e = 0; e < 3; e++) {
							if (Inventory.contains(DRY_LOGS)) {
								MyItems.Interact(DRY_LOGS, "Drop");
							}
						}
					}
				} else {
					MyPlayer.WalkTo(COMPLETEDTRAP.getLocation());
				}
			}
		}
	}

	private static Tile getNearstTile(Tile n) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<Tile> objects = new ArrayList<Tile>();
		Dist.clear();
		objects.clear();

		for (Tile x : Flood.getArea().getTileArray()) {
			if (x != null && getObject(x) == null) {
				int e = (int) Calculations.distance(x, n);
				Dist.add(e);
				objects.add(x);
			}
		}
		if (Dist.isEmpty()) {
			return null;
		}
		int m = Collections.min(Dist);
		int re = Dist.indexOf(m);
		if (objects.get(re) == null) {
			return null;
		}
		return objects.get(re);

	}

	private static Object getObject(Tile x) {
		SceneObject e = SceneEntities.getAt(x);
		if (e != null) {
			return e;
		}
		return null;
	}

}
