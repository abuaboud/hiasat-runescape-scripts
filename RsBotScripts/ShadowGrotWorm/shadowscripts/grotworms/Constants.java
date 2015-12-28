package shadowscripts.grotworms;

import java.util.ArrayList;
import java.util.HashMap;

import org.powerbot.core.script.util.Timer;
import org.powerbot.game.api.methods.tab.Summoning.Familiar;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class Constants {

	public static double version = 1.1;
	public static boolean FAMILIAR_FULL = false, USE_RANGE,STARTCHECK = true,
			startscript = false, FIRST_INV = true, SUMMON_ACTIVE, USE_PRAYER,
			USE_DEF, USE_ATT, USE_STR, ALCH_LOW_PROFIT,ROAMCAVE = true,RENEWAL = false,
			INV_FIRSTCHECK = true;
	public static Timer ABILTY_TIMER = new Timer(0);
	public static ArrayList<Integer>Store = new ArrayList<Integer>();
	public static ArrayList<Integer> DROPS = new ArrayList<Integer>(),
			HOTKEY = new ArrayList<Integer>();
	public static long LASTTIMER = 0,LASTINTERFACE = 0;
	public static int LASTMONEY = 0;
	public static Timer sig = new Timer(Random.nextInt(20000,40000));
	public static HashMap<Integer, Integer> PRICES = new HashMap<Integer, Integer>();
	public static int[] ALCH_ITEM = { 1213, 1432, 1303, 1147, 1111, 1183 },
			HERB_ITEM = { 207, 209, 211, 213, 215, 217, 2485, 5298, 5303, 5302,
					 5300 }, RAW_PROFIT = {  451, 24372 },
			NOTED_PROFIT = { 450, 1780 }, OTHER_LOOT = { 565, 995 },
			CRIMSON_LOOT = { 12158, 12159, 12160, 12163 },
			GROTWORMS_ID = { 15463 };
	public static final int[] WORLDP2P = { 5, 6, 9, 12, 15, 18, 21, 22, 23, 24, 25,
			26, 27, 28, 32, 36, 39, 40, 42, 44, 45, 46, 48, 49, 51, 52,
			53, 54, 56, 58, 59, 60, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71,
			72, 73, 74, 76, 78, 79, 82, 83, 84, 85, 86, 87, 88, 89, 91,
			92, 96, 97, 99, 100, 103, 104, 105, 114, 115, 116, 117, 119,
			123, 124, 137, 138, 139 };
	public static int BANK_SHARKAMOUNT, BANK_FALADOR_TELEPORT,BANK_POUCHAMOUNT,
	BANK_SUMMONAMOUNT,BANK_POLYPORESTAFF,BANK_NATURERUNE,BANK_FIRERUNE;
	public static int SSTRPOTION[] = { 2440, 157, 159, 161 }, SDEFPOTION[] = {
			2442, 163, 165, 167 }, SATTPOTION[] = { 2436, 145, 147, 149 },
			PRAYERPOTION[] = { 2434, 139, 141, 143 }, SUMMONPOTION[] = { 12140,
					12142, 12144, 12146 }, RANGE_POTION[] = { 2445, 169, 171,
					173 };
	public static final int[] rareTable = { 1515, 5289, 5315, 5316, 5300,
			20667, 9342, 1149, 2366, 1215, 987, 985, 1631, 1615, 1201 ,5304};
	public static final int[] grotDrop = { 217, 2485, 207, 24372, 1319, 1213,
			1303, 1432, 1373, 1113, 1147, 1185, 565, 3028, 451, 2363 };
	public static final int[] junks = { 229, 532 };
	public static final int[] notedDrops = { 7937, 6686, 270, 3001, 2999, 258,
			2364, 2362, 450, 452, 454, 384, 372, 574, 570, 1392, 1780 };
	public static int NATURE_RUNE = 561, FIRE_RUNE = 554, TOTAL_PROFIT = 0,
			MONKFISH = 7946, SHARK = 385, FALADOR_TAB = 8009, SWORDFISH = 373,
			ROCKTAIL = 15272, LOBSTER = 379, FAMILARINV, EAT_PERCENT,
			SUMMONAMOUNT, POUCHAMOUNT, PRAYER_AMOUNT, STARTXP, CURRENT_FOOD,
			FOODAMOUNT, ALCH_PROFIT, FIRSTINV_PROFIT, LAST_PROFIT,POLYPORE_STAFF = 22494;
	public static String STATUS = "loading...";
	public static long STARTTIME;
	public static Familiar CurrentFamiliar;
	public static Area BANKAREA = new Area(new Tile[] {
			new Tile(2948, 3365, 0), new Tile(2948, 3369, 0),
			new Tile(2947, 3369, 0), new Tile(2947, 3374, 0),
			new Tile(2941, 3374, 0), new Tile(2941, 3367, 0),
			new Tile(2944, 3367, 0), new Tile(2944, 3365, 0) });
	public static Area FALADOR_AREA = new Area(new Tile[] {
			new Tile(2996, 3334, 0), new Tile(2996, 3393, 0),
			new Tile(2939, 3393, 0), new Tile(2933, 3335, 0),
			new Tile(3256, 3467, 0) });
	public static Tile[] PATHTOCAVE = new Tile[] { new Tile(3009, 3215, 0),
			new Tile(3006, 3219, 0), new Tile(3004, 3224, 0),
			new Tile(3002, 3229, 0), new Tile(2999, 3234, 0),
			new Tile(2994, 3235, 0), new Tile(2990, 3236, 0) };
	public static Tile[] PATHTOFALABANK = new Tile[] { new Tile(2963, 3382, 0), new Tile(2958, 3380, 0), new Tile(2953, 3380, 0), 
			new Tile(2949, 3377, 0), new Tile(2945, 3373, 0), new Tile(2944, 3368, 0) };
	public static Tile[] PATHTOSHORTCUT = new Tile[] { new Tile(1203, 6371, 0),
			new Tile(1190, 6372, 0), new Tile(1179, 6368, 0),
			new Tile(1175, 6361, 0), new Tile(1177, 6354, 0) };
	public static Tile[] ROMAINGCAVES = new Tile[] { new Tile(1102, 6510, 0),
			new Tile(1104, 6500, 0), new Tile(1108, 6486, 0),
			new Tile(1117, 6479, 0), new Tile(1123, 6488, 0),
			new Tile(1134, 6496, 0), new Tile(1145, 6497, 0),
			new Tile(1159, 6495, 0), new Tile(1169, 6499, 0),
			new Tile(1180, 6500, 0), new Tile(1190, 6501, 0) };
	public static Tile[] PATHROAMINGCAVE = { new Tile(1195, 6497,0), new Tile(1191, 6497,0),
			new Tile(1187, 6497,0), new Tile(1183, 6497,0),
			new Tile(1179, 6498,0), new Tile(1174, 6498,0),
			new Tile(1169, 6498,0), new Tile(1164, 6498,0),
			new Tile(1159, 6495,0), new Tile(1155, 6495,0),
			new Tile(1150, 6496,0), new Tile(1146, 6496,0),
			new Tile(1142, 6496,0), new Tile(1139, 6492,0),
			new Tile(1138, 6487,0), new Tile(1135, 6484,0),
			new Tile(1131, 6484,0), new Tile(1127, 6483,0),
			new Tile(1122, 6485,0), new Tile(1120, 6480,0),
			new Tile(1116, 6478,0), new Tile(1112, 6482,0),
			new Tile(1108, 6487,0), new Tile(1108, 6491,0),
			new Tile(1107, 6496,0), new Tile(1105, 6500,0),
			new Tile(1104, 6505,0), new Tile(1104, 6509,0),
			new Tile(1107, 6512,0), new Tile(1106, 6516,0) };
}
