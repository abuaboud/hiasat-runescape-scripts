import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;



import com.epicbot.event.listeners.PaintListener;
import com.epicbot.script.Script;
import com.epicbot.script.ScriptManifest;
import com.epicbot.script.methods.Abilities.ActionSlot;
import com.epicbot.script.methods.Calculations;
import com.epicbot.script.methods.Interfaces;
import com.epicbot.script.methods.Settings;
import com.epicbot.script.util.Timer;
import com.epicbot.script.wrappers.RSArea;
import com.epicbot.script.wrappers.RSComponent;
import com.epicbot.script.wrappers.RSGroundItem;
import com.epicbot.script.wrappers.RSInterface;
import com.epicbot.script.wrappers.RSNPC;
import com.epicbot.script.wrappers.RSObject;
import com.epicbot.script.wrappers.RSPlayer;
import com.epicbot.script.wrappers.RSTile;


@ScriptManifest(authors = { "Magorium" }, name = "Pro MTA")
public class ProMTA extends Script implements PaintListener {

	private boolean ALCHMEY = true,PUZZLE = false,GRAVEYARD = false,DEVMODE = true;
	private final int STATE = 6888,PRESURE_TILE = 10777,GUARDAIN = 3102;

	public final static int[] SOLVE_X1 = {-10,-11,-1,0,-1,-11,-10},
	SOLVE_Y1 = {0,-1,-11,-10,0,-10,-11,-10},
	STATIC_X1 = {-1,-1,-5,-5,-3,-3,-10},
	STATIC_Y1 = {-10,-1,-1,-7,-7,-4,-4};
	public final static int[] SOLVE_X2 = {-10,-11,-10,-11,-10,-11,-10,-11},
			SOLVE_Y2 = {-11,-10,0,-1,-11,-10,0},
			STATIC_X2 = {-1,-1,-4,-4,-7,-7,-10},
			STATIC_Y2 = {-1,-10,-10,-1,-1,-10,-10};
	public final static int[] SOLVE_X3 = {-11,-10,0,-1,0,-1,-11},
			SOLVE_Y3= {-1,0,-10,-11,-10,-11,-10},
			STATIC_X3 = {-6,-10,-10,-5,-5,-1,-1},
			STATIC_Y3 = {-6,-6,-1,-1,-6,-6,-10};
	public final static int[] SOLVE_X4 = {-1,0,-1,-11,-10,0,-1,-11},
			SOLVE_Y4 = {-11,-1,0,-1,-11,-10,0,-1},
			STATIC_X4 = {-6,-6,-4,-4,-8,-8,-2,-2},
			STATIC_Y4 = {-5,-8,-8,-4,-4,-9,-9,-1};
	public final static int[] SOLVE_X5 = {-1,0,-1,0,-1,0,-1,0},
			SOLVE_Y5= {0,-1,-11,-10,0,-1,0,-1},
			STATIC_X5 = {-10,-10,-9,-9,-7,-7,-3,-3},
			STATIC_Y5 = {-10,-1,-1,-9,-9,-3,-3,-1};
	public final static int[] SOLVE_X6 = {-10,-11,-1,-0,-10,-11,-10,0},
			SOLVE_Y6 = {-11,-10,-11,-10,-11,-10,-11,-10},
			STATIC_X6 = {-3,-3,-5,-5,-1,-1,-8,-8},
			STATIC_Y6 = {-2,-3,-3,-7,-7,-9,-9,-10};

