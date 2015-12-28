package megascripts.dungeon.door;

import java.awt.Point;

import megascripts.api.Calc;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Door;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Dungeon_Doors;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;



public class Key extends Door{

	@Override
	public String getAuthor() {
		return "ShadowFiend";
	}

	@Override
	public String getStatus() {
		return null;
	}

	@Override
	public boolean isValid() {
			return MyActions.Set_Object(Key_Door()) != null
					&& MyActions.Object_IsValid(MyActions.Set_Object(Key_Door())
							.getId());
	}

	@Override
	public void Open() {
		Tile e = null;
		e = null;
		SceneObject KDoor = SceneEntities.getNearest(Key_Door());
		if (KDoor != null) {
			if (KDoor.validate()) {
				if (e == null) {
					e = KDoor.getLocation();
				}
				if (Calculations.distanceTo(KDoor.getLocation()) <= 2) {
					Camera.turnTo(KDoor);
					for (int x = 0; x < 30; x++) {
						if (MyActions.ThereBoss()) {
							x = 31;
						}
						if (!currRoom.contains(e)) {
							x = 31;
						}

						if (x != 31) {
							Camera.turnTo(KDoor);
							Point Centrals = KDoor.getModel().getCentralPoint();
							Mouse.move(Centrals.x, Centrals.y);
							if (Menu.contains("Enter")
									|| Menu.contains("Unlock")
									|| Menu.contains("Open")) {
								Mouse.click(Centrals.x, Centrals.y, true);
								Task.sleep(500, 700);
								ShadowDungeon.SleepTillStop(2);
							}else{
								SceneObject NearstKeyDoor = SceneEntities.getNearest(ulits
										.convertInt(Constants.Second_KeyDoor));
								if(NearstKeyDoor !=null){
									NearstKeyDoor.interact("Open");
									Task.sleep(500, 700);
									ShadowDungeon.SleepTillStop(2);
								}
							}

						}
						Constants.NEXTROOM = currRoom.NextRoomArea();
					}

					SceneObject NearstKeyDoor = SceneEntities.getNearest(ulits
							.convertInt(Constants.Second_KeyDoor));
					int x = NearstKeyDoor.getLocation().getX();
					int y = NearstKeyDoor.getLocation().getY();
					Constants.KeyEnterd.add(new Tile(x, y, 0));
					Constants.EnterdDoor.add(new Tile(x, y, 0));
					Dungeon_Doors.Add_BackDoor(KDoor);

				} else {
					MyPlayer.WalkTo(KDoor.getLocation(), false);
					Camera.turnTo(KDoor);
				}
			}
		}
	}

	public static Filter<SceneObject> Key_Door() {
		return new Filter<SceneObject>() {
			public boolean accept(SceneObject KDoor) {
				return (KDoor != null
						&& (Calc.Reach(KDoor) || Constants.CurrentRoom
								.contains(KDoor.getLocation()))
						&& (Constants.KeyEnterd.contains(KDoor.getLocation()) || MatchedKey(KDoor))
						&& KDoor.validate() && MatchIDKey(KDoor)
						&& !Constants.EnterdDoor.contains(KDoor.getLocation())
						&& !Constants.BlackDoor.contains(KDoor.getLocation()));
			}
		};
	}

	public static boolean MatchIDKey(SceneObject object) {
		int k [][] = new int[][]{ulits.convertInt(Constants.KEY_DOOR),ulits.convertInt(Constants.Second_KeyDoor)};
		return ulits.MatchID(object.getId(), ulits.convertInt(k));
	}

	public static boolean MatchedKey(SceneObject object) {
		if (ulits.MatchID(object.getId(), Constants.YELLOW_DOORS)) {

			Constants.CurrentKey = Constants.YellowKey[getIndex(
					object.getId(), Constants.YELLOW_DOORS)];

		} else if (ulits.MatchID(object.getId(), Constants.GREEN_DOORS)) {

			Constants.CurrentKey = Constants.GreenKey[getIndex(object.getId(),
					Constants.GREEN_DOORS)];

		} else if (ulits.MatchID(object.getId(), Constants.BLUE_DOORS)) {

			Constants.CurrentKey = Constants.BlueKey[getIndex(object.getId(),
					Constants.BLUE_DOORS)];

		} else if (ulits.MatchID(object.getId(), Constants.ORANGE_DOORS)) {

			Constants.CurrentKey = Constants.OrangeKey[getIndex(
					object.getId(), Constants.ORANGE_DOORS)];

		} else if (ulits.MatchID(object.getId(), Constants.SILVER_DOORS)) {

			Constants.CurrentKey = Constants.SilverKey[getIndex(
					object.getId(), Constants.SILVER_DOORS)];

		} else if (ulits.MatchID(object.getId(), Constants.PURPLE_DOORS)) {

			Constants.CurrentKey = Constants.PurbleKey[getIndex(
					object.getId(), Constants.PURPLE_DOORS)];

		} else if (ulits.MatchID(object.getId(), Constants.CRIMSON_DOORS)) {

			Constants.CurrentKey = Constants.CrimsonKey[getIndex(
					object.getId(), Constants.CRIMSON_DOORS)];

		} else if (ulits.MatchID(object.getId(), Constants.GOLD_DOORS)) {

			Constants.CurrentKey = Constants.GoldKey[getIndex(object.getId(),
					Constants.GOLD_DOORS)];

		}

		return (Constants.Inevntory_KEY.contains(Constants.CurrentKey) || Constants.KeyEnterd
				.contains(object.getLocation()));

	}

	private static int getIndex(int id, int[] g) {
		int p = 0;
		for (int i : g) {
			if (i == id) {
				return p;
			}
			p++;
		}
		return p;
	}


}
