package megascripts.dungeon.boss;

import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.wrappers.interactive.NPC;



public class Wraped_Gulega extends Boss{

	public static int ROOT_ANIMATION = 15004;
	
    @Override
	public String getName(){
		return "Warped Gulega";
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
				&& ulits.tileinroom(MyNpc.getNearstNpc(getName()).getLocation());
	}
    @Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		NPC Boss = MyNpc.getNearstNpc(getName());
		MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MELEE;
		MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MELEE;
		if (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
			MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive());
		} else {
			MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive());
		}
	
		if (Boss.getAnimation() == ROOT_ANIMATION) {
			ulits.WalktoRandomTileOnMap(3);
			ShadowDungeon.SleepTillStop();
		} else {
			MyNpc.Attack(Boss);
		}
	}

}
