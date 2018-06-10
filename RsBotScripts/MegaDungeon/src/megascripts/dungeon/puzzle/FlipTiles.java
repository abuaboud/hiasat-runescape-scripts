package megascripts.dungeon.puzzle;

import java.awt.Color;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class FlipTiles extends Puzzle {

	public static int Green[] = { 49638, 49639, 49640, 54065 };
	public static int Yellow[] = { 49641, 49642, 49643, 54066 };

	@Override
	public String getName() {
		return "Flip Tiles";
	}

	@Override
	public String getAuthor() {
		return "Arthurr";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		SceneObject Yellow_Tile = SceneEntities.getNearest(Yellow);
		SceneObject Green_Tile = SceneEntities.getNearest(Green);
		if (Green_Tile == null || Yellow_Tile == null) {
			return false;
		}
		return Calc.Reach(Yellow_Tile) && Calc.Reach(Green_Tile);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		SceneObject Green_Tile = SceneEntities.getNearest(Green);
		if (Green_Tile != null) {
			if (MyObjects.isOnScreen(Green_Tile)) {
				if (getTileCount(Green_Tile.getLocation(), Yellow) < getTileCount(
						Green_Tile.getLocation(), Green)) {
					Imbue(Green_Tile);
				} else {
					Force(Green_Tile);
				}
			} else {
				MyPlayer.WalkTo(Green_Tile.getLocation());
			}
		}
		if (isSolved()) {
			ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
		}
	}

	public static void Imbue(SceneObject object) {
		object.interact("Imbue");
		for (int d = 0; d < 20 && Players.getLocal().isMoving()
				|| Players.getLocal().getAnimation() != -1; d++, Task.sleep(
				100, 150))
			;
	}

	public static void Force(SceneObject object) {
		object.interact("Force");
		for (int d = 0; d < 20 && Players.getLocal().isMoving()
				|| Players.getLocal().getAnimation() != -1; d++, Task.sleep(
				100, 150))
			;
	}

	public static int getTileCount(Tile x, int[] yellow2) {
		int e = 0;

		if (getObject(x) != null && MatchID(getObject(x).getId(), yellow2)) {
			e++;
		}
		if (getObject(getWest(x)) != null
				&& MatchID(getObject(getWest(x)).getId(), yellow2)) {
			e++;
		}
		if (getObject(getEast(x)) != null
				&& MatchID(getObject(getEast(x)).getId(), yellow2)) {
			e++;
		}
		if (getObject(getNorth(x)) != null
				&& MatchID(getObject(getNorth(x)).getId(), yellow2)) {
			e++;
		}
		if (getObject(getSouth(x)) != null
				&& MatchID(getObject(getSouth(x)).getId(), yellow2)) {
			e++;
		}
		return e;
	}

	public static boolean MatchID(int e, int x[]) {
		for (int z : x) {
			if (z == e) {
				return true;
			}
		}
		return false;
	}

	public static SceneObject getObject(Tile x) {
		return SceneEntities.getAt(x);
	}

	public static Tile getWest(Tile x) {
		return new Tile((x.getX() - 1), x.getY(), 0);
	}

	public static Tile getEast(Tile x) {
		return new Tile((x.getX() + 1), x.getY(), 0);
	}

	public static Tile getNorth(Tile x) {
		return new Tile(x.getX(), (x.getY() + 1), 0);
	}

	public static Tile getSouth(Tile x) {
		return new Tile(x.getX(), (x.getY() - 1), 0);
	}
}
