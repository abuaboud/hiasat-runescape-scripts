package megascripts.dungeon.puzzle;


import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyGroundItems;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Loot_StartRoom;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


public class Enigmatic extends Puzzle {

	public static final int LOCKED_DOOR[] = {49600,49601,49602,37197};
	public static final int[] CHESTS= {49595,49596,49594};
	public static final int[] FOOD_BARREL= {49599,49598,49597};
	
	public static final int BOW_STRING = 17389;
	public static final int FEAHTER = 17393;
	public static final int HAMMER = 17401;
	public static final int HEADLESS_ARROW = 17403;
	public static final int SHIELD = 17405;
	public static final int ASHES = 17379;
	public static final int BONES = 17387;

	public static final int BANANA = 17281;
	public static final int Wiskey = 17395;
	public static final int FLYFISH = 17399;
	public static final int Needle = 17409;
	public static final int COIN = 17391;
	public static final int Bless = 17383;
	public static final int BLOODRUNE = 17385;
	public static final int MushRoom = 17407;
	public static final int Caveeel = 17397;
	public static final int VIAL = 17413;
	public static final int POSION = 17414;
	
	public static int CurrentItem = 0;
	public static String solve = "All the doors in the room are now unlocked";
	public static final WidgetChild RIDDLE = Widgets.get(1184,13);
	
	public static String Riddle = null;
	public static boolean Solved = false;
	
