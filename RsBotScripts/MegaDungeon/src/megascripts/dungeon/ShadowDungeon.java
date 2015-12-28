package megascripts.dungeon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;


import megascripts.api.Flood;
import megascripts.api.currRoom;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyActions;
import megascripts.dungeon.boss.Blink;
import megascripts.dungeon.boss.CurrentBoss;
import megascripts.dungeon.node.Dungeon_Doors;
import megascripts.dungeon.node.Enter_Dungeon;
import megascripts.dungeon.node.Loot_StartRoom;
import megascripts.dungeon.node.Room_Job;
import megascripts.dungeon.puzzle.Barrels;
import megascripts.dungeon.puzzle.ColoredFurret;
import megascripts.dungeon.puzzle.ColouredRecess;
import megascripts.dungeon.puzzle.CurrentPuzzle;
import megascripts.dungeon.puzzle.Fishingferret;
import megascripts.dungeon.puzzle.HunterFerret;
import megascripts.dungeon.puzzle.Leavers;
import megascripts.dungeon.puzzle.LineStatues;
import megascripts.dungeon.puzzle.Magicalconstruct;
import megascripts.dungeon.puzzle.SlidingPuzzle;
import megascripts.dungeon.puzzle.ToxinMaze;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Lobby;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import shadowscripts.graphic.Log;
import shadowscripts.graphic.LogHandler;



