package megascripts.dungeon;

import java.awt.Color;
import java.util.ArrayList;


import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class Constants {

	public static Color ROOM_COLOR = Color.blue;
	public static boolean STATE = false;
	public static boolean OverAll = true;
	public static boolean Misc = false;
	public static boolean OPTIONS = true;
	public static boolean LOG = false;
	public static boolean HIDE_BUTTON_HOVER = false;
	public static boolean DungeonStarted = false;
	public static boolean ShowPaint = true;
	public static boolean LeaveDungeon = false;
	public static boolean BUY_SUPPLIES= true;
	public static boolean Break_Puzzle = false;
	public static long StartTime = 0;
	public static boolean Dead = false;
	public static int [] PORTAL = {7528,53124,53125,53126,56146};
	public static int[] ENDLADDER = { 49695, 49696, 49698, 49697, 49699, 49700,
			53747, 53748, 55483, 55484 };
	public static int[] FINSHED_ENDLADDER = { 49698, 49696, 49700, 53748, 55484 };
	public static int GROUP_STONE = 18829;
	public static int POSION_POTION = 17566;
	public static int StartXp;
	public static int Death;
	public static int StartLevel;
	public static String Status = "Loading...";
	public static int PUZZLEDSOLVED = 0;
	public static int Chisel = 17444; 
	public static boolean DEV_MODE = false;
	public static int DUNGEONEERING_LEVEL = 0;
	public static int PRAYER_LEVEL = 0;
	public static int COOKING_LEVEL = 0;
	public static int STRENGTH_LEVEL = 0;
	public static int ATTACK_LEVEL = 0;
	public static int DEFENCE_LEVEL = 0;
	public static int MAX_FLOOR = 0;
	public static int CURRENT_PROGRESS = 10000;
	public static final int DUNGEON_RING []  = {18823,15707};
	public static final int Prayer_Skill[] = {50332,50333,50334,53971,55759};
	public static final int RuneCrafting_Skill[] = {50278,50279,50280,53953,55741};
	public static final int Strength_Skill[]= {50272,50273,50274,53951,55739};
	public static final int Mining_Skill[] = {50305,50306,50307,53962,55750};
	public static final int FireMaking_Skill[] = {50314,50315,50316,53965,55753};
	public static final int Magic_Skill[] = {50329,50330,50331,53970,55758};
	public static final int WoodCutting_Skill[] = {50317,50318,50319,53966,55754};
	public static final int Smithing_Skill[] = {50308,50309,50310,53963,55751};
	public static final int Crafing_Skill[] = {50299,50300,50301,53960,55748};
	public static final int Theving_Skill[] = {50294,50295,53958,55746};
	public static final int Summoning_Skill[] = {50327,50328,53969,55757};
	public static final int Herblore_Skill[] = {50336,50337,53972,55760};
	public static final int Farming_Skill[] = {50324,50325,53968,55756};
	public static final int Construction[] = {50282,50283,53954,55742};
	//ALL
	public static final int[] FLAMMABLE_DEBRIS = { 50314, 50315, 50316 };
	public static final int[] BROKEN_PULLY_DOOR = { 50299, 50300, 50301 };
	public static final int[] BROKEN_KEY_DOOR = { 50308, 50309, 50310 };
	public static final int[] DARK_SPIRIT = { 50332, 50333, 50334 };
	public static final int[] WOODEN_BARRICADE = { 50317, 50318, 50319 };
	public static final int[] RUNED_DOOR = { 50278, 50279, 50280 };
	public static final int[] PILE_OF_ROCKS = { 50305, 50306, 50307 };
	public static final int[] MAGICAL_BARRIER = { 50329, 50330, 50331 };
	public static final int[] BARRED_DOOR = { 50272, 50273, 50274 };
	public static final int[] LOCKED_DOOR = { 50287, 50288, 50289 };
	public static final int[] PADLOCKED_DOOR = { 50293, 50294, 50295 };
	public static final int[] RAMOKEE_EXILE = { 50326, 50327, 50328 };
	public static final int[] VINE_COVERED_DOOR = { 50323, 50324, 50325 };
	public static final int[] COLLAPSING_DOORFRAME = { 50281, 50282, 50283 };
	public static final int[] DISARM_DOOR = {55744,53956};
	
	public static final int[][] ALL_SKILL_DOOR = { Prayer_Skill,
			RuneCrafting_Skill, Strength_Skill, Mining_Skill, FireMaking_Skill,
			Magic_Skill, WoodCutting_Skill, Smithing_Skill, Crafing_Skill,
			Theving_Skill, Summoning_Skill, Herblore_Skill, Farming_Skill,
			Construction, FLAMMABLE_DEBRIS, BROKEN_PULLY_DOOR, BROKEN_KEY_DOOR,
			DARK_SPIRIT, WOODEN_BARRICADE, RUNED_DOOR, PILE_OF_ROCKS,
			MAGICAL_BARRIER, BARRED_DOOR, LOCKED_DOOR, PADLOCKED_DOOR,
			RAMOKEE_EXILE, VINE_COVERED_DOOR, COLLAPSING_DOORFRAME ,DISARM_DOOR};
	//
	
	public static int [] ALL_KEY;
	public static int Current_Complexity;
	public static int Current_Floor;
	public static int blackDoor = 0;
	public static int DungeonCompleted = 0;
	public static int ABORTED_DUNGEONS = 0;
	public static ArrayList<String> MESSAGE_LOG = new ArrayList<String>();
	public static ArrayList<Integer> Inevntory_KEY = new ArrayList<Integer>();
	public static ArrayList<Tile> EnterdDoor = new ArrayList<Tile>();
	public static ArrayList<Tile> BackBasic = new ArrayList<Tile>();
	public static ArrayList<Tile> BlackList = new ArrayList<Tile>();
	public static ArrayList<Tile> BlackDoor = new ArrayList<Tile>();
	public static ArrayList<Tile> KeyEnterd = new ArrayList<Tile>();
	public static ArrayList<Tile> SkillEnterd = new ArrayList<Tile>();
	public static ArrayList<Long> Dungeons_TIMER = new ArrayList<Long>();
	public static Timer Dungeon_Time;
	public static Timer PUZZLE_TIME;
	public static int CurrentKey;
	public static Area CurrentRoom;
	public static Area NEXTROOM;
	public static final int FIRE = 49941;
	public static final int SEEPING_ELM_BRANCHES = 17684;
	public static final int RAW_HEIM_CRAB = 17797, RAW_RED_EYE = 17799,
			RAW_DUSK_EEL = 17801, RAW_FLAT_FISH = 17803, RAW_SHORT_FIN = 17805,
			RAW_WEB_SNIPPER = 17807, RAW_BOULDA_BASS = 17809,
			RAW_SALVE_EEL = 17811, RAW_BLUE_CRAB = 17813,
			RAW_CAVE_MORAY = 17815;
	public static final int HEIM_CRAB = 18159, RED_EYE = 18161,
			DUSK_EEL = 18163, FLAT_FISH = 18165, SHORT_FIN = 18167,
			WEB_SNIPPER = 18169, BOULDA_BASS = 18171, SALVE_EEL = 18173,
			BLUE_CRAB = 18175, CAVE_MORAY = 18177;
	public static final int[] FOODS = { HEIM_CRAB, RED_EYE, DUSK_EEL,
			FLAT_FISH, SHORT_FIN, WEB_SNIPPER, BOULDA_BASS, SALVE_EEL,
			BLUE_CRAB, CAVE_MORAY };
	public static final int[] NOOB_FOODS = { HEIM_CRAB, RED_EYE };
	public static final int[] GOOD_FOODS = { DUSK_EEL, FLAT_FISH, SHORT_FIN,
			WEB_SNIPPER, BOULDA_BASS, SALVE_EEL, BLUE_CRAB, CAVE_MORAY, };
	public static int[] FOODTOEAT = FOODS;
    public static final int COIN = 18201;
	public static final int Gurdain_Door[] = {50346,50347,50348,53949,55763};
	public static final int GDOOR [][] = {Gurdain_Door};
	public static final int BACK_DOORS[] = {55762,50342,50343,50344,53948};
	public static final int Smuggler = 11226;


	public static final int[] PUZZLE_DOORS = { 33654, 49306, 49335, 49336,
			49337, 49338, 49375, 49376, 49377, 49378, 49379, 49380, 49387,
			49388, 49389, 49462, 49463, 49464, 49504, 49505, 49506, 49516,
			49517, 49518, 49564, 49565, 49566, 49574, 49575, 49576, 49577,
			49578, 49579, 49603, 49604, 49605, 49606, 49607, 49608, 49625,
			49626, 49627, 49644, 49645, 49646, 49650, 49651, 49652, 49677,
			49678, 49679, 53987, 53988, 53989, 53990, 53992, 54001, 54006,
			54067, 54070, 54071, 54072, 54073, 54000, 54106, 54107, 54108,
			54109, 54236, 54274, 54284, 54299, 54300, 54315, 54316, 54317,
			54318, 54319, 54320, 54335, 54360, 54361, 54362, 54363, 54404,
			54417, 54620, 55478, 55479, 55480, 55481, 56079, 56081, 56526, 56527,
			56528, 56529 };
	public static final int[] BASIC_DOORS = {53967, 53964, 55749, 55745, 53952,
			53955, 53973, 55743, 55761, 55747, 55755, 50304, 50320, 50321,
			50322, 50303, 50302, 49306, 49335, 49336, 50341, 50339, 50338,
			50340, 49337, 49338, 49375, 49625, 50312, 50313, 50311, 50276,
			50277, 50275, 50285, 50297, 50296, 50298, 50290, 50291, 50292,
			50284, 50285, 50286, 55740, 53959, 53957 };
	public static final int[][] StanderdDoor = { BACK_DOORS, BASIC_DOORS,PUZZLE_DOORS };
	//

	 public static final int[] Second_YELLOW_DOORS = { 50369, 50370, 50371, 50372,
			50373, 50374, 50375, 50376, 50433, 50434, 50435, 50436, 50437,
			50438, 50439, 50440, 50497, 50498, 50499, 50500, 50501, 50502,
			50503, 50504, 53900, 53901, 53902, 53903, 53904, 53905, 53906,
			53907, 55691, 55692, 55693, 55694, 55695, 55696, 55697, 55698 };

	public static final int[] Second_GREEN_DOORS = { 50377, 50378, 50379, 50380,
			50381, 50382, 50383, 50384, 50441, 50442, 50443, 50444, 50445,
			50446, 50447, 50448, 50505, 50506, 50507, 50508, 50509, 50510,
			50511, 50512, 53908, 53909, 53910, 53911, 53912, 53913, 53914,
			53915, 55699, 55700, 55701, 55702, 55703, 55704, 55705, 55706 };

	public static final int[] Second_BLUE_DOORS = { 50385, 50386, 50387, 50388,
			50389, 50390, 50391, 50392, 50449, 50450, 50451, 50452, 50453,
			50454, 50455, 50456, 50513, 50514, 50515, 50516, 50517, 50518,
			50519, 50520, 53916, 53917, 53918, 53919, 53920, 53921, 53922,
			53923, 55707, 55708, 55709, 55710, 55711, 55712, 55713, 55714 };

	public static final int[] Second_ORANGE_DOORS = { 50353, 50354, 50355, 50356,
			50357, 50358, 50359, 50360, 50417, 50418, 50419, 50420, 50421,
			50422, 50423, 50424, 50481, 50482, 50483, 50484, 50485, 50486,
			50487, 50488, 53884, 53885, 53886, 53887, 53888, 53889, 53890,
			53891, 55675, 55676, 55677, 55678, 55679, 55680, 55681, 55682 };

	public static final int[] Second_SILVER_DOORS = { 50361, 50362, 50363, 50364,
			50365, 50366, 50367, 50368, 50425, 50426, 50427, 50428, 50429,
			50430, 50431, 50432, 50489, 50490, 50491, 50492, 50493, 50494,
			50495, 50496, 53892, 53893, 53894, 53895, 53896, 53897, 53898,
			53899, 55683, 55684, 55685, 55686, 55687, 55688, 55689, 55690 };

	public static final int[] Second_PURPLE_DOORS = { 50393, 50394, 50395, 50396,
			50397, 50398, 50399, 50400, 50457, 50458, 50459, 50460, 50461,
			50462, 50463, 50464, 50521, 50522, 50523, 50524, 50525, 50526,
			50527, 50528, 53924, 53925, 53926, 53927, 53928, 53929, 53930,
			53931, 55715, 55716, 55717, 55718, 55719, 55720, 55721, 55722 };
	
	public static final int[] Second_CRIMSON_DOORS = { 50401, 50402, 50403, 50404,
			50405, 50406, 50407, 50408, 50465, 50466, 50467, 50468, 50469,
			50470, 50471, 50472, 50529, 50530, 50531, 50532, 50533, 50534,
			50535, 50536, 53932, 53933, 53934, 53935, 53936, 53937, 53938,
			53939, 55723, 55724, 55725, 55726, 55727, 55728, 55729, 55730 };

	public static final int[] Second_GOLD_DOORS = { 50409, 50410, 50411, 50412,
			50413, 50414, 50415, 50416, 50473, 50474, 50475, 50476, 50477,
			50478, 50479, 50480, 50537, 50538, 50539, 50540, 50541, 50542,
			50543, 50544, 53940, 53941, 53942, 53943, 53944, 53945, 53946,
			53947, 55731, 55732, 55733, 55734, 55735, 55736, 55737, 55738 };
	
	public static int[][] Second_KeyDoor = { Second_YELLOW_DOORS,
			Second_GREEN_DOORS, Second_BLUE_DOORS, Second_ORANGE_DOORS,
			Second_SILVER_DOORS, Second_PURPLE_DOORS, Second_CRIMSON_DOORS,
			Second_GOLD_DOORS };

	public static final int[] YELLOW_DOORS = { 50224, 50225, 50226, 50227,
			50228, 50229, 50230, 50231 };
	public static final int[] GREEN_DOORS = { 50232, 50233, 50234, 50235,
			50236, 50237, 50238, 50239 };
	public static final int[] BLUE_DOORS = { 50240, 50241, 50242, 50243, 50244,
			50245, 50246, 50247 };
	public static final int[] ORANGE_DOORS = { 50208, 50209, 50210, 50211,
			50212, 50213, 50214, 50215 };
	public static final int[] SILVER_DOORS = { 50216, 50217, 50218, 50219,
			50220, 50221, 50222, 50223 };
	public static final int[] PURPLE_DOORS = { 50248, 50249, 50250, 50251,
			50252, 50253, 50254, 50255 };
	public static final int[] CRIMSON_DOORS = { 50256, 50257, 50258, 50259,
			50260, 50261, 50262, 50263 };
	public static final int[] GOLD_DOORS = { 50264, 50265, 50266, 50267, 50268,
			50269, 50270, 50271 };
	public static final int KEY_DOOR[][] = { YELLOW_DOORS, GREEN_DOORS,
			BLUE_DOORS, ORANGE_DOORS, SILVER_DOORS, PURPLE_DOORS,
			CRIMSON_DOORS, GOLD_DOORS };

	// KEYS
	public static final int YellowKey[] = { 18234, 18236, 18238, 18240, 18242,
			18244, 18246, 18248 };
	public static final int GreenKey[] = { 18250, 18252, 18254, 18256, 18258,
			18260, 18262, 18264 };
	public static final int BlueKey[] = { 18266, 18268, 18270, 18272, 18274,
			18276, 18278, 18280 };
	public static final int OrangeKey[] = { 18202, 18204, 18206, 18208, 18210,
			18212, 18214, 18216 };
	public static final int SilverKey[] = { 18218, 18220, 18222, 18224, 18226,
			18228, 18230, 18232 };
	public static final int PurbleKey[] = { 18282, 18284, 18286, 18288, 18290,
			18292, 18294, 18296 };
	public static final int CrimsonKey[] = { 18298, 18300, 18302, 18304, 18306,
			18308, 18310, 18312 };
	public static final int GoldKey[] = { 18314, 18316, 18318, 18320, 18322,
			18324, 18326, 18328 };
	public static final int ALL_KEYS[][] = { YellowKey, GreenKey, BlueKey,
			OrangeKey, SilverKey, PurbleKey, CrimsonKey, GoldKey };
	
	public static final int [][][] ALL_DOORS = {StanderdDoor,Second_KeyDoor,KEY_DOOR,GDOOR,ALL_SKILL_DOOR};

}
