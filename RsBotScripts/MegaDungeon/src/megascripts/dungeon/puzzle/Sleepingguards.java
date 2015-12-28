package megascripts.dungeon.puzzle;


import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyGroundItems;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class Sleepingguards extends Puzzle{
	public static final int[] TRASH = { 49315, 49316, 49317, 49318 };
	public static final int GUARD_ROOM_KEY = 17363;
	public static boolean Solved = false;

	@Override
	public String getName() {
		return "Sleeping guards";
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
		if (Solved) {
			return false;
		}
		return SceneEntities.getNearest(TRASH) != null
				&& ulits.tileinroom(SceneEntities.getNearest(TRASH)
						.getLocation());
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		NPC Brute = MyNpc.getNearstNpc("Brute");
		if (Brute != null) {
			Kill();
		} else {
			GroundItem key = GroundItems.getNearest(GUARD_ROOM_KEY);
			if (MyItems.contains(GUARD_ROOM_KEY) && key == null) {
				Solved = true;
				ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
			} else {
				if (key != null) {
					if (MyGroundItems.isOnScreen(key)) {
						key.interact("Take", key.getGroundItem().getName());
						Task.sleep(400, 800);
					} else {
						MyPlayer.WalkTo(key.getLocation());
					}
				} else {
					ulits.WalktoRandomTileOnMap();
				}
			}
		}
	}

	public void Kill() {
		try {
			Room_Job.Eat();
			for (NPC Monster : NPCs.getLoaded()) {
				if (Monster != null) {
					if (Players.getLocal().getInteracting() == null
							&& Monster.getLevel() > 0
							&& Monster.getId() != Constants.Smuggler) {
						if (ulits.tileinroom(Monster.getLocation())) {
							if (Calculations.distanceTo(Monster) < 4) {
								Monster.interact("Attack");
								Task.sleep(400, 800);
								ShadowDungeon.SleepWhile(MyPlayer.isMoving());
							} else {
								MyPlayer.WalkTo(Monster.getLocation());
							}
						}
					}
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
