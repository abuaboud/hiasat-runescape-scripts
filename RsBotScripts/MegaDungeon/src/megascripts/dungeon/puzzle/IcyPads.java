package megascripts.dungeon.puzzle;

import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;

public class IcyPads extends Puzzle {

	public static final int[] ICY_PRESSURE_PADS = { 49320, 49321, 49322, 49323 };
	@Override
	public String getStatus() {
		return "Solving: Icy Pads";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public String getName() {
		return "Icy Pads";
	}
	@Override
	public boolean isValid() {
		return MyObjects.There(ICY_PRESSURE_PADS);
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
