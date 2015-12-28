package megascripts.dungeon.boss;



import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.Flood;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class NightGazer extends Boss {

	public static int[] UNLIGHT_COLLUM = { 49265 };
	public static final int[] SPECIAL_ANIMS = { 13426, 13427, 13428, 13429 };

	@Override
	public String getName() {
		return "Night-gazer Khighorahk";
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
		 
		if (Match(Boss.getAnimation(), SPECIAL_ANIMS)) {
		    MyPlayer.WalkToNearstTile(Flood.getArea().getBoundingTiles());
		} else {
			if (ThereUnlit()) {
				Light();
			} else {
					MyNpc.Attack(Boss);
			}
		}
	}

	private static boolean Match(int a, int[] s) {
		for (int e : s) {
			if (a == e) {
				return true;
			}
		}
		return false;
	}

	private static void Light() {
		SceneObject Unlit = SceneEntities.getNearest(UNLIGHT_COLLUM);
		if (Unlit != null) {
			if (Unlit.isOnScreen()) {
				Mouse.click(Unlit.getCentralPoint(), true);
				ShadowDungeon.SleepTillStop();
			} else {
				MyPlayer.WalkTo(Unlit.getLocation());
			}
		}
	}

	private static boolean ThereUnlit() {
		SceneObject Unlit = SceneEntities.getNearest(UNLIGHT_COLLUM);
		return Unlit != null && Calc.Reach(Unlit)
				&& MyObjects.getCount(UNLIGHT_COLLUM) > 2;
	}
}
