import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;




import com.epicbot.client.input.Keyboard;
import com.epicbot.event.events.MessageEvent;
import com.epicbot.event.listeners.MessageListener;
import com.epicbot.event.listeners.PaintListener;
import com.epicbot.event.listeners.PhysicalMouseListener;
import com.epicbot.script.Script;
import com.epicbot.script.ScriptManifest;
import com.epicbot.script.methods.Abilities;
import com.epicbot.script.methods.Abilities.Ability;
import com.epicbot.script.methods.Abilities.ActionSlot;
import com.epicbot.script.methods.Bank;
import com.epicbot.script.methods.Skills;
import com.epicbot.script.util.Timer;
import com.epicbot.script.wrappers.RSArea;
import com.epicbot.script.wrappers.RSComponent;
import com.epicbot.script.wrappers.RSGroundItem;
import com.epicbot.script.wrappers.RSItem;
import com.epicbot.script.wrappers.RSNPC;
import com.epicbot.script.wrappers.RSObject;
import com.epicbot.script.wrappers.RSPlayer;
import com.epicbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Magorium" }, name = "Pro Soulwar")
public class Prosoulwar extends Script implements PaintListener,
		MessageListener,PhysicalMouseListener {

	private final int NOMAD = 8525, BONES_ID = 14638;
	private final int RED_PORTAL = 42030, BLUE_PORTAL = 42029,
			RANDOM_PORTAL = 42031;
	private final static int[] PORTAL_GRAVE = { 42013, 42014, 42015, 42016,
			42017, 42018 }, BLUE_AVATER_ID = { 8597 }, REDAVATER_ID = { 8596 },
			BANADAGE_TABLE = { 42023, 42024 };
	private final int BANDAGE_ID = 14640;
	private int CURRENTTEAM = -1, LAST_WIN = -1, ZEAL = 0, GAMEPLAYED = 0,
			BANDAGE_VALVE, WEST_GRAVEYARD = -1, EAST_GRAVEYARD = -1,WIN = 0,STARTTOTALXP , STARTCONSXP;
	private long STARTIME = 0;
	private String CURRENT_TEAM, STATUS = "Loading..";
	private TASK CURRETNTASK;
	private final int PYRES_ID = 8598, JELLIES_ID = 8599;;
	
	private RSTile PORTAL_TILE = null;
	private final RSTile RANDOM_TEAM_PORTAL = new RSTile(1890, 3162),
			BANKCHEST_TILE = new RSTile(1893, 3180),
			BLUE_PORTAL_TILE = new RSTile(1879, 3162),
			RED_PORTAL_TILE = new RSTile(1900, 3162);

	private Familiar familiar;
	
	private ArrayList<String>MESSAGE_LOG = new ArrayList<String>();
	
	private final String[] ANTI_BAN_WAITING = { "come on", "let beat them",
			"we gonna beat them", "ogm!", "omg", "Go Go Go!!", "go!" };

	private enum TASK {
		ATTACK_PYRE, ATTACK_JELLIS, ATTACK_EVERYWHERE, ATTACK_OBELISK, ATTACK_AVATER, PICK_BONES;
	}

	private boolean JOIN_RANDOM_TEAM = false, JOIN_LAST_WIN = false,ENABLE_SUMMOING = false
			,WITHDRAWPOUCH = false,JOIN_LAST_LOSS = false,JOIN_HIGHESTLEVEL = false,JOIN_RED = false,JOIN_BLUE = false,SHOWPAINT = true;

	private RSComponent CHAT_CONTUINE, CHAT_OPITION_ONE, SUPPLY_INTERFACE,SUMMONG_ICON_TEXTURE;
	private ArrayList<Integer> hotkeys = new ArrayList<Integer>();
	private Timer t = new Timer(0);
	private MousePaint mousePaint = new MousePaint();
	private LogHandler loghandler = new LogHandler();
	@Override
	public boolean onStart() {
		log("Intilizing Varribles...");
		mouse.setSpeed(random(4,6));
		STARTIME = System.currentTimeMillis();
		SUPPLY_INTERFACE = interfaces.getComponent(752, 4);
		CHAT_OPITION_ONE = interfaces.get(1188).getComponent(11);
		CHAT_CONTUINE = interfaces.get(1186).getComponent(10);
		SUMMONG_ICON_TEXTURE = interfaces.get(747).getComponent(0);
		log("Loading Gui...");
		guiInit();
   
		log("Script Started...");
		return true;
	}
    public void guiInit() {
		try {

	        SwingUtilities.invokeLater(new Runnable() {
	                        public void run() {
	                        	 final SWGUI gui = new SWGUI();
	             	            gui.setVisible(true);
	                        }
	        });
		} catch (Throwable ignore) {
	        log.info("Could not load GUI.");
	        return ;
		}
	}
	@Override
	public int loop() {
		if (!InGame()) {
			if (!InTeam()) {
				if(inventory.getCount(229) > 0){
					
				}
				if (ENABLE_SUMMOING) {
					Summon();
				}
				STATUS = "Joining Team...";
				if (JOIN_RANDOM_TEAM) {
					PORTAL_TILE = RANDOM_TEAM_PORTAL;
					Join_Team(RANDOM_PORTAL);
				} else if (JOIN_HIGHESTLEVEL) {
					int Team = getHighestTeam();
					int portal = Team == 2 ? RED_PORTAL : BLUE_PORTAL;
					PORTAL_TILE = Team == 2 ? RED_PORTAL_TILE: BLUE_PORTAL_TILE;
					Join_Team(portal);
				} else if (JOIN_LAST_WIN) {
					String s = getTeam(LAST_WIN);
					if (s.equals("null")) {
						PORTAL_TILE = RANDOM_TEAM_PORTAL;
						Join_Team(RANDOM_PORTAL);
					} else {
						int portal = s.equals("red") ? RED_PORTAL : BLUE_PORTAL;
						PORTAL_TILE = s.equals("red") ? RED_PORTAL_TILE
								: BLUE_PORTAL_TILE;
						;
						
						Join_Team(portal);
					}
				} else if (JOIN_LAST_LOSS) {
					String s = getTeam(LAST_WIN);
					if (s.equals("null")) {
						PORTAL_TILE = RANDOM_TEAM_PORTAL;
						Join_Team(RANDOM_PORTAL);
					} else {
						int portal = s.equals("red") ? BLUE_PORTAL : RED_PORTAL;
						PORTAL_TILE = s.equals("red") ? BLUE_PORTAL_TILE: RED_PORTAL_TILE;;
						Join_Team(portal);
					}
				}else if (JOIN_RED) {
					PORTAL_TILE = RED_PORTAL_TILE;
					Join_Team(RED_PORTAL);
				}else if (JOIN_BLUE) {
					PORTAL_TILE = BLUE_PORTAL_TILE;
					Join_Team(BLUE_PORTAL);
				}
			} else {
				CURRENTTEAM = -1;
				LAST_WIN = -1;
				WEST_GRAVEYARD = -1;
				EAST_GRAVEYARD = -1;
				int a = random(0, 9);
				switch (a) {
				case 1:
					sleep(200, 400);
					break;
				case 2:
					camera.setAngle(random(0, 360));
					break;
				case 3:
					walkaway(random(2, 3));
					break;
				case 4:
					int e = random(0, 2);
					if (e == 2) {
						int x = random(0, ANTI_BAN_WAITING.length);
						keyboard.sendText(ANTI_BAN_WAITING[x], true);
					}
				case 5:
					sleep(200, 400);
					break;
				case 6:
					sleep(200, 800);
					break;
				case 7:
					sleep(200, 600);
					break;
				case 8:
					sleep(200, 900);
					break;
				case 9:
					sleep(200, 500);
					break;
				}
				int r = random(30, 90);
				for (int x = 0; x < r && !InGame(); x++, sleep(100, 150))
					;
			}
		} else {
			CURRENT_TEAM = getCurrentTeam();
			CURRENTTEAM = CURRENT_TEAM.equals("blue") ? 1 : 2;
			if (InGrave()) {
				if (isDead()) {
					STATUS = "Waiting to Revive...";
					sleep(600,900);
				} else {
					Leave_Grave(PORTAL_GRAVE);
				}
			} else {
				if(CURRETNTASK == null)
					return 50;
				switch (CURRETNTASK) {
				case ATTACK_PYRE:
					AttackPyres();
					break;
				case ATTACK_JELLIS:
					AttackJellis();
					break;
				case ATTACK_EVERYWHERE:
					Attack_Player();
					break;
				case ATTACK_OBELISK:
					Attack_Obelisk();
					break;
				case ATTACK_AVATER:
					Attack_Avater();
					break;
				case PICK_BONES:
					PICKANDBURY();
					break;
				}
			}

		}
		return 50;
	}

	private void sleepwhilemoving(int e){
		int x= e * 10;
		for(int i = 0 ; i < x && getMyPlayer().isMoving() ; i++ , sleep(100,150));
	}
	private int getHighestTeam() {
		int red = getCountTeam(LOCATIONS.RED_WAITING.getArea());
		int blue =   getCountTeam(LOCATIONS.BLUE_WAITING.getArea());
		int team = blue > red ? 1 : 2;
		return team;
	}
	private int getCountTeam(RSArea d){
		int x = 0;
		for(RSPlayer r : players.getAll()){
			if(r !=null && d.contains(r.getLocation())){
				x = x + r.getLevel();
			}
		}
		return x;
	}
	private void Summon() {
		for(int d = 0 ; d < 10 ; d++){
			if(isFamiliarSummoned()){
				break;
			}else{
			if (ENABLE_SUMMOING && !isFamiliarSummoned()) {
				int pouch = familiar.getPouchID();
				if (inventory.contains(pouch)) {
                     inventory.getItem(pouch).doAction("Summon");
                     SleepTillStop(3);
				}else if(WITHDRAWPOUCH){
					RSObject chest = objects.getNearest(Bank.BANK_CHESTS);
					if(chest !=null){
						if(chest.isOnScreen()){
							if(bank.isOpen()){
								bank.withdraw(pouch, 5);
								for(int x = 0 ; x < 5 && bank.isOpen();x++){
									bank.close();
								}
							}else{
								bank.open();
								for(int x = 0 ; x < 15 && !bank.isOpen();x++ ,sleep(100,150));
							}
						}else{
							walking.walkTileMM(BANKCHEST_TILE);
							turnToTile(BANKCHEST_TILE);
							SleepTillStop(2);
							sleepwhilemoving(1);
						}
					}else{
						walking.walkTileMM(BANKCHEST_TILE);
						turnToTile(BANKCHEST_TILE);
						SleepTillStop(2);
						sleepwhilemoving(1);
					}
				}
			}
		}
		}
	}



	private boolean isFamiliarSummoned() {
		return settings.getSetting(1786) != 0;
	}

	private void walkaway(int random) {
		RSTile p = getMyPlayer().getLocation();
		RSArea r = LOCATIONS.RED_WAITING.getArea();
		RSArea b = LOCATIONS.BLUE_WAITING.getArea();
		RSArea curr = r.contains(p) ? r : b;
		RSTile t = getTile(curr, random);
		if (!getMyPlayer().getLocation().equals(t)) {
			walking.walkTileOnScreen(t);
			sleepwhilemoving(1);
		}
	}

	private RSTile getTile(RSArea curr, int random) {
		for (RSTile e : curr.getTileArray()) {
			int d = calc.distanceTo(e);
			if (e != null && d == random && curr.contains(e)) {
				return e;
			}
		}
		return getMyPlayer().getLocation();
	}

	private void CastSpells() {
		STATUS = "Casting Combat Spells...";
		if(getMyPlayer().getAdrenalinePercent() == 100){
			if(abilities.isEnabled(ActionSlot.SLOT_6)){
				keyboard.sendText(abilities.getKeyBind(ActionSlot.SLOT_6) +  "", false);
				sleep(500,900);
			}
		}else{
			if(!t.isRunning()) {
				int hotkey = getHotKey(hotkeys);
				ActionSlot[] BASIC_ABILTY = { ActionSlot.SLOT_1,ActionSlot.SLOT_2, ActionSlot.SLOT_3,ActionSlot.SLOT_4, ActionSlot.SLOT_5, ActionSlot.SLOT_6 };
				if(abilities.isEnabled(BASIC_ABILTY[hotkey - 1])){
				keyboard.sendText(abilities.getKeyBind(BASIC_ABILTY[hotkey - 1]) + "",false);
				hotkeys.add(hotkey);
				t = new Timer(random(1500, 2500));
				}
			}
		}
	}

	private int getHotKey(ArrayList<Integer> hotkeys2) {
		if (!hotkeys2.contains(1)) {
			return 1;
		}else if (!hotkeys2.contains(2)){
			return 2;
		}else if (!hotkeys2.contains(3)){
			return 3;
		}else if (!hotkeys2.contains(4)){
			return 4;
		}else if (!hotkeys2.contains(5)){
			return 5;
		}
		hotkeys2.clear();
		return 1;
	}
	private void PICKANDBURY() {
		if (inventory.isFull()) {
			RSArea a = LOCATIONS.WEST_GRAVE_OUT.getArea();
			RSArea b = LOCATIONS.EAST_GRAVE_OUT.getArea();
			RSTile p = getMyPlayer().getLocation();
			if (a.contains(p) || b.contains(p)) {
				for (RSItem e : inventory.getItems()) {
					if(e.getName() !=null && (e.getName().toLowerCase().contains("bolt") || e.getName().toLowerCase().contains("arrow"))){
						e.doAction("Drop");
					}
					if (e != null && e.getID() == BONES_ID) {
						e.doAction("Bury");
						sleep(200, 500);
					}
				}
			} else {
				WalkTo(NearstGraveTile());
			}
		}
		if (!inventory.isFull()) {
			RSGroundItem bone = groundItems.getNearest(BONES_ID);
			if (bone != null) {
				if (bone.isOnScreen()
						&& calc.distanceTo(bone.getLocation()) < 8) {
					bone.doAction("Take", "Bones");
					sleep(200, 300);
					if (getMyPlayer().isMoving()) {
						SleepTillStop(1);
					}
				} else {
					walking.walkTileMM(walking.getClosestTileOnMap(bone
							.getLocation()));
					SleepTillStop(3);
					sleepwhilemoving(2);
				}
			} else {
				WalkTo(LOCATIONS.OBELISK.getArea().getCentralTile());
			}
		}
	}

	private RSTile NearstGraveTile() {
		if (graveIsOwned(WEST_GRAVEYARD) && graveIsOwned(EAST_GRAVEYARD)) {
			return NearstTile(LOCATIONS.WEST_GRAVE_OUT.getArea(),
					LOCATIONS.EAST_GRAVE_OUT.getArea());
		} else if (graveIsOwned(WEST_GRAVEYARD)) {
			return LOCATIONS.WEST_GRAVE_OUT.getArea().getCentralTile();
		} else if (graveIsOwned(EAST_GRAVEYARD)) {
			return LOCATIONS.EAST_GRAVE_OUT.getArea().getCentralTile();
		} else {
			return NearstTile(LOCATIONS.WEST_GRAVE_OUT.getArea(),
					LOCATIONS.EAST_GRAVE_OUT.getArea());
		}
	}

	private boolean graveIsOwned(int grave) {
		return getMyPlayer().getTeam() == grave;
	}

	private void getBandage() {
		LOCATIONS SUPPLY_LOC = CURRENTTEAM == 1 ? LOCATIONS.BLUE_SUPPLIES
				: LOCATIONS.RED_SUPPLIES;
		if (!SUPPLY_LOC.getArea().contains(getMyPlayer().getLocation())) {
			WalkTo(SUPPLY_LOC.getArea().getCentralTile());
		} else {
			RSObject Table = objects.getNearest(BANADAGE_TABLE);
			if (Table != null) {
				if (Table.isOnScreen() && calc.distanceTo(Table) < 4) {
					for (int e = 0; e < (BANDAGE_VALVE * 1.5); e++) {
						Table.doClick(true);
						sleep(200, 500);
					}
				} else {
					walking.walkTileMM(walking.getClosestTileOnMap(Table
							.getLocation()));
					SleepTillStop(2);
				}
			}
		}
	}

	private void Attack_Avater() {
		int[] avater_id = CURRENTTEAM == 1 ? REDAVATER_ID : BLUE_AVATER_ID;
		LOCATIONS AVATER_LOC = CURRENTTEAM == 1 ? LOCATIONS.RED_AVATAR
				: LOCATIONS.BLUE_AVATAR;
		RSNPC Avater = npcs.getNearest(avater_id);
		if (!AVATER_LOC.getArea().contains(getMyPlayer().getLocation())) {
			WalkTo(AVATER_LOC.getArea().getCentralTile());
		} else {
			if (Avater != null) {
				if (Avater.isOnScreen() && calc.distanceTo(Avater) < 6) {
					if (getMyPlayer().getInteracting() == null) {
						Avater.doAction("Attack", Avater.getName());
						SleepTillStop(4);
					}
				} else {
					walking.walkTileMM(walking.getClosestTileOnMap(Avater
							.getLocation()));
					SleepTillStop(3);
					for (int x = 0; x < 20 && getMyPlayer().isMoving(); x++, sleep(
							100, 150))
						;
				}
			}
		}
	}

	private void AttackPyres() {
		RSNPC PYRES = npcs.getNearest(PYRES_ID);
		if (PYRES == null) {
			WalkTo(NearstTile(LOCATIONS.SOUTHEAST_PYRES.getArea(),
					LOCATIONS.NORTHWEST_PRYES.getArea()));
		}
		if (PYRES != null) {
			if (PYRES.isOnScreen()) {
				STATUS = "Attacking Pyres";
				if (getMyPlayer().getInteracting() == null) {
					PYRES.doAction("Attack", "Pyrefiend");
					sleep(600, 900);
				} else {
					CastSpells();
				}
			} else {
				walking.walkTileMM(PYRES.getLocation());
				turnToTile(PYRES.getLocation());
				sleep(1000, 1500);
			}
		}
	}

	private void AttackJellis() {
		RSNPC JELLIES = npcs.getNearest(JELLIES_ID);
		if (JELLIES == null) {
			WalkTo(NearstTile(LOCATIONS.SOUTH_JELLIES.getArea(),
					LOCATIONS.NORTH_JELLIES.getArea()));
		}
		if (JELLIES != null) {
			if (JELLIES.isOnScreen()) {
				STATUS = "Attacking Jellis";
				if (getMyPlayer().getInteracting() == null) {
					JELLIES.doAction("Attack");
					sleep(600, 900);
				} else {
					CastSpells();
				}
			} else {
				walking.walkTileMM(JELLIES.getLocation());
				turnToTile(JELLIES.getLocation());
				sleep(1000, 1500);
			}
		}
	}

	private void Attack_Obelisk() {
		if (!LOCATIONS.OBELISK.getArea().contains(getMyPlayer().getLocation())) {
			WalkTo(LOCATIONS.OBELISK.getArea().getCentralTile());
		} else {
			Attack_Player();
		}
	}

	private void Attack_Player() {
		RSPlayer Target = getTargetPlayer();
		if (Target != null) {
			if (Target.isOnScreen()) {
				if (getMyPlayer().getInteracting() == null) {
					Target.doAction("Attack", Target.getName());
					SleepTillStop(5);
				} else {
					CastSpells();
				}
			} else {
				if (calc.distanceTo(Target) < 7) {
					turnToCharacter(Target);
				} else {
					walking.walkTileMM(walking.getClosestTileOnMap(Target.getLocation()));
					turnToTile(Target.getLocation());
					SleepTillStop(2);
				}
			}
		}
	}
	private void turnToTile(RSTile target) {
		int b = random(0, 3);
		if(b < 2)
			camera.setPitch(random(20, 90));
		camera.turnToTile(target);
	}
	private void turnToCharacter(RSPlayer target) {
		int b = random(0, 3);
		if(b < 2)
			camera.setPitch(random(20, 90));
		camera.turnToCharacter(target);
	}
	private void turnToObject(RSObject target) {
		int b = random(0, 3);
		if(b < 2)
			camera.setPitch(random(20, 90));
		camera.turnToObject(target);
	}

	private RSPlayer getTargetPlayer() {
		for (RSPlayer e : players.getAll()) {
			if (e != null && getMyPlayer().getTeam() != e.getTeam()
					&& LOCATIONS.OBELISK.getArea().contains(e.getLocation())) {
				return e;
			}
		}
		return null;
	}

	private void Leave_Grave(int[] portalGrave) {
		RSObject portal = objects.getNearest(portalGrave);
		if (portal != null) {
			if (portal.isOnScreen()) {
				portal.doAction("Pass");
				SleepTillStop(3);

			} else {
				walking.walkTileMM(portal.getLocation());
				turnToObject(portal);
				
			}
		}

	}

	private void WalkTo(RSTile t1) {
		STATUS = "Walking...";
		RSTile[] path = walking.findPath(t1);
		RSTile tempTile = t1;
		if (!game.isLoggedIn())
			return;
		if (!walking.isRunEnabled() && walking.getEnergy() > 40) {
			walking.setRun(true);
		}
		tempTile = divideTile(tempTile);
		path = walking.findPath(tempTile);
		if (path != null) {
			walkPath(path);
			SleepTillStop(random(3,4));
			sleepwhilemoving(2);
		}
		
	}
	private void walkPath(RSTile[] path) {
		walking.walkPathMM(path);
/*		RSTile vis = null;
		for (RSTile t : path) {
			if (calc.tileOnMap(t))
				vis = t;
		}
		if (vis != null) {
			walking.walkTo(vis);
			return true;
		}
		return false;*/
    }

	private RSTile divideTile(RSTile tile) {
		RSTile loc = getMyPlayer().getLocation();
		return new RSTile((loc.getX() + 4 * tile.getX()) / 5,
				(loc.getY() + 4 * tile.getY()) / 5);
	}
	private RSTile NearstTile(RSArea e, RSArea a) {
		int d = calc.distanceTo(e.getCentralTile());
		int d1 = calc.distanceTo(a.getCentralTile());
		RSTile x = d < d1 ? e.getCentralTile() : a.getCentralTile();
		return x;
	}

	private String getCurrentTeam() {
		int team = getMyPlayer().getTeam();
		return team == 1 ? "blue" : "red";
	}

	private String getTeam(int team) {
		if (team == -1) {
			return "null";
		}
		return team == 1 ? "blue" : "red";
	}

	private void Join_Team(int Portal) {
		STATUS = "Joining Team...";
		loghandler.Print("Joining " + getTeam(Portal) + " Team",Color.GREEN,false);
		RSObject portal = objects.getNearest(Portal);
		if (portal != null) {
			if (calc.distanceTo(portal) < 4) {

				if (CHAT_CONTUINE.isValid()) {
					CHAT_CONTUINE.doClick(true);
					SleepTillStop(1);
				} else if (CHAT_OPITION_ONE.isValid()) {
					CHAT_OPITION_ONE.doClick(true);
					SleepTillStop(1);
				} else {
					String Action = Portal == RANDOM_PORTAL ? "Join-team"
							: "Pass";
					for (int x = 0; x < 30 && getMyPlayer().isMoving(); x++, sleep(
							100, 150))
						;
					portal.doAction(Action);
					SleepTillStop(2);
				}

			} else {
				WalkTo(PORTAL_TILE);
				SleepTillStop(2);
			}
		} else {
			WalkTo(PORTAL_TILE);
			SleepTillStop(2);
		}
	}

	private void SleepTillStop(int e) {
		int x = 600 * 3;
		int y = 900 * 3;
		sleep(x, y);
	}

	private boolean InGrave() {
		RSTile pt = getMyPlayer().getLocation();
		return LOCATIONS.BLUE_START.getArea().contains(pt)
				|| LOCATIONS.RED_START.getArea().contains(pt)
				|| LOCATIONS.EAST_GRAVE.getArea().contains(pt)
				|| LOCATIONS.WEST_GRAVE.getArea().contains(pt)
				|| LOCATIONS.RED_SPAWN.getArea().contains(pt)
				|| LOCATIONS.BLUE_SPWAN.getArea().contains(pt);

	}

	private boolean InTeam() {
		return LOCATIONS.BLUE_WAITING.getArea().contains(
				getMyPlayer().getLocation())
				|| LOCATIONS.RED_WAITING.getArea().contains(
						getMyPlayer().getLocation());
	}

	private boolean InGame() {
		return !LOCATIONS.OUTSIDE.getArea().contains(getMyPlayer().getLocation());
	}
	private boolean isDead(){
		return getMyPlayer().getPassiveAnimation() == 5530;
	}
	private String Filter(String d){
		String[] s = d.split(" ");
		String x = d.toLowerCase().contains("blue") ? "blue" : "red";
		String e = d.replace(s[1], x);
		return e;
	}
    private enum Familiar {
        SPIRITWOLF("Spirit wolf", 12047, 12425),
        DREADFOWL("Dreadfowl", 12043,12445),
        MEERKAT("Meerkat", 19622, 19621),
        SPIRITSPIDER("Spirit spider", 12059, 12428),
        THORNYSNAIL("Thorny snail",12019, 12459),
        GRANITECRAB("Granite crab", 12009, 12533),
        SPIRITMOSQUITO("Spirit mosquito", 12778, 12838),
        DESERTWYRM("Desert wyrm",12049, 12460),
        SPIRITSCORPION("Spirit scorpion", 12055, 12432),
        SPIRITTZKIH("Spirit tz-kih", 12808, 12839),
        ALBINORAT("Albino rat", 12067,12430),
        SPIRITKALPHITE("Spirit kalphite", 12063, 12446),
        COMPSTMOUND("Compost mound", 12091, 12440),
        GIANTCHINCHOMPA("Giant chinchompa", 12800, 12834),
        VAMPIREBAT("Vampire bat", 12053, 12447),
        HONEYBADGER("Honey badger", 12065, 12433),
        BEAVER("Beaver", 12021, 12429),
        VOIDRAVAGER("Void ravager", 12818,12443),
        VOIDSPINNER("Void spinner", 12780, 12443),
        VOIDTOUCHER("Void torcher", 12798, 12443),
        VOIDSHIFTER("Void shifter",12814, 12443),
        BULLANT("Bull ant", 12087, 12431),
        MACAW("Macaw", 12071, 12422),
        EVILTURNIP("Evil turnip", 12051, 12448),
        SPCOCKATRICE("Sp. cockatrice", 12095, 12458),
        SPGUTHATRICE("Sp. guthatrice",12097, 12458),
        SPSARATRICE("Sp. saratrice", 12099, 12458),
        SPZAMATRICE("Sp. zamatrice", 12101, 12458),
        SPPENGATRICE("Sp. pengatrice",12103, 12458),
        SPCORAXTRICE("Sp. coraxatrice", 12105, 12458),
        SPVULATRICE("Sp. vulatrice", 12107, 12458),
        PYRELORD("Pyrelord", 12816,12829),
        MAGPIE("Magpie", 12041, 12426),
        BLOATEDLEECH("Bloated leech", 12061, 12444),
        SPIRITTERRORBIRD("Spirit terrorbird", 12007, 12441),
        ABYSSALPARASITE( "Abyssal parasite", 12035, 12454),
        SPIRITJELLY("Spirit jelly", 12027, 12453),
        IBIS("Ibis", 12531, 12424),
        SPIRITKYATT("Spirit kyatt", 12812, 12836),
        SPIRITLARUPIA("Spirit larupia", 12784, 12840),
        SPIRITGRAAK("Spirit graahk", 12810, 12835),
        KARAMOVERLOAD( "Karam. overlord", 12023, 12455),
        SMOKEDEVIL("Smoke devil",12085, 12468),
        ABYSSALLURKER("Abyssal lurker", 12037, 12427),
        SPIRITCOBRA("Spirit cobra", 12015, 12436),
        STRANGERPLANT("Stranger plant",12045, 12467),
        BARKERTOAD("Barker toad", 12123, 12452),
        WARTORTOISE("War tortoise", 12031, 12439),
        BUNYIP("Bunyip", 12029, 12438),
        FRUITBAT("Fruit bat", 12033, 12423),
        RAVENOUSLOCUST("Ravenous locust", 12820, 12830),
        ARCTICBEAR("Arctic bear", 12057, 12451),
        PHOENIX( "Phoenix", 14623, 14622),
        OBSIDIANGOLEM("Obsidian golem",12792, 12826),
        GRANITELOBSTER("Granite lobster", 12069, 12449),
        PRAYINGMANTIS( "Praying mantis", 12011, 12450),
        FORGEREGENT("Forge regent", 12782, 12841),
        TALONBEAST("Talon beast", 12794, 12831),
        GIANTENT( "Giant ent", 12013, 12457),
        FIRETITAN("Fire titan", 12802,12824),
        MOSSTITAN("Moss titan", 12804, 12824),
        ICETITAN("Ice titan", 12806, 12824),
        HYDRA("Hydra", 12025, 12442),
        SPIRITDAGANNOTH("Spirit dagannoth", 12017, 12456),
        LAVATITAN("Lava titan",12788, 12837),
        SWAMPTITAN("Swamp titan", 12776, 12832),
        BRONZEMINOTAUR("Bronze minotaur", 12073, 12461),
        IRONMINOTOUR("Iron minotaur",12075, 12462),
        STEELMINOTOUR("Steel minotaur", 12077, 12463),
        MITHRILMINOTAUR("Mithril minotaur", 12079, 12464),
        ADAMANTMINOTAUR("Adamant minotaur", 12081, 12465),
        RUNEMINOTAUR("Rune minotaur", 12083, 12466),
        UNICORNSTALLION("Unicorn stallion", 12039, 12434),
        GEYSERTITAN("Geyser titan",12786, 12833),
        WOLPERTINGER("Wolpertinger", 12089, 12437),
        ABYSSALTITAN("Abyssal titan", 12796, 12827),
        IRONTITAN("Iron titan", 12822,12828),
        PACKYAK("Pack yak", 12093, 12435),
        STEELTITAN("Steel titan", 12790, 12825);
        private final String name;
        private final int pouchID;
 
        Familiar(String name, int pouchID, int scrollID) {
            this.name = name;
            this.pouchID = pouchID;
        }
 
        String getName() {
            return this.name;
        }
 
        int getPouchID() {
            return this.pouchID;
        }
    }
	private enum LOCATIONS {
		   OUTSIDE( new RSArea(new RSTile(1918, 3187), new RSTile(1862, 3149)),"Outside Area"),
		RED_SPAWN(new RSArea(new RSTile(1951, 3234), new RSTile(1958, 3244)),
				"Inside red spawn"), BLUE_SPWAN(new RSArea(new RSTile(1816,
				3220), new RSTile(1823, 3230)), "Inside blue spawn"), EAST_GRAVE(
				new RSArea(new RSTile(1932, 3244), new RSTile(1934, 3246)),
				"Inside east grave"), WEST_GRAVE(new RSArea(new RSTile(1841,
				3217), new RSTile(1843, 3219)), "Inside west grave"), EAST_GRAVE_OUT(
				new RSArea(new RSTile(1937, 3250), new RSTile(1927, 3242)),
				"Outside east grave"), WEST_GRAVE_OUT(new RSArea(new RSTile(
				1847, 3223), new RSTile(1837, 3214)), "Outside west grave"), OBELISK(
				new RSArea(new RSTile[] { new RSTile(1869, 3245),
						new RSTile(1902, 3245), new RSTile(1904, 3221),
						new RSTile(1867, 3221) }), "Osblick"), NORTH_JELLIES(
				new RSArea(new RSTile(1906, 3263), new RSTile(1869, 3248)),
				"North jellies"), SOUTH_JELLIES(new RSArea(new RSTile(1899,
				3214), new RSTile(1873, 3199)), "South jellies"), SOUTHEAST_PYRES(
				new RSArea(new RSTile(1938, 3222), new RSTile(1917, 3204)),
				"South-east pryes"), NORTHWEST_PRYES(new RSArea(new RSTile(
				1856, 3258), new RSTile(1834, 3238)), "North-west pryes"), RED_START(
				new RSArea(new RSTile[] { new RSTile(1950, 3233),
						new RSTile(1959, 3233), new RSTile(1959, 3245),
						new RSTile(1950, 3245) }), "Red Start"), BLUE_START(
				new RSArea(new RSTile[] { new RSTile(1816, 3231),
						new RSTile(1824, 3231), new RSTile(1825, 3219),
						new RSTile(1816, 3219) }), "Blue Start"), BLUE_WAITING(
				new RSArea(new RSTile[] { new RSTile(1879, 3167),
						new RSTile(1869, 3167), new RSTile(1869, 3157),
						new RSTile(1880, 3157) }), "Blue Waiting Area"), RED_WAITING(
				new RSArea(new RSTile(1909, 3167), new RSTile(1899, 3156)),
				"Red waiting"), RED_SUPPLIES(new RSArea(new RSTile(1977, 3213),
				new RSTile(1961, 3203)), "Red supplies"), BLUE_SUPPLIES(
				new RSArea(new RSTile(1812, 3261), new RSTile(1795, 3250)),
				"Blue Supplies"), RED_AVATAR(new RSArea(new RSTile(1976, 3261),
				new RSTile(1959, 3244)), "Red avatar"), BLUE_AVATAR(new RSArea(
				new RSTile(1816, 3220), new RSTile(1798, 3202)), "Blue avatar");
		private RSArea area;
		private String name;

		LOCATIONS(final RSArea area, final String name) {
			this.area = area;
			this.name = name;
		}

		public RSArea getArea() {
			return this.area;
		}

		public String getName() {
			return this.name;
		}

	}

	private int getHour(int item) {
		return (int) ((item) * 3600000D / (System.currentTimeMillis() - STARTIME));
	}
	private int getHour(int item,boolean b) {
		double x =  ((item) * 3600000D / (System.currentTimeMillis() - STARTIME));
		return (int) Math.round(x);
	}
 public class MousePaint {
         
         public int waveSize = 0;
        
         @SuppressWarnings({"serial", "unused"})
         public class MousePathPoint extends Point {

                 private long finishTime;
                 private double lastingTime;

                 public MousePathPoint(int x, int y, int lastingTime) {
                         super(x, y);
                         this.lastingTime = lastingTime;
                         finishTime = System.currentTimeMillis() + lastingTime;
                 }

                 public boolean isUp() {
                         return System.currentTimeMillis() > finishTime;
                 }
                
         }

         public double getRot(int ticks){
             return (System.currentTimeMillis() % (360 * ticks)) / ticks;
         }
        
         public LinkedList<MousePathPoint> MousePath = new LinkedList<MousePathPoint>();
        
         public void drawTrail(Graphics g1) {
                 Graphics2D g = (Graphics2D) g1;
                 g.setStroke(new BasicStroke(1));
                 while (!MousePath.isEmpty() && MousePath.peek().isUp()) {
                         MousePath.remove();
                 }
                 Point clientCursor = mouse.getLocation();
                 MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y, 250);
                 if (MousePath.isEmpty() || !MousePath.getLast().equals(mpp)) {
                         MousePath.add(mpp);
                 }
                 MousePathPoint lastPoint = null;
                 for (MousePathPoint a : MousePath) {
                         if (lastPoint != null) {
                                 long mpt = System.currentTimeMillis() - mouse.getPressTime();
                                 if (mouse.getPressTime() == -1 || mpt >= 250) {
                                         g.setColor(Color.MAGENTA);
                                 }
                                 if (mpt < 250) {
                                 g.setColor(Color.RED);
                                 }
                                 g.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
                         }
                         lastPoint = a;
                 }
         }
                
         public void drawMouse(Graphics g1) {
                 Graphics2D g = (Graphics2D) g1;
                 g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
                 g.setStroke(new BasicStroke(3));
                 g.setColor(Color.BLACK);
             g.drawOval(mouse.getLocation().x - 8, mouse.getLocation().y - 8, 15, 15);
             g.setStroke(new BasicStroke(1));
             g.setColor(new Color(0, 0, 0, 114));
             g.fillOval(mouse.getLocation().x - 8, mouse.getLocation().y - 8, 15, 15);
                 Point MouseLoc = mouse.getLocation();
             long mpt = System.currentTimeMillis() - mouse.getPressTime();
             g.rotate(Math.toRadians(getRot(5)), mouse.getLocation().x, mouse.getLocation().y);
             if (mouse.getPressTime() == -1 || mpt >= 250) {
                 g.setColor(Color.MAGENTA);
                 g.drawLine(MouseLoc.x - 5, MouseLoc.y, MouseLoc.x + 5, MouseLoc.y);
                         g.drawLine(MouseLoc.x, MouseLoc.y - 5, MouseLoc.x, MouseLoc.y + 5);
             }
             if (mpt < 250) {
                 g.setColor(Color.RED);
                 g.drawLine(MouseLoc.x - 5, MouseLoc.y, MouseLoc.x + 5, MouseLoc.y);
                         g.drawLine(MouseLoc.x, MouseLoc.y - 5, MouseLoc.x, MouseLoc.y + 5);
             }
         }             
        
         public void draw(Graphics g1) {
                  Graphics2D g = (Graphics2D) g1;
                  g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
                  drawTrail(g);
                  drawMouse(g);
         }
 }

 
 //START: Code generated using Enfilade's Easel
 private Image getImage(String url) {
     try {
         return ImageIO.read(new URL(url));
     } catch(IOException e) {
         return null;
     }
 }

 private final Color color1 = new Color(0, 0, 0);
 private final Color color2 = new Color(204, 0, 0);

 private final BasicStroke stroke1 = new BasicStroke(1);

 private final Font font1 = new Font("Verdana", 1, 11);
 private final Font font2 = new Font("Verdana", 1, 14);

 private final Image img1 = getImage("https://dl.dropbox.com/u/129995508/EpicbotScripts/Pro%20SoulWar/Paint/Soulwar.png");

 public void onRepaint(Graphics g1) {
     Graphics2D g = (Graphics2D)g1;
    if(SHOWPAINT){
     g.drawImage(img1, 1, 389, null);
     g.setFont(font1);
     g.setColor(color1);
     g.drawString("TimeRunning:"+ Timer.format(System.currentTimeMillis() - STARTIME), 16, 445);
     g.drawString("ZealGained:" + ZEAL + " (" + getHour(ZEAL) + ")", 18, 468);
     g.drawString("Comat XP:", 19, 490);
     g.drawString("Cons XP:", 19, 509);
     g.drawString("Game Played:" + GAMEPLAYED + " (" + getHour(GAMEPLAYED,false) +")", 206, 442);
     g.drawString("Total Win:" + WIN, 206, 464);
     g.drawString("Total Loss:" + (GAMEPLAYED - WIN), 207, 488);
     g.fillRect(15, 404, 24, 20);
     g.setStroke(stroke1);
     g.drawRect(15, 404, 24, 20);
     g.drawString("Status:" + STATUS, 209, 509);
     g.setFont(font2);
     g.setColor(color2);
     g.drawString("X", 22, 422);
	 loghandler.Draw(g1);
	} else {
	  g.setColor(Color.black);
	  g.fillRect(15, 404, 24, 20);
	  g.setStroke(stroke1);
	  g.drawRect(15, 404, 24, 20);
	  g.setFont(font2);
	  g.setColor(Color.green);
	  g.drawString("O", 22, 422);
	}
		mousePaint.draw(g1);
	}
 //END: Code generated using Enfilade's Easel

	@Override
	public void messageReceived(MessageEvent e) {
		String message = e.getMessage().toLowerCase();
		if (message.contains("you receive 1 zeal")) {
			LAST_WIN = CURRENT_TEAM.equals("blue") ? 2 : 1;
			ZEAL++;
			GAMEPLAYED++;
			MESSAGE_LOG.clear();
		} else if (message.contains("you receive 2 zeal")) {
			LAST_WIN = CURRENTTEAM;
			ZEAL += 2;
			GAMEPLAYED++;
			WIN++;
			MESSAGE_LOG.clear();
		} else if (message.contains("you receive 3 zeal")) {
			LAST_WIN = CURRENTTEAM;
			ZEAL += 3;
			GAMEPLAYED++;
			WIN++;
			MESSAGE_LOG.clear();
		}
		if (message.contains("eastern")) {
			if (message.contains("taken")) {
				if (message.contains("red")) {
					EAST_GRAVEYARD = 2;
				} else if (message.contains("blue")) {
					EAST_GRAVEYARD = 1;
				}
			} else if (message.contains("lost")) {
				EAST_GRAVEYARD = 0;
			}
			loghandler.Print(Filter(message),Color.red);
		} else if (message.contains("western")) {
			if (message.contains("taken")) {
				if (message.contains("red")) {
					WEST_GRAVEYARD = 2;
				} else if (message.contains("blue")) {
					WEST_GRAVEYARD = 1;
				}
			} else if (message.contains("lost")) {
				WEST_GRAVEYARD = 0;
			}
			loghandler.Print(Filter(message),Color.red);
		}

	}
