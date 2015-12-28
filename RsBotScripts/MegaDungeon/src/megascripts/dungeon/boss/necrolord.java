package megascripts.dungeon.boss;

import megascripts.api.ulits;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.wrappers.interactive.NPC;



public class necrolord extends Boss{

	public static int FRACTICE_ARROW = 16447;
	public static int BOW = 16867;
	
	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getName() {
		return "Necrolord";
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
		NPC Boss = MyNpc.getNearstNpc(getName());
		if(MyItems.contains(BOW) && MyItems.contains(FRACTICE_ARROW)){
			MyNpc.Attack(Boss);
		}else{
			Room_Job.Kill();
		}
	}

}
