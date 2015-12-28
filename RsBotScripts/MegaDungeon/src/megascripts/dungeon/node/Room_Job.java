package megascripts.dungeon.node;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.Flood;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyArmor;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Door;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.boss.CurrentBoss;
import megascripts.dungeon.boss.Planefreezer;
import megascripts.dungeon.boss.SkeletalHorde;
import megascripts.dungeon.door.Back;
import megascripts.dungeon.puzzle.CurrentPuzzle;
import megascripts.dungeon.puzzle.Enigmatic;
import megascripts.dungeon.puzzle.Magicalconstruct;
import megascripts.dungeon.puzzle.Monolith;
import megascripts.dungeon.puzzle.Pondskaters;
import megascripts.dungeon.puzzle.SilidingStatues;
import megascripts.dungeon.puzzle.Sleepingguards;
import megascripts.dungeon.puzzle.SlidingPuzzle;
import megascripts.dungeon.puzzle.WhinchBridge;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import shadowscripts.graphic.Log;
import shadowscripts.graphic.LogHandler;

public class Room_Job extends Node {

	public static Tile snow = null;

	@Override
	public boolean activate() {
		return !MyActions.AtStartRoom()
				&& MyActions.InDungeon()
				&& (CurrentPuzzle.TherePuzzle() || MyActions.ThereBoss()
						|| MyActions.ThereFish() || MyActions.ThereKey()
						|| MyActions.ThreNPC() || MyActions.ThereCoin());
	}
	@Override
	public void execute() {

		try {
			if(Camera.getPitch() < 80){
				Camera.setPitch(90);
			}
			if(MyPlayer.isPoisoned()){
				MyItems.Interact(Constants.POSION_POTION, "drink");
				ShadowDungeon.SleepTillStop();
				ShadowDungeon.SleepTillStop();
			}
			Constants.CurrentRoom = Flood.getArea();
			if (MyActions.ThereBoss()) {
				Constants.ROOM_COLOR = Color.red;
				if (EndDungeon()) {
					ShadowDungeon.setStatus("Ending Dungeon...");
					End_Dungeon();
				} else {
					Eat();
					CurrentBoss.GetCurrentBossTactic();
				}

			} else if (MyActions.ThereGroupStone()) {
				Loot_StartRoom.loot(Constants.GROUP_STONE, false);
			} else if (DeadEnd()) {
				Constants.ROOM_COLOR = Color.magenta;
				Combat.TurnRet(false);
				if (MyActions.ThereKey()) {
					ShadowDungeon.setStatus("Dead End: Looting Keys...");
					Loot_StartRoom.loot(ulits.convertInt(Constants.ALL_KEYS),
							true);
				} else {
					ShadowDungeon.setStatus("Dead End: Leaving This Room...");
					final Door back = new Back();
					if (back.isValid()) {
						back.Open();
					}
				}
			} else if (CurrentPuzzle.TherePuzzle()) {
				CurrentPuzzle.solve();
			} else if (MyActions.ThreNPC()) {
				ShadowDungeon.setStatus("Clearing Room From NPC...");
				Constants.ROOM_COLOR = Color.BLUE;
				Combat.TurnRet(true);
				Kill();
			} else if (MyActions.ThereKey()) {
				ShadowDungeon.setStatus("Clearing Room From Key...");
				Constants.ROOM_COLOR = Color.yellow;
				Loot_StartRoom.loot(ulits.convertInt(Constants.ALL_KEYS), true);
			} else if (MyActions.ThereFish()) {
				ShadowDungeon.setStatus("Clearing Room From Food...");
				Constants.ROOM_COLOR = Color.yellow;
				Loot_StartRoom.loot(Constants.FOODTOEAT, false);
			} else if (MyActions.ThereCoin()) {
				ShadowDungeon.setStatus("Clearing Room From Coins");
				Loot_StartRoom.loot(Constants.COIN, false);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public static boolean DeadEnd() {
		if (MyActions.AtStartRoom()) {
			return false;
		}
		if (Constants.Break_Puzzle) {
			return true;
		}
		if (CurrentPuzzle.TherePuzzle()) {
			return false;
		}
		int REACHED_DOOR = 0;
		int Tunnel = 0;
		Constants.CurrentRoom = Flood.getArea();
		for (SceneObject Loaded : SceneEntities.getLoaded(ulits
				.convertInt(Constants.ALL_DOORS))) {
			if (Loaded != null
					&& (Calc.Reach(Loaded) || Constants.CurrentRoom
							.contains(Loaded.getLocation()))) {
				REACHED_DOOR++;
				if (Constants.BlackDoor.contains(Loaded.getLocation())) {
					Tunnel++;
				}
			}
		}
		return (REACHED_DOOR / 2 == 1 || REACHED_DOOR / 2 - Tunnel / 2 == 1);

	}

	public static void Kill() {
		try {
			Eat();
			for (NPC Monster : NPCs.getLoaded()) {
				if (Monster != null) {
					if (Players.getLocal().getInteracting() == null
							&& Monster.getLevel() > 0
							&& Monster.getId() != Constants.Smuggler) {
						if (ulits.tileinroom(Monster.getLocation())) {
							if (MyNpc.isOnScreen(Monster)) {
								Monster.interact("Attack");
								Task.sleep(400, 800);
								ShadowDungeon.SleepWhile(MyPlayer.isMoving());
							} else {
								MyPlayer.WalkTo(Monster.getLocation());
							}
						}
					}
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private static boolean EndDungeon() {
		SceneObject Door = SceneEntities.getNearest(Constants.FINSHED_ENDLADDER);
		return Door != null
				&& (Calc.Reach(Door) || ulits.tileinroom(Door.getLocation()));
	}

	public void End_Dungeon() {
		SceneObject Out = SceneEntities.getNearest(Constants.FINSHED_ENDLADDER);
		WidgetChild Contuine = Widgets.get(1188).getChild(2);
		Widget EndWindows = Widgets.get(933);
		WidgetChild Skip = Widgets.get(933).getChild(13);
		WidgetChild Ready = Widgets.get(933).getChild(323);
		if (Contuine.validate() || EndWindows.validate()) {
			if (Contuine.validate()) {
				if (Contuine.click(true)) {
					Reset();
					ShadowDungeon.SleepWhile(Contuine.validate());
					ShadowDungeon.SleepWhile(Contuine.validate());
					ShadowDungeon.SleepWhile(Contuine.validate());
				}
			}
			if (EndWindows.validate()) {
				if (Skip.validate()) {
					Skip.click(true);
					Task.sleep(1000, 1500);
				}
				if (Ready.click(true)) {
				//	MegaDungeons.XpGained = MegaDungeons.XpGained+ (Integer.parseInt(Widgets.get(933).getChild(41).getText()) * 10);
				    ShadowDungeon.SleepWhile(EndWindows.validate());
				    ShadowDungeon.SleepWhile(EndWindows.validate());
				    ShadowDungeon.SleepWhile(EndWindows.validate());
				}
			}
		} else {
			if (CurrentBoss.BossName !=null && CurrentBoss.BossName.contains(CurrentBoss.BOSSES[12].getName())) {
				Exit_PlaneFreezer(Out);
			} else {
				Exit(Out);
			}
		}
	}

	private void Exit_PlaneFreezer(SceneObject Out) {
		SceneObject e = MyObjects.getNearstObjectTo(Out.getId(),
				Planefreezer.SNOW);
		snow = e.getLocation();
		if (Out != null) {
			int OX = Out.getLocation().getX();
			int OY = Out.getLocation().getY();
			int distout = (int) Calculations.distanceTo(Out);
			if (distout < 2) {
				Out.interact("End-dungeon");
				Task.sleep(1500, 200);
			} else {
				if (currRoom.MatchTile(e.getLocation())) {
					MyPlayer.WalkTo(new Tile(OX - 1, OY - 1, 0));
					ShadowDungeon.SleepTillStop();
					ShadowDungeon.SleepTillStop();
				} else {
					MyPlayer.WalkTo(e.getLocation());
					ShadowDungeon.SleepTillStop();
					ShadowDungeon.SleepTillStop();
				}
			}
		}
	}

	private void Exit(SceneObject Out) {
		if (Out != null) {
			if ((Calculations.distance(Players.getLocal().getRegionOffset(),
					Out.getRegionOffset()) <= 3)) {
				int OX = Out.getLocation().getX();
				int OY = Out.getLocation().getY();
				Walking.walk(new Tile(OX - 1, OY - 1, 0));
				Out.interact("End-dungeon");
				Task.sleep(2000);
			} else {
				Walking.walk(Out.getLocation());
				Task.sleep(1500);
			}
		}
	}

	public static void Reset() {
		if (!Constants.LeaveDungeon) {
			Constants.Dungeons_TIMER.add(Constants.Dungeon_Time.getElapsed());
			Log.add(Log.makelog(Constants.Current_Floor, Constants.Current_Complexity, CurrentBoss.BossName, Time.format(Constants.Dungeon_Time.getElapsed())));
			if (Constants.Current_Floor != 0) {
				LogHandler.Print("You have Done This Dungeon on : "+ Time.format(Constants.Dungeon_Time.getElapsed()), Color.red);
			}
		}
		Constants.ROOM_COLOR = Color.BLUE;
		Constants.CURRENT_PROGRESS = 10000;
		Constants.Current_Complexity = 0;
		Constants.Current_Floor = 0;
		Constants.EnterdDoor.clear();
		Constants.KeyEnterd.clear();
		Constants.BlackList.clear();
		Constants.Inevntory_KEY.clear();
		Constants.SkillEnterd.clear();
		Constants.BlackDoor.clear();
		Constants.BackBasic.clear();
		Constants.MESSAGE_LOG.clear();
		MyPlayer.RESET_LEVEL();
		Constants.BUY_SUPPLIES = true;
		if (!Constants.LeaveDungeon) {
			Constants.DungeonCompleted++;
			LogHandler.Print(
					"We Done " + Constants.DungeonCompleted + " So Far",
					Color.red);
			ShadowDungeon.setStatus("Ready For Next Dungeon...");
			LogHandler.Print("Ready For Next Dungeon...", Color.GREEN);
		}
		SkeletalHorde.reset();
		CurrentBoss.BossName = null;
		Monolith.Solved = false;
		Pondskaters.Solved = false;
		Sleepingguards.Solved = false;
		SlidingPuzzle.Solved = false;
		WhinchBridge.Solved = false;
		Enigmatic.Solved = false;
		SilidingStatues.reset();
		MyArmor.reset();
	
	}

	private static Item getFood() {
		for (Item i : Inventory.getItems()) {
			for (int s : Constants.FOODTOEAT) {
				if (i.getId() == s) {
					return i;
				}
			}
		}
		return null;
	}




	public static void Eat() {
		if (MyPlayer.getHealthPercent() < 40) {
			Item food = getFood();
			if (food != null) {
				food.getWidgetChild().interact("Eat");
			}
		}
	}

}
