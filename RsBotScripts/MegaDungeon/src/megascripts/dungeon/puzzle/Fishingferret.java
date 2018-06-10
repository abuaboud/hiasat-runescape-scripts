package megascripts.dungeon.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


import megascripts.api.Flood;
import megascripts.api.LocalPathCustom;
import megascripts.api.currRoom;
import megascripts.api.myplayer.MyItems;
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
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.map.LocalPath.Vertex;
import org.powerbot.game.api.wrappers.map.Path.TraversalOption;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * 
 * @author Magorium
 * 
 */
public class Fishingferret extends Puzzle{

	public static final int RAW_FISH = 17374,COOKED_FISH = 17375 ,FIRE = 49940;
	
	public static int[] TILE = {49546,49547,49548}, HOLE = {49549,49550,49551}, PAD = {49555,49556,49557} , FISHING_SPOT = {49561,49562,49563};
	public static int Ferrets = 11007;
	public static Tile Path;
	public static Tile[] PathToPad = null;

	@Override
	public String getName() {
		return "Fishing Ferrts";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}
	@Override
	public boolean isValid() {
		SceneObject d = SceneEntities.getNearest(PAD);
		if (d == null) {
			return false;
		}
		Constants.CurrentRoom = Flood.getArea();
		return Constants.CurrentRoom.contains(d.getLocation());
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		NPC FERRET = NPCs.getNearest(Ferrets);
		SceneObject PRESUREPAD = SceneEntities.getNearest(PAD);
		PathToPad = LocalPathCustom.findPath(FERRET.getLocation(),	PRESUREPAD.getLocation());
	    int AMOUNT_FISH = PathToPad.length -1;
    	Tile nextTile = PathToPad[1];
	    if(Inventory.getCount(COOKED_FISH) >= AMOUNT_FISH){
	    	if(currRoom.isOnScreen(nextTile.getCentralPoint())){
	    		Camera.turnTo(nextTile);
	    		MyItems.Interact(COOKED_FISH, "Use");
	    		nextTile.interact("Use");
	    		Task.sleep(2000,2500);
	    		ShadowDungeon.SleepTillStop();
	    		ShadowDungeon.sleep(1000,1500);
	    	}else{
	    		MyPlayer.WalkTo(nextTile);
	    	}
		} else if (MyItems.contains(RAW_FISH)) {
            for(int e = 0 ; e < 100 && MyItems.contains(RAW_FISH) ; e++){
        		SceneObject n = SceneEntities.getNearest(FIRE);
    			if(n !=null){
    				if(MyObjects.isOnScreen(n)){
    					if(MyPlayer.getAnimation() == -1){
    				if(!ThereFish(nextTile)){
    						MyItems.Interact(RAW_FISH, "Use");
    						n.interact("Use");
    						Task.sleep(1000,1500);
    						ShadowDungeon.SleepTillStop();
    				}
    					}
    				}else{
    					MyPlayer.WalkTo(n.getLocation());
    				}
    			}
            }
		} else{
			SceneObject n = SceneEntities.getNearest(FISHING_SPOT);
			if(n !=null){
				if(MyObjects.isOnScreen(n)){
					if(MyPlayer.getAnimation() == -1){
						n.interact("Fish");
						Task.sleep(1000,1500);
					}
				}else{
					MyPlayer.WalkTo(n.getLocation());
				}
			}
		}
		if (isSolved()) {
			ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
		}
	}

	private static boolean ThereFish(Tile e) {
		for (GroundItem Fish : GroundItems.getLoaded(COOKED_FISH)) {
			if (Fish != null) {
				if (currRoom.MatchTile(Fish.getLocation(), e)) {
					return true;
				}
			}
		}
		return false;
	}
}
