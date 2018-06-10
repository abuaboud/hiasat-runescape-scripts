package megascripts.dungeon.puzzle;

import java.awt.Color;


import megascripts.api.Calc;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import megascripts.graphic.LogHandler;

public class Poltergeist extends Puzzle{

	public static int[] CLOSED_GRAVE = {54079, 54080, 54081 };
	public static String HERB_NAME = "None";
	public static String Herbs[] = { "corianger", "cardamaim", "parslay",
			"explosemary", "papreaper", "slaughtercress" };
	public static int HERBS_ID[] = { 19653, 19656, 19655, 19654, 19657, 19658 };
	public static int CLEANED_HERB_ID = 19659;
	public static int BURNERS[] = {54095, 54096, 54097 };
	public static int[] Farming_Patch = {54075, 54076 };
	public static int Herb_ID = 0;
	public static WidgetChild Instruction = Widgets.get(1186, 1);
	public static int OPTION = 1188;
	public static Widget OPTIONS = Widgets.get(1188);
	public static WidgetChild OPTION_ONE = Widgets.get(OPTION, 3);
	public static WidgetChild OPTION_TWO = Widgets.get(OPTION, 24);
	public static WidgetChild OPTION_THREE = Widgets.get(OPTION, 29);
	public static WidgetChild OPTION_FOUR = Widgets.get(OPTION, 34);
	public static int[] Burned_Full = {54099, 54100, 54101 };
	public static int[] Fire_Burner = {54103, 54104, 54105 };

	@Override
	public String getName() {
		return "Poltergeist";
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
		SceneObject n = SceneEntities.getNearest(CLOSED_GRAVE);
		if (n == null) {
			return false;
		}
		return Calc.Reach(n);
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
			if (HERB_NAME.equals("None")) {
				ReadNote();
			} else {
				if (There(Fire_Burner) && MyObjects.getCount(Fire_Burner) == 4) {
					Interact(CLOSED_GRAVE, "Open");
					ShadowDungeon.SleepTillStop();
					HERB_NAME = "None";
					ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
				} else if (There(Burned_Full)
						&& MyObjects.getCount(Fire_Burner) < 4) {
					Interact(Burned_Full, "Light");
				} else if (MyItems.contains(CLEANED_HERB_ID)) {
					Interact_Burner(BURNERS);
				} else if (MyItems.contains(Herb_ID)) {
					for (int x = 0; x < 10; x++) {
						if (Inventory.getCount(CLEANED_HERB_ID) != 4) {
							MyItems.Interact(Herb_ID, "Consecrate");
						}
					}
				} else {
					for (int x = 0; x < 10; x++) {
						if (Inventory.getCount(Herb_ID) != 4) {
							PickHerb(HERB_NAME);
						}
					}
				}
			}
		}
	}

	private static boolean There(int[] bf) {
		SceneObject obj = SceneEntities.getNearest(bf);
		return obj != null && Calc.Reach(obj);
	}

	private static void PickHerb(String h) {
		if (OPTIONS.validate()) {
			if (OPTION_ONE.getText().contains(h)) {
				OPTION_ONE.click(true);
			} else if (OPTION_TWO.getText().contains(h)) {
				OPTION_TWO.click(true);
			} else if (OPTION_THREE.getText().contains(h)) {
				OPTION_THREE.click(true);
			} else {
				OPTION_FOUR.click(true);
			}
			Task.sleep(600, 900);
		} else {
			Interact(Farming_Patch, "Harvest");
		}
	}

	private static void ReadNote() {
		if (Instruction.validate()) {
			String Test = Instruction.getText();
			for (int e = 0; e < Herbs.length; e++) {
				if (Test.contains(Herbs[e])) {
					HERB_NAME = Herbs[e];
					Herb_ID = HERBS_ID[e];
					LogHandler.Print("This Grave Required: " + HERB_NAME
							+ " Herb", Color.GREEN);
				}
			}
		} else {
			Interact(CLOSED_GRAVE, "Read");
			Task.sleep(1000, 2000);
			for (int x = 0; x < 20 && !Instruction.validate(); x++, Task.sleep(
					100, 150))
				;
		}

	}

	public static void Interact_Burner(int[] bURNERS2) {
		for (SceneObject n : SceneEntities.getLoaded(bURNERS2)) {
			if (n != null && n.validate() && Calc.Reach(n)) {
				if (MyObjects.isOnScreen(n)) {
					if (Calculations.distanceTo(n) > 5) {
						Camera.turnTo(n);
					}
					if (Inventory.getItem(CLEANED_HERB_ID).getWidgetChild().interact("Use")) {
						n.interact("Use");
						Task.sleep(1500, 2000);
					}
					ShadowDungeon.SleepTillStop();
				} else {
					if (!MyPlayer.isMoving()) {
						Walking.walk(n);
						Camera.turnTo(n);
						ShadowDungeon.SleepTillStop();
					}
				}
			}
		}
	}

	public static void Interact(int[] id, String Action) {
		for (SceneObject n : SceneEntities.getLoaded(id)) {
			if (n != null && n.validate() && Calc.Reach(n)) {
				if (MyObjects.isOnScreen(n)) {
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

	public static void Interact(int id, String Action) {
		for (SceneObject n : SceneEntities.getLoaded(id)) {
			if (n != null && n.validate() && Calc.Reach(n)) {
				if (MyObjects.isOnScreen(n)) {
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
}
