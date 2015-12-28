package megascripts.dungeon.puzzle;


import megascripts.api.Calc;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;


public class Lodestone extends Puzzle {

	public static final int PURPLE_CRYSTAL = 17376;

    @Override
	public String getName() {
		return "Lodestone Power";
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
		GroundItem n = GroundItems.getNearest(PURPLE_CRYSTAL);
		if (n == null) {
			return false;
		}
		return Calc.Reach(n);
	}
    @Override
	public boolean isSolved() {
		return !isValid();
	}
    @Override
	public void solve() {
		Constants.LeaveDungeon = true;
	}
}