	public final static int[] SOLVE_X7 = {-1,0,-1,-11,-10,-11,-1,0,-1},
			SOLVE_Y7 = {0,-1,0,-1,0,-1,-11,-10,-11,-1},
			STATIC_X7 = {-4,-4,-3,-3,-4,-4,-7,-7,-3},
			STATIC_Y7 = {-10,-5,-5,-3,-3,-1,-1,-9,-9};
	public final static int[] SOLVE_X8 = {-1,0,-1,0,-1,0,-1,0,-1},
			SOLVE_Y8 = {-11,-10,0,-1,-11,-10,0,-1,-11},
			STATIC_X8 = {-10,-10,-7,-7,-4,-4,-3,-3,-1},
			STATIC_Y8 = {-1,-10,-10,-2,-2,-10,-10,-1,-1};
	public final static int[] SOLVE_X9 = {-10,-11,-10,-11,-10,0,-1,-11,-10,0},
			SOLVE_Y9= {-11,-10,0,-1,0,-10,-11,-10,-11,-10},
			STATIC_X9 = {-5,-5,-6,-6,-9,-9,-6,-6,-10,-10},
			STATIC_Y9 = {-6,-9,-9,-6,-6,-4,-4,-5,-5,-10};
	public final static int[] SOLVE_X10 = {-1,0,-1,-11,-10,0,-1,0,-1,-11},
			SOLVE_Y10 = {-11,-10,-11,-10,-11,-1,0,-1,-11,-10},
			STATIC_X10 = {-6,-6,-2,-2,-5,-5,-3,-3,-1,-1},
			STATIC_Y10 = {-1,-4,-4,-6,-6,-8,-8,-7,-7,-10};
	private RSTile p1,p2,p3;
	private RSArea pa;
	private RSArea MAGIC_ARENA = new RSArea(new RSTile[] { new RSTile(3370, 3298, 0), new RSTile(3370, 3324, 0), new RSTile(3355, 3324, 0), 
			new RSTile(3355, 3299, 0) });
	
	private final int BLUE = 10801,RED = 10802,GOLD = 10799,GREEN = 10800;
	private final int BONES_PACK[] = {10725,10726,10727,10728},BONES[] = {6904,6905,6906,6907},FOOD_DEPOSIT = 10735,BANANA = 1963;
	
	//DEVLOPER TOOLS


	private final Game[] games = {new Telekinetic(), new Graveyard(), new Alchmey()}; 
	@Override
	public int loop() {
		if (!inGame()) {
			enterGame(getPoralID(games));
		} else {
			for (Game g : games) {
				if (g != null && g.isValid()) {
					g.play();
				}
			}
		}
		return random(50,100);
	}
	private void WalkTo(RSTile t1) {
		RSTile[] path = walking.findPath(t1);
		if (!game.isLoggedIn())
			return;
		if (!walking.isRunEnabled() && walking.getEnergy() > 40) {
			walking.setRun(true);
		}
		if (path != null) {
			walking.walkPathMM(path);
			sleep(1500,2000);
		}
	}
	public class Alchmey extends Game{

		@Override
		public int getPortalID() {
			return 0;
		}

		@Override
		public void play() {
			int id = getItemID();
			if (inventory.getCount(id) > 0) {
				keyboard.sendText(abilities.getKeyBind(ActionSlot.SLOT_3) + "",
						false);
				inventory.getItem(id).doAction("Cast");
				sleep(600, 1200);
			} else if (inventory.isFull()) {
				int idf = getInventoryID();
				keyboard.sendText(abilities.getKeyBind(ActionSlot.SLOT_3) + "",
						false);
				inventory.getItem(idf).doAction("Cast");
				sleep(600, 1200);
			} else {

			}
		}

		@Override
		public boolean isValid() {
			return ALCHMEY;
		}

		@Override
		public String getName() {
			return "Alchmey";
		}

		private int getInventoryID() {
			ArrayList<Integer> prices = new ArrayList<Integer>();
			for (AlchItem g : AlchItem.values()) {
				if (inventory.contains(g.getID())) {
					int price = Integer.parseInt(interfaces.get(194)
							.getComponent(g.getCompment()).getText());
					prices.add(price);
				}
			}
			int x = prices.indexOf(Collections.max(prices));
			return AlchItem.values()[x].getID();
		}
		
		private int getItemID() {
			ArrayList<Integer> prices = new ArrayList<Integer>();
			for (AlchItem g : AlchItem.values()) {
				int price = Integer.parseInt(interfaces.get(194)
						.getComponent(g.getCompment()).getText());
				prices.add(price);
			}
			int x = prices.indexOf(Collections.max(prices));
			return AlchItem.values()[x].getID();
		}
	}
	public class Graveyard extends Game{

		@Override
		public int getPortalID() {
			return 10781;
		}

