package megascripts.dungeon.boss;

import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;



public class gravecreeper extends Boss{

	public static int TOMB[] = {54456};
	public static int PLINTH[] = { 54443, 54444, 54449, 54450, 54457, 54458 };
	
	@Override
	public String getAuthor() {
	  return "Magorium";
	}

	@Override
	public String getName() {
		return "GraveCreeper";
	}

	@Override
	public String getStatus() {
		CurrentBoss.BossName = getName();
		return "Attacking " + CurrentBoss.BossName + "...";
	}

	@Override
	public boolean isValid() {
		return (MyNpc.getNearstNpc("Grave") !=null 
				&& ulits.tileinroom(MyNpc.getNearstNpc("Grave").getLocation()));
	}

	@Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		NPC Boss = MyNpc.getNearstNpc("Grave");
		Combat.TurnRet(false);
		if(Boss.getMessage() !=null && Boss.getMessage().toLowerCase().contains("buuurrnnnn")){
			ulits.WalktoCentralTile();
		}
		if(Boss.getMessage() !=null &&  Boss.getMessage().toLowerCase().contains("burrrrrry")
				&& Boss.getMessage().toLowerCase().contains("digggggg")
				&& Boss.getMessage().toLowerCase().contains("brrainnns") || Boss == null){

			SceneObject n = SceneEntities.getNearest(TOMB);
			if(n !=null){
				if(MyObjects.isOnScreen(n)){
					n.click(true);
					Task.sleep(1000,1500);
					ShadowDungeon.SleepTillStop();
				}else{
					Walking.walk(n);
				}
			}
		}else{
			MyNpc.Attack(Boss);
		}
	}

}
