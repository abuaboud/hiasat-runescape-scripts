
package megascripts.dungeon.node;

import java.awt.Point;
import java.awt.Rectangle;


import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Enter_Dungeon extends Node{

	public static int ENTERANCE_GATE = 48496;
	public static int BROKEN_LADDER= 50552;
	private boolean s = false;
	

	
	private boolean outSide() {
        Area area = new Area(new Tile(3445, 3718, 0), new Tile(3472, 3729, 0));
        return area.contains(Players.getLocal().getLocation().getX(), Players.getLocal().getLocation().getY());
}

	private void SetSize() {
        if (Widgets.get(1188).getChild(3).validate()) {
         Point Click = Widgets.get(1188).getChild(3).getAbsoluteLocation();
         Mouse.hop(Click.x, Click.y);
         Widgets.get(1188).getChild(3).click(true);
         sleep(1200, 2000);
        }
   
   }

	private void SetDifficulty(int f) {
		WidgetChild FLOOR_BUTTON = Widgets.get(947).getChild(761);
		int Floor = 608 + (f - 1);
		WidgetChild FLOOR_NUMBER = Widgets.get(947).getChild(Floor);
		WidgetChild SCROLLDOWN = Widgets.get(947).getChild(47).getChild(5);

	
		int CUREENTFLOOR = getCurrentChosenFloor();
		Rectangle FLOOR_AREA = new Rectangle(15, 86, 210, 189);
		if (FLOOR_BUTTON.validate()) {
			if (CUREENTFLOOR != f) {
				if (!FLOOR_AREA.contains(FLOOR_NUMBER.getCentralPoint())) {
					SCROLLDOWN.click(true);
				} else {
					FLOOR_NUMBER.click(true);
					ShadowDungeon.sleep(400, 800);
				}
			} else {
				FLOOR_BUTTON.click(true);
				ShadowDungeon.sleep(400, 800);
			}
		}
        // Complex
        if (Widgets.get(938).getChild(39).validate()) {
         Point Click = Widgets.get(938).getChild(39).getAbsoluteLocation();
         Mouse.hop(Click.x, Click.y);
         Widgets.get(938).getChild(39).click(true);
         sleep(1000, 1200);
        }
   
   }

	private int getCurrentChosenFloor() {
		if(Widgets.get(947).getChild(765).getText() == null){
			return 0;
		}
		if(Widgets.get(947).getChild(765).getText() == ""){
			return 0;
		}
		return Integer.parseInt(Widgets.get(947).getChild(765).getText()); 
	}

	private void FormParty() {
        if (Widgets.get(1186).getChild(8).validate()) {
         Point Click = Widgets.get(1186).getChild(8).getAbsoluteLocation();
         Mouse.hop(Click.x, Click.y);
         Widgets.get(1186).getChild(8).click(true);
         sleep(1200, 2000);
        }
        if (Widgets.get(1188).getChild(3).validate()) {
         Point Click = Widgets.get(1188).getChild(3).getAbsoluteLocation();
         Mouse.hop(Click.x, Click.y);
         Widgets.get(1188).getChild(3).click(true);
         sleep(1200, 2000);
        }
   
   }

	private void ClickDoor() {
        if (SceneEntities.getNearest(48496) !=null) {
         if (!Widgets.get(1188).getChild(3).validate() && !Widgets.get(938).getChild(39).validate() && !Widgets.get(1186).getChild(8).validate() && !Widgets.get(947).getChild(761).validate()) {
          SceneObject dungDoor = SceneEntities.getNearest(48496);
          if (!dungDoor.isOnScreen() && !Players.getLocal().isMoving()) {
           Walking.walk(dungDoor.getLocation());
           sleep(200, 300);
          }
          if (dungDoor.isOnScreen()) {
           dungDoor.interact("Climb-down");
           sleep(1200, 1400);
          }
         }
        }
   
   }

	private void clickStairs() {
        if (SceneEntities.getNearest(50552) != null && Players.getLocal().getAnimation() != 13760) {
         SceneObject dungStairs = SceneEntities.getNearest(50552);
         if (!MyObjects.isOnScreen(dungStairs) && !Players.getLocal().isMoving()) {
          Walking.walk(dungStairs.getLocation());
         }
         if (MyObjects.isOnScreen(dungStairs) && Players.getLocal().getAnimation() != 13760) {
          dungStairs.interact("Jump-down");
          sleep(800, 1000);
         }
        }
   
   }

	@Override
	public boolean activate() {
		return  (At_Gate() || ATSurf());
	}

	public static boolean ATSurf() {
		return MyActions.Object_IsValid(BROKEN_LADDER);
	}

	public static boolean At_Gate(){
		return MyActions.Object_IsValid(ENTERANCE_GATE);
	}
	@Override
	public void execute() {
		if (Game.getPlane() == 1) {
			ShadowDungeon.setStatus("Siliding Down...");
			clickStairs();
		}
		if (Game.getPlane() == 0 && outSide() && s == false) {
			ShadowDungeon.setStatus("Entering Dungeon...");
			FormParty();
            if(Constants.DUNGEONEERING_LEVEL  == 0){
            	if(!Tabs.STATS.isOpen()){
            		Tabs.STATS.open();
            	}
            		Constants.PRAYER_LEVEL = Integer.parseInt(Widgets.get(320,57).getText());
            		Constants.DUNGEONEERING_LEVEL = Integer.parseInt(Widgets.get(320,119).getText());
            		Constants.COOKING_LEVEL = Integer.parseInt(Widgets.get(320, 51).getText());
            		Tabs.INVENTORY.open();
            	
            }else if (Constants.CURRENT_PROGRESS == 10000) {
				 Constants.CURRENT_PROGRESS = getCurrentProgress();
					Constants.MAX_FLOOR = ShadowDungeon.getMaxFloor();
				 if( Constants.CURRENT_PROGRESS == Constants.MAX_FLOOR){
					 for(int x = 0 ; x < 100 && Constants.CURRENT_PROGRESS == Constants.MAX_FLOOR ; x++){
			        WidgetChild Reset = Widgets.get(939, 87);
			        WidgetChild contuine = Widgets.get(1186,7);
			        WidgetChild yes = Widgets.get(1188,3);
						if (contuine.validate()) {
							contuine.click(true);
							Task.sleep(1000,1500);
							yes.click(true);
						} else {
							Interact_Ring("Open party interface");
							Reset.click(true);
							Task.sleep(2000, 2500);
						}
					}
					 Constants.CURRENT_PROGRESS = 10000;
				}
			} else {
				int Floor = Constants.CURRENT_PROGRESS + 1;
				ClickDoor();
				SetDifficulty(Floor);
				SetSize();
			}
		}
	}
	private int getCurrentProgress() {
		if (Widgets.get(939).validate()) {
			return Integer.parseInt(Widgets.get(939).getChild(83).getText());
		}else{
		     Interact_Ring("Open party interface");
		}
		return 10000;
	}

	private void Interact_Ring(String string) {
		if(MyItems.contains(Constants.DUNGEON_RING)){
			MyItems.Interact(Constants.DUNGEON_RING, "Wear");
		}else{
			Tabs.EQUIPMENT.open();
			WidgetChild RING_SLOT = Widgets.get(387, 33);
			RING_SLOT.interact(string);
			Task.sleep(2500, 2700);
		}
		
	}

	public static void SurfDown() {
		SceneObject Surf = SceneEntities.getNearest(BROKEN_LADDER);
		if (Surf != null) {
			if (MyObjects.isOnScreen(Surf)) {
				Surf.interact("Jump-Down");
				Constants.DUNGEONEERING_LEVEL = 0;
				Task.sleep(1000,1400);
			}else{
				MyPlayer.WalkTo(Surf.getLocation());
			}
		}
	}

	public static void EnterDungeon(int FloorN,int Complex) {
		int FloorNumber = 669;
		WidgetChild Floor = Widgets.get(947).getChild(FloorNumber);
		WidgetChild ComplexNumber = Widgets.get(938).getChild(getComplex(Complex));
		WidgetChild FloorConform = Widgets.get(947).getChild(766);
		WidgetChild ComplexConfrom = Widgets.get(938).getChild(37);
		WidgetChild NoParty = Widgets.get(1186).getChild(9);
		WidgetChild Yes = Widgets.get(1188).getChild(12);
		if (!Floor.validate() && !ComplexNumber.validate()
				&& !NoParty.validate() && !Yes.validate()) {
			PressEnterDungeon();
		} else {
			if (NoParty.validate()) {
				NoParty.click(true);
				Task.sleep(500,1000);
			} else if (Yes.validate()) {
				Yes.click(true);
				Task.sleep(500,1000);
			} else if (Floor.validate()) {
				Floor.click(true);
				Task.sleep(500,1000);
				FloorConform.click(true);
				Task.sleep(500,1000);
			} else if (ComplexNumber.validate()) {
				if (ComplexNumber.click(true)) {
					Task.sleep(500, 1000);
					if (ComplexConfrom.click(true)) {
						Task.sleep(500, 1000);
					}
				}
			}

		}

	}

	public static int getComplex(int complex) {
		if(complex == 1){
			return 60;
		}else if(complex == 2){
			return 61;
		}else if(complex == 3){
			return 66;
		}else if(complex == 4){
			return 75;
		}else if(complex == 5){
			return 76;
		}else if(complex == 6){
			return 81;
		}
		return 60;
	}

	public static void PressEnterDungeon() {
		SceneObject Gate = SceneEntities.getNearest(ENTERANCE_GATE);
		if (Gate != null) {
			if (MyObjects.isOnScreen(Gate)) {
				Gate.interact("Climb-Down");
				Task.sleep(1000,1400);
			}else{
				Walking.walk(Gate);
				
			}
		}
	}
}