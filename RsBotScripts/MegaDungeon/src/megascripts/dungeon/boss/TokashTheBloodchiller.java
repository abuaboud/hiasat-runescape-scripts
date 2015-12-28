package megascripts.dungeon.boss;





import megascripts.api.Calc;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.wrappers.interactive.NPC;


public class TokashTheBloodchiller extends Boss{

    @Override
	public String getName() {
		return "To'Kash the Bloodchiller";
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
		MyNpc.Attack(Boss);
	}
}
