package megascripts.api.myplayer;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.Calc;
import megascripts.api.currRoom;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class MyNpc {

	public static boolean isOnScreen(NPC p){
		Rectangle ScreenArea = new Rectangle(0, 52, 521, 259);
		return ScreenArea.contains(p.getCentralPoint());
	}
	public static boolean There(NPC n){
		return n !=null && Calc.Reach(n);
	}
	public static boolean There(String e){
		NPC n = NPCs.getNearest(e);
		if(n == null){
			return false;
		}
		return n !=null && Calc.Reach(n);
	}
	public static boolean There(int e) {
		NPC d = NPCs.getNearest(e);
		if (d == null) {
			return false;
		}
		return Calc.Reach(d);
	}
	public static boolean There(int[] e) {
		NPC d = NPCs.getNearest(e);
		if (d == null) {
			return false;
		}
		return Calc.Reach(d);
	}
	
	public static NPC getAt(Tile e){
		for(NPC s : NPCs.getLoaded()){
			if(s != null && currRoom.MatchTile(s.getLocation(), e)){
				return s;
			}
		}
		return null;
	}
	public static NPC getNearstNpc(final String Name){
		return NPCs.getNearest(new Filter<NPC>(){

			@Override
			public boolean accept(NPC n) {
				return n.getName().toLowerCase().contains(Name.toLowerCase());
			}

		});
	}
	public static NPC getNearstNPCTo(int[] sILIDING1,int[] npc) {
		ArrayList<Integer>Dist = new ArrayList<Integer>();
		ArrayList<NPC>objects = new ArrayList<NPC>();
		Dist.clear();
		objects.clear();
		NPC obj =  NPCs.getNearest(sILIDING1);
		if(obj == null){
			return null;
		}
		for(NPC x : NPCs.getLoaded(npc)){
			if(x !=null && Calc.Reach(x)){
				int e = (int)Calculations.distance(x,obj);
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

	public static void Attack(NPC Boss) {
		if (!Boss.isInCombat()) {
			if (Calculations.distanceTo(Boss) < 4) {
				Boss.interact("Attack");
				Task.sleep(600, 900);
			} else {
				MyPlayer.WalkTo(Boss.getLocation());
			}
		}
	}

	public static void Attack(NPC Boss,boolean spam) {
		if (Players.getLocal().getInteracting() != Boss) {
			if (Calculations.distanceTo(Boss) < 4) {
				Boss.interact("Attack");
				Task.sleep(600, 900);
			} else {
				MyPlayer.WalkTo(Boss.getLocation());
			}
		}

	}
}
