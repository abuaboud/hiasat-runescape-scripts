package megascripts.dungeon.puzzle;

import megascripts.api.myplayer.MyActions;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Room_Job;

/**
 * 
 * @author Magorium
 *
 */
public class CurrentPuzzle {

	public static final Puzzle[] PUZZLES = { new Agilty_Maze(), new Barrels(),
			new DamagedBridge(), new BloodFountain(), new Collapsingroom(),
			new ColoredFurret(), new CrystalPower(), new Fremennikcamp(),
			new ColouredRecess(), new SilidingStatues(), new Lodestone(),
			new IcyPads(), new HunterFerret(), new LineStatues(),
			new Magicalconstruct(), new Leavers(), new ThreeWeapionStatus(),
			new Sleepingguards(), new Enigmatic(), new SeekerSentinel(),
			new UnhappyGhost(), new Fishingferret(), new WhinchBridge(),
			new FallowTheLeader(), new Monolith(), new GrooveSpike(),
			new FlipTiles(), new Mercenaryleader(), new Poltergeist(),
			new SlidingPuzzle(), new Pondskaters(), new FlowerRoot(),
			new ToxinMaze(), new StatuesBridge() };

	public static boolean TherePuzzle() {
		return TherePuzzles();
	}

	public static void solve() {
		try {
			if (MyActions.ThreNPC()
					&& (TherePuzzles() || PUZZLES[25].isSolved() || PUZZLES[17]	.isSolved())) {
				Room_Job.Kill();
			} else {
				Room_Job.Eat();
				for (Puzzle i : PUZZLES) {
					if (!i.isSolved() && Constants.Break_Puzzle == false) {
						i.solve();
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private static boolean TherePuzzles() {
		for (Puzzle i : PUZZLES) {
			if (!i.isSolved()) {
				return true;
			}
		}
		return false;
	}

}
