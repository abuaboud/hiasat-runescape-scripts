package megascripts.dungeon.boss;

import java.util.ArrayList;
import java.util.Collections;

import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;



public class Blink extends Boss{

	public static int[] PILLAR = {32195,32201,32231};
    public static Tile PILLAR_TILE = null;
    @Override
	public String getName() {
		return "Blink";
	}
    @Override
	public String getAuthor() {
		return "Magorium";
	}
    @Override
	public String getStatus() {
		CurrentBoss.BossName = getName();
		return "Attacking " + CurrentBoss.BossName + "...";
	}
    @Override
	public boolean isValid() {
		return MyNpc.getNearstNpc(getName()) != null
				&& Calc.Reach(MyNpc.getNearstNpc(getName()));
	}
    @Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		NPC Boss = MyNpc.getNearstNpc(getName());
		Tile pillar = getPillar(Boss);
		PILLAR_TILE = pillar;
		if((Boss.getMessage() != null && Boss.getMessage().toLowerCase().contains("oof")) || Boss.isInCombat()){
			Boss.interact("Attack");
			Task.sleep(1000,1500);
			ShadowDungeon.SleepTillStop();
		}else{
			SceneObject p = SceneEntities.getAt(pillar);
			if(p !=null && ulits.MatchID(p.getId(), PILLAR)){
				if(MyObjects.isOnScreen(p)){
					p.interact("Raise");
					Task.sleep(1000,1500);
					ShadowDungeon.SleepTillStop();
				}else{
					Walking.walk(p);
				}
			}else{
				Task.sleep(50,100);
			}
		}
		
	}

	private static Tile getPillar(NPC boss) {
		int Orientation = Math.round(boss.getOrientation() / 90);
		int x = 0;
		int y = 0;
		if (Orientation == 0) {
			x = 1;
			y = 0;
		} else if (Orientation == 1) {
			x = 0;
			y = 1;
		} else if (Orientation == 2) {
			x = -1;
			y = 0;
		} else if (Orientation == 3) {
			x = 0;
			y = -1;
		} else {
			x = 0;
			y = 0;
		}
		Tile e = getFarthestPillar(x, y, true, PILLAR, boss);
		return e;
	}

	private static Tile getFarthestPillar(int x, int y, boolean b,
			int[] pillar, NPC Boss) {
		ArrayList<SceneObject> obj = new ArrayList<SceneObject>();
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		obj.clear();
		Dist.clear();
		for (SceneObject o : SceneEntities.getLoaded(pillar)) {
			if (o != null && Boss != null && MatchTile(x, y, o, Boss)) {
                int di = (int) Calculations.distance(Boss, o);   
				Dist.add(di);
                obj.add(o);
			}
		}
		if(Dist.isEmpty()){
			return null;
		}
		int index = Dist.indexOf(Collections.max(Dist));
		return obj.get(index).getLocation();
	}

	private static boolean MatchTile(int x, int y, SceneObject o, NPC boss) {
		int ox = o.getLocation().getX();
		int oy = o.getLocation().getY();
		int bx = boss.getLocation().getX();
		int by = boss.getLocation().getY();
		if (x == -1 || x == 1) {
			if (x == -1) {
				if (oy == by && bx > ox) {
					return true;
				}
			} else if (x == 1) {
				if (oy == by && bx < ox) {
					return true;
				}
			}
		} else if (y == -1 || y == 1) {
			if (y == -1) {
				if (ox == bx && by > oy) {
					return true;
				}
			} else if (y == 1) {
				if (ox == bx && by < oy) {
					return true;
				}
			}
		}
		return false;
	}

}
