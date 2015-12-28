package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.Flood;
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
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class Shadow  extends Boss{

	public static int SpeicalAttack = 13030;
	public static int[] PILLAR = { 51110 };

    @Override
	public String getName() {
		return "Shadow-Forger Ihlakhizan";
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
		return MyNpc.getNearstNpc("Shadow-Forger") != null
				&& Flood.getArea().contains(MyNpc.getNearstNpc("Shadow-Forger").getLocation());
	}
    @Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		NPC Boss = MyNpc.getNearstNpc("Shadow-Forger");
		
		  MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MELEE;
		  MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MELEE; if
		  (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
		  MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive()); } else {
		  MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive()); }
		  Combat.TurnRet(false);
		
		if (Boss.getAnimation() != SpeicalAttack) {
			MyNpc.Attack(Boss);
		} else {
			SceneObject pillar = SceneEntities.getNearest(PILLAR);
			if (pillar != null) {
				ulits.getNearstTile(pillar.getLocation()).click(true);
				Task.sleep(500, 600);
			}
		}

	}
}
