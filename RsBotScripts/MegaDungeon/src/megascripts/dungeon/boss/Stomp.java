package megascripts.dungeon.boss;


import megascripts.api.Calc;
import megascripts.api.LocalPathCustom;
import megascripts.api.PathFinder;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyGroundItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class Stomp extends Boss {

	@Override
	public String getName() {
		return "Stomp";
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
				&& (ulits.tileinroom(MyNpc.getNearstNpc(getName())
						.getLocation()));
	}

	@Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		NPC Boss = MyNpc.getNearstNpc(getName());
		if (NoLights()) {
			MyNpc.Attack(Boss);
		} else {
			if (ThereBlue()) {
				int BlueKey = 15750;
				int BlueObject[] = { 49279, 49278 };
				Loot(BlueKey, BlueObject);
			} else if (ThereRed()) {
				int RedKey = 15752;
				int RedObject[] = { 49274, 49275 };
				Loot(RedKey, RedObject);
			} else if (ThereGreen()) {
				int GreenKey = 15751;
				int GreenObject[] = { 49276, 49277 };
				Loot(GreenKey, GreenObject);
			}
		}
	}

	private static void Loot(int Key, int Object[]) {
		SceneObject[] Stones = SceneEntities.getLoaded(Object);

		if (Inventory.getCount(Key) >= Stones.length) {
			SceneObject Stone = SceneEntities.getNearest(Object);
			if (Stone != null) {
				if (MyObjects.isOnScreen(Stone)) {
					Mouse.click(Stone.getCentralPoint(), true);
				} else {
					MyPlayer.WalkTo(Stone.getLocation());
				}
			}
		} else {
			GroundItem Keys = GroundItems.getNearest(Key);
			if (Keys != null) {
				if (Keys.getLocation().canReach()) {
					if (MyGroundItems.isOnScreen(Keys)) {
						Mouse.click(Keys.getCentralPoint(), true);
					} else {
						MyPlayer.WalkTo(Keys.getLocation());
					}
				}
			}
		}
	}

	public static boolean ThereBlue() {
		for (SceneObject o : SceneEntities.getLoaded()) {
			if (o != null) {
				if (o.getId() == 49278 || o.getId() == 49279) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean ThereGreen() {
		for (SceneObject o : SceneEntities.getLoaded()) {
			if (o != null) {
				if (o.getId() == 49276 || o.getId() == 49277) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean ThereRed() {
		for (SceneObject o : SceneEntities.getLoaded()) {
			if (o != null) {
				if (o.getId() == 49275 || o.getId() == 49274) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean NoLights() {
		for (SceneObject o : SceneEntities.getLoaded()) {
			if (o != null) {
				if (o.getId() == 49271) {
					return true;
				}
			}
		}
		return false;
	}
}
