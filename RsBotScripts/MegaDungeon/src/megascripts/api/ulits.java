package megascripts.api;

import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.myplayer.MyPlayer;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;


/**
 * 
 * @author Magorium
 *
 */
public class ulits {

	/*
	 * Covert ArarayList To int[]
	 */
	public static int[] convertIntegers(ArrayList<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	/*
	 * Convert int[][] to int[]
	 */
	public static int[] convertInt(int[][] g){
		ArrayList<Integer> integers = new ArrayList<Integer>();
		integers.clear();
		for(int[] e : g){
			for(int d :e ){
				integers.add(d);
			}
		}
		return convertIntegers(integers);
	}

	public static int[] convertInt(int[][][] g) {
		ArrayList<Integer> integers = new ArrayList<Integer>();
		integers.clear();
		for(int[][] e : g){
		for(int[] d : e){
			for(int o :d ){
				integers.add(o);
			}
		}
		}
		return convertIntegers(integers);
	}
	/*
	 * Tile in Room
	 */
	public static boolean tileinroom(Tile e){
		for(Tile x : Flood.getArea().getTileArray()){
			if(x !=null){
				if(currRoom.MatchTile(x,e)){
					return true;
				}
			}
		}
		return false;
	}
	/*
	 * Match Id with group
	 */
	public static boolean MatchID(int e , int d[]){
		for(int x :d ){
			if(x == e){
				return true;
			}
		}
		return false;
	}
	/*
	 * Match Tile with group
	 */
	public static boolean MatchTile(Tile e , Tile[] d){
		for(Tile x :d){
			if(currRoom.MatchTile(e,x)){
				return true;
			}
		}
		return false;
	}
	/*
	 * Click Random tile
	 */
	public static void WalktoRandomTileOnMap() {
		Constants.CurrentRoom = Flood.getArea();
		Tile[] tiles = Constants.CurrentRoom.getTileArray();
		Tile randTile = tiles[Random.nextInt(0, tiles.length)];
		MyPlayer.WalkTo(randTile);
	}
	/*
	 * Click Randon Tile that are away From you 
	 */
	public static void WalktoTileAway(int dist) {
		ArrayList<Tile> filter = new ArrayList<Tile>();
		Constants.CurrentRoom = Flood.getArea();
		for(Tile e : Constants.CurrentRoom.getTileArray()){
			if(e != null && Calculations.distanceTo(e) == dist){
				filter.add(e);
			}
		}
		Tile[] tiles = filter.toArray(new Tile[filter.size()]);
		Tile randTile = tiles[Random.nextInt(0, tiles.length)];
		Walking.walk(randTile);
	}
	/*
	 * Click Random Tile With distance as min
	 */
	
	public static void WalktoRandomTileOnMap(int dist) {
		ArrayList<Tile> filter = new ArrayList<Tile>();
		Constants.CurrentRoom = Flood.getArea();
		for(Tile e : Constants.CurrentRoom.getTileArray()){
			if(e != null && Calculations.distanceTo(e) > dist){
				filter.add(e);
			}
		}
		Tile[] tiles = filter.toArray(new Tile[filter.size()]);
		Tile randTile = tiles[Random.nextInt(0, tiles.length)];
		Walking.walk(randTile);
	}
	/*
	 * Walk To Centeral Tile
	 */
	public static void WalktoCentralTile() {
		Walking.walk(currRoom.getCentralTile());
		ShadowDungeon.SleepTillStop();
		ShadowDungeon.SleepTillStop();
	}
	/*
	 * Get NErast Tile 
	 */
	public static Tile getNearstTile(Tile n) {
		ArrayList<Integer>Dist = new ArrayList<Integer>();
		ArrayList<Tile>objects = new ArrayList<Tile>();
		Dist.clear();
		objects.clear();
		

		for(Tile x : Flood.getArea().getTileArray()){
			if(x !=null && getObject(x) == null){
				int e = (int)Calculations.distance(x,n);
				Dist.add(e);
				objects.add(x);
			}
		}
		if(Dist.isEmpty()){
			return null;
		}
		int m =Collections.min(Dist);
		int re = Dist.indexOf(m);
		if(objects.get(re)== null){
			return null;
		}
		return objects.get(re);

}
	public static Object getObject(Tile x) {
		return SceneEntities.getAt(x);
	}
}
