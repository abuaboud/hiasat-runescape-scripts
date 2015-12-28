package megascripts.dungeon.puzzle;

import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;

public class SeekerSentinel extends Puzzle {

	public static final int[] ICY_PRESSURE_PADS = { 49320, 49321, 49322, 49323 };

	@Override
	public String getStatus() {
		return "Solving: Seeker Sentinel...";
	}

	@Override
	public String getAuthor() {
		return "Arthurr";
	}

	@Override
	public String getName() {
		return "Icy Pads";
	}

	@Override
	public boolean isValid() {
		return MyNpc.getNearstNpc("Seeker sentinel") != null
				&& ulits.tileinroom(MyNpc.getNearstNpc("Seeker sentinel")
						.getLocation());
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
