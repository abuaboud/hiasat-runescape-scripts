package megascripts.dungeon.puzzle;

import megascripts.api.Calc;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyItems;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Loot_StartRoom;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;



public class Magicalconstruct extends Puzzle {

	public static int ROBOT = 11003;
	public static int Rock_Object[] = { 49543, 49544, 49545 };
	public static int Stone = 17364;
	public static int UnFinshed_leg = 17366;
	public static int UnFinshed_Arm = 17365;
	public static int UnFinshed_Head = 17367;

	public static int leg = 17370;
	public static int Arm = 17368;
	public static int Head = 17372;

	public static boolean arm_t = false;
	public static boolean leg_t = false;
	public static boolean head_t = false;

	public static int[] Ready_Parts = { 17368, 17370, 17372 };
	public static int[] Unf_Part = { 17367, 17366, 17365 };

	@Override
	public String getName() {
		return "Magical Construct";
	}

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		NPC Robet = NPCs.getNearest("Dormant construct", "Damaged Construct");
		if (Robet == null) {
			return false;
		}
		return Calc.Reach(Robet);
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		if (MyActions.ThreNPC()) {
			Room_Job.Kill();
		} else {
			ShadowDungeon.setStatus(getStatus());
			NPC a = NPCs.getNearest("Dormant construct");
			if (a != null && a.validate() && Calc.Reach(a)) {
				a.interact("Charge");
				Task.sleep(2000, 3000);
				arm_t = false;
				leg_t = false;
				head_t = false;
				ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
			} else {
				if (MyItems.contains(Constants.Chisel)) {
					if (head_t == false && leg_t == false && arm_t == false) {
						getBrokenItem();
					}
					if (arm_t) {
						if (MyItems.contains(Arm)) {
							Repair(11);
						} else if (MyItems.contains(UnFinshed_Arm)) {
							MyItems.Interact(UnFinshed_Arm, "Imbue");
						} else if (!MyItems.contains(UnFinshed_Arm)) {
							Carft(11);
						}
					} else if (leg_t) {
						if (MyItems.contains(leg)) {
							Repair(13);
						} else if (MyItems.contains(UnFinshed_leg)) {
							MyItems.Interact(UnFinshed_leg, "Imbue");
						} else if (!MyItems.contains(UnFinshed_leg)) {
							Carft(13);
						}
					} else if (head_t) {
						if (MyItems.contains(Head)) {
							Repair(14);
						} else if (MyItems.contains(UnFinshed_Head)) {
							MyItems.Interact(UnFinshed_Head, "Imbue");
						} else if (!MyItems.contains(UnFinshed_Head)) {
							Carft(14);
						}
					}

				} else {
					MyActions.loot(Constants.Chisel);
				}

				NPC d = NPCs.getNearest("Magic construct");
				if (d != null) {
					for (int p = 0; p < 100 && d.isMoving(); p++, Task.sleep(
							100, 150))
						;
				}
			}

			if (isSolved()) {
				if (MyItems.contains(Stone)) {
					MyItems.Interact(Stone, "Drop");
				}
				arm_t = false;
				leg_t = false;
				head_t = false;

			}
		}
	}

	private void getBrokenItem() {
		NPC Robet = getNPC();
		if (Robet != null) {
			if (Robet.isOnScreen()) {
				Robet.interact("Examine");
				Task.sleep(1000, 2000);
			} else {
				Camera.turnTo(Robet);
			}
		}
	}

	private NPC getNPC() {
		return NPCs.getNearest("Damaged Construct");
	}

	private void Repair(int i) {
		WidgetChild INTERFACE = Widgets.get(1188, i);
		if (INTERFACE.validate()) {
			INTERFACE.click(true);
			Task.sleep(400, 800);
		} else {
			NPC Robet = getNPC();
			if (Robet != null) {
				if (Robet.isOnScreen()) {
					Robet.interact("Repair");
					Task.sleep(2500, 3000);
				} else {
					Camera.turnTo(Robet);
				}
			}
		}
	}

	private void Carft(int x) {
		WidgetChild INTERFACE = Widgets.get(1188, x);
		if (!MyItems.contains(Stone)) {
			SceneObject Hole = SceneEntities.getNearest(Rock_Object);
			if (Hole != null) {
				if (Hole.isOnScreen()) {
					Hole.interact("Take");
					ShadowDungeon.SleepTillStop();
				} else {
					Camera.turnTo(Hole);
				}
			}
		} else {
			if (INTERFACE.validate()) {
				INTERFACE.click(true);
				Task.sleep(400, 800);
			} else {
				MyItems.Interact(Stone, "Carve");
				Task.sleep(400, 900);
			}
		}
	}
}
