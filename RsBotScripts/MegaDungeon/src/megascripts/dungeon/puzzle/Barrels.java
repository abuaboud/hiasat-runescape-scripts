package megascripts.dungeon.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import megascripts.api.Calc;
import megascripts.api.LocalPathCustom;
import megascripts.api.PathFinder;
import megascripts.api.ulits;
import megascripts.api.LocalPathCustom.Vertex;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.map.Path.TraversalOption;
import org.powerbot.game.api.wrappers.node.SceneObject;



public class Barrels extends Puzzle {

	public static int PRESURE_PAD = 52206;
	public static int BARREL = 11072;
	public static int BROKEN_BARREL = 11075;

	public static Tile BarrelTile = null;
	public static Tile Path = null;
	public static Tile[] PathToPad = null;
	public static ArrayList<Tile> BlackTiles = new ArrayList<Tile>();

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getName() {
		return "Barrels";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		return MyObjects.There(PRESURE_PAD)
				&& MyNpc.getNearstNpc("Barrel") != null
				&& ulits.tileinroom(MyNpc.getNearstNpc("Barrel").getLocation());
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		Constants.LeaveDungeon = true;
		/*
		 * SceneObject Pad = SceneEntities.getNearest(PRESURE_PAD); PathToPad =
		 * PathFinder.findPath(MyPlayer.getLocation(), Pad.getLocation());
		 * BarrelTile = getNearstBarrel(PathToPad).getLocation(); Path =
		 * BarrelTile; Tile next = BarrelTile; next.click(true);
		 * MegaDungeon.SleepTillStop(); Task.sleep(2000,2500);
		 * MegaDungeon.SleepTillStop();
		 * if(MegaDungeon.Message.contains("something stopping you")){
		 * BlackTiles.add(next); }
		 */

	}

	private static NPC getNearstBarrel(Tile[] e) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<NPC> objects = new ArrayList<NPC>();
		Dist.clear();
		objects.clear();

