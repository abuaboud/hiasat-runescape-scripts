package megascripts.dungeon.puzzle;

import java.awt.Color;

import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


/**
 * 
 * @author Magorium
 * 
 */
public class ThreeWeapionStatus extends Puzzle{

	public static int Weapon;
	public static int Stone = 17415;
	public static int[] BrokenWall = { 49649, 49648, 49647, 53991 };

	public static int Melee_Weapon = 17416;
	public static int Range_Weapon = 17418;
	public static int Magic_Weapon = 17420;

	public static int UnCompletd_Range[] = { 11039, 11040,11041, 12095 };
	public static int UnCompletd_Melee[] = { 11036, 11037, 11038 };
	public static int UnCompletd_Mage[] = { 11042, 11043, 11044 };
	public static int Status[][] = { UnCompletd_Mage, UnCompletd_Melee,
			UnCompletd_Range };
	public static int widgetnumber;

	public static boolean Mage = false;
	public static boolean Melee = false;
	public static boolean Range = false;

	@Override
	public String getName() {
		return "Three Weapon Statues";
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
		NPC State = NPCs.getNearest(ulits.convertInt(Status));
		return State != null && State.validate() && Calc.Reach(State);
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		if (MyItems.contains(Constants.Chisel)) {
			if (There(UnCompletd_Melee)) {
				Mage = false;
				Melee = true;
				Range = false;
				Weapon = 17416;
				widgetnumber = 11;
			} else if (There(UnCompletd_Range)) {
				Mage = false;
				Melee = false;
				Range = true;
				Weapon = Range_Weapon;
				widgetnumber = 13;
			} else if (There(UnCompletd_Mage)) {
				Mage = true;
				Melee = false;
				Range = false;
				Weapon = Magic_Weapon;
				widgetnumber = 14;
			}
			if (MyItems.contains(Weapon)) {
				NPC n = NPCs.getNearest(ulits.convertInt(Status));
				if (n != null && n.validate()) {
					if (MyNpc.isOnScreen(n)) {
						n.interact("Arm");
						Task.sleep(1000, 1500);
						ShadowDungeon.SleepTillStop();
						if (isSolved()) {
							ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
						}

					} else {
						Walking.walk(n);
						Camera.turnTo(n);
						ShadowDungeon.SleepTillStop();
					}
				}
			} else if (MyItems.contains(Stone)) {
				Carft(widgetnumber);
			} else {
				SceneObject n = SceneEntities.getNearest(BrokenWall);
				if (n != null && n.validate()) {
					if (MyObjects.isOnScreen(n)) {
						if (Players.getLocal().getAnimation() == -1) {
							n.interact("Mine");
							Task.sleep(2200, 3200);
						}
					} else {
						Walking.walk(n);
						Camera.turnTo(n);
					}
				}
			}
		} else {
			MyActions.loot(Constants.Chisel);
		}
	}

	private static void Carft(int x) {
		WidgetChild INTERFACE = Widgets.get(1188, x);
		if (INTERFACE.validate()) {
			INTERFACE.click(true);
			Task.sleep(400, 800);
		} else {
			MyItems.Interact(Stone, "Carve");
			Task.sleep(400, 900);
		}
	}

	private static boolean There(int[] melee) {
		NPC m = NPCs.getNearest(melee);
		if (m == null) {
			return false;
		}
		return Calc.Reach(m);
	}
}
