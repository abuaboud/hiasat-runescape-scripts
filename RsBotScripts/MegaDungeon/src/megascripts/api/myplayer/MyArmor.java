package megascripts.api.myplayer;

import java.awt.Color;


import megascripts.api.ulits;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.node.Loot_StartRoom;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import shadowscripts.graphic.LogHandler;

public class MyArmor {

	public static int HEAD = 0 , LEG = 0 , BODY = 0 , HAND = 0, BOOT = 0;;
	public static int[] NOVITE ={16691,16669,16647,17239,16713,16889,17019,17063,16339}
	, BATHUS = {16693,16671,16649,17241,16715,16891,17021,17071,16341}
	, MARMAROS = {16695,16673,16651,17243,16717,16893,17023,17079,16343}
	, KRATONITE = {16697,16675,16653,17245,16719,16895,17025,17087,16345}
	, FRACTITE = {16699,16677,16655,17245,16721,16897,17027,17095,16347}
	, ZEPHYRIUM = {16701,16679,16657,17247,16723,16899,17029,17103,16349}
	, ARGONITE = {16703,16681,16659,17249,16725,16901,17031,17111,16351}
	, KATAGON = {16705,16683,16661,17251,16727,16903,17033,17119,16353}
	, GORGNITE = {16707,16685,16663,17253,16729,16905,17035,17127,16355}
	, PROMETHIUM = {16709,16687,16665,17255,16731,16907,17037,17135,16357}
	, PRIMAL = {16711,16689,16667,17257,16733,16909,17039,17143,16359};
	public static int[][] ALL_ARMOR = { NOVITE, BATHUS, MARMAROS, KRATONITE,
			FRACTITE, ZEPHYRIUM, ARGONITE, KATAGON, GORGNITE, PROMETHIUM,
			PRIMAL };
	public static WidgetChild HEAD_WIDGET = Widgets.get(387,7)
        ,LEG_WIDGET = Widgets.get(387,25)
       , BODY_WIDGET = Widgets.get(387,19)
       , HAND_WIDGET = Widgets.get(387,16)
       , BOOTS_WIDGET = Widgets.get(387,31);
	
	public enum ARMOR {
		Tier1("Novite", 1, 1), 
		Tier2("Bathus", 2, 10), 
		Tier3("Marmaros", 3, 20),
		Tier4("Kratonite", 4, 30),
		Tier5("Fractite", 5, 40), 
		Tier6("Zephyrium", 6, 50),
		Tier7("Argonite", 7, 60),
		Tier8("Katagon",8, 70), 
		Tier9("Gorgonite", 9, 80),
		Tier10("Promethium", 10, 90),
		Tier11("Primal", 11, 99);
		private String Name;
		private int Tier;
		private int Levelreq;

		ARMOR(final String Name, final int Tier, final int Levelreq) {
			this.Name = Name;
			this.Tier = Tier;
			this.Levelreq = Levelreq;
		}

		public String getName() {
			return this.Name;
		}

		public int getTier() {
			return this.Tier;
		}

		public int getLevelRequired() {
			return this.Levelreq;
		}
	}

	public static boolean CHECKARMOR() {
		return HEAD == 0 || LEG == 0 || BODY == 0 || HAND == 0 || BOOT == 0;
	}

	public static void getCurrentArmor() {
		if (!Tabs.EQUIPMENT.isOpen()) {
			Tabs.EQUIPMENT.open();
		}
		HEAD = HEAD_WIDGET.getChildId();
		LEG = LEG_WIDGET.getChildId();
		BODY = BODY_WIDGET.getChildId();
		HAND = HAND_WIDGET.getChildId();
		BOOT = BOOTS_WIDGET.getChildId();
		Task.sleep(1000,1500);
		Tabs.INVENTORY.open();
	}
	public static ARMOR DEFINE_ARMOR(int e){
		if(ulits.MatchID(e, NOVITE)){
			return ARMOR.Tier1;
		} else if (ulits.MatchID(e, BATHUS)) {
			return ARMOR.Tier2;
		} else if (ulits.MatchID(e, MARMAROS)) {
			return ARMOR.Tier3;
		} else if (ulits.MatchID(e, KRATONITE)) {
			return ARMOR.Tier4;
		} else if (ulits.MatchID(e, FRACTITE)) {
			return ARMOR.Tier5;
		} else if (ulits.MatchID(e, ZEPHYRIUM)) {
			return ARMOR.Tier6;
		} else if (ulits.MatchID(e, ARGONITE)) {
			return ARMOR.Tier7;
		} else if (ulits.MatchID(e, KATAGON)) {
			return ARMOR.Tier8;
		} else if (ulits.MatchID(e, GORGNITE)) {
			return ARMOR.Tier9;
		} else if (ulits.MatchID(e, PROMETHIUM)) {
			return ARMOR.Tier10;
		} else if (ulits.MatchID(e, PRIMAL)) {
			return ARMOR.Tier11;
		}
		return null;
	}

