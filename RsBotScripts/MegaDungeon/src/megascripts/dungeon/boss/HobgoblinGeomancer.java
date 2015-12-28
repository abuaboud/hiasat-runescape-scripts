package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.Combat;
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


public class HobgoblinGeomancer extends Boss {

    @Override
	public String getName() {
		return "Hobgoblin Geomancer";
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
		MyNpc.Attack(Boss);
	}
}
