package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.myplayer.MyPrayer.Ancient;
import megascripts.api.myplayer.MyPrayer.Modern;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;


public class Skeletaltrio extends Boss {

	public static String[] SKELETAL = { "Skeletal Warrior", "Skeletal Archer","Skeletal Sorcerer" };
	public static int MAGIC = 2;
	public static int MELEE = 0;
	public static int RANGE = 1;

	@Override
	public String getName() {
		return "Skeletal trio";
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
		int index = getCurrentIndex();
		if(index == -1){
			return false;
		}
		return MyNpc.getNearstNpc(SKELETAL[index]) != null
				&& ulits.tileinroom(MyNpc.getNearstNpc(SKELETAL[index]).getLocation());
	}

	@Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		int index = getCurrentIndex();
		NPC Boss = MyNpc.getNearstNpc(SKELETAL[index]);
		
		  MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MAGIC;
		 MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MAGIC; if
		 (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
		 MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive()); } else {
		 MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive()); }
		 
		MyNpc.Attack(Boss);
	}

	private int getCurrentIndex() {
		if (MyNpc.There(SKELETAL[MAGIC])) {
			return MAGIC;
		} else if (MyNpc.There(SKELETAL[RANGE])) {
			return RANGE;
		} else if (MyNpc.There(SKELETAL[MELEE])) {
			return MELEE;
		}
		return -1;
	}
}
