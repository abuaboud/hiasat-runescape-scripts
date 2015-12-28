package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class Gluttonousbehemoth extends Boss {
	static int Gluttonous_Food = 49283;
    @Override
	public String getName() {
		return "Gluttonous behemoth";
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
		SceneObject Food = SceneEntities.getNearest(Gluttonous_Food);
		NPC Boss = MyNpc.getNearstNpc(getName());
		MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MELEE;
		MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MELEE;
		if (MyPlayer.getPrayerLevel() >= effect2.getRequiredLevel()) {
			MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive());
		} else {
			MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive());
		}
		if(Calculations.distanceTo(Food) < 4){
		    MyNpc.Attack(Boss);
		}else{
			MyPlayer.WalkTo(Food.getLocation());
		}
	}
}
