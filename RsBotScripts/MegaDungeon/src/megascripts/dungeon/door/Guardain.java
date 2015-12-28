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


public class Guardain extends Door {

	@Override
	public String getAuthor() {
		return "ShadowFiend";
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		return MyActions.Set_Object(Gurdain_Door()) != null
				&& MyActions.Object_IsValid(MyActions
						.Set_Object(Gurdain_Door()).getId());
	}

	@Override
	public void Open() {
		SceneObject n = MyActions.Set_Object(Gurdain_Door());
		Tile gd = null;

		Tile o = null;
		o = null;
	
		if (n != null && n.validate()) {
			if (Calculations.distanceTo(n) < 2) {
				if (o == null) {
					o = n.getLocation();
					gd = o;
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
					if (ulits.MatchID(n.getId(), Constants.BACK_DOORS)) {
						Dungeon_Doors.Add_BackDoor(n, gd);
					} else {
						Dungeon_Doors.Add_BackDoor(n);
					}
				

				ShadowDungeon.SleepTillStop(2);
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

	public Filter<SceneObject> Gurdain_Door() {
		return new Filter<SceneObject>() {
			public boolean accept(SceneObject GDoor) {
				return (GDoor != null
						&& ulits.MatchID(GDoor.getId(), Constants.Gurdain_Door)
						&& !Constants.EnterdDoor.contains(GDoor.getLocation())
						&& !Constants.BlackDoor.contains(GDoor.getLocation()) && (Calc
						.Reach(GDoor) || ulits.tileinroom(GDoor.getLocation())));
			}

		};
	}


}
