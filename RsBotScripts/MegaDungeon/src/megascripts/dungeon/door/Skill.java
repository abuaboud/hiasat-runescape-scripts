package megascripts.dungeon.door;

import java.awt.Color;

import megascripts.api.Calc;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Door;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import megascripts.graphic.LogHandler;


public class Skill extends Door {

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getStatus() {
		return null;
	}

	@Override
	public boolean isValid() {
		return MyActions.Set_Object(Skill_Door()) != null
				&& MyActions.Object_IsValid(MyActions.Set_Object(Skill_Door())
						.getId());
	}

	@Override
	public void Open() {
		final Door basic = new Standerd();
		int x = 0;
		int y = 0;
		SceneObject n = MyActions.Set_Object(Skill_Door());
		if (n != null && n.validate()) {
			if (Calculations.distanceTo(n) < 3) {
				x = n.getLocation().getX();
				y = n.getLocation().getY();
				for (int d = 0; d < 25; d++) {
					Mouse.move(n.getCentralPoint().x, n.getCentralPoint().y);
					if (n == null && basic.isValid()) {
						ShadowDungeon.setStatus("Openning Standerd Door...");
						basic.Open();
					} else {
						if (MyActions.ThereBoss()
								|| Constants.BlackDoor
										.contains(n.getLocation())) {
							d = 26;
						}
						if (x == 0) {
							x = n.getLocation().getX();
							y = n.getLocation().getY();
						}
						Tile r = new Tile(x, y, 0);

						if (!currRoom.contains(r)) {
							d = 26;
						}
						if (d != 26) {
							if (!Constants.BlackDoor.contains(n.getLocation())) {
								if (Menu.contains("Imbue-energy")
										|| Menu.contains("Dismiss")
										|| Menu.contains("Exorcise")
										|| Menu.contains("Repair-key")
										|| Menu.contains("Repair")
										|| Menu.contains("Fix-pulley")
										|| Menu.contains("Force-bar")
										|| Menu.contains("Chop-down")
										|| Menu.contains("Dispel")
										|| ThereMine(Menu.getActions())
										|| Menu.contains("Burn")
										|| Menu.contains("Add-compound")
										|| Menu.contains("Disarm")
										|| Menu.contains("Pick-lock")
										|| Menu.contains("Prune")) {
									if (n.interact(getAction())) {
										Task.sleep(1000, 1500);
										ShadowDungeon.SleepTillStop();
									}
								}

								if (ShadowDungeon.Message != null) {
									if (ShadowDungeon.Message
											.contains("level of")
											|| ShadowDungeon.Message
													.contains("unable to")) {
										LogHandler.Print(ShadowDungeon.Message,
												Color.red);
										Constants.BlackDoor
												.add(n.getLocation());
										Constants.BlackDoor.add(new Tile(x + 1,
												y, 0));
										Constants.BlackDoor.add(new Tile(x - 1,
												y, 0));
										Constants.BlackDoor.add(new Tile(x,
												y + 1, 0));
										Constants.BlackDoor.add(new Tile(x,
												y - 1, 0));
										
									}
								}
							}
						}
					}
				}

			} else {
				MyPlayer.WalkTo(n.getLocation(), false);
				Camera.turnTo(n);
			}
		}
	}

	public static Filter<SceneObject> Skill_Door() {
		return new Filter<SceneObject>() {
			public boolean accept(SceneObject SDoor) {
				return (SDoor != null
						&& (ulits.MatchID(SDoor.getId(),
								ulits.convertInt(Constants.ALL_SKILL_DOOR)) || Constants.SkillEnterd
								.contains(SDoor.getLocation()))
						&& !Constants.EnterdDoor.contains(SDoor.getLocation())
						&& !Constants.BlackDoor.contains(SDoor.getLocation()) && (Calc
						.Reach(SDoor) || ulits.tileinroom(SDoor.getLocation())));
			}

		};
	}

	private static boolean ThereMine(String[] actions) {
		for (String s : actions) {
			if (s.equals("Mine")) {
				return true;
			}
		}
		return false;
	}

	public static String getAction() {
		if (Menu.contains("Imbue-energy")) {
			return "Imbue-energy";
		} else if (Menu.contains("Dismiss")) {
			return "Dismiss";
		} else if (Menu.contains("Force-bar")) {
			return "Force-bar";
		} else if (Menu.contains("Repair-key")) {
			return "Repair-key";
		} else if (Menu.contains("Repair")) {
			return "Repair";
		} else if (Menu.contains("Fix-pulley")) {
			return "Fix-pulley";
		} else if (Menu.contains("Chop-down")) {
			return "Chop-down";
		} else if (Menu.contains("Dispel")) {
			return "Dispel";
		} else if (ThereMine(Menu.getActions())) {
			return "Mine";
		} else if (Menu.contains("Burn")) {
			return "Burn";
		} else if (Menu.contains("Add-compound")) {
			return "Add-compound";
		} else if (Menu.contains("Exorcise")) {
			return "Exorcise";
		} else if (Menu.contains("Disarm")) {
			return "Disarm";
		} else if (Menu.contains("Pick-lock")) {
			return "Pick-lock";
		} else if (Menu.contains("Prune")) {
			return "Prune";
		} else if (Menu.contains("Enter")) {
			return "Enter";
		} else if (Menu.contains("Unlock")) {
			return "Unlock";
		} else if (Menu.contains("Open")) {
			return "Open";
		}

		return null;
	}

}
