package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.myplayer.MyPrayer.Ancient;
import megascripts.api.myplayer.MyPrayer.Modern;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class SkeletalHorde extends Boss {

	public static boolean blocktunnel = false;
	public static boolean Killed = false;
	public static int[] SKELETAL_TUNNELS = { 49286, 49287, 49288 };
    @Override
	public String getName() {
		return "Divine skinweaver";
	}
    @Override
	public String getAuthor() {
		return "Magorium";
	}
    @Override
	public String getStatus() {
		CurrentBoss.BossName = getName();
		return "Attacking Skeleton Horde...";
	}
    @Override
	public boolean isValid() {
		return MyNpc.getNearstNpc(getName()) != null
				&& Calc.Reach(MyNpc.getNearstNpc(getName())) && !Killed;
	}
    @Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		final NPC Divine = MyNpc.getNearstNpc(getName());
		
		  MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MELEE;
		  MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MELEE;
		 if (MyPlayer.getPrayerLevel() <
		 effect2.getRequiredLevel()) { MyPlayer.TurnPrayer(effect,
		  MyPrayer.isModernSetActive()); } else { MyPlayer.TurnPrayer(effect2,
		  !MyPrayer.isModernSetActive()); }
		 
		Combat.TurnRet(false);
		if (Divine != null && Divine.getMessage() != null) {
			blocktunnel = true;
		}

		if (blocktunnel) {
			Block_Tunnel();
		} else {
			if (MyPlayer.getHealthPercent() < 40) {
				if (Calculations.distanceTo(Divine) > 2) {
					MyPlayer.WalkTo(Divine.getLocation());
				   Task.sleep(3000,5000);
					
				}
			} else {
				NPC Target = NPCs.getNearest(new Filter<NPC>() {
					@Override
					public boolean accept(NPC n) {
						return n != null && Calc.Reach(n)
								&& n.getId() != Divine.getId()
								&& n.getLevel() > 0;
					}

				});
				if (MyNpc.There(Target)) {
					MyNpc.Attack(Target);
				} else {
					Talk(Divine);
				}
			}
		}
	}

	private static void Talk(NPC skinweaver) {
		if (skinweaver != null) {
			if (Calculations.distanceTo(skinweaver) < 4) {
				if (skinweaver.isOnScreen() && skinweaver.interact("Talk")) {
					ShadowDungeon.SleepWhile(skinweaver.getMessage() == null);
					if (skinweaver.getMessage() != null
							&& skinweaver.getMessage().contains(
									"little danger in")) {
						Killed = true;
					}
				}
			} else {
				MyPlayer.WalkTo(skinweaver.getLocation());
			}
		}
	}

	private static void Block_Tunnel() {
		SceneObject tunnel = SceneEntities.getNearest(SKELETAL_TUNNELS);
		if (tunnel == null) {
			blocktunnel = false;
		}
		if (tunnel != null) {
			if (MyObjects.isOnScreen(tunnel)) {
				if (tunnel != null && tunnel.interact("block")) {
					blocktunnel = false;
				}
			} else {
				MyPlayer.WalkTo(tunnel.getLocation());
			}
		}
	}

	public static void reset() {
		Killed = false;
		blocktunnel = false;
	}
}