		@Override
		public void play() {
			
			if(inventory.getCount(BANANA) > 0){
				if(getMyPlayer().getHPPercent() < 95){
					inventory.getItem(BANANA).doAction("Eat");
					sleep(200,300);
				}else{
					RSObject d = objects.getNearest(FOOD_DEPOSIT);
					if(d == null)
						return;
					if(d.isOnScreen()){
						d.doAction("Deposit");
						for(int i = 0 ; i < 10 && inventory.getCount(BANANA) > 0 ; i++,sleep(100,150));
					}else{
						WalkTo(d.getLocation());
					}
				}
			}else if(inventory.isFull()){
				if(inventory.getCount(BONES) > 0){
					keyboard.sendText("" + abilities.getKeyBind(ActionSlot.SLOT_2),false);
					sleep(1000,1500);
				}
			}else{
				RSObject d = objects.getNearest(BONES_PACK);
				if(d == null)
					return;
				if(d.isOnScreen()){
					if (menu.contains("Grab")) {
						menu.doAction("Grab");
					} else {
						d.doAction("Grab");
					}
					sleep(300,600);
				}else{
					if (calc.distanceTo(d) < 7) {
						camera.turnToTile(d.getLocation());
					} else {
						WalkTo(d.getLocation());
					}
				}
			}
		}

		@Override
		public boolean isValid() {
			return GRAVEYARD;
		}

		@Override
		public String getName() {
			return "Graveyard";
		}
		
	}
	public class Telekinetic extends Game{

		@Override
		public void play() {
		 getArea();
			if (!isSolved()) {
				if(interfaces.get(640).getComponent(3).isVisible())
					interfaces.get(640).getComponent(3).doClick();
				if (interfaces.get(137).getComponent(56).getText() != null&& !interfaces.get(137).getComponent(56).getText().contains("Press Enter")) 
					keyboard.sendText("1", true);
				
				TekPuzzle cur = getCurrentPuzzle();
				Solve(cur.getSolveX(), cur.getSolveY(), cur.getStaticX(), cur.getStaticY());
			} else {
				RSComponent Chat = interfaces.get(1191).getComponent(18),
						Chat1 = interfaces.get(1184).getComponent(18),
						option = interfaces.get(1188).getComponent(3);
				if(Chat.isValid()){
					keyboard.sendText(" ", false);
					for(int i = 0 ; i < 10 && Chat.isValid(); i++,sleep(100,150));
				}else if(Chat1.isValid()){
					keyboard.sendText(" ", false);
					for(int i = 0 ; i < 10 && Chat1.isValid(); i++,sleep(100,150));
				}else if(option.isValid()){
					keyboard.sendText("1", false);
					for(int i = 0 ; i < 10 && option.isValid(); i++,sleep(100,150));
				}else{
					RSNPC g = npcs.getNearest(GUARDAIN);
					if(g == null)
						return;
					g.doAction("Talk-to");
					for(int i = 0 ; i < 10 && !Chat.isValid(); i++,sleep(100,150));
				}
			}
		}
		

		@Override
		public boolean isValid() {
			return PUZZLE;
		}

		@Override
		public String getName() {
			return "Telekinetic Puzzle";
		}


		@Override
		public int getPortalID() {
			return 10778;
		}
		
	}
	private boolean isSolved() {
		if(npcs.getNearest(GUARDAIN) !=null)
			return true;
		RSGroundItem g = groundItems.getNearest(STATE);
		RSObject e = objects.getNearest(PRESURE_TILE);
		if(e == null || g == null)
			return false;
		return tileMatched(e.getLocation(),g.getLocation());
	}
	private TekPuzzle getCurrentPuzzle(){
		int a = (settings.getSetting(1347) / 100000);
		int mazeID = -1;
		if (a == 9395) {					 
			mazeID = 1;					
		} else if (a == 6710) {					
			mazeID = 2;					
		} else if (a == 2684) {					
			mazeID = 3;
		} else if (a == 8053 ) {		
			mazeID = 4;	
		} else if (a == 5368) {		
			mazeID = 5;		
		} else if (a == 1342) {		
			mazeID = 6;		
		} else if (a ==10737 ) {		
			mazeID = 7;					
		} else if (a ==12079 ) {					
			mazeID = 8;				
		} else if (a == 4026) {
			mazeID = 9;		
		} else if (a == 0) {
			mazeID = 10;		
		} else {	
			log.info("Unknown maze");
		}
		if(mazeID == -1)
			return null;
		return TekPuzzle.values()[mazeID - 1];
	}

	
	private void Solve(int[] sx, int[] sy, int[] tx,int[] ty) {
		RSGroundItem g = groundItems.getNearest(STATE);
		if(g == null || getMyPlayer().isMoving())
			return;
		int px = g.getLocation().getX(),py =g.getLocation().getY();
		RSTile ST = getTile(sx, sy, tx,ty,px,py);
		p3 = ST;
		if (!tileMatched(ST, getMyPlayer().getLocation())) {
			if(calc.tileOnScreen(ST)){
				Point ne = calc.tileToScreen(new RSTile(ST.getX(),ST.getY()));
			   mouse.click(ne,true);
			}else{
				WalkTo(ST);
			}
			sleep(1000,1500);
		}else{
			if(g.isOnScreen()){
				keyboard.sendText("1", false);
				Point p = null;
				if(g.getModel() !=null)
			    p = g.getModel().getCentralPoint();
				
				if(p !=null)
				mouse.move(p);
				
				g.doAction("Cast");
				sleep(4000,4500);
			
			}else{
				turnToTile(g.getLocation());
			}
		}
	}
	private void turnToTile(RSTile target) {
		int b = random(0, 3);
		if(b < 2)
			camera.setPitch(random(20, 90));
		camera.turnToTile(target);
	}

