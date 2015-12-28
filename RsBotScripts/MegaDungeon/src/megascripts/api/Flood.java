package megascripts.api;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
 
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
 
/**
 * Made by Aonor.
 * Flood fill algorithm for calculating room tiles.
 */
public class Flood {
 
	public static int[] ALLFLAG = { 0x1, 0x2, 0x4, 0x8, 0x10, 0x20,
			0x40, 0x80, 0x100, 0x200, 0x400, 0x800, 0x1000, 0x2000, 0x4000,
			0x8000, 0x10000, 0x20000, 0x40000, 0x400000, 0x800000, 0x1000000,
			0x2000000, 0x4000000, 0x8000000, 0x10000000, 0x20000000,
			0x40000000, 0x1280100 };
	public static int WALL_NORTHWEST = 0x1, WALL_NORTH = 0x2,
			WALL_NORTHEAST = 0x4, WALL_EAST = 0x8, WALL_SOUTHEAST = 0x10,
			WALL_SOUTH = 0x20, WALL_SOUTHWEST = 0x40, WALL_WEST = 0x80,
			OBJECT_TILE = 0x100, WALL_BLOCK_NORTHWEST = 0x200,
			WALL_BLOCK_NORTH = 0x400, WALL_BLOCK_NORTHEAST = 0x800,
			WALL_BLOCK_EAST = 0x1000, WALL_BLOCK_SOUTHEAST = 0x2000,
			WALL_BLOCK_SOUTH = 0x4000, WALL_BLOCK_SOUTHWEST = 0x8000,
			WALL_BLOCK_WEST = 0x10000, OBJECT_BLOCK = 0x20000,
			DECORATION_BLOCK = 0x40000, WALL_ALLOW_RANGE_NORTHWEST = 0x400000,
			WALL_ALLOW_RANGE_NORTH = 0x800000,
			WALL_ALLOW_RANGE_NORTHEAST = 0x1000000,
			WALL_ALLOW_RANGE_EAST = 0x2000000,
			WALL_ALLOW_RANGE_SOUTHEAST = 0x4000000,
			WALL_ALLOW_RANGE_SOUTH = 0x8000000,
			WALL_ALLOW_RANGE_SOUTHWEST = 0x10000000,
			WALL_ALLOW_RANGE_WEST = 0x20000000,
			OBJECT_ALLOW_RANGE = 0x40000000, WATER = 0x1280100, BLOCKED = 0x1280100;

    private static Tile[] flooded;
    private static  Area area;
 
    public static ArrayList<Tile> getRoomBlocks(Area area){
    	ArrayList<Tile> blocks = new ArrayList<Tile>();
    	int[][] cblocks = Walking.getCollisionFlags(Game.getPlane());
    	for(Tile t : area.getTileArray()){
    		if((cblocks[t.getX() - Game.getBaseX()][t.getY() - Game.getBaseY()] & (0x1280100 | 0x100)) != 0){
    			blocks.add(t);
    		}
    	}
    	return blocks;//brb a min
    }
    
    /*public static ArrayList<Tile> getRoomBlocks(Area area){
    	ArrayList<Tile> blocks = new ArrayList<Tile>();
    	int[][] cblocks = Walking.getCollisionFlags(Game.getPlane());
    	for(Tile t : area.getTileArray()){
    		if((cblocks[t.getX() - Game.getBaseX()][t.getY() - Game.getBaseY()] & 0x1280100) != 0){
    			blocks.add(t);
    		}
    	}
    	return blocks;//brb a min
    }*/
    
	public static void CalcRoomArea(final Tile start) {
    	try{
        final Queue<Tile> queue = new LinkedList<Tile>();
        queue.add(start);
        final Queue<Tile> queue2 = new LinkedList<Tile>();
        final Queue<Tile> checked = new LinkedList<Tile>();
        int iterations = 0;
        while (!queue.isEmpty() && iterations < 1000) {
            final Tile t = queue.remove();
            if (!isWall(t) && !checked.contains(t)) {
                queue2.add(t);
                queue.add(t.derive(0, 1));
                queue.add(t.derive(1, 0));
                queue.add(t.derive(0, -1));
                queue.add(t.derive(-1, 0));
            }
            checked.add(t);
            iterations++;
        }
        flooded = queue2.toArray(new Tile[queue2.size()]);
        int min_x = Integer.MAX_VALUE, min_y = Integer.MAX_VALUE;
        int max_x = Integer.MIN_VALUE, max_y = Integer.MIN_VALUE;
        for (final Tile tile : flooded) {
            final int x = tile.getX(), y = tile.getY();
            if (x < min_x) min_x = x;
            if (y < min_y) min_y = y;
            if (x > max_x) max_x = x;
            if (y > max_y) max_y = y;
        }
        area = new Area(new Tile(min_x - 1, min_y - 1, start.getPlane()), new Tile(max_x + 2, max_y + 2, start.getPlane()));
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
 
    public static Tile[] getFlood() {
        return flooded;
    }
 
    public static  Area getArea() {
    	CalcRoomArea(Players.getLocal().getLocation());
        return area;
    }
 
    private static int getFlag(final Tile t) {
        final int base_x = Game.getBaseX(), base_y = Game.getBaseY();
        final int x = t.getX() - base_x, y = t.getY() - base_y;
        final Tile off = Walking.getCollisionOffset(Game.getPlane());
        return Walking.getCollisionFlags(Game.getPlane())[x - off.getX()][y - off.getY()];
    }
 
    public static boolean isWall(final Tile t) {
        return (getFlag(t) >>> 21 & 0x401) != 0;
    }
 
}