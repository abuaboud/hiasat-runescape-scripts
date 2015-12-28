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
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;


public class Sagittare extends Boss {

	@Override
	public String getName() {
		return "Sagittare";
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
		
		  MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MAGIC;
		  MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MAGIC; if
		  (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
		  MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive()); } else {
		  MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive()); }
		Combat.TurnRet(false);
		if (Boss.getMessage() != null) {
			ulits.WalktoRandomTileOnMap();
			Task.sleep(600, 900);
			ShadowDungeon.SleepTillStop();
		} else {
			MyNpc.Attack(Boss);
		}
	}
	public static void Attack(NPC Boss) {
		if (!Boss.isInCombat()) {
			if (Calculations.distanceTo(Boss) < 4) {
				Boss.interact("Attack");
				Task.sleep(600, 900);
				ShadowDungeon.SleepTillStop();
				ShadowDungeon.SleepTillStop();
			} else {
				MyPlayer.WalkTo(Boss.getLocation());
			}
		}
	}
}