	private RSTile getTile(int[] sx, int[] sy, int[] tx, int[] ty, int px, int py) {
		for(int i = 0 ; i < tx.length;i++){
			int ry = p1.getY() - ty[i],rx =  p1.getX() - tx[i];
			if(tileMatched(px,py,rx,ry)){
			  return new RSTile(p1.getX() -  sx[i], p1.getY() - sy[i]);
			}
		}
		return new RSTile(p1.getX() - sx[sx.length -1],p1.getY() - sy[sx.length -1]);
	}

	private boolean tileMatched(int px ,int py,int x, int y){
		boolean a = (x == px), b = (y == py);
		return (a && b);
	}
	private boolean tileMatched(RSTile p,RSTile e){
		int ex = e.getX(),ey = e.getY(),px = p.getX() , py = p.getY();
		boolean a = (ex == px), b = (ey == py);
		return (a && b);
	}
	private RSArea getArea() {
		RSTile all[] = getAllTile();
		if(all == null)
			return null;
		RSTile t1 = getMin(all), t2 = getMax(all);
		p1 = t1;
		p2 = t2;
		pa = new RSArea(t1, t2);
		return pa;
	}

	private RSTile[] getAllTile() {
		ArrayList<RSTile> tiles = new ArrayList<RSTile>();
		for (RSObject e : objects.getAll()) {
			if (e != null && e.getID() == 10755) {
				tiles.add(e.getLocation());
			}
		}
		return tiles.toArray(new RSTile[tiles.size()]);
	}
    private RSTile getMin(RSTile[] d){
    	ArrayList<Integer> cords = new ArrayList<Integer>();
    	ArrayList<Integer> cx = new ArrayList<Integer>();
    	ArrayList<Integer> cy = new ArrayList<Integer>();
		for(RSTile e : d){
			if(e !=null){
				int x = e.getX() , y = e.getY();
				int a = x + y;
				cx.add(x);
				cy.add(y);
				cords.add(a);
			}
		}
		int e = cords.indexOf(Collections.min(cords));
		return new RSTile(cx.get(e),cy.get(e),0);
    }
    private RSTile getMax(RSTile[] d){
    	ArrayList<Integer> cords = new ArrayList<Integer>();
    	ArrayList<Integer> cx = new ArrayList<Integer>();
    	ArrayList<Integer> cy = new ArrayList<Integer>();
		for(RSTile e : d){
			if(e !=null){
				int x = e.getX() , y = e.getY();
				int a = x + y;
				cx.add(x);
				cy.add(y);
				cords.add(a);
			}
		}
		int e = cords.indexOf(Collections.max(cords));
		return new RSTile(cx.get(e),cy.get(e),0);
    }
    
