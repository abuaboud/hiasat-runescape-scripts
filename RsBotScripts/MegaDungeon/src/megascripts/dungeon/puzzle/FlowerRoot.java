package megascripts.dungeon.puzzle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.ulits;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class FlowerRoot extends Puzzle{
	
	public static final int[] CENTRAL_FLOWERS_ALL = { 35525,35520,35507, 35523, 35562, 35569 ,35576,35562,35568};
	public static int[] RED = {35689,35809,35734,35606,35604},YELLOW = {35709,35830,35778,35609,35611},
			PURPLE = {35655,35719,35804,35602,35588}, BLUE = {35616,35799,35715,35613,35577};
	public static int[][] FLOWERS = { BLUE, PURPLE, RED, YELLOW };
	public static int[] C_RED = {35525,35562},C_YELLOW = {35568,35569},
			C_PURPLE = {35520,35523}, C_BLUE = {35576,35507};
	public static final int[][] CENTRAL_FLOWERS = { C_BLUE, C_PURPLE, C_RED, C_YELLOW };
	public static final String[] FLOWER_COLORS = { "blue", "purple", "red", "yellow" };
	public static int [] Flower_Color_ID = {0};
	public static int [] Central_Flower = {0};
	
	@Override
	public String getName(){
		return "Flower Root";
	}
	@Override
	public String getStatus(){
		return "Solving " + getName()+ "...";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public boolean isValid(){
		SceneObject f = SceneEntities.getNearest(CENTRAL_FLOWERS_ALL);
		if(f == null){
			return false;
		}
		return ulits.tileinroom(f.getLocation());
	}
	public boolean isSolved(){
		return !isValid();
	}
	@Override
	public void solve(){
		ShadowDungeon.setStatus(getStatus());
		SceneObject f = SceneEntities.getNearest(CENTRAL_FLOWERS_ALL);
		if(f != null){
		if(Reach(f)){
			if(f.isOnScreen()){
				f.interact("Uproot");
				Task.sleep(900,1600);
			}else{
				Walking.walk(f);
				ShadowDungeon.SleepTillStop();
			}
		} else {
				int CFID = f.getId();
				for (int z = 0; z < CENTRAL_FLOWERS.length; z++) {
					for (int x = 0; x < CENTRAL_FLOWERS[z].length; x++) {
						if (CFID == CENTRAL_FLOWERS[z][x]) {
							Flower_Color_ID = FLOWERS[z];
						}
					}
				}
			for(SceneObject sf: SceneEntities.getLoaded(Flower_Color_ID)){
			if(sf !=null && Calc.Reach(sf) && Dist(sf,f)){
				if(Calculations.distanceTo(sf) < 3){
					sf.interact("Chop");
					ShadowDungeon.SleepTillStop();
				} else {
					if (!Players.getLocal().isMoving()) {
						Walking.walk(sf);
						ShadowDungeon.SleepTillStop();
							}
						}
					}
				}
			}
		}
		if(ShadowDungeon.Message.contains("level of")){
			Constants.Break_Puzzle = true;
			ShadowDungeon.Message= null;
		}
		if(isSolved()){
			ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
		}
	}

	private static boolean Reach(SceneObject f) {
		int[] Flowers = { 35689, 35809, 35734, 35606, 35604, 35709, 35830,
				35778, 35609, 35611, 35655, 35719, 35804, 35602, 35588, 35616,
				35799, 35715, 35613, 35577 };
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		for (SceneObject m : SceneEntities.getLoaded(Flowers)) {
			if (m != null) {
				int d = (int) Calculations.distance(m, f);
				Dist.add(d);
			}
		}
		int md = (int) Calculations.distance(Players.getLocal(), f);
		Dist.add(md);
		int min = Collections.min(Dist);
		return min == md|| Calc.Reach(f);
	}
	private static boolean Dist(SceneObject sf, SceneObject f) {
		int e =(int) Calculations.distance(sf, f);
		int d = (int) Calculations.distance(Players.getLocal().getLocation(), f.getLocation());
		return e <=  d;
	}

}
