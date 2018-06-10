package megascripts.dungeon.puzzle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * 
 * @author Magorium
 * 
 */
public class LineStatues extends Puzzle{

	public static int Melee_Enmey[] = { 11029, 11027 };
	public static int Range_Enmey[] = { 11030, 11031, 11032 };
	public static int Magic_Enmey[] = { 11033, 11034, 11035 };
	public static int Free_Army[] = { 11012, 11014, 11013 };

	public static int[] BrokenWall = { 49647, 49648, 49649, 53991 };

	public static int Enmey[] = { 11031, 11027, 11029, 11030, 11033, 11035,
			11032, 11034 };

	public static int Melee_Weapon = 17416;
	public static int Range_Weapon = 17418;
	public static int Magic_Weapon = 17420;

	public static int MeleeAmount = 0;
	public static int RangeAmount = 0;
	public static int MagicAmount = 0;

	public static int Stone = 17415;

	public static Tile First = null;

	@Override
	public String getName() {
		return "X Status Weapon";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public boolean isValid() {
		NPC fe = NPCs.getNearest(Free_Army);
		if (fe == null) {
			return false;
		}
		return Calc.Reach(fe);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		NPC enmey = getEnmey(Enmey, Free_Army);
		NPC FreeArm = NPCs.getNearest(Free_Army);
		if (MatchEnmey(enmey.getId(), Melee_Enmey)) {
			First = enmey.getLocation();
			MakeWeponAndArm(FreeArm, Magic_Weapon, 14);
		} else if (MatchEnmey(enmey.getId(), Range_Enmey)) {
			First = enmey.getLocation();
			MakeWeponAndArm(FreeArm, Melee_Weapon, 11);
		} else if (MatchEnmey(enmey.getId(), Magic_Enmey)) {
			First = enmey.getLocation();
			MakeWeponAndArm(FreeArm, Range_Weapon, 13);
		}
		if (isSolved()) {
			First = null;
			ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
		}
	}

	public boolean MatchEnmey(int e, int[] r) {
		for (int x : r) {
			if (e == x) {
				return true;
			}
		}
		return false;
	}

	private NPC getEnmey(int x[], int e[]) {
		return getNearstNPC(x, e);
	}

	private NPC getNearstNPC(int[] melee_Enmey2, int[] red2) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<NPC> objects = new ArrayList<NPC>();
		Dist.clear();
		objects.clear();
		NPC object = NPCs.getNearest(red2);
		for (NPC x : NPCs.getLoaded(melee_Enmey2)) {
			if (x != null && Calc.Reach(x)) {
				int e = (int) Calculations.distance(x, object);
				Dist.add(e);
				objects.add(x);
			}
		}
		if (Dist.isEmpty()) {
			return null;
		}
		int m = Collections.min(Dist);
		int re = Dist.indexOf(m);
		if (objects.get(re) == null) {
			return null;
		}
		return objects.get(re);
	}

	public void MakeWeponAndArm(NPC n, int w, int widgetnumber) {
		if (MyItems.contains(w)) {
			if (n != null && n.validate()) {
				if (MyNpc.isOnScreen(n)) {
					n.interact("Arm");
					Task.sleep(1000, 1500);
					ShadowDungeon.SleepTillStop();
				} else {
					MyPlayer.WalkTo(n.getLocation());
				}
			}
		} else {
			if (MyItems.contains(Stone)) {
				Carft(widgetnumber);
			} else {
				SceneObject rocker = SceneEntities.getNearest(BrokenWall);
				if (rocker != null && rocker.validate()) {
					if (MyObjects.isOnScreen(rocker)) {
						if (Players.getLocal().getAnimation() == -1) {
							rocker.interact("Mine");
							Task.sleep(2200, 3200);
						}
					} else {
						MyPlayer.WalkTo(rocker.getLocation());
					}
				}
			}
		}
	}

	private void Carft(int x) {
		WidgetChild INTERFACE = Widgets.get(1188, x);
		if (INTERFACE.validate()) {
			INTERFACE.click(true);
			Task.sleep(400, 800);
		} else {
			MyItems.Interact(Stone, "Carve");
			Task.sleep(400, 900);
		}
	}

}
