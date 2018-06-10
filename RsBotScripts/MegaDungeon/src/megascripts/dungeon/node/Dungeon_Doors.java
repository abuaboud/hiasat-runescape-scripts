package megascripts.dungeon.node;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;


import megascripts.api.Flood;
import megascripts.api.Shop;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.api.myplayer.MyGroundItems;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Door;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.boss.CurrentBoss;
import megascripts.dungeon.boss.necrolord;
import megascripts.dungeon.door.Back;
import megascripts.dungeon.door.Guardain;
import megascripts.dungeon.door.Key;
import megascripts.dungeon.door.Skill;
import megascripts.dungeon.door.Standerd;
import megascripts.dungeon.puzzle.CurrentPuzzle;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.Lobby;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import megascripts.graphic.LogHandler;

public class Dungeon_Doors extends Node {

	public static Tile skill = null;
	public static Tile kd = null;
	public static Tile gd = null;

	@Override
	public boolean activate() {
		return MyActions.InDungeon() && !MyActions.ThereFish()
				&& !MyActions.ThereBoss() && !MyActions.ThereKey()
				&& !MyActions.ThreNPC() && !CurrentPuzzle.TherePuzzle()
				&& MyActions.ThereStartRoom() && (!MyActions.ThereCoin());
	}

	@Override
	public void execute() {
		
		try {

			if(ShadowDungeon.getDungeonTime() >= 2400000){
				Constants.LeaveDungeon = true;
			}
			Constants.ROOM_COLOR = Color.green;
			Constants.CurrentRoom = Flood.getArea();

			if (Constants.Dead && MyActions.AtStartRoom()) {
				if (ThereNecrolord()) {
					if (!MyItems.contains(necrolord.BOW)) {
						Shop.buy(necrolord.BOW, 1, 700);
					} else if (!MyItems.contains(necrolord.FRACTICE_ARROW)) {
						Shop.buy(necrolord.FRACTICE_ARROW, 5, 440);
					}
				} else if (CurrentBoss.BossName != null) {
			          ShadowDungeon.setStatus("Preparing Food To Kill a Boss");
                    int Fish = getFish();
					
                    int Price = getFishPrice(Fish);
                    int FISH_AMOUNT = (int) MyItems.getCoinStack() / Price;
                    if(FISH_AMOUNT > 0){
                    	FISH_AMOUNT = (int) (MyItems.getCoinStack() / Price) - 1;
                    }
                    if((Inventory.isFull()) ||  (!Inventory.isFull() && Inventory.getCount(Fish) == 0 && FISH_AMOUNT == 0)){
                  	  Teleport_Portal();
					} else {
						if (MyItems.contains(Constants.SEEPING_ELM_BRANCHES)|| ThereFire()) {
							if (MyItems.contains(Fish)) {
								if ((MyItems.contains(Constants.SEEPING_ELM_BRANCHES) || MyGroundItems.There(Constants.SEEPING_ELM_BRANCHES))&& !ThereFire()) {
                                 if(Shop.isOpen()){
                              	   Shop.close();
                                 }
									if (MyGroundItems.There(Constants.SEEPING_ELM_BRANCHES)) {
										GroundItem b = GroundItems.getNearest(Constants.SEEPING_ELM_BRANCHES);
										if(b !=null){
											b.interact("Light");
											ShadowDungeon.SleepTillStop();
										}
									} else {
										if(Calculations.distanceTo(NPCs.getNearest(11226)) < 2){
											ulits.WalktoTileAway(2);
										}else{
										     MyItems.Interact(Constants.SEEPING_ELM_BRANCHES,"Light");
										}
									}
									ShadowDungeon.SleepTillStop();
									ShadowDungeon.SleepTillStop();
									ShadowDungeon.SleepTillStop();
								} else {
									 SceneObject fire = SceneEntities.getNearest(Constants.FIRE);
									 if(fire !=null){
										 if(Shop.isOpen()){
											 Shop.close();
										 }
										if (Widgets.get(1370, 12).validate()) {
											Widgets.get(1370, 12).click(true);
											Task.sleep(1000,2500);
											if (Widgets.get(1251, 2).validate()) {
												Task.sleep(30000,35000);
											} else {
												ShadowDungeon.SleepTillStop();
												ShadowDungeon.SleepTillStop();
												ShadowDungeon.SleepTillStop();
												ShadowDungeon.SleepTillStop();
											}
										} else {
											MyItems.Interact(Fish, "Use");
											fire.interact("Use");
										}
										
									 }
								}
							} else {
								Shop.buy(Fish, 4, Price,true);
							}
                           
						} else {
							Shop.buy(Constants.SEEPING_ELM_BRANCHES, 1, 600);
						}
					}
						
					}else {
					 	  Teleport_Portal();
					}
			
				
			} else {
				final Door Skill = new Skill(),Guardain = new Guardain(),Standerd= new Standerd(), Key = new Key(), Back = new Back();
				if (Skill.isValid()) {
						ShadowDungeon.setStatus("Openning Skilling Door...");
						Skill.Open();
				}else if (Guardain.isValid()) {
					ShadowDungeon.setStatus("Openning Gurdain Door...");
					Guardain.Open();
				} else if (Standerd.isValid()) {
					ShadowDungeon.setStatus("Openning Standerd Door...");
					Standerd.Open();
				} else if (Key.isValid()) {
					ShadowDungeon.setStatus("Openning Key Door...");
					Key.Open();
				} else if (Back.isValid()) {
					ShadowDungeon.setStatus("Openning Back Door...");
					Back.Open();
				} else if (MyActions.AtStartRoom()) {
					Constants.EnterdDoor.clear();
					Constants.BlackList.clear();
				} else if (!MyActions.ThereBoss()) {
					if (Game.isLoggedIn()) {
						LogHandler.Print(
								"We got Lost On This Dungeon , Abandon..",
								Color.red);
						Toolkit.getDefaultToolkit().beep();
						Task.sleep(1000,1500);
						//Variable.LeaveDungeon = true;
					} else {

					}
				}
			}
			Constants.CurrentRoom = Flood.getArea();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private boolean ThereNecrolord() {
		if (CurrentBoss.BossName == null) {
			return false;
		}
		return CurrentBoss.BossName.equals("Necrolord");
	}

	private boolean ThereFire() {
		SceneObject fire = SceneEntities.getNearest(Constants.FIRE);
		return fire !=null && ulits.tileinroom(fire.getLocation());
	}

	private void Teleport_Portal() {
		SceneObject portal = SceneEntities.getNearest(Constants.PORTAL);
		if (portal != null) {
			if (MyObjects.isOnScreen(portal)) {
				LogHandler.Print("Using Portal To get Back");
				if (portal.interact("Enter")) {
					ShadowDungeon.sleep(1000, 1500);
					ShadowDungeon.SleepTillStop();
					ShadowDungeon.sleep(2500, 3500);
					ShadowDungeon.SleepTillStop();
					Constants.Dead = false;
				}
			} else {
				MyPlayer.WalkTo(portal.getLocation());
			}
		}
	}

	private int getFish() {
		int c = Constants.COOKING_LEVEL;
		int id = 0;
		/*if (c >= 80) {
			id = Variable.RAW_SALVE_EEL;
		}else if (c >= 70) {
			id = Variable.RAW_BOULDA_BASS;
		}else if (c >= 60) {
			id = Variable.RAW_WEB_SNIPPER;
		}else */
			if (c >= 50) {
			id = Constants.RAW_SHORT_FIN;
		} else if (c >= 40) {
			id = Constants.RAW_FLAT_FISH;
		} else if (c >= 30) {
			id = Constants.RAW_DUSK_EEL;
		} else if (c >= 20 || c == 10) {
			id = Constants.RAW_RED_EYE;
		}else{
			id = Constants.RAW_HEIM_CRAB;
		}
		return id;
	}
    private int getFishPrice(int id){
		if (id == Constants.RAW_SALVE_EEL) {
			return 4500;
		} else if (id == Constants.RAW_BOULDA_BASS) {
			return 3400;
		}else if (id == Constants.RAW_WEB_SNIPPER) {
			return 2700;
		}else if (id == Constants.RAW_SHORT_FIN) {
			return 1920;
		} else if (id == Constants.RAW_FLAT_FISH) {
			return 1400;
		} else if (id == Constants.RAW_DUSK_EEL) {
			return 840;
		} else if (id == Constants.RAW_RED_EYE) {
			return 550;
		} else {
			return 200;
		}
    }
	public static void Teleport_Home() {
		WidgetChild Magic_Tab = Widgets.get(275, 33);
		WidgetChild Tele_Tab = Widgets.get(275, 38);
		WidgetChild Home_Tele = Widgets.get(275, 16).getChild(155);
		if (!Tabs.MAGIC.isOpen()) {
			Tabs.MAGIC.open();
		}
		Magic_Tab.click(true);
		Tele_Tab.click(true);
		Home_Tele.click(true);
		Task.sleep(1000, 1500);
		ShadowDungeon.SleepTillStop();
		if (!Tabs.INVENTORY.isOpen()) {
			Tabs.INVENTORY.open();
		}
	}

	public static void Leave_Dungeon() {
		if (MyActions.InDungeon()) {
			WidgetChild Leave = Widgets.get(1186, 8);
			WidgetChild OPTION_ONE = Widgets.get(1188, 3);
			int PARTY_INTERFACE = 939;
			int LEAVE_BUTTON = 13;
			if (OPTION_ONE.validate()) {
				OPTION_ONE.click(true);
				Task.sleep(300, 700);

			} else if (Leave.validate()) {
				Leave.click(true);
				Task.sleep(400, 700);
			} else {
				if (Widgets.get(PARTY_INTERFACE, LEAVE_BUTTON).visible()) {
					Widgets.get(PARTY_INTERFACE, LEAVE_BUTTON).click(true);
					Task.sleep(400, 800);
				} else {
					if (Inventory.contains(Constants.DUNGEON_RING)) {
						MyItems.Interact(Constants.DUNGEON_RING,
								"Open party interface");
						Task.sleep(300, 700);
					} else {
						Tabs.EQUIPMENT.open();
						WidgetChild RING_SLOT = Widgets.get(387, 33);
						RING_SLOT.interact("Open party interface");
						Task.sleep(2500, 2700);
					}
				}
			}
		}
	}


	public static void Add_BackDoor(SceneObject n) {
		if (!Constants.EnterdDoor.contains(n.getLocation())) {
			Constants.EnterdDoor.add(n.getLocation());
		}
		SceneObject backDoor = SceneEntities.getNearest(Constants.BACK_DOORS);
		if (backDoor != null) {
			if (!Constants.EnterdDoor.contains(backDoor.getLocation())) {
				Constants.EnterdDoor.add(backDoor.getLocation());
			}
			Constants.BackBasic.add(backDoor.getLocation());
		}
	}
	public static void Add_BackDoor(SceneObject n,final Tile gd) {
		if (!Constants.EnterdDoor.contains(n.getLocation())) {
			Constants.EnterdDoor.add(n.getLocation());
		}
		SceneObject backDoor = SceneEntities.getNearest(new Filter<SceneObject>() {
					@Override
					public boolean accept(SceneObject ob) {
						return ob != null
								&& ulits.MatchID(ob.getId(),Constants.BACK_DOORS)
								&& !currRoom.MatchTile(ob.getLocation(), gd)
								&& !Constants.EnterdDoor.contains(ob.getLocation());
					}
				});
		if (backDoor != null) {
			Constants.BackBasic.add(backDoor.getLocation());
			if (!Constants.EnterdDoor.contains(backDoor.getLocation())) {
				Constants.EnterdDoor.add(backDoor.getLocation());
			}
		}
	}
	
}
