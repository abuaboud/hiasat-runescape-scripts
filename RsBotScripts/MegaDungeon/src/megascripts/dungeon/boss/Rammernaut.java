package megascripts.dungeon.boss;

import java.awt.Color;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.myplayer.MyPrayer.Ancient;
import megascripts.api.myplayer.MyPrayer.Modern;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;

import shadowscripts.graphic.LogHandler;

public class Rammernaut extends Boss{

    @Override
	public String getName() {
		return "Rammernaut";
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
		MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MELEE;
		MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MELEE;
		if (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
			MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive());
		} else {
			MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive());
		}
		if (Boss.getMessage() != null) {
			LogHandler.Print("[" + getName() + "] " +"Running From Charge!!!", Color.GREEN);
			int y = Random.nextInt(0, 3);
			Walking.walk(Constants.CurrentRoom.getBoundingTiles()[y]);
            ShadowDungeon.SleepTillStop();
            ShadowDungeon.SleepWhile(Boss.isMoving());
		}else{
		MyNpc.Attack(Boss);
		}
	}
}
