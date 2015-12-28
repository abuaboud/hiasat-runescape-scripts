package megascripts.dungeon.boss;

import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.Flood;
import megascripts.api.currRoom;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.myplayer.MyPrayer.Ancient;
import megascripts.api.myplayer.MyPrayer.Modern;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class LexicusRunewright extends Boss{
    @Override
	public String getName() {
		return "Lexicus Runewright";
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
	public boolean isValid(){
		return MyNpc.getNearstNpc(getName()) !=null && Calc.Reach(MyNpc.getNearstNpc(getName()));
	}
    @Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		NPC Boss = MyNpc.getNearstNpc(getName());
		MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MAGIC;
		MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MAGIC;
		if (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
			MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive());
		} else {
			MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive());
		}
		Constants.CurrentRoom = Flood.getArea();
		if (Boss.getMessage() != null) {
			int y = Random.nextInt(0, 3);
				Walking.walk(Constants.CurrentRoom.getBoundingTiles()[y]);
				ShadowDungeon.SleepTillStop();
			
		} else {
			MyNpc.Attack(Boss);
		}
	}
	public static Tile getNearstTile(Tile tils[]){
			ArrayList<Integer>Dist = new ArrayList<Integer>();
			ArrayList<Tile>objects = new ArrayList<Tile>();
			Dist.clear();
			objects.clear();
            Player p = Players.getLocal();
			for(Tile x : tils){
				if(x !=null && Calc.Reach(x)){
					int e = (int)Calculations.distance(x,p);
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
}