public class SWGUI extends JFrame {
	public SWGUI() {
		initComponents();
	}
	private void button1ActionPerformed(ActionEvent e) {
		familiar = getFamiliar(comboBox3.getSelectedItem().toString());
		ENABLE_SUMMOING = checkBox1.isSelected();
        WITHDRAWPOUCH = checkBox2.isSelected();
		String chosenTeam = comboBox1.getSelectedItem().toString();
		JOIN_LAST_WIN = chosenTeam.equals("Last Won");
		JOIN_LAST_LOSS = chosenTeam.equals("Last Lost");
		JOIN_RANDOM_TEAM = chosenTeam.equals("Random Team");
		JOIN_RED = chosenTeam.equals("Red");
		JOIN_BLUE = chosenTeam.equals("Blue");
		JOIN_HIGHESTLEVEL = chosenTeam.equals("The higest total level");
		String choesnTask = comboBox2.getSelectedItem().toString();
		if(choesnTask.equals("Attack_Pyre")){
			CURRETNTASK = TASK.ATTACK_PYRE;
		}else if(choesnTask.equals("Attack_Jellis")){
			CURRETNTASK = TASK.ATTACK_JELLIS;
		}else if(choesnTask.equals("Attack_Player(in obslick)")){
			CURRETNTASK = TASK.ATTACK_OBELISK;
		}else if(choesnTask.equals("Pick'nd bury")){
			CURRETNTASK = TASK.PICK_BONES;
		}
		log("You have Choosen " + chosenTeam + " As Join Mode");
		log("You have Choosen " + choesnTask + " As Task");
		if (ENABLE_SUMMOING) {
			log("You have Enabled Summoning");
			log("You have Chosesn " + familiar.getName() +  " As Familiar");
		}
			if (WITHDRAWPOUCH) {
				log("You have Enabled Withdraw Pouch from bank");
			}
		setVisible(false);
		dispose();
	}

