package megascripts.dungeon.puzzle;

import java.awt.Color;


import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.plugin.Door;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.door.Back;
import megascripts.dungeon.node.Dungeon_Doors;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;

public class WhinchBridge extends Puzzle{

	public static int Meatcorn_Box[] = { 54568, 54255, 54646 };
	public static int Crate[] = { 54645, 54251, 54567 };

	public static int MeatCorn = 19665;
	public static int Broken_Hook = 19663;

	public static int Spinning_Wheel[] = { 49934, 49935, 49936, 53749, 55566 };
	public static int Anvil[] = { 54257, 54259 };

	public static int Rope = 19667;
	public static int Fixed_Hook = 19664;

	public static int HookRope = 19668;
	public static int Edge[] = { 39912, 54240, 54238, 54239, 39929, 37265 };

	public static int Rope_Bridge[] = { 54248, 39915, 54246, 37267 };

	public static int UnRope_Bridge[] = { 39914, 39930 };
	public static int Whinch[] = { 39917, 54647 };

	public static boolean Solved = false;

	@Override
	public String getName() {
		return "Whinch Bridge";
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
		if (Solved) {
			return false;
		}
		SceneObject Crates = SceneEntities.getNearest(Crate);
		if (Crates == null) {
			return false;
		}
		return Calc.Reach(Crates);
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	@Override
	public void solve() {
		if (!MyObjects.There(Whinch)) {
			final Door back = new Back();
			back.Open();
			Constants.LeaveDungeon = true;
		} else {
			if (MyActions.ThreNPC()) {
				Room_Job.Kill();
			} else {
				ShadowDungeon.setStatus(getStatus());
				SceneObject RopeBridge = SceneEntities.getNearest(Rope_Bridge);
				if (RopeBridge != null) {
					Constants.LeaveDungeon = true;
					/*
					 * RopeBridge.interact("Cross-the");
					 * MegaDungeon.SleepTillStop();
					 * MegaDungeon.AnnoucePuzzle(getName()); Solved = true;
					 */
				} else {
					SceneObject UnRopeBridge = SceneEntities
							.getNearest(UnRope_Bridge);
					if (UnRopeBridge != null && MyObjects.There(Whinch)) {
						Interact(Whinch, "Operate", false);
						Task.sleep(900, 1200);
						ShadowDungeon.SleepTillStop();
						ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
						Solved = true;
					} else if (MyItems.contains(HookRope)) {
						Interact(Edge, "Grapple", false);
						Task.sleep(900, 1200);
					} else if (MyItems.contains(Fixed_Hook)
							&& MyItems.contains(Rope)) {
						MyItems.Interact(Rope, "Use");
						MyItems.Interact(Fixed_Hook, "Use");
						Task.sleep(1000, 1500);
					} else {
						if (!MyItems.contains(Rope)) {
							if (MyItems.contains(MeatCorn)) {
								InteractSpin(Spinning_Wheel, "Use");
							} else if (!MyItems.contains(MeatCorn)) {
								Interact(Meatcorn_Box, "Take-from", "Plunder");
							}
						} else {
							if (MyItems.contains(Broken_Hook)) {
								InteractAnvil(Anvil, "Use");
							} else if (!MyItems.contains(Broken_Hook)) {
								Interact(Crate, "Take-from", "Retrieve");
							}
						}
					}
				}
			}
		}
	}

	public static void Interact(int[] id, String Action, String Action2) {
		for (SceneObject n : SceneEntities.getLoaded(id)) {
			if (n != null && n.validate()) {
				if (n.isOnScreen()) {
					Camera.turnTo(n);
					Mouse.move(n.getCentralPoint());
					if (Menu.contains(Action) || Menu.contains(Action2)) {
						Mouse.click(n.getCentralPoint(), true);
						ShadowDungeon.SleepTillStop();
					}
				} else {
					Walking.walk(n);
					Camera.turnTo(n);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				}
			}
		}
	}

	public static void Interact(int[] id, String Action, boolean r) {
		for (SceneObject n : SceneEntities.getLoaded(id)) {
			if (n != null && n.validate()) {
				if (n.isOnScreen()) {
					Camera.turnTo(n);
					n.interact(Action);
					Task.sleep(1000, 1500);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				} else {
					Walking.walk(n);
					Camera.turnTo(n);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				}
			}
		}
	}

	public static void InteractSpin(int[] id, String Action) {
		for (SceneObject n : SceneEntities.getLoaded(id)) {
			if (n != null && n.validate() && Calc.Reach(n)) {
				if (n.isOnScreen()) {
					Camera.turnTo(n);
					MyItems.Interact(MeatCorn, "Use");
					n.interact(Action);
					Task.sleep(1000, 1500);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				} else {
					Walking.walk(n);
					Camera.turnTo(n);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				}
			}
		}
	}

	public static void Interact(int[] id, String Action) {
		for (SceneObject n : SceneEntities.getLoaded(id)) {
			if (n != null && n.validate() && Calc.Reach(n)) {
				if (n.isOnScreen()) {
					Camera.turnTo(n);
					n.interact(Action);
					Task.sleep(500, 900);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				} else {
					Walking.walk(n);
					Camera.turnTo(n);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				}
			}
		}
	}

	public static void InteractAnvil(int[] anvil2, String Action) {
		for (SceneObject n : SceneEntities.getLoaded(anvil2)) {
			if (n != null && n.validate() && Calc.Reach(n)) {
				if (n.isOnScreen()) {
					Camera.turnTo(n);
					MyItems.Interact(Broken_Hook, "Use");
					n.interact(Action);
					Task.sleep(500, 900);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				} else {
					Walking.walk(n);
					Camera.turnTo(n);
					for (int x = 0; x < 20 && Players.getLocal().isMoving()
							|| Players.getLocal().getAnimation() != -1; x++, Task
							.sleep(100, 150))
						;
				}
			}
		}
	}
}
