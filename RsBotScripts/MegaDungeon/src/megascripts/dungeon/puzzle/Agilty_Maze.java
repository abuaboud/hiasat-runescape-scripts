package megascripts.dungeon.puzzle;

import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.node.SceneObject;



public class Agilty_Maze extends Puzzle{

	public static final int[] Agilty_Grove = { 49666,49667 },
			Agitly_Trap = { 49657,51772 }, Agitly_Pendulum = { 49660,49661 },
			Agitly_Blades = { 49663 ,49664}, AGILITY_DOORS = { 49693 };
	
	@Override
	public  String getName(){
		return "Agilty Maze";
	}
	@Override
	public  String getStatus(){
		return "Solving " + getName()+ "...";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public boolean isValid(){
		return SceneEntities.getNearest(AGILITY_DOORS) != null && ulits.tileinroom(SceneEntities.getNearest(AGILITY_DOORS).getLocation());
	}
	@Override
	public  boolean isSolved(){
		return !isValid();
	}
	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
	        SceneObject r = SceneEntities.getNearest(AGILITY_DOORS);
		if((r !=null && Calc.Reach(r)) && MyObjects.There(Agitly_Blades)){

			if(r != null){
				r.interact("Open");
				ShadowDungeon.SleepTillStop();
			}
		}
		if (MyObjects.There(Agitly_Blades)) {
			MyPlayer.WalkTo(SceneEntities.getNearest(AGILITY_DOORS).getLocation());
		} else if (MyObjects.There(Agitly_Pendulum)) {
			MyPlayer.WalkTo(SceneEntities.getNearest(Agitly_Blades).getLocation());
		}else if (MyObjects.There(Agitly_Trap)) {
			MyPlayer.WalkTo(SceneEntities.getNearest(Agitly_Pendulum).getLocation());
		}else if (MyObjects.There(Agilty_Grove)) {
			SceneObject n = SceneEntities.getNearest(Agilty_Grove);
			if(n != null){
				n.interact("Step-onto");
				ShadowDungeon.SleepTillStop();
			}
		}
	}
	public static boolean There(int[] e) {
		SceneObject d = SceneEntities.getNearest(e);
		if (d == null) {
			return false;
		}
		return Calc.Reach(d) || Flood.getArea().contains(d.getLocation());
	}


}
