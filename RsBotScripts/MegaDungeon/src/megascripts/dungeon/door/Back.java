package megascripts.dungeon.door;

import java.awt.Color;
import java.awt.Point;

import megascripts.api.Calc;
import megascripts.api.Flood;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Door;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Dungeon_Doors;
import megascripts.dungeon.node.Room_Job;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.LogHandler;


public class Back extends Door{

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
		return MyActions.Set_Object(Door_Back()) != null
				&& MyActions.Object_IsValid(MyActions.Set_Object(Door_Back())
						.getId());
	}

	@Override
	public void Open() {
		boolean e = false;
		SceneObject n = MyActions.Set_Object(Door_Back());
			boolean DeadEnd = false;
			Tile o = null;
			o = null;
			Area roomarea = Flood.getArea();
			if (n != null && n.validate()) {
				if (Calculations.distanceTo(n) < 2) {
					if (o == null) {
						o = n.getLocation();
					}
					if (!e) {
						DeadEnd = Room_Job.DeadEnd();
					}
					
					for (int x = 0; x < 15; x++) {
	                  
						if (MyPlayer.isMoving()) {
							ShadowDungeon.SleepTillStop();
						}
						if (!Calc.Reach(o)) {
							x = 15;
						}
						if (x != 15) {
							Point Central = n.getModel().getCentralPoint();
							Mouse.move(Central.x, Central.y);
							if (Menu.contains("Enter") || Menu.contains("Unlock")
									|| Menu.contains("Open")) {
								Mouse.click(Central.x, Central.y, true);
								Task.sleep(500, 900);
								ShadowDungeon.SleepTillStop();
								ShadowDungeon.SleepTillStop();
							}else{
								n.interact("Enter");
								Task.sleep(500, 900);
								ShadowDungeon.SleepTillStop();
								ShadowDungeon.SleepTillStop();
							}
						}
						Constants.NEXTROOM = currRoom.NextRoomArea();
					}
					if (e) {
						if (ulits.MatchID(n.getId(), Constants.BACK_DOORS)) {
							Dungeon_Doors.Add_BackDoor(n, o);
						} else {
							Dungeon_Doors.Add_BackDoor(n);
						}
					} else {
						if (DeadEnd && !Flood.getArea().equals(roomarea)) {
							Constants.Break_Puzzle = false;
							SceneObject Back = SceneEntities
									.getNearest(getBlackDoor());
							if (Back != null) {
								Constants.BlackDoor.add(Back.getLocation());
								LogHandler.Print("Added This Door To Black List",
										Color.red);
							}
						}
						Constants.BlackList.add(n.getLocation());
					}

					ShadowDungeon.SleepTillStop();
				} else {
					if (Calculations.distanceTo(n) < 3) {
						if (!MyPlayer.isMoving()) {
							n.interact("Walk here");
							ShadowDungeon.SleepTillStop();
							ShadowDungeon.SleepTillStop();
						}
					} else {
						MyPlayer.WalkTo(n.getLocation(), false);
						Camera.turnTo(n);
					}
				}
			}
	}
	public static Filter<SceneObject> getBlackDoor() {
		return new Filter<SceneObject>() {
			@Override
			public boolean accept(SceneObject obj) {
				return obj != null
						&& ulits.MatchID(obj.getId(),
								ulits.convertInt(Constants.ALL_DOORS))
						&& (Calc.Reach(obj) || ulits.tileinroom(obj.getLocation()))
						&& Calculations.distanceTo(obj) < 6
						&& !Constants.BackBasic.contains(obj.getLocation());
			}
		};
	}
	public static Filter<SceneObject> Door_Back() {
		return new Filter<SceneObject>() {
			public boolean accept(SceneObject GDoor) {
				return (GDoor != null
						&& ulits.MatchID(GDoor.getId(), Constants.BACK_DOORS)
						&& !Constants.BlackList.contains(GDoor.getLocation())
						&& Constants.BackBasic.contains(GDoor.getLocation())
						&& !Constants.SkillEnterd.contains(GDoor.getLocation())
						&& !Constants.BlackDoor.contains(GDoor.getLocation()) && (Calc
						.Reach(GDoor) || ulits.tileinroom(GDoor.getLocation())));

			}

		};
	}

	public static SceneObject getDoor(){
		return SceneEntities.getNearest(Door_Back());
	}
}