	@Override
	public String getName() {
		return "Enigmatic hoardstalker";
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
		if(Solved){
			return false;
		}
		NPC n = MyNpc.getNearstNpc("Enigmatic hoardstalker");
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
		ShadowDungeon.setStatus(getStatus());
		if(MyObjects.There(LOCKED_DOOR)){
			SceneObject LOCK = SceneEntities.getNearest(LOCKED_DOOR);
			if(LOCK !=null){
				if(MyObjects.isOnScreen(LOCK)){
					LOCK.interact("Open");
					Constants.BlackDoor.add(LOCK.getLocation());
					ShadowDungeon.SleepTillStop();
				}else{
					MyPlayer.WalkTo(LOCK.getLocation());
				}
			}
		}else if(Riddle == null){
			NPC n = MyNpc.getNearstNpc("Enigmatic hoardstalker");
			if(RIDDLE.validate()){
				Riddle = RIDDLE.getText();	
			}else{
				if(MyNpc.isOnScreen(n)){
				n.interact("Get-Riddle");
				Task.sleep(1000,1500);
				ShadowDungeon.SleepTillStop();
				}else{
					MyPlayer.WalkTo(n.getLocation());
				}
			}
		}else{
			if(!MyItems.contains(CurrentItem)){
			if(Riddle.toLowerCase().contains("deathslinger".toLowerCase())){
				CurrentItem = BOW_STRING;
				loot(BOW_STRING);
			}else if(Riddle.toLowerCase().contains("though i'm light")){
				CurrentItem = FEAHTER;
				Loot_StartRoom.loot(FEAHTER, false);
			}else if(Riddle.toLowerCase().contains("the blunt force ")){
				CurrentItem = HAMMER;
				Loot_StartRoom.loot(HAMMER, false);
			}else if(Riddle.contains("Completed I can make you dead")){
				CurrentItem = HEADLESS_ARROW;
				Loot_StartRoom.loot(HEADLESS_ARROW, false);
			}else if(Riddle.contains("Born through fire")){
				CurrentItem = ASHES;
				Loot_StartRoom.loot(ASHES, false);
			}else if(Riddle.contains("Sticks and stones")){
				CurrentItem = BONES;
				Loot_StartRoom.loot(BONES, false);
			}else if(Riddle.contains("Though my friends")){
				CurrentItem = SHIELD;
				Loot_StartRoom.loot(SHIELD, false);
			}else if(Riddle.contains("From your veins")){
				grabChest("Blood Rune");
				CurrentItem = BLOODRUNE;
			}else if(Riddle.contains("I can get you almost")){
				grabChest("Coins");
				CurrentItem = COIN;
			}else if(Riddle.contains("I pluck")){
				
				CurrentItem = FLYFISH;
				grabChest("Fly fishing rod");
			}else if(Riddle.contains("I demonstrate")){
				CurrentItem = Bless;
				grabChest("Unholy symbol");
			}else if(Riddle.contains("I am cursed")){
				CurrentItem = Needle;
				grabChest("Needle");
			}else if(Riddle.contains("Remove my yellow skin")){
				CurrentItem = BANANA;
				grabBarrel("Banana");
			}else if(Riddle.contains("I am water")){
				CurrentItem = Wiskey;
				grabBarrel("Firebreath whiskey");
			}else if(Riddle.contains("While many call")){
				CurrentItem = MushRoom;
				grabBarrel("mushroom");
			}else if(Riddle.contains("Without me you")){
				CurrentItem = VIAL;
				grabBarrel("Vial of Water");
			}else if(Riddle.contains("A serpent am")){
				CurrentItem = Caveeel;
				grabBarrel("Cave eel");
			}else if(Riddle.contains("The slowest of assassins")){
				CurrentItem = POSION;
				grabBarrel("Poison");
			}
		}else{
			WidgetChild contuine = Widgets.get(1184, 19);
				NPC n = MyNpc.getNearstNpc("Enigmatic hoardstalker");
				if (MyNpc.isOnScreen(n)) {
					if(contuine.validate()){
						contuine.click(true);
						ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
					    Solved = true;
					}else{
					MyItems.Interact(CurrentItem, "Use");
					 if(n.interact("Use")){
							contuine.click(true);
							ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
						    Solved = true;
					 }
					}
					Task.sleep(1000, 1500);
					ShadowDungeon.SleepTillStop();
				} else {
					MyPlayer.WalkTo(n.getLocation());
				}
			}
		}

	}
	public static void loot(int item) {
		GroundItem loot = GroundItems.getNearest(item);
		if (loot != null) {
			if (MyGroundItems.isOnScreen((loot))) {
				if (ulits.tileinroom(loot.getLocation())) {
					Camera.turnTo(loot);
					String name = loot.getGroundItem().getName();
					loot.interact("Take", name);
					Task.sleep(500, 650);
					ShadowDungeon.SleepTillStop();
				}
			} else {

				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
	}
	private static void grabChest(String e){
		Widget CHEST =  Widgets.get(1188);
		if(CHEST.validate()){
			ShadowDungeon.log(e);
			WidgetChild Item = getItemWidget(e);
			if(Item !=null){
			Item.click(true);
			Task.sleep(1000,1500);
			}
		}else{
			SceneObject chest = SceneEntities.getNearest(CHESTS);
			if(chest !=null){
				if(MyObjects.isOnScreen(chest)){
					chest.interact("Search");
					Task.sleep(1000,1500);
				}else{
					MyPlayer.WalkTo(chest.getLocation());
				}
			}
		}
	}
	private static void grabBarrel(String e){
		Widget CHEST =  Widgets.get(1188);
		if(CHEST.validate()){
			WidgetChild Item = getItemWidget(e,false);
			Item.click(true);
			Task.sleep(1000,1500);
		}else{
			ShadowDungeon.log(e);
			SceneObject chest = SceneEntities.getNearest(FOOD_BARREL);
			if(chest !=null){
				if(MyObjects.isOnScreen(chest)){
					chest.interact("Search");
					Task.sleep(1000,1500);
				}else{
					MyPlayer.WalkTo(chest.getLocation());
				}
			}
		}
	}
	private static WidgetChild getItemWidget(String e,boolean o) {
		for(WidgetChild r : Widgets.get(1188).getChildren()){
			if(r.getText().contains(e)){
				return r;
			}
		}
		for(WidgetChild r : Widgets.get(1188).getChildren()){
			if(r.getText().contains("More")){
				return r;
			}
		}
		return null;
	}
	private static WidgetChild getItemWidget(String e) {
		for(WidgetChild r : Widgets.get(1188).getChildren()){
			if(r.getText().toLowerCase().contains(e.toLowerCase())){
				return r;
			}
		}
		return null;
	}

}