	private Familiar getFamiliar(String string) {
		for(Familiar f : Familiar.values()){
			if(f.getName().equals(string)){
				return f;
			}
		}
		return null;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Mega Team
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		label2 = new JLabel();
		checkBox1 = new JCheckBox();
		checkBox2 = new JCheckBox();
		label3 = new JLabel();
		comboBox2 = new JComboBox();
		button1 = new JButton();
		label4 = new JLabel();
		comboBox3 = new JComboBox();

		//======== this ========
		setTitle("Pro Soulwar");
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Join Team Options:");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(label1);
		label1.setBounds(10, 120, 125, 20);

		//---- comboBox1 ----
		comboBox1.setModel(new DefaultComboBoxModel(new String[] {
			"Last Won",
			"Last Lost",
			"Random Team",
			"Red",
			"Blue",
			"The higest total level"
		}));
		contentPane.add(comboBox1);
		comboBox1.setBounds(135, 120, 145, 20);

		//---- label2 ----
		label2.setText("Summoing Options");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(label2);
		label2.setBounds(5, 5, 140, label2.getPreferredSize().height);

		//---- checkBox1 ----
		checkBox1.setText("Activate");
		contentPane.add(checkBox1);
		checkBox1.setBounds(135, 0, 100, checkBox1.getPreferredSize().height);

		//---- checkBox2 ----
		checkBox2.setText("Withdraw Pouch");
		contentPane.add(checkBox2);
		checkBox2.setBounds(5, 55, 140, checkBox2.getPreferredSize().height);

		//---- label3 ----
		label3.setText("Task To do:");
		label3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(label3);
		label3.setBounds(10, 85, 120, label3.getPreferredSize().height);

		//---- comboBox2 ----
		comboBox2.setModel(new DefaultComboBoxModel(new String[] {
			"Attack_Pyre",
			"Attack_Jellis",
			"Attack_Player(in obslick)",
			"Pick'nd bury"
		}));
		contentPane.add(comboBox2);
		comboBox2.setBounds(135, 85, 145, comboBox2.getPreferredSize().height);

		//---- button1 ----
		button1.setText("Start");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1ActionPerformed(e);
			}
		});
		contentPane.add(button1);
		button1.setBounds(110, 170, 75, button1.getPreferredSize().height);

		//---- label4 ----
		label4.setText("Chosen Familiar:");
		label4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(label4);
		label4.setBounds(5, 30, 120, 20);

		//---- comboBox3 ----
		comboBox3.setModel(new DefaultComboBoxModel(new String[] {
			"Spirit wolf",
			"Dreadfowl",
			"Meerkat",
			"Spirit spider",
			"Thorny snail",
			"Granite crab",
			"Spirit mosquito",
			"Desert wyrm",
			"Spirit scorpion",
			"Spirit tz-kih",
			"Albino rat",
			"Spirit kalphite",
			"Compost mound",
			"Giant chinchompa",
			"Vampire bat",
			"Honey badger",
			"Beaver",
			"Void ravager",
			"Void spinner",
			"Void torcher",
			"Void shifter",
			"Bull ant",
			"Macaw",
			"Evil turnip",
			"Sp. cockatrice",
			"Sp. guthatrice",
			"Sp. saratrice",
			"Sp. zamatrice",
			"Sp. pengatrice",
			"Sp. coraxatrice",
			"Sp. vulatrice",
			"Pyrelord",
			"Magpie",
			"Bloated leech",
			"Spirit terrorbird",
			"Abyssal parasite",
			"Spirit jelly",
			"Ibis",
			"Spirit kyatt",
			"Spirit larupia",
			"Spirit graahk",
			"Karam. overlord",
			"Smoke devil",
			"Abyssal lurker",
			"Spirit cobra",
			"Stranger plant",
			"Barker toad",
			"War tortoise",
			"Bunyip",
			"Fruit bat",
			"Ravenous locust",
			"Arctic bear",
			"Phoenix",
			"Obsidian golem",
			"Granite lobster",
			"Praying mantis",
			"Forge regent",
			"Talon beast",
			"Giant ent",
			"Fire titan",
			"Moss titan",
			"Ice titan",
			"Hydra",
			"Spirit dagannoth",
			"Lava titan",
			"Swamp titan",
			"Bronze minotaur",
			"Iron minotaur",
			"Steel minotaur",
			"Mithril minotaur",
			"Adamant minotaur",
			"Rune minotaur",
			"Unicorn stallion",
			"Geyser titan",
			"Wolpertinger",
			"Abyssal titan",
			"Iron titan",
			"Pack yak",
			"Steel titan"
		}));
		contentPane.add(comboBox3);
		comboBox3.setBounds(135, 30, 145, comboBox3.getPreferredSize().height);

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Mega Team
	private JLabel label1;
	private JComboBox comboBox1;
	private JLabel label2;
	private JCheckBox checkBox1;
	private JCheckBox checkBox2;
	private JLabel label3;
	private JComboBox comboBox2;
	private JButton button1;
	private JLabel label4;
	private JComboBox comboBox3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
	public class LogEntry {
	    public String text;
	    public Color color;
	    public long timeSent;
	    public int alpha = 255;

	    public LogEntry(String text, Color color) {
	        this.text = text;
	        this.color = color;
	        timeSent = System.currentTimeMillis();
	    }

	    public String getText() {
	        return text;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }

	    public Color getColor() {
	        return color;
	    }


	    public void setColor(Color color) {

	        this.color = color;
	    }
	}
	public class LogHandler {
	    public int totalCapacity = 5;
	    public final int X_LOC = 5;
	    public final int Y_LOC = 313;
	    public LinkedList<LogEntry> Entries = new LinkedList<LogEntry>();

	    public void add(LogEntry logEntry) {
	        Entries.push(logEntry);
	    }

	    public void Print(String s) {
			String time = Timer.format(System.currentTimeMillis() - STARTIME);
			add(new LogEntry("[" + time + "]: " + s, Color.white));
	    }
	    public void Print(String s, Color c) {
	    	 c = new Color(random(1,255), random(1,255), random(1,255));
			String time = Timer.format(System.currentTimeMillis() - STARTIME);
				add(new LogEntry("[" + time + "]: " + s, c));
	    }

		public void Print(String s, Color c, boolean spam) {
			String time = Timer.format(System.currentTimeMillis() - STARTIME);
			if (!MESSAGE_LOG.contains(s)) {
				add(new LogEntry("[" + time + "]: " + s, c));
			    MESSAGE_LOG.add(s);
			}
		}
	    public void Draw(Graphics g) {
	        Font f = new Font("Arial Black", 0, 11);
	        g.setFont(f);
	        int i = 0;
	        for (LogEntry logEntry : Entries) {
	            g.setColor(new Color(logEntry.getColor().getRed(), logEntry.getColor().getGreen(), logEntry.getColor().getBlue(), logEntry.alpha));
	            g.drawString(logEntry.getText(), X_LOC, Y_LOC - (i * 15));
	            if (i > 4) {
	                logEntry.alpha -= 20;
	            }
	            if (logEntry.alpha < 0) {
	                Entries.remove(logEntry);
	            }
	            i++;
	        }
	    }
	}
	private boolean contains(Rectangle rec , Point m){
		return rec.contains(m.x + rec.x, m.y + rec.y);
	}
	@Override
	public Rectangle getPhysicalMouseArea() {
		return new Rectangle(15, 404, 24, 20);
	}
	@Override
	public void physicalMouseClicked(MouseEvent e) {
		Rectangle d = new Rectangle(15, 404, 24, 20);
		if(contains(d,e.getPoint()))
			SHOWPAINT = !SHOWPAINT;
	}
	@Override
	public void physicalMouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void physicalMouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void physicalMouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void physicalMouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void physicalMousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void physicalMouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void physicalMouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
