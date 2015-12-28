package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.interactive.NPC;


public class LuminescentIcefiend extends Boss{

	public static int SPECIAL_ANITMATION = 13338;

	@Override
	public String getName() {
		return "Luminescent icefiend";
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
		
		  MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MISSILES;
		  MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MISSILE; if
		  (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
		  MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive()); } else {
		  MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive()); }
		 
		if (Boss.getAnimation() == SPECIAL_ANITMATION) {
			int CORNR[] = { 0, 1, 2, 3 };
			for (int c : CORNR) {
				if (Constants.CurrentRoom.getBoundingTiles()[c] != null) {
					if (Boss.getAnimation() == SPECIAL_ANITMATION) {
						Walking.walk(Constants.CurrentRoom.getBoundingTiles()[c]);
						Walking.walk(Constants.CurrentRoom.getBoundingTiles()[c]);
						for (int x = 0; x < 30
								&& Calculations.distanceTo(Constants.CurrentRoom
										.getBoundingTiles()[c]) > 6; x++, Task
								.sleep(100, 150))
							;
					}
				}
			}
		} else {
			MyNpc.Attack(Boss);
		}
	}
}
