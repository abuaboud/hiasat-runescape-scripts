package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.interactive.NPC;


public class Planefreezer extends Boss {

	public static final int[] SNOW = { 49334 };

	@Override
	public String getName() {
		return "Plane-freezer Lakhrahnaz";
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
		
		  MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MAGIC;
		  MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MAGIC; if
		  (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
		  MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive()); } else {
		 MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive()); }
		 
		Attack(Boss);
	}

	public void Attack(NPC Boss) {
		if (Players.getLocal().getInteracting() != Boss) {
			if (Calculations.distanceTo(Boss) < 2) {
				Boss.interact("Attack");
				Task.sleep(600, 900);
			} else {
				if (Boss.getLocation().isOnScreen()) {
					Boss.getLocation().interact("Walk here");
					Task.sleep(1000, 1500);
				} else {
					MyPlayer.WalkTo(Boss.getLocation());
				}
			}
		}
	}
}
