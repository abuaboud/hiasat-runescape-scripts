package megascripts.api.myplayer;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.Calc;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class MyObjects {

	public static SceneObject getNearstObjectTo(int[] o,int objs) {
		ArrayList<Integer>Dist = new ArrayList<Integer>();
		ArrayList<SceneObject>objects = new ArrayList<SceneObject>();
		Dist.clear();
		objects.clear();
		SceneObject obj =  SceneEntities.getNearest(o);
		if(obj == null){
			return null;
		}
		for(SceneObject x : SceneEntities.getLoaded(objs)){
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
	public static SceneObject getNearstObjectTo(int o,int[] objs) {
		ArrayList<Integer>Dist = new ArrayList<Integer>();
		ArrayList<SceneObject>objects = new ArrayList<SceneObject>();
		Dist.clear();
		objects.clear();
		SceneObject obj =  SceneEntities.getNearest(o);
		if(obj == null){
			return null;
		}
		for(SceneObject x : SceneEntities.getLoaded(objs)){
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
	
	public static NPC getNearstNPC(int[] o,int npc) {
		ArrayList<Integer>Dist = new ArrayList<Integer>();
		ArrayList<NPC>objects = new ArrayList<NPC>();
		Dist.clear();
		objects.clear();
		SceneObject obj =  SceneEntities.getNearest(o);
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
	
	public static int getCount(int e) {
		int count = 0;
		for (SceneObject d : SceneEntities.getLoaded(e)) {
			if (d != null && Calc.Reach(d)) {
				count++;
			}
		}
		return count;
	}
	public static int getCount(int[] e) {
		int count = 0;
		for (SceneObject d : SceneEntities.getLoaded(e)) {
			if (d != null && Calc.Reach(d)) {
				count++;
			}
		}
		return count;
	}
	public static boolean isOnScreen(SceneObject p){
		Rectangle ScreenArea = new Rectangle(0, 52, 521, 259);
		return ScreenArea.contains(p.getCentralPoint());
	}

	public static boolean There(int e) {
		SceneObject d = SceneEntities.getNearest(e);
		if (d == null) {
			return false;
		}
		return Calc.Reach(d);
	}
	public static boolean There(SceneObject d) {
		if (d == null) {
			return false;
		}
		return Calc.Reach(d);
	}
	public static boolean There(int[] e) {
		SceneObject d = SceneEntities.getNearest(e);
		if (d == null) {
			return false;
		}
		return Calc.Reach(d);
	}
}