@Manifest(authors = { "ShadowFiend" }, name = "Shadow Dungeon", description = "Dungeoneering Like A Boss...", hidden = true)
public class ShadowDungeon extends ActiveScript implements PaintListener,
		MessageListener, MouseListener {

	public static String Message;
	public static boolean ShutDown = false;
	public static int XpGained = 0;
	
	public static final LinkedList<Node> nodes = new LinkedList<Node>();

	@Override
	public void onStart() {
		Constants.DEV_MODE = true;
		LogHandler.Print("Welcome To ShadowDungeon Script [Beta] - V0.1.20",Color.orange);
		Constants.StartXp = Skills.getExperience(Skills.DUNGEONEERING);
		Constants.StartLevel = Skills.getLevel(Skills.DUNGEONEERING);
		nodes.add(new Enter_Dungeon());
		nodes.add(new Loot_StartRoom());
		nodes.add(new Dungeon_Doors());
		nodes.add(new Room_Job());

		Mouse.setSpeed(Speed.VERY_FAST);
		Constants.StartTime = System.currentTimeMillis();
	}

	public static void log(String e) {
		System.out.println("[ShadowDungeon] " + e);
	}


	public static void AnnoucePuzzle(String Puzzlename, String AUTHOR) {
		LogHandler.Print("You Have Completed " + Puzzlename
				+ " Puzzle Coded by: " + AUTHOR, Color.GREEN);
		Constants.PUZZLEDSOLVED++;
	}

	public static void SleepTillStop(int e) {
		for(int x = 0 ; x < e ; x++){
			SleepTillStop();
		}
	}

	public static void SleepTillStop() {
		Task.sleep(600, 900);
		for (int e = 0; e < 50 && Players.getLocal().isMoving()
				&& Players.getLocal().getAnimation() != -1; e++, Task.sleep(
				100, 150))
			;
	}

	public static void SleepWhile(boolean r) {
		Task.sleep(600, 900);
		for (int e = 0; e < 20 && r; e++, Task.sleep(100, 150))
			;
	}

	public static int getMaxFloor() {
		return (int) ((Constants.DUNGEONEERING_LEVEL + 1) / 2);
	}

	public static int getComplexity() {
		return Constants.Current_Complexity;
	}

	public static int getCurrentFloor() {
		return Constants.Current_Floor;
	}

	public static long getLastDungeon() {
		int d = Constants.Dungeons_TIMER.size();
		if (d == 0) {
			return 0;
		}
		return Constants.Dungeons_TIMER.get(0);
	}

	public static long getSlowestDungeon() {
		int d = Constants.Dungeons_TIMER.size();
		if (d == 0) {
			return 0;
		}
		return Collections.max(Constants.Dungeons_TIMER);
	}

	public static long getDungeonTime() {
		if (ShadowDungeon.getComplexity() == 0) {
			return 0;
		}
		if (!MyActions.InDungeon()) {
			return 0;
		} else {
			long d = Constants.Dungeon_Time.getElapsed();
			if (d == 0) {
				return 0;
			}
		}
		return Constants.Dungeon_Time.getElapsed();
	}

	public static long getFastestDungeon() {
		int d = Constants.Dungeons_TIMER.size();
		if (d == 0) {
			return 0;
		}
		return Collections.min(Constants.Dungeons_TIMER);
	}

	public static long getAverageDungeon() {
		long e = 0;
		int d = Constants.Dungeons_TIMER.size();
		if (d == 0) {
			return 0;
		}
		for (long x : Constants.Dungeons_TIMER) {
			e = e + x;
		}
		return e / d;
	}

	@Override
	public int loop() {

		if (!Game.isLoggedIn()) {
			if (Lobby.isOpen()) {
				LogHandler.Print("We are trying Login back...", Color.red);
				Lobby.enterGame();
			} else {
				LogHandler.Print("We can't Login..", Color.red);
			}
		} else if (megascripts.dungeon.Constants.LeaveDungeon) {
			if (!MyActions.InDungeon() && Game.isLoggedIn()) {
				Room_Job.Reset();
				megascripts.dungeon.Constants.LeaveDungeon = false;
				Constants.ABORTED_DUNGEONS++;
			}
			Dungeon_Doors.Leave_Dungeon();
		} else {
			for (Node tree : nodes) {
				if (tree.activate() && tree != null) {
					tree.execute();
				}
			}
		}
		return Random.nextInt(0, 500);
	}

	@Override
	public void messageReceived(MessageEvent e) {

		Message = e.getMessage();
		if (e.getMessage().toLowerCase().contains("oh dear")) {
			LogHandler.Print("Died,Death Walking...");
			Constants.Dead = true;
			megascripts.dungeon.Constants.Death++;
		}
		if (CurrentPuzzle.TherePuzzle()
				&& Message.toLowerCase().contains("level of")
				&& Message.toLowerCase().contains("you need")) {
			LogHandler.Print(
					"Aborting This puzzle , Don't have Required Level",
					Color.red);
			megascripts.dungeon.Constants.Break_Puzzle = true;

		}
		if (CurrentPuzzle.PUZZLES[6].isValid()
				&& e.getMessage().toLowerCase().contains(
						"in the room are now unlocked")) {
			ShadowDungeon.AnnoucePuzzle("Crystal Power", "Magorium");
		}
		if (CurrentPuzzle.PUZZLES[24].isValid()
				&& ShadowDungeon.Message.toLowerCase().contains(
						"in the room are now unlocked")) {
			ShadowDungeon.AnnoucePuzzle("Monolith", "Magorium");
			megascripts.dungeon.puzzle.Monolith.Solved = true;
		}
		if (CurrentPuzzle.PUZZLES[30].isValid()
				&& e.getMessage().toLowerCase().contains(
						"the act of simply retrieving")) {
			megascripts.dungeon.puzzle.Pondskaters.Solved = true;
			ShadowDungeon.AnnoucePuzzle("Pondskaters", "Magorium");
		}
		if (CurrentPuzzle.PUZZLES[14].isValid()
				&& Message.toLowerCase().contains("a broken construct")) {
			if (Message.toLowerCase().contains("head")) {
				LogHandler.Print("This Statue is Missing: Head", Color.green);
				Magicalconstruct.arm_t = false;
				Magicalconstruct.leg_t = false;
				Magicalconstruct.head_t = true;
			} else if (Message.toLowerCase().contains("leg")) {
				LogHandler.Print("This Statue is Missing: Leg", Color.green);
				Magicalconstruct.arm_t = false;
				Magicalconstruct.leg_t = true;
				Magicalconstruct.head_t = false;
			} else if (Message.toLowerCase().contains("arm")) {
				LogHandler.Print("This Statue is Missing: Arm", Color.green);
				Magicalconstruct.arm_t = true;
				Magicalconstruct.leg_t = false;
				Magicalconstruct.head_t = false;
			}
		}

		if (CurrentPuzzle.PUZZLES[29].isValid()
				&& (Message.toLowerCase().contains("you hear a click") || ShadowDungeon.Message
						.toLowerCase().contains("unlocks"))) {
			SlidingPuzzle.Solved = true;
			ShadowDungeon.AnnoucePuzzle("Siliding Tiles", "Magorium");
		}
		if (Message.contains("Floor") && Message.contains("Complexity")) {
			if (Constants.Current_Floor != 0) {
				Room_Job.Reset();
			}
			String[] Messages = Message.split(" ");
			String FloorNumber = Messages[1].replace("<col=641d9e>", "");
			megascripts.dungeon.Constants.Current_Floor = Integer
					.parseInt(FloorNumber);
			String ComplexityNumber = Messages[6].replace("<col=641d9e>", "");
			megascripts.dungeon.Constants.Current_Complexity = Integer
					.parseInt(ComplexityNumber);
			LogHandler.Print("We Are At Floor: "
					+ megascripts.dungeon.Constants.Current_Floor,
					Color.blue);
			LogHandler.Print("Current Complexity is: "
					+ megascripts.dungeon.Constants.Current_Complexity,
					Color.blue);
			megascripts.dungeon.Constants.Dungeon_Time = new Timer(
					2000000000);
			if (megascripts.dungeon.Constants.Dungeon_Time != null
					&& megascripts.dungeon.Constants.Dungeon_Time
							.getElapsed() != 0) {
				megascripts.dungeon.Constants.Dungeon_Time.reset();
			}
			megascripts.dungeon.Constants.DungeonStarted = true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle Button1 = new Rectangle(16, 439, 94, 19);
		Rectangle Button2 = new Rectangle(15, 462, 94, 19);
		Rectangle Button3 = new Rectangle(14, 486, 94, 19);
		Rectangle OPTION = new Rectangle(184, 499, 58, 18);
		Rectangle LOG = new Rectangle(129, 501, 51, 15);
		Rectangle OPTION_FOOD = new Rectangle(130, 445, 15, 12);
		Rectangle hide = new Rectangle(475, 452, 38, 29);
		Rectangle Shutdown = new Rectangle(475, 483, 41, 37);
		Rectangle ShowPaint = new Rectangle(127, 390, 220, 32);
		if (Constants.OPTIONS) {
			if (OPTION_FOOD.contains(e.getPoint())) {
				if (Constants.FOODTOEAT == Constants.FOODS) {
					LogHandler.Print(
							"Food Setting Changed To Use Good Fish only",
							Color.blue);
					Constants.FOODTOEAT = Constants.GOOD_FOODS;
				} else {
					LogHandler.Print(
							"Food Setting Changed To Use All Kind of Food",
							Color.blue);
					Constants.FOODTOEAT = Constants.FOODS;
				}
			}
		}
		if (LOG.contains(e.getPoint())) {
			megascripts.dungeon.Constants.OverAll = false;
			megascripts.dungeon.Constants.STATE = false;
			megascripts.dungeon.Constants.Misc = false;
			Constants.OPTIONS = false;
			Constants.LOG = true;
		}
		if (OPTION.contains(e.getPoint())) {
			megascripts.dungeon.Constants.OverAll = false;
			megascripts.dungeon.Constants.STATE = false;
			megascripts.dungeon.Constants.Misc = false;
			Constants.OPTIONS = true;
			Constants.LOG = false;
		}
		if (Button1.contains(e.getPoint())) {
			megascripts.dungeon.Constants.OverAll = true;
			megascripts.dungeon.Constants.STATE = false;
			megascripts.dungeon.Constants.Misc = false;
			Constants.OPTIONS = false;
			Constants.LOG = false;
		}
		if (Button2.contains(e.getPoint())) {
			Constants.OverAll = false;
			Constants.STATE = true;
			Constants.Misc = false;
			Constants.OPTIONS = false;
			Constants.LOG = false;
		}
		if (Button3.contains(e.getPoint())) {
			megascripts.dungeon.Constants.OverAll = false;
			megascripts.dungeon.Constants.STATE = false;
			megascripts.dungeon.Constants.Misc = true;
			Constants.OPTIONS = false;
			Constants.LOG = false;
		}
		if (megascripts.dungeon.Constants.ShowPaint
				&& hide.contains(e.getPoint())) {
			megascripts.dungeon.Constants.ShowPaint = false;
		}
		if (ShowPaint.contains(e.getPoint())
				&& !megascripts.dungeon.Constants.ShowPaint) {
			megascripts.dungeon.Constants.ShowPaint = true;
		}
		if (Shutdown.contains(e.getPoint())) {
			ShutDown = true;
			stop();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void drawTile(Graphics2D g, Tile tile, Color col) {
		top: for (Polygon poly : tile.getBounds()) {
			for (int i = 0; i < poly.npoints; i++)
				if (!Calculations.isOnScreen(new Point(poly.xpoints[i],
						poly.ypoints[i])))
					continue top;
			g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(),
					80));
			g.fillPolygon(poly);
			g.setColor(col);
			g.drawPolygon(poly);
		}
	}

	public static void drawAraeMM(Graphics render, Area x, Color col) {
		if (x != null) {
			for (Tile e : x.getTileArray()) {
				drawTileMM(render, e, col);
			}
		}
	}

	public static void drawAreaRoom(Graphics render, Color col) {
		for (Tile e : Flood.getArea().getTileArray()) {
			SceneObject r = SceneEntities.getAt(e);
			if (r != null&& (r.getType() == SceneEntities.TYPE_BOUNDARY
							|| r.getType() == SceneEntities.TYPE_INTERACTIVE 
							|| r.getType() == SceneEntities.TYPE_UNKNOWN )) {
					if (ulits.MatchID(r.getId(),ulits.convertInt(Constants.ALL_DOORS))
							&& !Constants.BlackDoor.contains(r.getLocation())
							&& !Constants.BlackList.contains(r.getLocation())
							&& !Constants.BackBasic.contains(r.getLocation())
							&& !Constants.KeyEnterd.contains(r.getLocation())
							&& !Constants.EnterdDoor.contains(r.getLocation())) {
					if (ulits.MatchID(r.getId(),
							ulits.convertInt(Constants.KEY_DOOR))
							|| ulits.MatchID(r.getId(),ulits.convertInt(Constants.ALL_SKILL_DOOR))) {
						drawTileMM(render, e, Color.red, false);
					} else {
						drawTileMM(render, e, Color.WHITE, false);
					}
					}else{
						drawTileMM(render, e, col);
					}
				}
			}
		}
	
	private void drawDoorArea(Graphics g1) {
		for (Tile e : Flood.getArea().getTileArray()) {
			SceneObject r = SceneEntities.getAt(e);
			if (r != null) {
				if (Constants.BlackDoor.contains(r.getLocation())) {
					drawTileMM(g1, e, Color.red, true);
				} else if (Constants.BlackList.contains(r.getLocation())) {
					drawTileMM(g1, e, Color.yellow, true);
				}else if (Constants.BackBasic.contains(r.getLocation())) {
					drawTileMM(g1, e, Color.orange, true);
				} else if (Constants.KeyEnterd.contains(r.getLocation())) {
					drawTileMM(g1, e, Color.BLUE, true);
				} else if (Constants.EnterdDoor.contains(r.getLocation())) {
					drawTileMM(g1, e, Color.MAGENTA, true);
				} 
			}
		}
	}

	public static void drawTileMM(Graphics render, Tile x, Color col, boolean r) {
		Point p = x.getMapPoint();
		if (p != null && p.x > 0 && p.y > 0) {
			render.setColor(col);
			render.fillRect(p.x - 1, p.y - 1, 5, 5);
		}
	}

	public static void drawTileMM(Graphics render, Tile x, Color col) {
		Point p = x.getMapPoint();
		if (p != null && p.x > 0 && p.y > 0) {

			render.setColor(new Color(col.getRed(), col.getGreen(), col
					.getBlue(), 120));
			render.fillRect(p.x - 1, p.y - 1, 5, 5);
		}
	}

	public static String formatNumber(int start) {
		DecimalFormat nf = new DecimalFormat("0.0");
		double i = start;
		if (i >= 1000000) {
			return nf.format((i / 1000000)) + "m";
		}
		if (i >= 1000) {
			return nf.format((i / 1000)) + "k";
		}
		return "" + start;
	}

	public static int getXpHour(int xp) {
		return (int) ((xp) * 3600000D / (System.currentTimeMillis() - Constants.StartTime));
	}

	public static String getTimeToNextLevel(final int skill, final int xpPerHour) {
		if (xpPerHour < 1) {
			return Time.format(0L);
		}
		return Time.format((long) (Skills.getExperienceToLevel(skill,
				Skills.getLevel(skill) + 1) * 3600000D / xpPerHour));
	}

	public static void setStatus(String status) {
		Constants.Status = status;
	}

	// START: Code generated using Enfilade's Easel
	public static Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

	public static final Color color1 = new Color(255, 255, 255);
	public static final Color OPTION_COLOR2 = new Color(255, 255, 255);

	public static final Font font1 = new Font("Verdana", 1, 11);

	public static final Font font2 = new Font("Verdana", 1, 10);

	public static Image img1 = getImage("http://dl.dropbox.com/u/100458739/Scripts/MegaDungeon/megadungeon%20paint.png");
	public static Image img2 = getImage("http://dl.dropbox.com/u/100458739/Scripts/MegaDungeon/navbar.png");
	public static Image Cursor = getImage("http://dl.dropbox.com/u/100458739/Scripts/MegaDungeon/DungeonCursor.png");
	public static Image HIDE_HOVER = getImage("http://dl.dropbox.com/u/100458739/Scripts/MegaDungeon/hide%20button.png");
	public static Image SHUTDOWN_HOVER = getImage("http://dl.dropbox.com/u/100458739/Scripts/MegaDungeon/shut%20button.png");
	public static Image ShowPaint = getImage("http://dl.dropbox.com/u/100458739/Scripts/MegaDungeon/lawl.png");

	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		if (MyActions.InDungeon()) {

			if (Constants.DEV_MODE) {
				for (Tile x : Constants.EnterdDoor) {
					if (x != null) {
						if(!Constants.BackBasic.contains(x)){
						drawTile(g, x, Color.GREEN);
						}
					}
				}
				for (Tile x : Constants.BackBasic) {
					if (x != null) {
						drawTile(g, x, Color.PINK);
						
					}
				}
				for (Tile x : Constants.BlackList) {
					if (x != null) {
						drawTile(g, x, Color.yellow);
						
					}
				}
				
				for (Tile x : Constants.BlackDoor) {
					if (x != null) {
						drawTile(g, x, Color.RED);
						
					}
				}
			
			
			
			}
			if (ColouredRecess.tile != null) {
				for (Tile x : ColouredRecess.tile) {
					if (x != null) {
						drawTile(g, x, Color.RED);
						
					}
				}
			}
			if (Fishingferret.PathToPad != null) {
				for (Tile x : Fishingferret.PathToPad) {
					if (x != null) {
						drawTile(g, x, Color.RED);
					}
				}
			}
			if (ColoredFurret.Path != null) {
				for (Tile x : ColoredFurret.Path) {
					if (x != null) {
						drawTile(g, x, Color.RED);
					}
				}
			}

			if (ColoredFurret.Room != null) {
				drawTile(g, ColoredFurret.Room, Color.black);
			}
	
			if (ToxinMaze.Path != null) {
				drawTile(g, ToxinMaze.Path[0], Color.red);
		}
			if (Fishingferret.PathToPad != null) {
				for (Tile x : Fishingferret.PathToPad) {
					if (x != null && currRoom.isOnScreen(x.getCentralPoint())) {
						drawTile(g, x, Color.RED);
					}
				}
			}
			if (Barrels.PathToPad != null) {
				for (Tile x : Barrels.PathToPad) {
					if (x != null && currRoom.isOnScreen(x.getCentralPoint())) {
						drawTile(g, x, Color.RED);
					}
				}
			}

			if (Barrels.Path != null) {
				if (Barrels.Path != null
						&& currRoom.isOnScreen(Barrels.Path.getCentralPoint())) {
					drawTile(g, Barrels.Path, Color.RED);
				}
			}
			if (HunterFerret.tile != null) {
				drawTile(g, HunterFerret.tile, Color.red);
			}
			if (LineStatues.First != null) {
				drawTile(g, LineStatues.First, Color.red);
			}
			if (Leavers.step != null) {
				drawTile(g, Leavers.step, Color.red);
			}
			if (Room_Job.snow != null) {
				drawTile(g, Room_Job.snow, Color.red);
			}
			if (ToxinMaze.Solve != null) {
				drawTile(g, ToxinMaze.Solve, Color.cyan);
			}
			if (Blink.PILLAR_TILE != null) {
				drawTile(g, Blink.PILLAR_TILE, Color.cyan);
			}
				for (Tile x : ToxinMaze.PASSED) {
					drawTile(g, x, Color.yellow);
				}
				if (ToxinMaze.Path != null) {
						drawTile(g, ToxinMaze.Path[0], Color.red);
				}
			if (ColouredRecess.colortile != null) {
				for (Tile x : ColouredRecess.colortile) {
					drawTile(g, x, Color.yellow);
				}
			}
			
			Constants.CurrentRoom = Flood.getArea();
			drawAreaRoom(g1,Constants.ROOM_COLOR);	
			drawDoorArea(g1);
		}


		Point p = Mouse.getLocation();
		Rectangle hide = new Rectangle(475, 452, 38, 29);
		Rectangle Shutdown = new Rectangle(475, 483, 41, 37);
		String TimeRunning = Time.format(System.currentTimeMillis()
				- Constants.StartTime);

		if (Constants.ShowPaint) {
			megascripts.dungeon.Constants.HIDE_BUTTON_HOVER = hide
					.contains(p);
			g.drawImage(img1, 2, 387, null);
			g.setFont(font2);
			g.setColor(color1);
			g.drawString("" + Constants.Status, 261, 418);
			g.setFont(font1);
			if (Constants.OverAll) {
				g.drawString("TimeRunning:" + TimeRunning, 129, 453);
				g.drawString("Complexity:" + getComplexity(), 129, 467);
				g.drawString("Floor:" + getCurrentFloor(), 130, 479);
				g.drawString(
						"Fastest Dungeon:" + Time.format(getFastestDungeon()),
						284, 452);
				g.drawString(
						"Slowest Dungeon:" + Time.format(getSlowestDungeon()),
						284, 466);
				g.drawString("Last Dungeon:" + Time.format(getLastDungeon()),
						284, 480);
				g.drawString(
						"Average Time:" + Time.format(getAverageDungeon()),
						283, 493);
				g.drawString("Floor Completed:" + Constants.DungeonCompleted,
						130, 492);
			} else if (Constants.STATE) {
			//	int XpGained = Skills.getExperience(Skills.DUNGEONEERING)- Variable.StartXp;
				int LevelGained = Skills.getLevel(Skills.DUNGEONEERING)
						- Constants.StartLevel;
				int TokensGained = (int) XpGained / 10;
				g.drawString("Exp Gained:" + XpGained, 129, 453);
				g.drawString("Exp/Hour:" + formatNumber(XpGained), 129, 467);
				g.drawString("Token:" + TokensGained, 130, 479);
				g.drawString("Token/Hour:" + formatNumber(TokensGained), 284,
						452);
				g.drawString("Level Gained:" + LevelGained, 284, 466);
				g.drawString("Average Dungeon PH:"
						+ formatNumber(Constants.DungeonCompleted), 284, 480);
				g.drawString(
						"Time To Level "
								+ Skills.getLevel(Skills.DUNGEONEERING)
								+ ":"
								+ getTimeToNextLevel(Skills.DUNGEONEERING,
										getXpHour(XpGained)), 283, 493);
				g.drawString("Dungeon Time:" + Time.format(getDungeonTime()),
						130, 492);
			} else if (Constants.Misc) {
				g.drawString("Boss Name:" + CurrentBoss.BossName, 129, 453);
				g.drawString("Max Floor:" + Constants.MAX_FLOOR, 129, 467);
				g.drawString("Total Deaths:" + Constants.Death, 130, 479);
				g.drawString("Aborted: " + Constants.ABORTED_DUNGEONS, 284, 452);
				/*
				 * g.drawString("Level Gained:" + LevelGained, 284, 466);
				 * g.drawString("Average Dungeon PH:" +
				 * formatNumber(Variable.DungeonCompleted),284, 480);
				 * g.drawString("Time To Level " +
				 * Skills.getLevel(Skills.DUNGEONEERING) +":" +
				 * getTimeToNextLevel(Skills.DUNGEONEERING,getXpHour(XpGained)),
				 * 283, 493); g.drawString("Dungeon Time:" +
				 * Time.format(getDungeonTime()) , 130, 492);
				 */
			} else if (Constants.OPTIONS) {
				g.setColor(color1);
				g.setFont(font1);
				if (Constants.FOODTOEAT == Constants.FOODS) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.red);
				}
				g.fillRect(130, 445, 15, 12);
				g.setColor(color1);
				g.drawString("Use Hiem Crabs", 149, 455);

			} else if (Constants.LOG) {
				g.setFont(font2);
				g.setColor(color1);
				if (Log.LOG_INFO.get(0) != null) {
					g.drawString("" + Log.LOG_INFO.get(0), 130, 457);
				}
				if (Log.LOG_INFO.size() >= 1) {
					g.drawString("" + Log.LOG_INFO.get(1), 130, 467);
				}
				if (Log.LOG_INFO.size() >= 2) {
					g.drawString("" + Log.LOG_INFO.get(2), 130, 477);
				}
				if (Log.LOG_INFO.size() >= 3) {
					g.drawString("" + Log.LOG_INFO.get(3), 130, 487);
				}
				g.setFont(font1);
			}
			if (Constants.HIDE_BUTTON_HOVER) {
				g.setColor(Color.red);
				g.drawString("Hide Paint..", 3, 71);
				g.drawImage(HIDE_HOVER, 477, 446, null);
				g.setColor(color1);
			}
			if (Shutdown.contains(p)) {
				g.setColor(Color.red);
				g.drawString("Shutdown Script..", 3, 71);
				g.drawImage(SHUTDOWN_HOVER, 477, 477, null);
				g.setColor(color1);
			}
		} else {
			g.drawImage(ShowPaint, 166, 401, null);
		}
		g.drawImage(img2, 2, 4, null);
		g.drawString("" + TimeRunning, 330, 28);
		g.drawString("" + Constants.DungeonCompleted, 375, 45);
		g.drawString("" + Constants.PUZZLEDSOLVED, 500, 48);
		g.drawString("" + Time.format(getDungeonTime()), 545, 28);

		g.drawImage(Cursor, p.x - 8, p.y - 8, null);
		LogHandler.Draw(g);
	}

}