		for (NPC d : NPCs.getLoaded(BARREL)) {
			if (d != null && Calc.Reach(d) && ulits.tileinroom(d.getLocation())
					&& TherePushTile(d)
					&& !BlackTiles.contains(d.getLocation())) {
				objects.add(d);
				int a = (int) Calculations.distanceTo(d);
				Dist.add(a);
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

	private static boolean TherePushTile(NPC d) {
		Tile ex = d.getLocation();
		boolean a = getObject(getWest(ex)) == null
				|| getObject(getEast(ex)) == null
				|| getObject(getNorth(ex)) == null
				|| getObject(getSouth(ex)) == null;
		boolean b = getNPC(getWest(ex)) == null || getNPC(getEast(ex)) == null
				|| getNPC(getNorth(ex)) == null || getNPC(getSouth(ex)) == null;
		if (b || a) {
			return true;
		}
		return false;
	}

	public static NPC getNPC(Tile x) {
		return MyNpc.getAt(x);
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

	private static Tile end;
	private static Tile base;
	private static int[][] flags;
	private static int offX, offY;
	public static int PAD = PRESURE_PAD, TILE = PRESURE_PAD;
	private static TilePath tilePath;

	public boolean traverse(final EnumSet<TraversalOption> options) {
		return getNext() != null && tilePath.traverse(options);
	}

	public boolean validate() {
		return getNext() != null
				&& Calculations.distanceTo(getEnd()) > Math.sqrt(2);
	}

	/**
	 * {@inheritDoc}
	 */

	public boolean init() {
		if (!Game.getMapBase().equals(base)) {
			final int[][] flags = adjustCollisionFlags(Walking
					.getCollisionFlags(Game.getPlane()));
			if (flags != null) {
				base = Game.getMapBase();
				final Tile start = Players.getLocal().getLocation();
				final Tile[] tiles = findPath(start, end);
				if (tiles == null) {
					base = null;
					return false;
				}
				tilePath = new TilePath(tiles);
			}
		}
		return true;
	}

	public Tile getNext() {
		if (!init()) {
			return null;
		}
		return tilePath.getNext();
	}

	public Tile getStart() {
		return null;
	}

	public Tile getEnd() {
		return end;
	}

	public TilePath getTilePath() {
		return tilePath;
	}

	public static Tile[] findPath(final Tile start, final Tile end) {
		if (start.getPlane() != end.getPlane()) {
			return null;
		}
		final int curr_plane = Game.getPlane();
		final int base_x = Game.getBaseX(), base_y = Game.getBaseY();
		final int curr_x = start.getX() - base_x, curr_y = start.getY()
				- base_y;
		int dest_x = end.getX() - base_x, dest_y = end.getY() - base_y;

		final int plane = Game.getPlane();
		if (curr_plane != plane) {
			return null;
		}
		flags = Walking.getCollisionFlags(plane);
		final Tile offset = Walking.getCollisionOffset(plane);
		offX = offset.getX();
		offY = offset.getY();

		if (flags == null || curr_x < 0 || curr_y < 0 || curr_x >= flags.length
				|| curr_y >= flags.length) {
			return null;
		} else if (dest_x < 0 || dest_y < 0 || dest_x >= flags.length
				|| dest_y >= flags.length) {
			if (dest_x < 0) {
				dest_x = 0;
			} else if (dest_x >= flags.length) {
				dest_x = flags.length - 1;
			}
			if (dest_y < 0) {
				dest_y = 0;
			} else if (dest_y >= flags.length) {
				dest_y = flags.length - 1;
			}
		}

		final HashSet<Vertex> open = new HashSet<Vertex>();
		final HashSet<Vertex> closed = new HashSet<Vertex>();
		Vertex curr = new Vertex(curr_x, curr_y, curr_plane);
		final Vertex dest = new Vertex(dest_x, dest_y, curr_plane);

		curr.f = heuristic(curr, dest);
		open.add(curr);

		while (!open.isEmpty()) {
			curr = lowest_f(open);
			if (curr.equals(dest)) {
				return path(curr, base_x, base_y);
			}
			open.remove(curr);
			closed.add(curr);
			for (final Vertex next : Successors(curr)) {
				if (!closed.contains(next)) {
					final double t = curr.g + dist(curr, next);
					boolean use_t = false;
					if (!open.contains(next)) {
						open.add(next);
						use_t = true;
					} else if (t < next.g) {
						use_t = true;
					}
					if (use_t) {
						next.prev = curr;
						next.g = t;
						next.f = t + heuristic(next, dest);
					}
				}
			}
		}
		System.out.println("Null");
		return null;
	}

	public static double heuristic(final Vertex start, final Vertex end) {
		final double dx = Math.abs(start.x - end.x);
		final double dy = Math.abs(start.y - end.y);
		final double diag = Math.min(dx, dy);
		final double straight = dx + dy;
		return Math.sqrt(2.0) * diag + straight - 2 * diag;
	}

	public static double dist(final Vertex start, final Vertex end) {
		if (start.x != end.x && start.y != end.y) {
			return 1.41421356;
		} else {
			return 1.0;
		}
	}

	public static Vertex lowest_f(final Set<Vertex> open) {
		Vertex best = null;
		for (final Vertex t : open) {
			if (best == null || t.f < best.f) {
				best = t;
			}
		}
		return best;
	}

	public static LinkedList<Vertex> Successors(final Vertex t) {
		LinkedList<Vertex> tiles = new LinkedList<Vertex>();
		int x = t.x, y = t.y;
		try {
			if (SceneEntities.getAt(new Tile(x + 1, y, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x + 1, y, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)
				tiles.add(new Vertex(x + 1, y, 0));
		} catch (Exception e) {
		}
		try {
			if (SceneEntities.getAt(new Tile(x + 1, y + 1, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x + 1, y + 1, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)
				tiles.add(new Vertex(x + 1, y + 1, 0));
		} catch (Exception e) {
		}
		try {
			if (SceneEntities.getAt(new Tile(x, y + 1, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x, y + 1, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)
				tiles.add(new Vertex(x, y + 1, 0));
		} catch (Exception e) {
		}
		try {
			if (SceneEntities.getAt(new Tile(x - 1, y + 1, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x - 1, y + 1, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)
				tiles.add(new Vertex(x - 1, y + 1, 0));
		} catch (Exception e) {
		}
		try {
			if (SceneEntities.getAt(new Tile(x - 1, y, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x - 1, y, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)
				tiles.add(new Vertex(x - 1, y, 0));
		} catch (Exception e) {
		}
		try {
			if (SceneEntities.getAt(new Tile(x - 1, y - 1, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x - 1, y - 1, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)
				tiles.add(new Vertex(x - 1, y - 1, 0));
		} catch (Exception e) {
		}
		try {
			if (SceneEntities.getAt(new Tile(x, y - 1, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x, y - 1, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)
				tiles.add(new Vertex(x, y - 1, 0));
		} catch (Exception e) {
		}
		try {
			if (SceneEntities.getAt(new Tile(x + 1, y - 1, 0),
					SceneEntities.TYPE_FLOOR_DECORATION).getId() == TILE
					|| SceneEntities.getAt(new Tile(x + 1, y - 1, 0),
							SceneEntities.TYPE_FLOOR_DECORATION).getId() == PAD)

				tiles.add(new Vertex(x + 1, y - 1, 0));
		} catch (Exception e) {
		}
		return tiles;
	}

	public static Tile[] path(final Vertex end, final int base_x,
			final int base_y) {
		final LinkedList<Tile> path = new LinkedList<Tile>();
		Vertex p = end;
		while (p != null) {
			path.addFirst(p.get(base_x, base_y));
			p = p.prev;
		}
		return path.toArray(new Tile[path.size()]);
	}

	public static final class Vertex {
		public final int x, y, z;
		public Vertex prev;
		public double g, f;
		public boolean special;

		public Vertex(final int x, final int y, final int z) {
			this(x, y, z, false);
		}

		public Vertex(final int x, final int y, final int z,
				final boolean special) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.special = special;
			g = f = 0;
		}

		public int hashCode() {
			return x << 4 | y;
		}

		public boolean equals(final Object o) {
			if (o instanceof Vertex) {
				final Vertex n = (Vertex) o;
				return x == n.x && y == n.y && z == n.z;
			}
			return false;
		}

		public String toString() {
			return "(" + x + "," + y + ")";
		}

		public Tile get(final int baseX, final int baseY) {
			return new Tile(x + baseX, y + baseY, z);
		}
	}

	private int[][] adjustCollisionFlags(final int[][] flags) {
		final int lx = flags.length - 1;
		final int lx_m = lx - 5;
		for (int x = 0; x <= lx; x++) {
			final int ly = flags[x].length - 1;
			final int ly_m = ly - 5;
			for (int y = 0; y <= ly; y++) {
				if (x <= 5 || y <= 5 || x >= lx_m || y >= ly_m) {
					flags[x][y] = -1;
				}
			}
		}

		return flags;
	}

}