	public static int DEFINE(String e) {
		String d = e.toLowerCase();
		if (d.contains("helm")) {
			return HEAD;
		} else if (d.contains("leg")) {
			return LEG;
		} else if (d.contains("body")) {
			return BODY;
		} else if (d.contains("boot")) {
            return BOOT;
		} else {
			return HAND;
		}
	}
	
	public static boolean gotLevelReq(int e){
		String d = GroundItems.getNearest(e).getGroundItem().getName().toLowerCase();
		ARMOR x = DEFINE_ARMOR(e);
		if (d.contains("helm")) {
			return Constants.DEFENCE_LEVEL >= x.getLevelRequired();
		} else if (d.contains("leg")) {
			return Constants.DEFENCE_LEVEL >= x.getLevelRequired();
		} else if (d.contains("body")) {
			return Constants.DEFENCE_LEVEL >= x.getLevelRequired();
		} else if (d.contains("boot")) {
            return  Constants.DEFENCE_LEVEL >= x.getLevelRequired();
		}else{
			return Constants.ATTACK_LEVEL >= x.getLevelRequired();
		}
	}
	public static String DEFINE_NAME(String e){
		String d = e.toLowerCase();
		if (d.contains("helm")) {
			return "Head";
		} else if (d.contains("leg")) {
			return "Leg";
		} else if (d.contains("body")) {
			return "Body";
		} else if (d.contains("boot")) {
            return "Boots";
		}else{
			return "Hand";
		}
	}
	public static boolean isArmor(int e) {
		return (ulits.MatchID(e, ulits.convertInt(ALL_ARMOR)));
	}

	public static void UPGRAGE() {
		int g = getGoodItem();
		int tier = getTier(g);
		int starttier = getTier(DEFINE(DEFINE_NAME(GroundItems.getNearest(g).getGroundItem().getName())));
		String message = "Upgrading " + DEFINE_NAME(GroundItems.getNearest(g).getGroundItem().getName()) + " From Tier " + starttier
				+ " To Tier " + tier;
		LogHandler.Print(message,Color.cyan,false);
		for (int e = 0; e < 30 && MyItems.contains(g) || MyGroundItems.There(g); e++) {
			if (MyGroundItems.There(g)) {
				if (MyActions.AtStartRoom()) {
					Loot_StartRoom.lootTable(new int[] { getGoodItem() });
				} else {
					Loot_StartRoom.loot(getGoodItem(), false);
				}
			} else if (MyItems.contains(g)) {
				Inventory.getItem(g).getWidgetChild().click(true);
				ShadowDungeon.SleepWhile(MyItems.contains(g));
			}
		}
		getCurrentArmor();
	}
	private static int getTier(int g) {
		ARMOR e = DEFINE_ARMOR(g);
		if(e !=null){
			return e.getTier();
		}
		return 0;
	}

	public static boolean ThereGoodArmor() {
		for (GroundItem e : GroundItems.getLoaded()) {
			int d = e.getId();
			if (e !=null && isArmor(d)) {
				if (NeedUprage(e)) {
					return true;
				}
			}
		}
		return false;
	}
	private static boolean NeedUprage(GroundItem e) {
		if(!gotLevelReq(e.getId())){
			return false;
		}
		if(	DEFINE_ARMOR(DEFINE(e.getGroundItem().getName())) == null){
			return true;
		}
		return DEFINE_ARMOR(e.getId()).getTier() > DEFINE_ARMOR(
				DEFINE(e.getGroundItem().getName())).getTier();
	}

	public static int getGoodItem() {
		for (GroundItem e : GroundItems.getLoaded()) {
			int d = e.getId();
			if (isArmor(d)) {
				if (NeedUprage(e)) {
					return e.getId();
				}
			}
		}
		return -1;
	}
	public static void reset(){
		HEAD = 0;
		LEG = 0;
		HAND = 0;
		BODY = 0;
		BOOT = 0;
	}
}
