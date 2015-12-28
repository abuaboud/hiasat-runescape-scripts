package megascripts.dungeon.puzzle;


import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class Pondskaters extends Puzzle{

	public static int[] Pondskaters = { 12089, 12091, 12092, 12093 };
	public static boolean Solved = false;

	@Override
	public String getName() {
		return "Pondskaters";
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
		NPC n = NPCs.getNearest(Pondskaters);
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
		if (MyActions.ThreNPC()) {
			Room_Job.Kill();
		} else {
			ShadowDungeon.setStatus(getStatus());
			NPC a = getKeyNpc();
			if (a != null) {
				if (MyNpc.isOnScreen(a)) {
					a.interact("Catch");
				} else {
					Camera.turnTo(a);
				}
			}
		}
	}

	private static NPC getKeyNpc() {
		for (NPC n : NPCs.getLoaded(Pondskaters)) {
			if (n != null && n.getAnimation() == 14405) {
				return n;
			}
		}
		return null;
	}
}