	@Override
	public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
		if (DEVMODE) {
			/*tileDraw(p1, g1);
			tileDraw(p2, g1);
			tileDraw(p3, g1);*/
		}
	}

	private boolean inGame(){
		return !MAGIC_ARENA.contains(getMyPlayer().getLocation());
	}
	private int getPoralID(Game[] e){
		for(Game d : e){
			if(d.isValid()){
				return d.getPortalID();
			}
		}
		return -1;
	}
	private void enterGame(int x){
		RSObject portal = objects.getNearest(x);
		if(portal == null)
			return;
		if(portal.isOnScreen()){
			if(interfaces.get(1186).isValid()){
				keyboard.sendText(" ",false);
			}
			portal.doAction("Enter");
			sleep(1000,1500);
		}else{
			WalkTo(portal.getLocation());
		}
	}
	private void Anaylize(RSTile start, RSTile target) {
		int sx = start.getX(),sy = start.getY(),tx = target.getX(),ty = target.getY();
		int rx = sx - tx , ry = sy - ty;
		System.out.println(" x : " + rx  + " y: " + ry);
	}

	private void tileDraw(RSTile tile, Graphics g) {
		if(tile == null)
			return;
		Point sw = calc.tileToScreen(tile, 0, 0,
				calc.tileHeight(tile.getX(), tile.getY()));
		Point se = calc.tileToScreen(new RSTile(tile.getX() + 1, tile.getY()),
				0, 0, 0);
		Point nw = calc.tileToScreen(new RSTile(tile.getX(), tile.getY() + 1),
				0, 0, 0);
		Point ne = calc.tileToScreen(new RSTile(tile.getX() + 1,
				tile.getY() + 1), 0, 0, 0);

		if (calc.pointOnScreen(sw) && calc.pointOnScreen(se)
				&& calc.pointOnScreen(nw) && calc.pointOnScreen(ne)) {
			g.setColor(new Color(0, 0, 0, 255));
			g.drawPolygon(new int[] { nw.x, ne.x, se.x, sw.x }, new int[] {
					(int) nw.getY(), ne.y, se.y, sw.y }, 4);
			g.setColor(new Color(0, 0, 255, 155));
			g.fillPolygon(new int[] { nw.x, ne.x, se.x, sw.x }, new int[] {
					(int) nw.getY(), ne.y, se.y, sw.y }, 4);
		}
	}


	public abstract class Game {
		
		public abstract int getPortalID();
		
		public abstract void play();
		
		public abstract boolean isValid();
		
		public abstract String getName();
		
	}
	private enum AlchItem{
		Long_Runesword(6897,13,"Long Runesword"),
		Emerald(6896,12,"Emerald"),
		AdamantHelm(6895,11,"Adamant Helm"),
		AdmanantKiteshield(6894,12,"Admanant Kiteshield"),
		LeatherBoots(6893,12,"Leather Boots");
		
		private int widget;
		private String name;
		private int id;
		
		AlchItem(int id, int widget,String name){
			this.widget = widget;
			this.name = name;
			this.id = id;
		}
		private int getCompment(){
			return widget;
		}
		private int getID(){
			return id;
		}
		private String getName(){
			return name;
		}
	}
	private enum TekPuzzle{
		Puzzle1(SOLVE_X1,SOLVE_Y1,STATIC_X1,STATIC_Y1),
		Puzzle2(SOLVE_X2,SOLVE_Y2,STATIC_X2,STATIC_Y2),
		Puzzle3(SOLVE_X3,SOLVE_Y3,STATIC_X3,STATIC_Y3),
		Puzzle4(SOLVE_X4,SOLVE_Y4,STATIC_X4,STATIC_Y4),
		Puzzle5(SOLVE_X5,SOLVE_Y5,STATIC_X5,STATIC_Y5),
		Puzzle6(SOLVE_X6,SOLVE_Y6,STATIC_X6,STATIC_Y6),
		Puzzle7(SOLVE_X7,SOLVE_Y7,STATIC_X7,STATIC_Y7),
		Puzzle8(SOLVE_X8,SOLVE_Y8,STATIC_X8,STATIC_Y8),
		Puzzle9(SOLVE_X9,SOLVE_Y9,STATIC_X9,STATIC_Y9),
		Puzzle10(SOLVE_X10,SOLVE_Y10,STATIC_X10,STATIC_Y10);
		
		private int[] solvex;
		private int[] solvey;
		private int[] staticx;
		private int[] staticy;
		
		TekPuzzle(int[] solvex,int[] solvey , int[] staticx, int[] staticy){
			this.solvex = solvex;
			this.solvey = solvey;
			this.staticx = staticx;
			this.staticy = staticy;
		}
		private int[] getSolveX(){
			return solvex;
		}
		private int[] getSolveY(){
			return solvey;
		}
		private int[] getStaticX(){
			return staticx;
		}
		private int[] getStaticY(){
			return staticy;
		}
	}
	
}
