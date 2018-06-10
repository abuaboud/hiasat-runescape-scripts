package megascripts.dungeon.boss;

import java.awt.Color;

import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

import megascripts.graphic.LogHandler;


public class Runebound extends Boss {

	public static int MAGIC_CRYSTAL = 53978;
	public static int RANGE_CRYSTAL = 53979;
	public static int MELEE_CRYSTAL= 53977;
    @Override
	public String getName() {
		return "Runebound behemoth";
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
		return MyNpc.getNearstNpc("Runebound") != null
				&& Calc.Reach(MyNpc.getNearstNpc("Runebound"));
	}
    @Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		Combat.TurnRet(false);
		NPC Boss = MyNpc.getNearstNpc("Runebound");
		MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MAGIC;
		MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MAGIC;
		if (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
			MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive());
		} else {
			MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive());
		}
		if (Boss.getMessage() != null) {
			LogHandler.Print("[" + getName() +"]: " + " Running from his Rawr!!!",Color.GREEN);
			ulits.WalktoRandomTileOnMap(4);
			ShadowDungeon.SleepTillStop();
			ShadowDungeon.SleepTillStop();
		} else if (!MyObjects.There(MELEE_CRYSTAL)) {
			MyNpc.Attack(Boss);
		} else {
			Deactivate(MELEE_CRYSTAL);
		}
	}
	public static void Deactivate(int e){
		SceneObject d = SceneEntities.getNearest(e);
		if(d != null && d.validate()){
			if(MyObjects.isOnScreen(d)){
				d.interact("Deactivate");
				Task.sleep(600,900);
			}else{
				MyPlayer.WalkTo(d.getLocation());
			}
		}
	}
}
