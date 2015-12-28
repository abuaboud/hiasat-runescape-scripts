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
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;



import com.epicbot.event.events.MessageEvent;
import com.epicbot.event.listeners.MessageListener;
import com.epicbot.event.listeners.PaintListener;
import com.epicbot.event.listeners.PhysicalMouseListener;
import com.epicbot.script.Script;
import com.epicbot.script.ScriptManifest;
import com.epicbot.script.methods.Abilities;
import com.epicbot.script.methods.Abilities.ActionSlot;
import com.epicbot.script.methods.Equipment.Item;
import com.epicbot.script.methods.Skills.Skill;
import com.epicbot.script.methods.Game;
import com.epicbot.script.methods.Lodestones;
import com.epicbot.script.util.Filter;
import com.epicbot.script.util.Timer;
import com.epicbot.script.wrappers.RSArea;
import com.epicbot.script.wrappers.RSCharacter;
import com.epicbot.script.wrappers.RSComponent;
import com.epicbot.script.wrappers.RSItem;
import com.epicbot.script.wrappers.RSNPC;
import com.epicbot.script.wrappers.RSObject;
import com.epicbot.script.wrappers.RSTile;

@ScriptManifest(authors = { "Magorium" }, name = "Pro Slayer")
public class ProSlayer extends Script implements MessageListener,PaintListener,PhysicalMouseListener{

	public int CURRENT_TAB ,Enchanted_gem = 4155,STARTXP , STARTSLAYERXP,TASKCOMPLETED,CURRENT_FOOD,FOODAMOUNT;
	public final int DREMAN_STFF = 772,INSUALTED_BOOT = 7159,DUSTY_KEY = 1590,BRASS_KEY = 983,UNLIT_BUGLATERN = 7051,LIT_BUGLATERN = 7053,LEAFSPEAR = 4158, ANTI_DRAGONSHIELD = 1540,MIRROR_SHIELD =4156,EMPTYWATERSKIN = 1831,WATERSKIN = 1823,MONKFISH = 7946,SHARK = 385,SWORDFISH = 373,ROCKTAIL = 15272,LOBSTER = 379;
	public boolean startscript = false,betatest = true,SHOWPAINT = true;
	private long STARTTIME;
	public String currentTask;
	private Task[] tasks = {new bird(), new ghosts(), new goblins(), new cavebugs()
	, new cows(), new skeletons(), new dogs(), new scorpions(), new grotworms()
	, new dwarves(), new minotaurs() , new spiders(), new zombies(), new trolls()
	, new bears(), new caveslime(), new icefiends(), new bats(), new monkeys()
	, new icegaints(), new ogres(), new lesserdemons() , new pyrefiends()
	, new earthwarriors() , new ankous(), new crocodiles(), new greendragons()
	, new icewarriors() , new mossgaints(), new cockatrices(), new basilisks()
	, new turoths(), new harpiebug(), new shadowwarriors(), new hillgiants()
	, new ghouls(), new bluedragons(), new werewolves(), new killerwatts()
	, new otherworldlybeings(), new infernamages()};
	private SM currentMaster;
	private static RSArea TravelyArea = new RSArea(new RSTile[] { new RSTile(2865, 3499, 0), new RSTile(2968, 3497, 0), new RSTile(2938, 3372, 0), 
			new RSTile(2853, 3378, 0) });
	private static RSArea BrimhavenArea = new RSArea(new RSTile[] { new RSTile(2735, 3262, 0), new RSTile(2685, 3217, 0), new RSTile(2747, 2869, 0), 
			new RSTile(3041, 2869, 0), new RSTile(2994, 3008, 0), new RSTile(3026, 3061, 0), 
			new RSTile(2949, 3116, 0), new RSTile(2971, 3173, 0) });
	private static RSArea PortsarimArea = new RSArea(new RSTile[] { new RSTile(3064, 3268, 0), new RSTile(3061, 3093, 0), new RSTile(2959, 3101, 0), 
			new RSTile(2993, 3267, 0), new RSTile(3064, 3268, 0) });
	private RSArea EDGEVILLAGE_BANKAREA = new RSArea(new RSTile[] { new RSTile(3098, 3487, 0),
			new RSTile(3098, 3500, 0), new RSTile(3089, 3500, 0),
			new RSTile(3089, 3487, 0) });
	public RSArea Vine_Area =  new RSArea(new RSTile[] { new RSTile(2689, 9557), new RSTile(2686, 9568), new RSTile(2712, 9571), new RSTile(2713, 9557) });
	RSTile[] PATHTOTRUAEL = new RSTile[] { new RSTile(2879, 3440, 0), new RSTile(2881, 3432, 0), new RSTile(2883, 3424, 0), 
			new RSTile(2886, 3416, 0), new RSTile(2894, 3415, 0), new RSTile(2902, 3415, 0), 
			new RSTile(2909, 3419, 0), new RSTile(2912, 3423, 0) };
	RSTile[] PATHTOBRIMHAVENDUNGEON = new RSTile[] { new RSTile(2953, 3146, 0),
			new RSTile(2944, 3146, 0), new RSTile(2935, 3146, 0),
			new RSTile(2926, 3148, 0), new RSTile(2917, 3152, 0),
			new RSTile(2907, 3153, 0), new RSTile(2898, 3159, 0),
			new RSTile(2889, 3162, 0), new RSTile(2880, 3164, 0),
			new RSTile(2875, 3168, 0), new RSTile(2870, 3177, 0),
			new RSTile(2864, 3184, 0), new RSTile(2857, 3190, 0),
			new RSTile(2848, 3194, 0), new RSTile(2839, 3197, 0),
			new RSTile(2830, 3196, 0), new RSTile(2824, 3188, 0),
			new RSTile(2815, 3184, 0), new RSTile(2806, 3184, 0),
			new RSTile(2799, 3178, 0), new RSTile(2790, 3178, 0),
			new RSTile(2781, 3181, 0), new RSTile(2772, 3180, 0),
			new RSTile(2763, 3176, 0), new RSTile(2757, 3169, 0),
			new RSTile(2753, 3160, 0), new RSTile(2751, 3151, 0),
			new RSTile(2743, 3149, 0) },path;
	RSTile[] EDGEVILLAGEDUGNEONTOZOMBIE = new RSTile[] { new RSTile(3096, 9875),
			new RSTile(3096, 9884), new RSTile(3095, 9894),
			new RSTile(3094, 9903), new RSTile(3098, 9908),
			new RSTile(3103, 9909), new RSTile(3111, 9909),
			new RSTile(3121, 9909), new RSTile(3130, 9911),
			new RSTile(3138, 9915), new RSTile(3139, 9907),
			new RSTile(3145, 9901) };
	RSTile[] PATHTOTROLL = new RSTile[] { new RSTile(2895, 3545, 0), new RSTile(2890, 3550, 0), new RSTile(2883, 3553, 0), 
			new RSTile(2876, 3554, 0), new RSTile(2869, 3555, 0), new RSTile(2864, 3560, 0), 
			new RSTile(2859, 3565, 0), new RSTile(2853, 3569, 0), new RSTile(2848, 3574, 0), 
			new RSTile(2845, 3579, 0), new RSTile(2839, 3583, 0), new RSTile(2834, 3588, 0), 
			new RSTile(2835, 3595, 0), new RSTile(2839, 3601, 0), new RSTile(2845, 3605, 0), 
			new RSTile(2852, 3606, 0), new RSTile(2859, 3607, 0), new RSTile(2864, 3602, 0), 
			new RSTile(2867, 3595, 0), new RSTile(2871, 3589, 0), new RSTile(2875, 3583, 0), 
			new RSTile(2868, 3580, 0) };
	RSTile[] PATHTOICEGAINT = new RSTile[] { new RSTile(3002, 9549),
			new RSTile(2995, 9554), new RSTile(2994, 9562),
			new RSTile(2994, 9574), new RSTile(3003, 9579),
			new RSTile(3015, 9579), new RSTile(3026, 9581),
			new RSTile(3043, 9582), new RSTile(3052,9582) };
	private int MELEE_WEPION = 4151;
	private int [] TOOL,MELEE_ARMOR ={MELEE_WEPION,0};
	private ArrayList<String>betausers = new ArrayList<String>();

	//MISC
	private ArrayList<Integer> EOC_KEY = new ArrayList<Integer>();
	private Timer EOC_TIMER = new Timer(0);
	private String user,CombatStatus;
	
	@Override
	public void onFinish(){
		startscript = false;
	}
	@Override
	public boolean onStart(){
		betausers.add("magorium");
		betausers.add("kier da man");
		betausers.add("lazycory");
		betausers.add("cooda i");
		betausers.add("lipzo");
		betausers.add("stacyisback");
		betausers.add("pancakesftw");
		betausers.add("mannetjepies");
		betausers.add("exomemphiz");
		user = env.getAccountName();
		log("Welcome " + user + " to pro Slayer");
		if(!betausers.contains(user.toLowerCase())){
			return true;
		}
		guiInit();
		STARTTIME = System.currentTimeMillis();
		STARTXP = skills.getCurrentExp(Skill.CONSTITUTION) 
				+ skills.getCurrentExp(Skill.ATTACK)
				+ skills.getCurrentExp(Skill.STRENGTH)
				+ skills.getCurrentExp(Skill.DEFENCE);
		STARTSLAYERXP = skills.getCurrentExp(Skill.SLAYER);
		currentMaster = SM.Vannaka;
		CURRENT_TAB = 8007;
		log("We have Coded " + tasks.length + " Tasks");
		while(!startscript)sleep(20,30);
		TOOL = new int[]{DREMAN_STFF,LEAFSPEAR,BRASS_KEY,INSUALTED_BOOT,CURRENT_FOOD,CURRENT_TAB,WATERSKIN,Enchanted_gem,MIRROR_SHIELD,ANTI_DRAGONSHIELD};
		return true;
	}
	public void guiInit() {
		try {
	        SwingUtilities.invokeLater(new Runnable() {
	                        public void run() {
	                        	ProSlayerGUI g = new ProSlayerGUI();
	             	            g.setVisible(true);
	                        }
	        });
		} catch (Throwable ignore) {
	        log.info("Could not load GUI.");
	        return ;
		}
	}

	@Override
	public int loop() {
		if(!betausers.contains(user.toLowerCase())){
			betatest = true;
			return random(50,100);
		}else{
			betatest = false;
		}
		if (isCompleted()) {
			currentTask = null;
			RSNPC master = npcs.getNearest(currentMaster.getID());
			if (master != null) {
				if(inventory.getCount(MELEE_ARMOR) > 0){
					for (int i = 0; i < 10 && inventory.getCount(MELEE_ARMOR) > 0; i++) {
						inventory.getItem(MELEE_ARMOR).doClick(true);
						sleep(1000,1500);
					}
					CacheEquipItems();
				}
				if (master.isOnScreen()) {
					TalkTo("get-task",master.getID());
				}else{
					if (SM.Turael.getID() == currentMaster.getID()) {
						walkPath(PATHTOTRUAEL);
					} else if (SM.Vannaka.getID() == currentMaster.getID()) {
						WalkTileMM(master.getLocation(),true);
					} else {
						WalkTo(master);
					}
				}
			} else {
				if (SM.Turael.getID() == currentMaster.getID()) {
					if(!TravelyArea.contains(getMyPlayer().getLocation())){
						doTeleport(Teleport.Taverly);
					}else{
						walkPath(PATHTOTRUAEL);
					}
				}else if(SM.Vannaka.getID() == currentMaster.getID()){
					WalkToVannaka();
				}else {
					WalkTo(currentMaster.getLocation());
				}
			}
		} else {
			if (currentTask == null) {
				if(inventory.getItem(Enchanted_gem) == null){
					log("We miss Enchanted Gem");
					return -1;
				}
				inventory.getItem(Enchanted_gem).doAction("Kills-left");
				for(int i = 0 ; i < 30 && currentTask == null;i++,sleep(100,150));
			} else {
				//DREMAN_STFF
				if(currentTask.toLowerCase().contains("harpie bug") && (!inventory.contains(UNLIT_BUGLATERN) && !inventory.contains(LIT_BUGLATERN) && !equipcontain(LIT_BUGLATERN))){
					grabBankitem(UNLIT_BUGLATERN, 1);
				}else if(currentTask.toLowerCase().contains(new killerwatts().getName()) && (!inventory.contains(INSUALTED_BOOT) && !equipcontain(INSUALTED_BOOT))) {
					grabBankitem(INSUALTED_BOOT, 1);
				}else if(currentTask.toLowerCase().contains(new hillgiants().getName()) && !inventory.contains(BRASS_KEY)) {
					grabBankitem(BRASS_KEY, 1);
				}else if(currentTask.toLowerCase().contains(new turoths().getName()) && (!inventory.contains(LEAFSPEAR) && !equipcontain(LEAFSPEAR)) ){
					grabBankitem(LEAFSPEAR, 1);
				}else if(currentTask.toLowerCase().contains(new otherworldlybeings().getName()) && (!inventory.contains(DREMAN_STFF) && !equipcontain(DREMAN_STFF)) ){
					grabBankitem(DREMAN_STFF, 1);
				}else if(currentTask.toLowerCase().contains(new greendragons().getName()) && (!inventory.contains(ANTI_DRAGONSHIELD) && !equipcontain(ANTI_DRAGONSHIELD)) ){
					grabBankitem(ANTI_DRAGONSHIELD, 1);
				}else if(currentTask.toLowerCase().contains(new bluedragons().getName()) && (!inventory.contains(ANTI_DRAGONSHIELD) && !equipcontain(ANTI_DRAGONSHIELD)) ){
					grabBankitem(ANTI_DRAGONSHIELD, 1);
				}else if(currentTask.toLowerCase().contains(new bluedragons().getName()) && !inventory.contains(DUSTY_KEY) && skills.getRealLevel(Skill.AGILITY) < 70){
					grabBankitem(DUSTY_KEY, 1);
				}else if(currentTask.toLowerCase().contains(new cockatrices().getName()) && (!inventory.contains(MIRROR_SHIELD) && !equipcontain(MIRROR_SHIELD)) ){
					grabBankitem(MIRROR_SHIELD, 1);
				}else if(currentTask.toLowerCase().contains(new basilisks().getName()) && (!inventory.contains(MIRROR_SHIELD) && !equipcontain(MIRROR_SHIELD)) ){
					grabBankitem(MIRROR_SHIELD, 1);
				}else if(currentTask.toLowerCase().contains(new crocodiles().getName()) && !inventory.contains(WATERSKIN) ){
					grabBankitem(WATERSKIN, 15);
				}else if((!inventory.contains(CURRENT_FOOD) || !inventory.contains(CURRENT_TAB)) || bank.isOpen()){
					Bank();
				} else {
					boolean b = false;
					for (Task job : tasks) {
						if (currentTask.toLowerCase().contains(job.getName().toLowerCase())) {
							b = true;
							if (getMyPlayer().getHPPercent() < 50) {
								inventory.getItem(CURRENT_FOOD).doAction("Eat");
								sleep(300, 500);
							} else {
								job.Slay();
							}
						}
					}
					if(!b){
						Toolkit.getDefaultToolkit().beep();
					}
				}
			}
		}
		return random(50,100);
	}

	public void SleepTillStop(int i) {
		int x = i * 10;
		for (int j = 0; j < x&& (getMyPlayer().isMoving() || getMyPlayer().getAnimation() != -1); j++, sleep(100, 150));
	}
	public boolean canEquip(int id){
		return inventory.contains(id) && !equipcontain(id);
	}
	public void equip(int id, boolean isWeild){
		String action = isWeild ? "Wield" : "Wear";
		int count = inventory.getCount(id);
		inventory.getItem(id).doAction(action);
		for(int i = 0 ; i < 10 && inventory.getCount(id) == count;i++,sleep(100,150));
		CacheEquipItems();
	}
	public boolean atZanris(){
		int[] ids = {52504,52503,52502,52510,52508,52509};
		return nullcheck(true,ids);
	}
	public boolean nullcheck(boolean isObject,int ...id) {
		RSObject g = objects.getNearest(id);
		RSNPC n = npcs.getNearest(id);
		return isObject ? g != null : n != null;
	}
	public void climbDown(int id) {
		RSObject door = objects.getNearest(id);
		if (door != null) {
			if (door.isOnScreen()) {
				door.doClick();
				sleep(900, 1200);
				SleepTillStop(4);
			} else {
				if (calc.distanceTo(door.getLocation()) > 7) {
					WalkTileMM(door.getLocation(), true);
				} else {
					camera.turnToObject(door);
				}
			}
		}
	}
	public void openDoor(int id){
		RSObject door = objects.getNearest(id);
		if(door !=null){
			if(door.isOnScreen()){
				door.doAction("Open");
				sleep(900,1200);
				SleepTillStop(4);
			}else{
				if(calc.distanceTo(door.getLocation()) > 7){
					WalkTileMM(door.getLocation(),true);
				}else {
					camera.turnToObject(door);
				}
			}
		}
	}
	public abstract class Task {
		
		public abstract String getName();
		
		public abstract void Slay();
		
		public abstract SM getSlayerMaster();
		
	}

	public class otherworldlybeings extends Task{

		@Override
		public String getName() {
			return "otherworldly beings";
		}

		@Override
		public void Slay() {
			if (checkNPC("otherworldly being")) {
				if (canEquip(MELEE_WEPION)) {
					equip(MELEE_WEPION, true);
				} else {
					Attack("otherworldly being");
				}
			} else {
				int door = 2406;
				if(atZanris()){
					WalkTo(new RSTile(2387,4428));
				}else if(nullcheck(true,door)){
					if (canEquip(DREMAN_STFF)) {
						equip(DREMAN_STFF,true);
					} else {
						if (canContuine()) {
							doContuine(1);
						} else {
							openDoor(door);
						}
					}
				}else{
					WalkTo(new RSTile(3199, 3169));
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class killerwatts extends Task{

		@Override
		public String getName() {
			return "killerwatts";
		}
		RSTile[] PATHTOKILLER = new RSTile[] { new RSTile(3106, 3298, 0), new RSTile(3110, 3307, 0), new RSTile(3113, 3316, 0), 
				new RSTile(3110, 3325, 0), new RSTile(3109, 3334, 0), new RSTile(3109, 3343, 0), 
				new RSTile(3108, 3352, 0), new RSTile(3108, 3361, 0), new RSTile(3108, 3362, 0) };
		@Override
		public void Slay() {
			if (checkNPC("killerwatt")) {
				if (inventory.contains(INSUALTED_BOOT) && !equipcontain(INSUALTED_BOOT, true)) {
					inventory.getItem(INSUALTED_BOOT).doAction("Wear");
					sleep(1000, 1500);
					CacheEquipItems();
				} else {
					Attack("killerwatt");
				}
			} else {
				if (needTeleport(new RSTile(3108, 3362), Teleport.Draynor)) {
					doTeleport(Teleport.Draynor);
				} else {
					if (inventory.contains(INSUALTED_BOOT) && !equipcontain(INSUALTED_BOOT, true)) {
						inventory.getItem(INSUALTED_BOOT).doAction("Wear");
						sleep(1000, 1500);
						CacheEquipItems();
					}
					RSObject door = objects.getNearest(47424,47421);
					RSObject ladder = objects.getNearest(47364,47574,11355);
					RSObject d = objects.getNearest(47512);
					if(getMyPlayer().getLocation().getPlane() == 2 &&d != null){
						if(d.isOnScreen()){
							d.doClick();
							sleep(2000,3000);
						}else{
							WalkTileMM(d.getLocation());
						}
					}else if(ladder !=null && calc.canReach(ladder.getLocation(), true)){
						if(ladder.isOnScreen()){
							ladder.doClick();
							sleep(2000,3000);
						}else{
							WalkTileMM(ladder.getLocation());
						}
					}else if(door !=null && door.isOnScreen() && calc.canReach(new RSTile(3108,3351), false)){
						door.doClick();
						sleep(2000,3000);
					}else{
						walkPath(PATHTOKILLER);
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class werewolves extends Task{
		RSTile[] PATHTODUNGEON = new RSTile[] { new RSTile(3212, 3375, 0),
				new RSTile(3211, 3384, 0), new RSTile(3210, 3393, 0),
				new RSTile(3210, 3400, 0), new RSTile(3210, 3409, 0),
				new RSTile(3210, 3418, 0), new RSTile(3212, 3427, 0),
				new RSTile(3221, 3429, 0), new RSTile(3230, 3429, 0),
				new RSTile(3239, 3430, 0), new RSTile(3248, 3430, 0),
				new RSTile(3257, 3429, 0), new RSTile(3266, 3429, 0),
				new RSTile(3275, 3428, 0), new RSTile(3280, 3436, 0),
				new RSTile(3283, 3445, 0), new RSTile(3287, 3454, 0),
				new RSTile(3294, 3460, 0), new RSTile(3303, 3462, 0),
				new RSTile(3312, 3466, 0), new RSTile(3321, 3468, 0),
				new RSTile(3329, 3473, 0), new RSTile(3335, 3480, 0),
				new RSTile(3343, 3485, 0), new RSTile(3352, 3489, 0),
				new RSTile(3361, 3483, 0), new RSTile(3370, 3481, 0),
				new RSTile(3379, 3485, 0), new RSTile(3388, 3484, 0),
				new RSTile(3397, 3486, 0), new RSTile(3403, 3493, 0),
				new RSTile(3403, 3502, 0), new RSTile(3403, 3503, 0) };
		RSTile[] DungeonPath = new RSTile[] { new RSTile(3405, 9900),
				new RSTile(3405, 9896), new RSTile(3408, 9883),
				new RSTile(3415, 9888), new RSTile(3420, 9888),
				new RSTile(3425, 9895), new RSTile(3431, 9897),
				new RSTile(3437, 9897), new RSTile(3440, 9887) };
		RSTile[] PATHTOWOLF = new RSTile[] { new RSTile(3428, 3484),
				new RSTile(3442, 3488), new RSTile(3455, 3487),
				new RSTile(3463, 3478), new RSTile(3474, 3476) };
		int[] ladder = {30571,30572};
		@Override
		public String getName() {
			return "werewolves";
		}

		@Override
		public void Slay() {
			RSNPC n = npcs.getNearest(6033,6029,6026,6035,6034,6032,6044,6028);
			if(n !=null || checkNPC("werewolf")){
				if (checkNPC("werewolf")) {
					Attack("werewolf");
				} else {
					Attack(n.getID());
				}
			}else{
				RSObject dungeondoor = objects.getNearest(3445,3444,3443);
				if (dungeondoor != null) {
					if(dungeondoor.getID() == 3443 && calc.canReach(dungeondoor.getLocation(), true)){
						dungeondoor.doAction("Pass-through");
						sleep(1000,1500);
					}else if(dungeondoor.getID() == 3444 && dungeondoor.isOnScreen() && calc.canReach(new RSTile(3405, 9896), false)){
						dungeondoor.doAction("Open");
						sleep(1000,1500);
					}else if(dungeondoor.getID() == 3445 && dungeondoor.isOnScreen()&& calc.canReach(new RSTile(3430,9898),false)){
						dungeondoor.doAction("Open");
						sleep(1000,1500);
					}else{
						walkPath(DungeonPath);
					}
				} else if (calc.canReach(new RSTile(3434, 3482), false)) {
					walkPath(PATHTOWOLF);
				} else {
					RSObject la = objects.getNearest(ladder);
					RSObject door = objects.getNearest(24369, 24370);
					if (la != null) {
						if (la.isOnScreen()) {
							la.doClick();
							sleep(1000, 1500);
						} else {
							if (needTeleport(new RSTile(3403, 3503),
									Teleport.Varrock)) {
								doTeleport(Teleport.Varrock);
							} else {
								if(calc.distanceTo(la) < 7){
									camera.turnToObject(la);
								}
								walkPath(PATHTODUNGEON);
							}
						}
					} else if (door != null && door.isOnScreen()) {
						door.doClick();
						sleep(1000, 1500);
					} else {
						if (needTeleport(new RSTile(3403, 3503),
								Teleport.Varrock)) {
							doTeleport(Teleport.Varrock);
						} else {
							walkPath(PATHTODUNGEON);
						}
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class ghouls extends Task{
		RSTile[] PATHTODUNGEON = new RSTile[] { new RSTile(3212, 3375, 0),
				new RSTile(3211, 3384, 0), new RSTile(3210, 3393, 0),
				new RSTile(3210, 3400, 0), new RSTile(3210, 3409, 0),
				new RSTile(3210, 3418, 0), new RSTile(3212, 3427, 0),
				new RSTile(3221, 3429, 0), new RSTile(3230, 3429, 0),
				new RSTile(3239, 3430, 0), new RSTile(3248, 3430, 0),
				new RSTile(3257, 3429, 0), new RSTile(3266, 3429, 0),
				new RSTile(3275, 3428, 0), new RSTile(3280, 3436, 0),
				new RSTile(3283, 3445, 0), new RSTile(3287, 3454, 0),
				new RSTile(3294, 3460, 0), new RSTile(3303, 3462, 0),
				new RSTile(3312, 3466, 0), new RSTile(3321, 3468, 0),
				new RSTile(3329, 3473, 0), new RSTile(3335, 3480, 0),
				new RSTile(3343, 3485, 0), new RSTile(3352, 3489, 0),
				new RSTile(3361, 3483, 0), new RSTile(3370, 3481, 0),
				new RSTile(3379, 3485, 0), new RSTile(3388, 3484, 0),
				new RSTile(3397, 3486, 0), new RSTile(3403, 3493, 0),
				new RSTile(3403, 3502, 0), new RSTile(3403, 3503, 0) };
		RSTile[] DungeonPath = new RSTile[] { new RSTile(3405, 9900),
				new RSTile(3405, 9896), new RSTile(3408, 9883),
				new RSTile(3415, 9888), new RSTile(3420, 9888),
				new RSTile(3425, 9895), new RSTile(3431, 9897),
				new RSTile(3437, 9897), new RSTile(3440, 9887) };
		int[] ladder = {30571,30572};
		@Override
		public String getName() {
			return "ghouls";
		}

		@Override
		public void Slay() {
			if(checkNPC("ghoul")){
				Attack("ghoul");
			}else{
				RSObject dungeondoor = objects.getNearest(3445,3444,3443);
				if (dungeondoor != null) {
					if(dungeondoor.getID() == 3443 && calc.canReach(dungeondoor.getLocation(), true)){
						dungeondoor.doAction("Pass-through");
						sleep(1000,1500);
					}else if(dungeondoor.getID() == 3444 && dungeondoor.isOnScreen() && calc.canReach(new RSTile(3405, 9896), false)){
						dungeondoor.doAction("Open");
						sleep(1000,1500);
					}else if(dungeondoor.getID() == 3445 && dungeondoor.isOnScreen()&& calc.canReach(new RSTile(3430,9898),false)){
						dungeondoor.doAction("Open");
						sleep(1000,1500);
					}else{
						walkPath(DungeonPath);
					}
				} else if (calc.canReach(new RSTile(3434, 3482), false)) {
					WalkTo(new RSTile(3436,3464),true);
				} else {
					RSObject la = objects.getNearest(ladder);
					RSObject door = objects.getNearest(24369, 24370);
					if (la != null) {
						if (la.isOnScreen()) {
							la.doClick();
							sleep(1000, 1500);
						} else {
							if (needTeleport(new RSTile(3403, 3503),
									Teleport.Varrock)) {
								doTeleport(Teleport.Varrock);
							} else {
								if(calc.distanceTo(la) < 7){
									camera.turnToObject(la);
								}
								walkPath(PATHTODUNGEON);
							}
						}
					} else if (door != null && door.isOnScreen()) {
						door.doClick();
						sleep(1000, 1500);
					} else {
						if (needTeleport(new RSTile(3403, 3503),
								Teleport.Varrock)) {
							doTeleport(Teleport.Varrock);
						} else {
							walkPath(PATHTODUNGEON);
						}
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class hillgiants extends Task{

		int door = 1804,ladder = 12389;
		@Override
		public String getName() {
			return "hill giants";
		}

		@Override
		public void Slay() {
			if(checkNPC("hill giant")){
				Attack("hill giant");
			}else{
				RSObject d = objects.getNearest(door);
				RSObject l = objects.getNearest(ladder);
				if(l != null && calc.canReach(l.getLocation(), true)){
					l.doAction("Climb-down");
					sleep(2000,2500);
				}else if (d != null) {
					if(d.isOnScreen()){
						d.doAction("open");
						sleep(1000,1500);
					}else{
						WalkTileMM(d.getLocation(),true);
					}
				} else {
					WalkTo(new RSTile(3114, 3449), true);
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class shadowwarriors extends Task{

		@Override
		public String getName() {
			return "shadow warriors";
		}

		RSTile[] PATHTODOOR = new RSTile[] { new RSTile(2729, 3351),
				new RSTile(2729, 3359), new RSTile(2729, 3366),
				new RSTile(2728, 3372) };
		RSTile[] PATHTOSHADOW = new RSTile[] { new RSTile(2724, 9775),
				new RSTile(2730, 9769), new RSTile(2729, 9758),
				new RSTile(2721, 9752), new RSTile(2716, 9745),
				new RSTile(2711, 9737), new RSTile(2708, 9742),
				new RSTile(2706, 9752), new RSTile(2703, 9761) };
		@Override
		public void Slay() {
			if (checkNPC("shadow warrior")) {
				Attack("shadow warrior");
			} else {
				RSObject door = objects.getNearest(2896,2897);
				RSObject ladder = objects.getNearest(41425);
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if(x > 2690 && x < 2740 && y > 9750 && y < 9810){
					walkPath(PATHTOSHADOW);
				}else if(ladder!=null && calc.canReach(ladder.getLocation(), true)){
					if (ladder.isOnScreen()) {
						ladder.doAction("Climb-down");
						sleep(3000,3500);
					} else {
						turnTo(ladder.getLocation());
					}
				}else if(calc.canReach(new RSTile(2728,3352), false)){
					if (door != null && door.isOnScreen()) {
						door.doAction("Open");
						sleep(3000,3500);
					} else {
						walkPath(PATHTODOOR);
					}
				}else if (objects.getNearest(2391) != null && objects.getNearest(2391).isOnScreen()) {
					objects.getNearest(2391).doClick();
					sleep(1000, 1500);
				} else {
					WalkTo(new RSTile(2728, 3349), true);
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class harpiebug extends Task{

		RSTile[] PATHTOBUG = new RSTile[] { new RSTile(2952, 3146, 0),
				new RSTile(2943, 3146, 0), new RSTile(2934, 3148, 0),
				new RSTile(2925, 3150, 0), new RSTile(2916, 3151, 0),
				new RSTile(2907, 3153, 0), new RSTile(2899, 3159, 0),
				new RSTile(2890, 3162, 0), new RSTile(2881, 3167, 0),
				new RSTile(2872, 3170, 0), new RSTile(2866, 3177, 0),
				new RSTile(2860, 3184, 0), new RSTile(2853, 3190, 0),
				new RSTile(2845, 3195, 0), new RSTile(2835, 3196, 0),
				new RSTile(2826, 3191, 0), new RSTile(2820, 3185, 0),
				new RSTile(2811, 3184, 0), new RSTile(2802, 3180, 0),
				new RSTile(2793, 3179, 0), new RSTile(2784, 3179, 0),
				new RSTile(2775, 3180, 0), new RSTile(2767, 3181, 0),
				new RSTile(2761, 3174, 0), new RSTile(2760, 3165, 0),
				new RSTile(2762, 3156, 0), new RSTile(2766, 3147, 0),
				new RSTile(2771, 3139, 0), new RSTile(2778, 3133, 0),
				new RSTile(2785, 3127, 0), new RSTile(2790, 3125, 0),
				new RSTile(2795, 3116, 0), new RSTile(2803, 3110, 0),
				new RSTile(2812, 3107, 0), new RSTile(2821, 3105, 0),
				new RSTile(2830, 3104, 0), new RSTile(2839, 3104, 0),
				new RSTile(2848, 3104, 0), new RSTile(2857, 3105, 0),
				new RSTile(2865, 3110, 0), new RSTile(2868, 3113, 0) };
		int[] DOORS = {24370,24369};
		
		@Override
		public String getName() {
			return "harpie bug";
		}

		@Override
		public void Slay() {
			if(checkNPC("harpie bug")){
				if (inventory.contains(UNLIT_BUGLATERN)){
					inventory.getItem(UNLIT_BUGLATERN).doAction("Light");
					sleep(1000, 1500);
				}else if (inventory.contains(LIT_BUGLATERN)&& !equipcontain(LIT_BUGLATERN,true)) {
					inventory.getItem(LIT_BUGLATERN).doAction("Wield");
					sleep(1000, 1500);
					CacheEquipItems();
				} else {
					Attack("harpie bug");
				}
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
			if(Locations.Brimhaven.getArea().contains(getMyPlayer().getLocation())&& getMyPlayer().getLocation().getPlane() == 0){
				RSObject o = objects.getNearest(DOORS);
				if (o != null && o.isOnScreen()) {
					o.doClick();
					sleep(1000, 1400);
				} else {
					walkPath(PATHTOBUG);
				}
			}else if(x < 3000 && y < 3200 && getMyPlayer().getLocation().getPlane() == 1){
					RSObject o = objects.getNearest(2082);
					if(o != null){
						if(!o.isOnScreen()){
							camera.turnToTile(o.getLocation());
						}else{
							o.doClick();
							sleep(1000,1400);
						}
					}
				}else if (Locations.Portsarim.getArea().contains(getMyPlayer().getLocation())) {
					RSNPC g = npcs.getNearest(378);
					if(g != null){
						if(!g.isOnScreen()){
							WalkTo(new RSTile(3027, 3218));
						}else{
							TalkTo("Pay-fare",378);
						}
					}else{
						WalkTo(new RSTile(3027, 3218));
					}
				} else {
					WalkTo(new RSTile(3027, 3218));
				}
				
			}
		}
		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class turoths extends Task{


		int dungeon = 34395;
		RSTile[] PATHTOMONSTER= new RSTile[] { new RSTile(2804, 10004),
				new RSTile(2794, 9998), new RSTile(2785, 9998),
				new RSTile(2777, 10005), new RSTile(2783, 10016),
				new RSTile(2792, 10020), new RSTile(2802, 10023),
				new RSTile(2794, 10030), new RSTile(2786, 10035),
				new RSTile(2778, 10034), new RSTile(2767, 10036),
				new RSTile(2759, 10031), new RSTile(2763, 10022),
				new RSTile(2764, 10015), new RSTile(2761, 10003),
				new RSTile(2757, 9994), new RSTile(2743, 10003),
				new RSTile(2741, 10013), new RSTile(2744, 10021),
				new RSTile(2744, 10029), new RSTile(2734, 10032),
				new RSTile(2728, 10021), new RSTile(2718, 10030),
				new RSTile(2709, 10029), new RSTile(2704, 10023),
				new RSTile(2706, 10015), new RSTile(2713, 10012),
				new RSTile(2719, 10007), new RSTile(2721, 10002) };
		@Override
		public String getName() {
			return "turoths";
		}

		@Override
		public void Slay() {
			if(checkNPC("turoth")){
				if (inventory.contains(LEAFSPEAR)&& !equipcontain(LEAFSPEAR)) {
					inventory.getItem(LEAFSPEAR).doAction("Wield");
					sleep(1000, 1500);
					CacheEquipItems();
				} else {
					Attack("turoth");
				}
			}else{
				if(calc.distanceTo(new RSTile(2721, 10002)) < 15){
					return;
				}
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9950 && x < 2950 && y < 10150) {
					walkPath(PATHTOMONSTER);
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Enter");
							sleep(1000, 1200);
						} else {
							WalkTileMM(new RSTile(2792, 3614), true);
						}
					} else {
						if(needTeleport(new RSTile(2792, 3614),Teleport.Seers_Village)){
							doTeleport(Teleport.Seers_Village);
						}else{
						  WalkTileMM(new RSTile(2792, 3614),true);
						}
					}
				}
			}
		}
		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class basilisks extends Task{


		int dungeon = 34395;
		RSTile[] PATHTOFIEND = new RSTile[] { new RSTile(2804, 10004),
				new RSTile(2794, 9998), new RSTile(2785, 9998),
				new RSTile(2777, 10005), new RSTile(2783, 10016),
				new RSTile(2792, 10020), new RSTile(2802, 10023),
				new RSTile(2794, 10030), new RSTile(2786, 10035),
				new RSTile(2778, 10034), new RSTile(2767, 10036),
				new RSTile(2759, 10031), new RSTile(2763, 10022),
				new RSTile(2764, 10015), new RSTile(2761, 10003),
				new RSTile(2757,9994), new RSTile(2743,10003)};
		@Override
		public String getName() {
			return "basilisks";
		}

		@Override
		public void Slay() {
			if(checkNPC("basilisk")){
				if(inventory.contains(MIRROR_SHIELD) && !equipcontain(MIRROR_SHIELD)){
					inventory.getItem(MIRROR_SHIELD).doAction("Wield");
					sleep(1000,1500);
					CacheEquipItems();
				} else {
					Attack("basilisk");
				}
			}else{
				if(calc.distanceTo(new RSTile(2743,10003)) < 10){
					return;
				}
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9950 && x < 2950 && y < 10150) {
					walkPath(PATHTOFIEND);
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Enter");
							sleep(1000, 1200);
						} else {
							WalkTileMM(new RSTile(2792, 3614), true);
						}
					} else {
						if(needTeleport(new RSTile(2792, 3614),Teleport.Seers_Village)){
							doTeleport(Teleport.Seers_Village);
						}else{
						  WalkTileMM(new RSTile(2792, 3614),true);
						}
					}
				}
			}
		}
		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class cockatrices extends Task{


		int dungeon = 34395;
		RSTile[] PATHTOFIEND = new RSTile[] { new RSTile(2804, 10004),
				new RSTile(2794, 9998), new RSTile(2785, 9998),
				new RSTile(2777, 10005), new RSTile(2783, 10016),
				new RSTile(2792, 10020), new RSTile(2802, 10023),
				new RSTile(2794, 10030), new RSTile(2786, 10035),
				new RSTile(2778, 10034), new RSTile(2767, 10036),
				new RSTile(2759, 10031), new RSTile(2763, 10022),
				new RSTile(2764, 10015), new RSTile(2761, 10003) };
		@Override
		public String getName() {
			return "cockatrices";
		}

		@Override
		public void Slay() {
			if(checkNPC("cockatrice")){
				if(inventory.contains(MIRROR_SHIELD) && !equipcontain(MIRROR_SHIELD)){
					inventory.getItem(MIRROR_SHIELD).doAction("Wield");
					sleep(1000,1500);
					CacheEquipItems();
				} else {
					Attack("cockatrice");
				}
			}else{
				if(calc.distanceTo(new RSTile(2793,10035)) < 20){
					return;
				}
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9950 && x < 2950 && y < 10150) {
					walkPath(PATHTOFIEND);
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Enter");
							sleep(1000, 1200);
						} else {
							WalkTileMM(new RSTile(2792, 3614), true);
						}
					} else {
						if(needTeleport(new RSTile(2792, 3614),Teleport.Seers_Village)){
							doTeleport(Teleport.Seers_Village);
						}else{
						  WalkTileMM(new RSTile(2792, 3614),true);
						}
					}
				}
			}
		}


		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class mossgaints extends Task{

		int[] DOORS = {24370,24369};
		
		@Override
		public String getName() {
			return "moss giants";
		}

		@Override
		public void Slay() {
			if(checkNPC("moss giant") ){
				Attack("moss giant");
			}else{
				if(calc.distanceTo(new RSTile(2558,3404)) < 15){
					return;
				}
				WalkTo(new RSTile(2558,3404));
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
	}
		
	public class crocodiles extends Task{

		@Override
		public String getName() {
			return "crocodiles";
		}
		RSTile[] PATHTOCORD = new RSTile[] { new RSTile(3352, 3000, 0), new RSTile(3359, 2995, 0), new RSTile(3362, 2987, 0), 
				new RSTile(3363, 2979, 0), new RSTile(3359, 2972, 0), new RSTile(3359, 2964, 0), 
				new RSTile(3351, 2962, 0), new RSTile(3345, 2956, 0), new RSTile(3341, 2949, 0), 
				new RSTile(3335, 2941, 0), new RSTile(3327, 2938, 0), new RSTile(3320, 2934, 0), 
				new RSTile(3312, 2932, 0), new RSTile(3304, 2929, 0), new RSTile(3297, 2925, 0), 
				new RSTile(3292, 2918, 0) };
		RSArea CORDAREA = new RSArea(new RSTile[] { new RSTile(3279, 3010, 0), new RSTile(3400, 3014, 0), new RSTile(3374, 2921, 0), 
				new RSTile(3265, 2891, 0) });
		@Override
		public void Slay() {
			if(checkNPC("crocodile")){
				if(inventory.contains(EMPTYWATERSKIN)){
					inventory.getItem(EMPTYWATERSKIN).doAction("Drop");
					
				}
				Attack("crocodile");
			}else{
				if (getInterface(565, 13).isValid()) {
					getInterface(565, 13).doClick();
				} else {
					RSNPC g = npcs.getNearest(2291);
					if (g != null) {
						TalkTo("Travel", getCurrentTeleport(), 2291);
					} else {
						if(CORDAREA.contains(getMyPlayer().getLocation())){
							walkPath(PATHTOCORD);
						}else if(needTeleport(new RSTile(3305, 2928),Teleport.AL_Kharid)){
							doTeleport(Teleport.AL_Kharid);
						}else{
						  WalkTileMM(new RSTile(3305, 2928),true);
						}
					}
				}
			}
		}

		private String getCurrentTeleport() {
			int[] comps = {3,24,29,34,39};
			for(int i = 0 ; i < comps.length; i++){
				if(interfaces.get(1188).getComponent(comps[i]).getText().contains("north")){
					return i+1 + "";
				}
			}
			return 2 +"";
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class ankous extends Task{

		@Override
		public String getName() {
			return "ankous";
		}
		RSTile[] tile = new RSTile[] { new RSTile(2356, 5217), new RSTile(2356, 5220), new RSTile(2360, 5231), new RSTile(2360, 5234)};
		RSTile[] doortiles = new RSTile[] { new RSTile(2356, 5218),
				new RSTile(2356, 5221), new RSTile(2359, 5232),
				new RSTile(2360, 5235) };
		
		@Override
		public void Slay() {
			if(checkNPC("ankou")){
				Attack("ankou");
			}else{
				RSObject e = objects.getNearest(16043);
				if (e != null) {
					for (int i = 0; i < tile.length; i++) {
						if (calc.canReach(tile[i], false)) {
							RSObject g = objects.getTopAt(doortiles[i]);
							if (g == null)
								return;
							if (g.isOnScreen()) {
								g.doAction("Open");
								sleep(1000, 1500);
							} else {
								WalkTileMM(g.getLocation(), true);
							}
						}
					}
				}else{
					RSObject p1 = objects.getNearest(16150);
					RSObject p2 = objects.getNearest(16082);
					RSObject p3 = objects.getNearest(16116);
					RSObject l1 = objects.getNearest(16149);
					RSObject l2 = objects.getNearest(16081);
					RSObject l3 = objects.getNearest(16115);
					if (p1 != null && calc.distanceTo(p1) < 20) {
						doClick(p1);
					} else if (p2 != null && calc.distanceTo(p2) < 20) {
						doClick(p2);
					} else if (p3 != null && calc.distanceTo(p3) < 20) {
						doClick(p3);
					} else if (l1 != null && calc.distanceTo(l1) < 20) {
						doClick(l1);
					} else if (l2 != null && calc.distanceTo(l2) < 20) {
						doClick(l2);
					} else if (l3 != null && calc.distanceTo(l3) < 20) {
						doClick(l3);
					}else {
						RSObject x = objects.getNearest(16154);
						if (x != null) {
							if (x.isOnScreen()) {
								x.doAction("Climb-down");
								sleep(1500, 2500);
							} else {
								WalkTileMM(x.getLocation(), true);
							}
						} else {
							WalkTo(new RSTile(3081, 3424));
						}
					}
				}
			}
		}

		private void doClick(RSObject g){
			if(g.isOnScreen()){
				if (getInterface(572, 15).isValid()) {
					getInterface(572, 15).doClick();
				}else if (getInterface(579, 15).isValid()) {
					getInterface(579, 15).doClick();
				} else {
					g.doClick();
				}
				sleep(2000,3000);
			}else{
				WalkTileMM(g.getLocation(),true);
			}
		}
		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class bluedragons extends Task{
		int dungeon = 66991;
	     private final RSTile[] LADDERS_TO_GATE_ENTRANCE = { new RSTile(2884, 9800),
                 new RSTile(2884, 9802), new RSTile(2884, 9804),
                 new RSTile(2884, 9806), new RSTile(2884, 9808),
                 new RSTile(2884, 9810), new RSTile(2884, 9812),
                 new RSTile(2884, 9814), new RSTile(2884, 9817),
                 new RSTile(2884, 9820), new RSTile(2884, 9824),
                 new RSTile(2884, 9826), new RSTile(2885, 9829),
                 new RSTile(2885, 9832), new RSTile(2885, 9836),
                 new RSTile(2885, 9839), new RSTile(2883, 9842),
                 new RSTile(2885, 9844), new RSTile(2888, 9845),
                 new RSTile(2890, 9847), new RSTile(2893, 9849),
                 new RSTile(2897, 9849), new RSTile(2901, 9849),
                 new RSTile(2905, 9849), new RSTile(2909, 9849),
                 new RSTile(2913, 9849), new RSTile(2917, 9849),
                 new RSTile(2921, 9848), new RSTile(2924, 9846),
                 new RSTile(2926, 9843), new RSTile(2929, 9840),
                 new RSTile(2933, 9837), new RSTile(2935, 9833),
                 new RSTile(2937, 9830), new RSTile(2937, 9827),
                 new RSTile(2938, 9823), new RSTile(2938, 9820),
                 new RSTile(2938, 9816), new RSTile(2938, 9812),
                 new RSTile(2940, 9809), new RSTile(2941, 9806),
                 new RSTile(2942, 9802), new RSTile(2944, 9799),
                 new RSTile(2945, 9796), new RSTile(2949, 9795),
                 new RSTile(2951, 9791), new RSTile(2951, 9788),
                 new RSTile(2951, 9785), new RSTile(2951, 9782),
                 new RSTile(2951, 9779), new RSTile(2951, 9776),
                 new RSTile(2949, 9774), new RSTile(2946, 9774),
                 new RSTile(2943, 9776), new RSTile(2940, 9778),
                 new RSTile(2937, 9778), new RSTile(2935, 9775),
                 new RSTile(2935, 9772), new RSTile(2934, 9768),
                 new RSTile(2934, 9765), new RSTile(2934, 9762),
                 new RSTile(2935, 9759), new RSTile(2934, 9757),
                 new RSTile(2931, 9756), new RSTile(2928, 9756),
                 new RSTile(2925, 9756), new RSTile(2923, 9759),
                 new RSTile(2924, 9762), new RSTile(2924, 9765),
                 new RSTile(2925, 9768), new RSTile(2927, 9771),
                 new RSTile(2930, 9774), new RSTile(2930, 9777),
                 new RSTile(2931, 9781), new RSTile(2933, 9784),
                 new RSTile(2936, 9786), new RSTile(2935, 9789),
                 new RSTile(2934, 9792), new RSTile(2932, 9796),
                 new RSTile(2930, 9799), new RSTile(2928, 9802),
                 new RSTile(2924, 9803) };
		@Override
		public String getName() {
			return "blue dragons";
		}
		@Override
		public void Slay() {
			if(checkNPC("blue dragon")){
				if (inventory.contains(ANTI_DRAGONSHIELD)&& !equipcontain(ANTI_DRAGONSHIELD)) {
					inventory.getItem(ANTI_DRAGONSHIELD).doAction("Wear");
					sleep(1000, 1500);
					CacheEquipItems();
				} else {
					Attack("blue dragon");
				}
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9400) {
					if (skills.getRealLevel(Skill.AGILITY) >= 70) {
						RSObject g = objects.getNearest(9293);
						if(g !=null){
							if(g.isOnScreen()){
								g.doAction("Squeeze-through");
								sleep(3000,3500);
							}else{
								turnTo(g.getLocation());
							}
						}
					} else {
						RSObject g = objects.getNearest(2623);
						if (g != null && g.isOnScreen() ) {
							inventory.getItem(DUSTY_KEY).doAction("Use");
							g.doAction("Use");
							sleep(2000,3000);
						} else {
							walkPath(LADDERS_TO_GATE_ENTRANCE);
						}
					}
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Climb-down");
							sleep(1000, 1200);
						} else {
							WalkTo(new RSTile(2883, 3394));
						}
					} else {
						WalkTo(new RSTile(2883, 3394));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class greendragons extends Task{

		@Override
		public String getName() {
			return "green dragons";
		}
		RSArea Wildycut = new RSArea(new RSTile[] { new RSTile(3133, 3526, 0), new RSTile(3133, 3517, 0), new RSTile(3233, 3518, 0), 
				new RSTile(3233, 3526, 0) });
		@Override
		public void Slay() {
			if(checkNPC("green dragon")){
				if(inventory.contains(ANTI_DRAGONSHIELD) && !equipcontain(ANTI_DRAGONSHIELD)){
					inventory.getItem(ANTI_DRAGONSHIELD).doAction("Wear");
					sleep(1000,1500);
					CacheEquipItems();
				} else {
				Attack("green dragon");
				}
			}else{
				RSObject portal = objects.getNearest(77745);
				if (portal != null) {
					enterPortal(new RSTile(3290, 5463));
				}else{
				RSObject g = objects.getNearest(65203);
				if (g != null) {
					if(g.isOnScreen()){
							if (getInterface(676, 15).isValid()) {
								getInterface(676, 15).doClick();
							} else {
								g.doAction("Enter");
								sleep(2000, 3000);
							}
					}else{
						walktodungeon();
					}
				} else {
					walktodungeon();
				}
			}
			}
		}

		private void walktodungeon(){
			if (getInterface(382, 21).isValid()) {
				getInterface(382, 21).doClick();
			} 
			int y = getMyPlayer().getLocation().getY();
			RSObject g = objects.getNearest(new Filter<RSObject>(){

				@Override
				public boolean accept(RSObject g) {
					return g !=null&& g.getID() == 65076&& Wildycut.contains(g.getLocation());
				}
				
			});
			if (g != null && g.isOnScreen() && y < g.getLocation().getY()) {
				if (getInterface(676, 15).isValid()) {
					getInterface(676, 15).doClick();
				} else {
					g.doClick();
				}
				sleep(1000, 2500);
			} else {
				WalkTo(new RSTile(3163, 3562));
			}
		}
		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class infernamages extends Task{

		@Override
		public String getName() {
			return "infernal mages";
		}
		 RSTile[] portaltiles =  new RSTile[] { new RSTile(3185, 5478), new RSTile(3191, 5495), new RSTile(3210, 5477), new RSTile(3229, 5454) };
		
		@Override
		public void Slay() {
			if(checkNPC("infernal mage")){
				Attack("infernal mage");
			}else{
				RSObject dungeon = objects.getNearest(65200);
				RSObject portal = objects.getNearest(77745);
				if (portal != null) {
					for (RSTile t : portaltiles) {
						if (calc.canReach(t, true)) {
							enterPortal(t);
						}
					}
				} else {
					if(dungeon !=null){
						if(!dungeon.isOnScreen()){
							if (calc.distanceTo(dungeon) > 7) {
								walktodungeon();
							} else {
								camera.turnToObject(dungeon);
							}
						}else if (getInterface(676, 15).isValid()) {
							getInterface(676, 15).doClick();
							sleep(600,900);
						} else {
							climbDown(dungeon.getID());
						}
					} else {
						walktodungeon();
					}
				}
			}
		}

		private void walktodungeon(){
			int y = getMyPlayer().getLocation().getY();
			RSObject g = objects.getNearest(65081, 65083,65084,65078);
			if (getInterface(382, 21).isValid()) {
				getInterface(382, 21).doClick();
			}else if (g != null && g.isOnScreen()&& y < g.getLocation().getY()) {
				g.doClick();
				sleep(2000, 3500);
			} else {
				WalkTo(new RSTile(3060, 3550));
			}
		}
		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class earthwarriors extends Task{

		@Override
		public String getName() {
			return "earth warriors";
		}
		RSTile[] portaltiles = new RSTile[] { new RSTile(3171, 5478), new RSTile(3141, 5480), new RSTile(3159, 5501), new RSTile(3165, 5515), new RSTile(3156, 5523) };
		@Override
		public void Slay() {
			if(checkNPC("earth warrior")){
				Attack("earth warrior");
			}else{
				RSObject dungeon = objects.getNearest(65200);
				RSObject portal = objects.getNearest(77745);
				if (portal != null) {
					for(RSTile t : portaltiles){
						if(calc.canReach(t, true)){
							enterPortal(t);
						}
					}
				} else {
					if (dungeon != null) {
						if (dungeon.isOnScreen()) {
							 if (getInterface(676, 15).isValid()) {
								getInterface(676, 15).doClick();
							} else {
								dungeon.doAction("Enter");
							}
							sleep(2000, 3000);
						} else {
							walktodungeon();
						}
					} else {
						walktodungeon();
					}
				}
			}
		}
		private void walktodungeon(){
			if (getInterface(382, 21).isValid()) {
				getInterface(382, 21).doClick();
			} 
				
			int y = getMyPlayer().getLocation().getY();
			RSObject g = objects.getNearest(65081, 65083);
			if (g != null && g.isOnScreen()&& y < g.getLocation().getY()) {
				g.doClick();
				sleep(2000, 3500);
			} else {
				WalkTo(new RSTile(3060, 3550));
			}
		}
		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class lesserdemons extends Task{
		RSTile[] PATHTOCAVE = new RSTile[] { new RSTile(2950, 3146, 0), new RSTile(2942, 3146, 0), new RSTile(2934, 3148, 0), 
				new RSTile(2926, 3151, 0), new RSTile(2918, 3152, 0), new RSTile(2910, 3153, 0), 
				new RSTile(2902, 3155, 0), new RSTile(2895, 3159, 0), new RSTile(2887, 3163, 0), 
				new RSTile(2879, 3162, 0), new RSTile(2871, 3162, 0), new RSTile(2863, 3162, 0), 
				new RSTile(2859, 3165, 0), new RSTile(2854, 3167, 0) };
		@Override
		public String getName() {
			return "lesser demons";
		}

		@Override
		public void Slay() {
			if (checkNPC("lesser demon")) {
				Attack("lesser demon");
			} else {
				if(calc.distanceTo(new RSTile(2852,9571)) < 40){
					return;
				}
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if(BrimhavenArea.contains(getMyPlayer().getLocation()) && getMyPlayer().getLocation().getPlane() == 0 ){
					RSObject o = objects.getNearest(492);
					if (o != null) {
						if (!o.isOnScreen()) {
							WalkTileMM(o.getLocation(),true);
						} else {
							o.doClick();
							sleep(1000, 1400);
						}
					}else{
					walkPath(PATHTOCAVE);
					}
				}else if (x < 3000 && y < 3200 && getMyPlayer().getLocation().getPlane() == 1) {
						RSObject o = objects.getNearest(2082);
						if (o != null) {
							if (!o.isOnScreen()) {
								camera.turnToTile(o.getLocation());
							} else {
								o.doClick();
								sleep(1000, 1400);
							}
						}
					} else if (Locations.Portsarim.getArea().contains(
							getMyPlayer().getLocation())) {
						RSNPC g = npcs.getNearest(378);
						if (g != null) {
							if (!g.isOnScreen()) {
								WalkTo(new RSTile(3027, 3218));
							} else {
								TalkTo("Pay-fare", 378);
							}
						} else {
							WalkTo(new RSTile(3027, 3218));
						}
					} else {
						WalkTo(new RSTile(3027, 3218));
					}

				
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class ogres extends Task{

		@Override
		public String getName() {
			return "ogres";
		}
		RSTile[] PATHTOORGE = new RSTile[] { new RSTile(2524, 3094, 0), new RSTile(2517, 3098, 0), new RSTile(2509, 3099, 0), 
				new RSTile(2502, 3096, 0) };
		@Override
		public void Slay() {
			if (checkNPC("ogre")) {
				Attack("ogre");
			} else {
				if (needTeleport(new RSTile(2502, 3096))) {
					doTeleport(getClosetTeleport(new RSTile(2502, 3096)));
				} else {
					walkPath(PATHTOORGE);
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	
	public class icewarriors extends Task{

		@Override
		public String getName() {
			return "ice warriors";
		}

		@Override
		public void Slay() {
			if(checkNPC("ice warrior")){
				Attack("ice warrior");
			}else{
				int x = getMyPlayer().getLocation().getX(), y = getMyPlayer()
						.getLocation().getY();
				if (x > 2900 && x < 3150 && y > 9400 && y < 9600) {
					walkPath(PATHTOICEGAINT);
				} else {
					RSObject g = objects.getNearest(9472);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doClick();
							sleep(2000, 2500);
						} else {
							WalkTileMM(g.getLocation(), true);
						}
					} else {
						WalkTo(new RSTile(3009, 3147));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class icegaints extends Task{

		@Override
		public String getName() {
			return "ice giants";
		}

		@Override
		public void Slay() {
			RSNPC b = npcs.getNearest(4687,3072);
			if(b != null){
				Attack(b.getID());
			}else{
				int x = getMyPlayer().getLocation().getX(), y = getMyPlayer()
						.getLocation().getY();
				if (x > 2900 && x < 3150 && y > 9400 && y < 9600) {
					walkPath(PATHTOICEGAINT);
				} else {
					RSObject g = objects.getNearest(9472);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doClick();
							sleep(2000, 2500);
						} else {
							WalkTileMM(g.getLocation(), true);
						}
					} else {
						WalkTo(new RSTile(3009, 3147));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
		
	}
	public class bears extends Task{

		@Override
		public String getName() {
			return "bears";
		}

		@Override
		public void Slay() {
			if(checkNPC("bear")){
				Attack("bear");
			}else{
				WalkTo(new RSTile(2700, 3332));
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class trolls extends Task{
		RSArea TROLL_AREA = new RSArea(new RSTile[] { new RSTile(2841, 3594, 0), new RSTile(2875, 3593, 0), new RSTile(2882, 3576, 0), 
				new RSTile(2848, 3579, 0) });
	
		@Override
		public String getName() {
			return "trolls";
		}

		@Override
		public void Slay() {
			if (checkNPC("Mountain troll") && TROLL_AREA.contains(getMyPlayer().getLocation())) {
				Attack("Mountain troll");
			} else {
				if (needTeleport(new RSTile(2867, 3584))) {
					doTeleport(getClosetTeleport(new RSTile(2867, 3584)));
				} else {
					walkPath(PATHTOTROLL);
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class zombies extends Task{

		@Override
		public String getName() {
			return "zombies";
		}

		@Override
		public void Slay() {
			if (checkNPC("zombie")) {
				Attack("zombie");
			} else {
				if (calc.distanceTo(new RSTile(3143, 9899)) < 15) {
					return;
				}
				int x = getMyPlayer().getLocation().getX(), y = getMyPlayer()
						.getLocation().getY();
				if (x > 3000 && x < 3200 && y > 9800 && y < 9950) {
					RSObject door = objects.getNearest(29316);
					if (door != null && door.isOnScreen()) {
						door.doClick();
						sleep(3000,5500);
					}else{
					walkPath(EDGEVILLAGEDUGNEONTOZOMBIE);
					}
				}else{
					RSObject trapdoor = objects.getNearest(26934,26933);
					if (trapdoor != null) {
						if(trapdoor.isOnScreen()){
							trapdoor.doClick();
							sleep(1000,1500);
						}else{
							WalkTileMM(trapdoor.getLocation(),true);
						}
					}else{
						WalkTo(new RSTile(3095,3469));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class spiders extends Task{

		@Override
		public String getName() {
			return "spiders";
		}

		@Override
		public void Slay() {
			if(checkNPC("spider") && calc.distanceTo(new RSTile(3214,9620)) < 30){
				Attack("spider");
			}else{
				if(calc.distanceTo(new RSTile(3214,9620)) < 30){
					return;
				}
				RSObject g = objects.getNearest(36687);
				if(g !=null){
					if(g.isOnScreen()){
						g.doAction("Climb-down");
						sleep(2000,2500);
					}else{
						WalkTileMM(g.getLocation(),true);
					}
				}else{
					WalkTo(new RSTile(3208,3213));
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
	}
	public class dwarves extends Task{

		@Override
		public String getName() {
			return "dwarves";
		}

		@Override
		public void Slay() {
			if(checkNPC("dwarf") && calc.distanceTo(new RSTile(3019,9843)) < 50){
				Attack("dwarf");
			}else{
				if(calc.distanceTo(new RSTile(3019,9843)) < 50){
					return;
				}
				RSObject g = objects.getNearest(30942);
				if(g !=null){
					if(g.isOnScreen()){
						g.doAction("Climb-down");
						sleep(2000,2500);
					}else{
						WalkTileMM(g.getLocation(),true);
					}
				}else{
					WalkTo(new RSTile(3016,3448));
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	
	public class minotaurs extends Task{

		
		@Override
		public String getName() {
			return "minotaurs";
		}
		RSTile[] tile = new RSTile[] { new RSTile(1858, 5239), new RSTile(1858, 5236), new RSTile(1864, 5227), new RSTile(1867, 5227) };
		RSTile[] doortiles = new RSTile[] { new RSTile(1859, 5238), new RSTile(1859, 5235), new RSTile(1865, 5226), new RSTile(1868, 5226) };
		@Override
		public void Slay() {
			if(checkNPC("minotaur")){
				Attack("minotaur");
			}else{
				RSObject e = objects.getNearest(16124);
				if (e != null) {
					for (int i = 0; i < tile.length; i++) {
						if (calc.canReach(tile[i], false)) {
							RSObject g = objects.getTopAt(doortiles[i]);
							if (g == null)
								return;
							if (g.isOnScreen()) {
								g.doAction("Open");
								sleep(1000, 1500);
							} else {
								WalkTileMM(g.getLocation(), true);
							}
						}
					}
				}else{
					RSObject x = objects.getNearest(16154);
					if (x != null) {
						if(x.isOnScreen()){
							x.doAction("Climb-down");
							sleep(1500,2500);
						}else{
							WalkTileMM(x.getLocation(),true);
						}
					}else{
						WalkTo(new RSTile(3081,3424));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class grotworms extends Task{

		
		@Override
		public String getName() {
			return "grotworms";
		}

		@Override
		public void Slay() {
			if(checkNPC("Young grotworm")){
				Attack("Young grotworm");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 1150 && y > 6250 && x < 1300 && y < 6450) {
					if(calc.distanceTo(new RSTile(1166,6468)) > 15){
						WalkTo(new RSTile(1174,6366));
					}else{
						return;
					}
				} else {
					RSObject g = objects.getNearest(70792);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Enter");
							sleep(2000, 2500);
						} else {
							WalkTileMM(g.getLocation(), true);
						}
					} else {
						WalkTo(new RSTile(2991, 3235));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.All;
		}
		
	}
	public class scorpions extends Task{

	
		@Override
		public String getName() {
			return "scorpions";
		}

		@Override
		public void Slay() {
			if(checkNPC("scorpion")){
				Attack("scorpion");
			}else{
				WalkTo(new RSTile(3304,3284));
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class dogs extends Task{

		int[] DOORS = {24370,24369};
		@Override
		public String getName() {
			return "dogs";
		}

		@Override
		public void Slay() {
			if(checkNPC("dog")){
				Attack("dog");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if(x > 2500 && y > 9500 && y < 9650 && x < 2750 && !Vine_Area.contains(getMyPlayer().getLocation())){
					return;
				}
				RSObject vine = objects.getNearest(new Filter<RSObject>(){
					@Override
					public boolean accept(RSObject g) {
						return g !=null && g.getID() == 77371 && Vine_Area.contains(g.getLocation());
					}
					
				});
				if(vine != null && Vine_Area.contains(getMyPlayer().getLocation())){
					if(vine.isOnScreen()){
						vine.doAction("Chop-down");
						sleep(2000,2500);
					}else{
						WalkTileMM(vine.getLocation(),true);
					}
				} else {
				if(BrimhavenArea.contains(getMyPlayer().getLocation()) &&  getMyPlayer().getLocation().getPlane() == 0){
					RSObject dungeon = objects.getNearest(5083);
					if (dungeon != null) {
						if(dungeon.isOnScreen()){
							RSNPC n = npcs.getNearest(1595);
							if(n.isOnScreen()){
								TalkTo("Pay",n.getID());
								camera.turnToTile(dungeon.getLocation());
								dungeon.doAction("Enter");
							}else{
								camera.turnToCharacter(n);
							}
						}else{
							WalkTileMM(dungeon.getLocation(),true);
						}
					} else {
						RSObject o = objects.getNearest(DOORS);
						if (o != null && o.isOnScreen()) {
							o.doClick();
							sleep(1000, 1400);
						} else {
							walkPath(PATHTOBRIMHAVENDUNGEON);
						}
					}
				}else if(x < 3000 && y < 3200 && getMyPlayer().getLocation().getPlane() == 1){
					RSObject o = objects.getNearest(2082);
					if(o != null){
						if(!o.isOnScreen()){
							camera.turnToTile(o.getLocation());
						}else{
							o.doClick();
							sleep(1000,1400);
						}
					}
				}else if (Locations.Portsarim.getArea().contains(getMyPlayer().getLocation())) {
					RSNPC g = npcs.getNearest(378);
					if(g != null){
						if(!g.isOnScreen()){
							WalkTo(new RSTile(3027, 3218));
						}else{
							TalkTo("Pay-fare",378);
						}
					}else{
						WalkTo(new RSTile(3027, 3218));
					}
				} else {
					WalkTo(new RSTile(3027, 3218));
				}
				
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class monkeys extends Task{

		RSTile[] PATHTOKARAMJA = new RSTile[] { new RSTile(2953, 3146, 0),
				new RSTile(2945, 3146, 0), new RSTile(2937, 3147, 0),
				new RSTile(2929, 3148, 0), new RSTile(2921, 3151, 0),
				new RSTile(2913, 3151, 0), new RSTile(2905, 3154, 0),
				new RSTile(2898, 3158, 0), new RSTile(2891, 3163, 0),
				new RSTile(2885, 3162, 0) };
		
		@Override
		public String getName() {
			return "monkeys";
		}

		@Override
		public void Slay() {
			if(checkNPC("monkey")){
				Attack("monkey");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
			if(BrimhavenArea.contains(getMyPlayer().getLocation()) && getMyPlayer().getLocation().getPlane() == 0 ){
				walkPath(PATHTOKARAMJA);
			}else if (x < 3000 && y < 3200 && getMyPlayer().getLocation().getPlane() == 1) {
					RSObject o = objects.getNearest(2082);
					if (o != null) {
						if (!o.isOnScreen()) {
							camera.turnToTile(o.getLocation());
						} else {
							o.doClick();
							sleep(1000, 1400);
						}
					}
				} else if (Locations.Portsarim.getArea().contains(
						getMyPlayer().getLocation())) {
					RSNPC g = npcs.getNearest(378);
					if (g != null) {
						if (!g.isOnScreen()) {
							WalkTo(new RSTile(3027, 3218));
						} else {
							TalkTo("Pay-fare", 378);
						}
					} else {
						WalkTo(new RSTile(3027, 3218));
					}
				} else {
					WalkTo(new RSTile(3027, 3218));
				}

			}
		}
		
		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class goblins extends Task{

		@Override
		public String getName() {
			return "goblins";
		}

		@Override
		public void Slay() {
			if(checkNPC("goblin")){
				Attack("goblin");
			}else{
				WalkTo(new RSTile(3247, 3235));
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class skeletons extends Task{

		int dungeon = 66991;
		
		@Override
		public String getName() {
			return "skeletons";
		}
		@Override
		public void Slay() {
			if (checkNPC("skeleton")) {
				Attack("skeleton");
			} else {
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9400) {
					WalkTo(new RSTile(2884,9819));
				}
				RSObject g = objects.getNearest(dungeon);
				if (g != null) {
					if (g.isOnScreen()) {
						g.doAction("Climb-down");
						sleep(1000, 1200);
					} else {
						WalkTo(new RSTile(2883, 3394));
					}
				} else {
					WalkTo(new RSTile(2883, 3394));
				}

			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	
	public class ghosts extends Task{

		int dungeon = 66991;
		
		@Override
		public String getName() {
			return "ghosts";
		}

		@Override
		public void Slay() {
			if(checkNPC("ghost")){
				Attack("ghost");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9400) {
					WalkTo(new RSTile(2893,9849));
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Climb-down");
							sleep(1000, 1200);
						} else {
							WalkTo(new RSTile(2883, 3394));
						}
					} else {
						WalkTo(new RSTile(2883, 3394));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class bats extends Task{

		int dungeon = 66991;
		
		@Override
		public String getName() {
			return "bats";
		}

		@Override
		public void Slay() {
			if(checkNPC("bat")){
				Attack("bat");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9400) {
					WalkTo(new RSTile(2915,9849));
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Climb-down");
							sleep(1000, 1200);
						} else {
							WalkTo(new RSTile(2883, 3394));
						}
					} else {
						WalkTo(new RSTile(2883, 3394));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class cows extends Task{

		@Override
		public String getName() {
			return "cows";
		}

		@Override
		public void Slay() {
			if(checkNPC("Cow")){
				Attack("Cow");
			}else{
				if(calc.distanceTo(new RSTile(2913,3429)) < 9){
					return;
				}
				if(!TravelyArea.contains(getMyPlayer().getLocation())){
					doTeleport(Teleport.Taverly);
				}else{
					walkPath(PATHTOTRUAEL);
				}
			}
		}
		@Override
		public SM getSlayerMaster() {
		  return SM.Turael;
		}
	}
	public class caveslime extends Task{

		int dungeon = 5947;
		
		@Override
		public String getName() {
			return "cave slime";
		}

		@Override
		public void Slay() {
			if(checkNPC("cave slime")){
				if (inventory.getCount(36) != 0 && inventory.getCount(33) == 0) {
					inventory.getItem(36).doClick(true);
				}
				Attack("cave slime");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 3000 && y > 9450) {
					WalkTo(new RSTile(3147,9572));
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (inventory.getCount(33) == 0) {
							if (inventory.getCount(36) != 0) {
								inventory.getItem(36).doClick(true);
							} else {
								TalkTo("Talk-to", 1834);
							}
						} else {
							if (g.isOnScreen()) {
								if (getInterface(572, 15).isValid()) {
									getInterface(572, 15).doClick();
								} else {
									g.doAction("Climb-down");
								}
								sleep(1000, 1200);
							} else {
								WalkTileMM(new RSTile(3168,3172),true);
							}
						}
					} else {
						WalkTo(new RSTile(3170,3173));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class pyrefiends extends Task{

		int dungeon = 34395;
		RSTile[] PATHTOFIEND = new RSTile[] { new RSTile(2804, 10004),
				new RSTile(2794, 9998), new RSTile(2785, 9998),
				new RSTile(2777, 10005), new RSTile(2783, 10016),
				new RSTile(2792, 10020), new RSTile(2802, 10023),
				new RSTile(2794, 10030), new RSTile(2786, 10035),
				new RSTile(2778, 10034), new RSTile(2767, 10036),
				new RSTile(2759, 10031), new RSTile(2763, 10022),
				new RSTile(2764, 10015), new RSTile(2761, 10003) };
		@Override
		public String getName() {
			return "pyrefiends";
		}

		@Override
		public void Slay() {
			if(checkNPC("pyrefiend")){
				Attack("pyrefiend");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 2600 && y > 9950 && x < 2950 && y < 10150) {
					walkPath(PATHTOFIEND);
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (g.isOnScreen()) {
							g.doAction("Enter");
							sleep(1000, 1200);
						} else {
							WalkTileMM(new RSTile(2792, 3614), true);
						}
					} else {
						if(needTeleport(new RSTile(2792, 3614),Teleport.Seers_Village)){
							doTeleport(Teleport.Seers_Village);
						}else{
						  WalkTileMM(new RSTile(2792, 3614),true);
						}
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Vannaka;
		}
	}
	public class cavebugs extends Task{

		int dungeon = 5947;
		
		@Override
		public String getName() {
			return "cave bugs";
		}

		@Override
		public void Slay() {
			if(checkNPC("cave bug")){
				if (inventory.getCount(36) != 0 && inventory.getCount(33) == 0) {
					inventory.getItem(36).doClick(true);
				}
				Attack("cave bug");
			}else{
				int x = getMyPlayer().getLocation().getX(),y = getMyPlayer().getLocation().getY();
				if (x > 3000 && y > 9450) {
					WalkTo(new RSTile(3147,9572));
				} else {
					RSObject g = objects.getNearest(dungeon);
					if (g != null) {
						if (inventory.getCount(33) == 0) {
							if (inventory.getCount(36) != 0) {
								inventory.getItem(36).doClick(true);
							} else {
								TalkTo("Talk-to", 1834);
							}
						} else {
							if (g.isOnScreen()) {
								if (getInterface(572, 15).isValid()) {
									getInterface(572, 15).doClick();
								} else {
									g.doAction("Climb-down");
								}
								sleep(1000, 1200);
							} else {
								WalkTileMM(new RSTile(3168,3172),true);
							}
						}
					} else {
						WalkTo(new RSTile(3170,3173));
					}
				}
			}
		}

		@Override
		public SM getSlayerMaster() {
			return SM.Turael;
		}
		
	}
	public class bird extends Task{

		@Override
		public String getName() {
			return "bird";
		}

		@Override
		public void Slay() {
			if(checkNPC("chicken")){
				Attack("chicken");
			}else{
				WalkTo(new RSTile(3029, 3285));
			}
		}
		@Override
		public SM getSlayerMaster() {
		  return SM.Turael;
		}
	}

	public class icefiends extends Task{

		@Override
		public String getName() {
			return "icefiends";
		}

		@Override
		public void Slay() {
			RSNPC g = npcs.getNearest(7716);
			if(g != null){
				Attack(g.getID());
			}else{
				WalkTo(new RSTile(3007,3477),true);
			}
		}
		@Override
		public SM getSlayerMaster() {
		  return SM.Turael;
		}
	}
	public enum Locations{
		Brimhaven(BrimhavenArea,"Brimhaven"),
		Travely(TravelyArea,"Travely"),
		Portsarim(PortsarimArea,"Port sarim");
		
		private RSArea area;
		private String name;
		
		Locations(RSArea area , String name){
			this.area = area;
			this.name = name;
		}

		private String getName(){
			return this.name;
		}
		private RSArea getArea(){
			return this.area;
		}
	}
	public enum SM{
		Turael(new RSTile(2911,3422),"Turael",8480),
		Vannaka(null,"Vannaka",1597),
		All(null,"All",-1);
		private RSTile tile;
		private String name;
		private int id;
		
		SM(RSTile tile , String name,int id){
			this.tile = tile;
			this.name = name;
			this.id = id;
		}
		private int getID(){
			return this.id;
		}
		private String getName(){
			return this.name;
		}
		private RSTile getLocation(){
			return this.tile;
		}
	}
	public boolean isCompleted(){
		return settings.getSetting(183) == 0;
	}
	private void CacheEquipItems() {
		game.openTab(Game.TAB_EQUIPMENT);
		equipment.getItems();
	}
	public void Attack(final int id){
		if(walking.getEnergy() > 40 && !walking.isRunEnabled()){
			walking.setRun(true);
		}
		RSNPC g = npcs.getNearest(new Filter<RSNPC>() {
			@Override
			public boolean accept(RSNPC npc) {
				return npc != null
						&& npc.getID() == id
						&& !npc.isInCombat()
						&& calc.canReach(npc.getLocation(), false) 
						&& calc.distanceTo(npc) <30 
						&& npc.getLevel() !=0;
			}
		});
		if(getMyPlayer().getInteracting() == null && g !=null){
			if(g.isOnScreen()){
				g.doAction("Attack",g.getName());
				sleep(400,900);
				for(int i = 0 ; i < 30 && getMyPlayer().isMoving();i++,sleep(100,150));
			}else{
				if (calc.distanceTo(g.getLocation()) > 7) {
					turnTo(g.getLocation());
				} else {
					WalkTo(g);
				}
			}
		}else if(getMyPlayer().getInteracting() != null){
			CastSpells();
		}
	}
	
	public void enterPortal(final RSTile tile){
		final int id = 77745;
		RSObject g = objects.getNearest(new Filter<RSObject>(){
			@Override
			public boolean accept(RSObject g) {
				return g !=null 
						&& g.getID() == id 
						&& g.getLocation().equals(tile)
						&&calc.canReach(g.getLocation(), true);
			}
		});
		if (g != null) {
			if (g.isOnScreen()) {
				g.doAction("Enter");
				sleep(1000, 1500);
				for (int i = 0; i < 20 && getMyPlayer().getAnimation() != -1; i++, sleep(100, 150));
			} else {
				WalkTileMM(tile, true);
			}
		}
	}
	public void Attack(final String name){
		if(walking.getEnergy() > 40 && !walking.isRunEnabled()){
			walking.setRun(true);
		}
		RSNPC g = npcs.getNearest(new Filter<RSNPC>() {
			@Override
			public boolean accept(RSNPC npc) {
				return npc != null
						&& npc.getName().toLowerCase().contains(name.toLowerCase())
						&& !npc.isInCombat()
						&& calc.canReach(npc.getLocation(), false) 
						&& calc.distanceTo(npc) <30 
						&& npc.getLevel() !=0;
			}
		});
		if(getMyPlayer().getInteracting() == null && g !=null){
			if(g.isOnScreen()){
				g.doAction("Attack",g.getName());
				sleep(400,900);
				for(int i = 0 ; i < 30 && getMyPlayer().isMoving();i++,sleep(100,150));
			}else{
				if (calc.distanceTo(g.getLocation()) > 7) {
					turnTo(g.getLocation());
				} else {
					WalkTo(g);
				}
			}
		}else if(getMyPlayer().getInteracting() != null){
			CastSpells();
		}
	}
	private void CastSpells() {
		if (getInterface(640, 3).isVisible()) {
			getInterface(640, 3).doClick(true);
		}
		if (getInterface(137, 56).getText() != null
				&& !getInterface(137, 56).getText().contains("Press Enter")) {
			keyboard.sendText("1", true);
		}
		if(getMyPlayer().getAdrenalinePercent() == 100 && (getMyPlayer().getInteracting() !=null && getMyPlayer().getInteracting().getHPPercent() > 40)){
			CombatStatus = "Activating Ulitamte Ability";
			if(abilities.isEnabled(ActionSlot.SLOT_7)){
				keyboard.sendText(abilities.getKeyBind(ActionSlot.SLOT_7) +  "", false);
				sleep(500,900);
			}
		}else{
			CombatStatus = "Recharging adrenaline bar";
			if (!EOC_TIMER.isRunning()) {
				int hotkey = getHotKey(EOC_KEY);
				ActionSlot[] BASIC_ABILTY = { ActionSlot.SLOT_1,
						ActionSlot.SLOT_2, ActionSlot.SLOT_3,
						ActionSlot.SLOT_4, ActionSlot.SLOT_5, ActionSlot.SLOT_6 };
				if (abilities.isEnabled(BASIC_ABILTY[hotkey - 1])) {
					keyboard.sendText(abilities.getKeyBind(BASIC_ABILTY[hotkey - 1]) + "",false);
					EOC_KEY.add(hotkey);
					EOC_TIMER = new Timer(random(1500, 2500));
				}
			}
		}
	}

	private int getHotKey(ArrayList<Integer> hotkeys2) {
		if (!hotkeys2.contains(1)) {
			return 1;
		} else if (!hotkeys2.contains(2)) {
			return 2;
		} else if (!hotkeys2.contains(3)) {
			return 3;
		} else if (!hotkeys2.contains(4)) {
			return 4;
		} else if (!hotkeys2.contains(5)) {
			return 5;
		} 
		hotkeys2.clear();
		return 1;
	}
	public void TalkTo(String action,String op,int... id) {
		RSComponent Chat = interfaces.get(1191).getComponent(18), Chat1 = interfaces
				.get(1184).getComponent(18), option = interfaces.get(1188)
				.getComponent(3);
		if (Chat.isValid()) {
			keyboard.sendText(" ", false);
			for (int i = 0; i < 10 && Chat.isValid(); i++, sleep(100, 150))
				;
		} else if (Chat1.isValid()) {
			keyboard.sendText(" ", false);
			for (int i = 0; i < 10 && Chat1.isValid(); i++, sleep(100, 150))
				;
		} else if (option.isValid()) {
			keyboard.sendText(op, false);
			for (int i = 0; i < 10 && option.isValid(); i++, sleep(100, 150))
				;
		} else {
			RSNPC g = npcs.getNearest(id);
			if (g == null)
				return;
			if(!g.isOnScreen()){
				turnTo(g.getLocation());
			}
			g.doAction(action);
			for (int i = 0; i < 10 && !Chat.isValid(); i++, sleep(100, 150))
				;
		}
	}

	public boolean canContuine() {
		RSComponent Chat = interfaces.get(1191).getComponent(18), Chat1 = interfaces
				.get(1184).getComponent(18), option = interfaces.get(1188)
				.getComponent(3);
		return Chat.isValid() || Chat1.isValid() || option.isValid();
	}
	public void doContuine(int options){
		RSComponent Chat = interfaces.get(1191).getComponent(18), Chat1 = interfaces
				.get(1184).getComponent(18), option = interfaces.get(1188)
				.getComponent(3);
		if (Chat.isValid()) {
			keyboard.sendText(" ", false);
			for (int i = 0; i < 10 && Chat.isValid(); i++, sleep(100, 150))
				;
		} else if (Chat1.isValid()) {
			keyboard.sendText(" ", false);
			for (int i = 0; i < 10 && Chat1.isValid(); i++, sleep(100, 150))
				;
		} else if (option.isValid()) {
			keyboard.sendText("" + options, false);
			for (int i = 0; i < 10 && option.isValid(); i++, sleep(100, 150));
		}
	}
	public void TalkTo(String action,int... id) {
		RSComponent Chat = interfaces.get(1191).getComponent(18), Chat1 = interfaces
				.get(1184).getComponent(18), option = interfaces.get(1188)
				.getComponent(3);
		if (Chat.isValid()) {
			keyboard.sendText(" ", false);
			for (int i = 0; i < 10 && Chat.isValid(); i++, sleep(100, 150))
				;
		} else if (Chat1.isValid()) {
			keyboard.sendText(" ", false);
			for (int i = 0; i < 10 && Chat1.isValid(); i++, sleep(100, 150))
				;
		} else if (option.isValid()) {
			keyboard.sendText("1", false);
			for (int i = 0; i < 10 && option.isValid(); i++, sleep(100, 150))
				;
		} else {
			RSNPC g = npcs.getNearest(id);
			if (g == null)
				return;
			if(!g.isOnScreen()){
				turnTo(g.getLocation());
			}
			g.doAction(action);
			for (int i = 0; i < 10 && !Chat.isValid(); i++, sleep(100, 150))
				;
		}
	}
	public boolean checkNPC(final String name){
		RSNPC g = npcs.getNearest(new Filter<RSNPC>(){
			@Override
			public boolean accept(RSNPC e) {
				return e !=null &&e.getName().toLowerCase().contains(name.toLowerCase()) && e.getLevel() !=0 && calc.canReach(e.getLocation(), false);
			}
			
		});
		return g!=null;
	}
	private void turnTo(RSTile target) {
		int b = random(0, 3);
		if(b < 2)
			camera.setPitch(random(20, 90));
		camera.turnToTile(target);
	}
	public void WalkTileMM(RSTile g){
		walking.walkTileMM(g);
		sleep(2500, 3000);
	}
	public void WalkTileMM(RSTile g,boolean web){
		if ((path == null || path.length == 0 ) || (path !=null&& path.length != 0 && !path[path.length -1].equals(g))) {
			path = walking.findPath(g);
		}
		if (!game.isLoggedIn())
			return;
		if (!walking.isRunEnabled() && walking.getEnergy() > 40) {
			walking.setRun(true);
		}
		if (path != null) {
			walking.walkPathMM(path);
			RSTile next = walking.nextTile(path);
			for (int i = 0; i < 40 && calc.distanceTo(next) > 5; i++, sleep(100, 150));
		}
	}
	public void WalkTo(RSNPC g){
		WalkTileMM(g.getLocation(),true);
	}
	public void WalkTo(RSTile t1,boolean web) {
		if (needTeleport(t1)) {
			doTeleport(getClosetTeleport(t1));
		} else {
			if ((path == null || path.length == 0 )|| (path.length != 0 && path !=null && !path[path.length -1].equals(t1))) {
				path = walking.findPath(t1);
			}
			if (!game.isLoggedIn())
				return;
			if (!walking.isRunEnabled() && walking.getEnergy() > 40) {
				walking.setRun(true);
			}
			if (path != null) {
				walking.walkPathMM(path);
				RSTile next = walking.nextTile(path);
				for (int i = 0; i < 40 && calc.distanceTo(next) > 7; i++, sleep(100, 150));
			}
		}
	}
	public void WalkTo(RSTile t1) {
		if(calc.distanceTo(t1) < 14){
			return;
		}
		if (needTeleport(t1)) {
			doTeleport(getClosetTeleport(t1));
		} else {
			if ((path == null || path.length == 0 ) || (path !=null && path.length != 0  && !path[path.length -1].equals(t1))) {
				path = walking.findPath(t1);
			}
			if (!game.isLoggedIn())
				return;
			if (!walking.isRunEnabled() && walking.getEnergy() > 40) {
				walking.setRun(true);
			}
			if (path != null) {
				walking.walkPathMM(path);
				RSTile next = walking.nextTile(path);
				for (int i = 0; i < 40 && calc.distanceTo(next) > 7; i++, sleep(100, 150));
			}
		}
	}

	private int getKillLeft(){
		return settings.getSetting(183);
	}
	private RSComponent getInterface(int i , int x) {
		return interfaces.get(i).getComponent(x);
	}

	public boolean isInCombat() {
		boolean isInteracting = false;
		for (int i = 0; i < npcs.getAll().length; i++) {
			if (npcs.getAll()[i] != null
					&& npcs.getAll()[i].getInteracting() != null
					&& npcs.getAll()[i].isInteractingWithLocalPlayer()) {
				isInteracting = true;
				break;
			}
		}
		return getMyPlayer().isInCombat() && isInteracting;
	}
	private void doTeleport(Teleport t) {
		for(int i = 0 ; i < 40 && (isInCombat() && !interfaces.get(1092).isValid());i++,sleep(100,150));
		if(isInCombat() && inventory.contains(CURRENT_TAB)){
			inventory.getItem(CURRENT_TAB).doAction("Break");
			sleep(2000,2500);
			for(int i = 0 ; i < 40 && getMyPlayer().getAnimation() !=-1 && !isInCombat();i++,sleep(100,150));
		}
		if (!interfaces.get(1092).isValid()) {
			abilities.doActionAtSlot(ActionSlot.SLOT_12, "Cast");
			for(int i = 0 ; i < 50 && !interfaces.get(1092).isValid();i++,sleep(100,150));
		} else {
			if (interfaces.get(1092).getComponent(t.getComponent()).doClick()) {
				for(int i = 0 ; i < 50 && interfaces.get(1092).isValid();i++,sleep(100,150));
				sleep(2000,2500);
				for(int i = 0 ; i < 100 && getMyPlayer().getAnimation() !=-1;i++,sleep(100,150));
			}
		}
	}
	private Teleport getClosetTeleport(RSTile t1){
		ArrayList<Integer> d = new ArrayList<Integer>();
		for(Teleport t : Teleport.values()){
				int dist = (int) calc.distanceBetween(t.getLocation(), t1);
				d.add(dist);
		}
		int index = d.indexOf(Collections.min(d));
		return Teleport.values()[index];
	}
	private void walkPath(RSTile[] path){
		if(walking.getEnergy() > 40 && !walking.isRunEnabled()){
			walking.setRun(true);
		}
		walking.walkPathMM(path);
		sleep(2000,2500);
	}
	private boolean needTeleport(RSTile t){
		int minDist = getMinDistanceLoadStone(t);
		int mydist = calc.distanceTo(t) - 15;
		return minDist < mydist;
	}
	private boolean needTeleport(RSTile t,Teleport r){
		int minDist = (int) calc.distanceBetween(r.getLocation(), t);
		int mydist = calc.distanceTo(t) - 15;
		return (minDist + 15) < mydist;
	}
	private void TurnRet(boolean on) {
		int x = on ? 1 : 0;
		RSComponent or = interfaces.get(464).getComponent(5);
		RSComponent Combat = interfaces.get(548).getComponent(124);
		if (settings.getSetting(462) == x) {
			if(!or.isVisible()){
				Combat.doClick(true);
			}
			or.doClick(true);
			game.openTab(Game.TAB_INVENTORY);
		}
	}
	private boolean atBank(){
		return EDGEVILLAGE_BANKAREA.contains(getMyPlayer().getLocation()) || bank.isOpen();
	}

	private void grabBankitem(int id, int count) {
		if (atBank()) {
			if (bank.isOpen()) {
				if (inventory.getCount(id) == 0) {
					bank.withdraw(id, count);
					for (int i = 0; i < 20 && (inventory.getCount(id) == 0); i++, sleep(100, 150));
				} 
				bank.close();
			} else {
				bank.open();
			}
		} else {
			WalkTo(EDGEVILLAGE_BANKAREA.getRandomTile());
		}
	}
	private void Bank() {
		if (atBank()) {
			if (bank.isOpen()) {
				int count = inventory.getCount() - inventory.getCount(TOOL);
				if(count > 5){
					bank.depositAllExcept(TOOL);
				}else if (inventory.getCount(CURRENT_TAB) == 0) {
					int amount = bank.getCount(CURRENT_TAB) > 100 ? 100:bank.getCount(CURRENT_TAB)- 1;
					bank.withdraw(CURRENT_TAB, amount);
					for(int i = 0 ; i < 20 && (inventory.getCount(CURRENT_TAB) == 0);i++,sleep(100,150));
				}else if (inventory.getCount(CURRENT_FOOD) == 0) {
					bank.withdraw(CURRENT_FOOD, FOODAMOUNT);
					for(int i = 0 ; i < 20 && (inventory.getCount(CURRENT_FOOD) == 0);i++,sleep(100,150));
				} else {
					bank.close();
				}
			} else {
				bank.open();
			}
		} else {
			WalkTo(EDGEVILLAGE_BANKAREA.getRandomTile());
		}
	}
	private void WalkToVannaka() {
		int x = getMyPlayer().getLocation().getX(), y = getMyPlayer()
				.getLocation().getY();
		if (x > 3000 && x < 3200 && y > 9800 && y < 9950 && !checkNPC("hill giant")) {
			RSObject door = objects.getNearest(29316);
			if (door != null && door.isOnScreen()) {
				door.doClick();
				sleep(1000,1500);
				for(int i = 0 ; i < 300 && getMyPlayer().isMoving();i++,sleep(100,150));
			}else{
			walkPath(EDGEVILLAGEDUGNEONTOZOMBIE);
			}
		}else{
			RSObject trapdoor = objects.getNearest(26934,26933);
			if (trapdoor != null) {
				if(trapdoor.isOnScreen()){
					trapdoor.doClick();
					sleep(3000,3500);
				}else{
					WalkTileMM(trapdoor.getLocation(),true);
				}
			}else{
				WalkTo(new RSTile(3095,3469));
			}
		}
	}
	

	private int getMinDistanceLoadStone(RSTile t1) {
		ArrayList<Integer> d = new ArrayList<Integer>();
		for(Teleport t : Teleport.values()){
				int dist = (int) calc.distanceBetween(t.getLocation(), t1);
				d.add(dist);
		}
		return Collections.min(d);
	}
	private boolean equipcontain(int m) {
		for(RSItem g : equipment.getCachedItems()){
			if(g.getID() == m){
				return true;
			}
		}
		return false;
	}

	private boolean equipcontain(int m, boolean monster) {
		if (checkSlot()) {
			return equipment.containsOneOf(m);
		} else {
			return equipcontain(m);
		}
	}

	private boolean checkSlot() {
		int count = 0;
		for (RSItem e : inventory.getCachedItems()) {
			if (e.getID() == -1) {
				count++;
			}
		}
		return count == inventory.getCachedItems().length ;
	}
	public enum Teleport{
		AL_Kharid(new RSTile(3296, 3184),40,"AL Kharid"),
		Ardougne(new RSTile(2633, 3348),41,"Ardougne"),
		Burthorpe(new RSTile(2898, 3544),42,"Burthorpe"),
		Catherby(new RSTile(2830, 3451),43,"Catherby"),
		Draynor(new RSTile(3104, 3298),44,"Draynor"),
		Edgeville(new RSTile(3066, 3505),45,"Edgeville"),
		Falador(new RSTile(2966, 3403),46,"Falador"),
		Lumbridge(new RSTile(3232, 3221),47,"Lumbridge"),
	    Port_Sarim(new RSTile(3010, 3215),48,"Port Sarim"),
	    Seers_Village(new RSTile(2688, 3482),49,"Seers Village"),
	    Taverly(new RSTile(2877, 3442),50,"Taverly"),
	    Varrock(new RSTile(3213, 3376),51,"Varrock"),
	    Yanille(new RSTile(2528, 3095),52,"Yanille");
		
		private int comp;
		private RSTile tile;
		private String name;
		
		Teleport(final RSTile tile , final int comp,final String name){
			this.tile = tile;
			this.comp = comp;
			this.name = name;
		}
		
		private int getComponent(){
			return this.comp;
		}
		private RSTile getLocation(){
			return this.tile;
		}
		private String getName(){
			return this.name;
		}
	}
	@Override
	public void messageReceived(MessageEvent e) {
		String message = e.getMessage().toLowerCase();
		if(currentTask == null && message.contains("your current assignment")){
			String[] s = message.split(" ");
			if (s[4].contains(";")) {
				currentTask = s[4];
			} else {
				currentTask = s[4] + " " + s[5];
			}
			currentTask = currentTask.replace(";", "");
			System.out.println(currentTask);
		}
		if(message.contains("you've completed your task")){
			TASKCOMPLETED++;
		}
	}
    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }
	private int getHour(int item) {
		return (int) ((item) * 3600000D / (System.currentTimeMillis() - STARTTIME));
	}
    private final Color color1 = new Color(255, 255, 255);

    private final Font font1 = new Font("Verdana", 1, 12);

    private final Image img1 = getImage("https://dl.dropbox.com/u/129995508/EpicbotScripts/Pro%20Slayer/Paint.jpg");

    private final Color betacolor1 = new Color(0, 0, 0);
    private final Color betacolor2 = new Color(255, 255, 255);

    private final BasicStroke betastroke1 = new BasicStroke(1);

    private final Font betafont1 = new Font("Arial", 0, 11);

    private final Image betaimg1 = getImage("https://dl.dropbox.com/u/129995508/EpicbotScripts/logo.png");
    
    private final Font devfont = new Font("Arial", 0, 9);

    private final Color devcolor1 = new Color(0, 0, 0, 155);
    private final Color devcolor2 = new Color(51, 153, 0);

    private final Font devfont1 = new Font("Arial", 0, 9);

    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        if(betatest){
        	  g.setColor(betacolor1);
              g.fillRect(95, 81, 336, 215);
              g.setStroke(betastroke1);
              g.drawRect(95, 81, 336, 215);
              g.drawImage(betaimg1, 158, 87, null);
              g.setFont(betafont1);
              g.setColor(betacolor2);
              g.drawString("Unfortunately you have not been picked to beta-test this script", 117, 258);
              g.drawString("Please wait for the public release.", 180, 278);

        }else{
		int combatxp = skills.getCurrentExp(Skill.CONSTITUTION) 
				+ skills.getCurrentExp(Skill.ATTACK)
				+ skills.getCurrentExp(Skill.STRENGTH)
				+ skills.getCurrentExp(Skill.DEFENCE) - STARTXP;
		int slayerxp = skills.getCurrentExp(Skill.SLAYER) - STARTSLAYERXP;
		if(SHOWPAINT){
        g.drawImage(img1, 0, 347, null);
        g.setFont(font1);
        g.setColor(color1);
        g.drawString("loading..", 172, 420);
        g.drawString("" + currentTask , 170, 441);
        g.drawString("" + slayerxp, 172, 463);
        g.drawString("" + combatxp, 171, 484);
        g.drawString("" + Timer.format(System.currentTimeMillis() - STARTTIME), 173, 506);
        g.drawString("" + getKillLeft(), 428, 420);
        g.drawString("" + TASKCOMPLETED, 429, 441);
        g.drawString("" + getHour(slayerxp), 429, 463);
        g.drawString("" + getHour(combatxp), 427, 485);
		}
        // DEV
        g.setFont(devfont);
        g.setColor(Color.WHITE);
        g.drawString("Combat Status:" + CombatStatus, 552, 470);
        g.drawString("Current User:" + user, 552, 486);
        g.drawString("Current Ability:" + getHotKey(EOC_KEY), 552, 503);
			if (getMyPlayer().getInteracting() != null && currentTask != null) {
				drawModel(g, getMyPlayer().getInteracting(), Color.red,"", Color.GREEN);
				RSCharacter target = getMyPlayer().getInteracting();
				if(target.getLevel() == 0){
					return;
				}
				Point p = getMyPlayer().getInteracting().getModel().getCentralPoint();
				int px = p.x - 35, py = p.y- 50;
				g.setColor(Color.yellow);
				Point mp = getMyPlayer().getModel().getCentralPoint();
				g.drawLine(mp.x, mp.y, p.x, p.y);
		        g.setColor(devcolor1);
		        g.fillRect(px, py - 12, 110, 12);
		        g.fillRect(px, py, 75, 12);
		        g.fillRect(px, py + 12, 61, 12);
		        g.setFont(devfont1);
		        g.setColor(Color.red);
		        g.drawString("Name:" + target.getName() , px + 2 , (py - 12) + 10);
		        g.drawString("Hp Percent<" +  target.getHPPercent() +">", px + 2, py + 10);
		        g.drawString("Level:" + target.getLevel(),px + 2, (py + 12)  + 10);

			}
		}
    }

	public void drawModel(Graphics g, RSCharacter rsCharacter, Color c, String s, Color tc) {
		Polygon[] model = rsCharacter.getModel().getTriangles();
		Point point = rsCharacter.getModel().getCentralPoint();
		for (Polygon p : model) {
			g.setColor(c);
			g.fillPolygon(p);
			g.setColor(c.darker());
			g.drawPolygon(p);
		}
		g.setColor(tc);
		g.drawString(s, point.x - 20 , point.y - 15);
	}
    public class ProSlayerGUI extends JFrame {
    	public ProSlayerGUI() {
    		initComponents();
    	}
    	int Version = 1;
    	
    	private void button2ActionPerformed(ActionEvent e) {
    		if(textField1.getText().length() == 0){
    			return;
    		}
    		save(textField1.getText());
    		updateProfile();
    	}

    	private void button3ActionPerformed(ActionEvent e) {
    		     FOODAMOUNT = (Integer) spinner1.getValue();
    		String string = comboBox1.getSelectedItem().toString();
    		if(string.equals("Rocktail")){
    			CURRENT_FOOD =  ROCKTAIL;
    		}else if(string.equals("Shark")){
    			CURRENT_FOOD =  SHARK;
    		}else if(string.equals("Monkfish")){
    			CURRENT_FOOD = MONKFISH;
    		}else if(string.equals("Swordfish")){
    			CURRENT_FOOD =  SWORDFISH;
    		}
    		startscript = true;
    		setVisible(false);
    		dispose();
    	}

    	private void button5ActionPerformed(ActionEvent e) {
    		String name = list1.getSelectedValue().toString();
    		if(name == null){
    			return;
    		}
    		Load(name);
    	}

    	private void button4ActionPerformed(ActionEvent e) {
    		updateProfile();
    	}
    	private void updateProfile(){
    		DefaultListModel model = new DefaultListModel();
    		for (File profile : new File(System.getProperty("user.home")+ "/AppData/Roaming/ProSlayer/settings/").listFiles()) {
    			if(profile != null){
    				String name = profile.getName();
    				String n1 = name.replace(".ini", "");
    				model.addElement(n1);
    			}
    		}
    		list1.setModel(model);
    	}
    	private void save(String name) {
    		Properties file = new Properties();
    		file.setProperty("FoodType", comboBox1.getSelectedItem().toString());
    		file.setProperty("foodamount", spinner1.getValue().toString());
    		try {
    			File folder = new File(System.getProperty("user.home")
    					+ "/AppData/Roaming/ProSlayer"), sFolder = new File(folder + "/settings");
    			if (!folder.exists()) {
    				folder.mkdir();
    			}
    			if (!sFolder.exists()) {
    				sFolder.mkdir();
    			}
    			file.store(new FileWriter(new File(System.getProperty("user.home")
    					+ "/AppData/Roaming/ProSlayer/settings/", name + ".ini")),
    					"Proslayer Last Settings (Version: " + Version + ")");
    		} catch (java.lang.Exception ioe) {
    			System.out.println("Error occurred when saving!");
    		}
    	}
    	private void Load(String name){
       	 Properties propIn = new Properties();
            FileInputStream fis = null;
    		try {
    			fis = new FileInputStream(System.getProperty("user.home")+"/AppData/Roaming/ProSlayer/settings/"+ name + ".ini");
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
            try {
    			propIn.load(fis);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            setCombox(comboBox1, propIn.getProperty("FoodType"));
            spinner1.setValue(Integer.parseInt(propIn.getProperty("foodamount").toString()));
            try {
    			fis.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	private void setCombox(JComboBox option,String op){
    		option.setSelectedItem(op);
    		ComboBoxModel g = option.getModel();
    		DefaultComboBoxModel model = new DefaultComboBoxModel();
    		model.addElement(op);
    		for(int i = 0 ; i < g.getSize();i++){
    			if (!g.getElementAt(i).toString().equals(op)) {
    				model.addElement(g.getElementAt(i));
    			}
    		}
    		option.setModel(model);
    	}

    	private void initComponents() {
    		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    		// Generated using JFormDesigner Evaluation license - Pro Scripts
    		tabbedPane1 = new JTabbedPane();
    		panel1 = new JPanel();
    		panel3 = new JPanel();
    		label1 = new JLabel();
    		spinner1 = new JSpinner();
    		label2 = new JLabel();
    		comboBox1 = new JComboBox();
    		label3 = new JLabel();
    		comboBox2 = new JComboBox();
    		comboBox3 = new JComboBox();
    		comboBox4 = new JComboBox();
    		label9 = new JLabel();
    		label8 = new JLabel();
    		label7 = new JLabel();
    		spinner2 = new JSpinner();
    		spinner3 = new JSpinner();
    		label4 = new JLabel();
    		label5 = new JLabel();
    		spinner4 = new JSpinner();
    		spinner5 = new JSpinner();
    		label6 = new JLabel();
    		label10 = new JLabel();
    		comboBox5 = new JComboBox();
    		panel2 = new JPanel();
    		button2 = new JButton();
    		textField1 = new JTextField();
    		label11 = new JLabel();
    		button4 = new JButton();
    		button5 = new JButton();
    		list1 = new JList();
    		button3 = new JButton();

    		//======== this ========
    		setTitle("Pro Slayer");
    		Container contentPane = getContentPane();
    		contentPane.setLayout(null);

    		//======== tabbedPane1 ========
    		{

    			//======== panel1 ========
    			{

    				// JFormDesigner evaluation mark
    				panel1.setBorder(new javax.swing.border.CompoundBorder(
    					new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
    						"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
    						javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
    						java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

    				panel1.setLayout(null);

    				{ // compute preferred size
    					Dimension preferredSize = new Dimension();
    					for(int i = 0; i < panel1.getComponentCount(); i++) {
    						Rectangle bounds = panel1.getComponent(i).getBounds();
    						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
    						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
    					}
    					Insets insets = panel1.getInsets();
    					preferredSize.width += insets.right;
    					preferredSize.height += insets.bottom;
    					panel1.setMinimumSize(preferredSize);
    					panel1.setPreferredSize(preferredSize);
    				}
    			}
    			tabbedPane1.addTab("Home", panel1);


    			//======== panel3 ========
    			{
    				panel3.setLayout(null);

    				//---- label1 ----
    				label1.setText("Current Food:");
    				panel3.add(label1);
    				label1.setBounds(5, 10, 75, 20);

    				//---- spinner1 ----
    				spinner1.setModel(new SpinnerNumberModel(1, 1, 27, 1));
    				panel3.add(spinner1);
    				spinner1.setBounds(110, 10, 80, spinner1.getPreferredSize().height);

    				//---- label2 ----
    				label2.setText("Type:");
    				panel3.add(label2);
    				label2.setBounds(195, 10, 40, 20);

    				//---- comboBox1 ----
    				comboBox1.setModel(new DefaultComboBoxModel(new String[] {
    					"Rocktail",
    					"Shark",
    					"Monkfish",
    					"Swordfish"
    				}));
    				panel3.add(comboBox1);
    				comboBox1.setBounds(260, 10, 150, comboBox1.getPreferredSize().height);

    				//---- label3 ----
    				label3.setText("Strength Amount");
    				panel3.add(label3);
    				label3.setBounds(5, 40, 105, 20);

    				//---- comboBox2 ----
    				comboBox2.setModel(new DefaultComboBoxModel(new String[] {
    					"Normal",
    					"Super",
    					"Extreme"
    				}));
    				panel3.add(comboBox2);
    				comboBox2.setBounds(260, 40, 150, 20);

    				//---- comboBox3 ----
    				comboBox3.setModel(new DefaultComboBoxModel(new String[] {
    					"Normal",
    					"Super",
    					"Extreme"
    				}));
    				panel3.add(comboBox3);
    				comboBox3.setBounds(260, 70, 150, 20);

    				//---- comboBox4 ----
    				comboBox4.setModel(new DefaultComboBoxModel(new String[] {
    					"Normal",
    					"Super",
    					"Extreme"
    				}));
    				panel3.add(comboBox4);
    				comboBox4.setBounds(260, 100, 150, 20);

    				//---- label9 ----
    				label9.setText("Type:");
    				panel3.add(label9);
    				label9.setBounds(195, 100, 40, 20);

    				//---- label8 ----
    				label8.setText("Type:");
    				panel3.add(label8);
    				label8.setBounds(195, 70, 40, 20);

    				//---- label7 ----
    				label7.setText("Type:");
    				panel3.add(label7);
    				label7.setBounds(195, 40, 40, 20);

    				//---- spinner2 ----
    				spinner2.setModel(new SpinnerNumberModel(0, 0, 28, 1));
    				panel3.add(spinner2);
    				spinner2.setBounds(110, 40, 80, 20);

    				//---- spinner3 ----
    				spinner3.setModel(new SpinnerNumberModel(0, 0, 28, 1));
    				panel3.add(spinner3);
    				spinner3.setBounds(110, 70, 80, 20);

    				//---- label4 ----
    				label4.setText("Attack Amount");
    				panel3.add(label4);
    				label4.setBounds(5, 70, 105, 20);

    				//---- label5 ----
    				label5.setText("Defence Amount");
    				panel3.add(label5);
    				label5.setBounds(5, 100, 105, 20);

    				//---- spinner4 ----
    				spinner4.setModel(new SpinnerNumberModel(0, 0, 28, 1));
    				panel3.add(spinner4);
    				spinner4.setBounds(110, 100, 80, 20);

    				//---- spinner5 ----
    				spinner5.setModel(new SpinnerNumberModel(1, 1, 28, 1));
    				panel3.add(spinner5);
    				spinner5.setBounds(110, 130, 80, 20);

    				//---- label6 ----
    				label6.setText("Prayer Potion");
    				panel3.add(label6);
    				label6.setBounds(5, 130, 105, 20);

    				//---- label10 ----
    				label10.setText("Current Tab:");
    				panel3.add(label10);
    				label10.setBounds(195, 130, 65, 20);

    				//---- comboBox5 ----
    				comboBox5.setModel(new DefaultComboBoxModel(new String[] {
    					"Varrock",
    					"Falador",
    					"Camolet"
    				}));
    				panel3.add(comboBox5);
    				comboBox5.setBounds(260, 130, 150, comboBox5.getPreferredSize().height);

    				{ // compute preferred size
    					Dimension preferredSize = new Dimension();
    					for(int i = 0; i < panel3.getComponentCount(); i++) {
    						Rectangle bounds = panel3.getComponent(i).getBounds();
    						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
    						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
    					}
    					Insets insets = panel3.getInsets();
    					preferredSize.width += insets.right;
    					preferredSize.height += insets.bottom;
    					panel3.setMinimumSize(preferredSize);
    					panel3.setPreferredSize(preferredSize);
    				}
    			}
    			tabbedPane1.addTab("Supplies", panel3);


    			//======== panel2 ========
    			{
    				panel2.setLayout(null);

    				//---- button2 ----
    				button2.setText("Save");
    				button2.addActionListener(new ActionListener() {
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						button2ActionPerformed(e);
    					}
    				});
    				panel2.add(button2);
    				button2.setBounds(315, 90, 80, button2.getPreferredSize().height);
    				panel2.add(textField1);
    				textField1.setBounds(400, 90, 75, 20);

    				//---- label11 ----
    				label11.setText("Save/Load Options");
    				label11.setFont(new Font("Tahoma", Font.PLAIN, 18));
    				panel2.add(label11);
    				label11.setBounds(120, 5, 170, label11.getPreferredSize().height);

    				//---- button4 ----
    				button4.setText("Load All Profiles");
    				button4.addActionListener(new ActionListener() {
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						button2ActionPerformed(e);
    						button4ActionPerformed(e);
    					}
    				});
    				panel2.add(button4);
    				button4.setBounds(315, 30, 160, 23);

    				//---- button5 ----
    				button5.setText("Download");
    				button5.addActionListener(new ActionListener() {
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						button2ActionPerformed(e);
    						button5ActionPerformed(e);
    					}
    				});
    				panel2.add(button5);
    				button5.setBounds(315, 60, 160, 23);

    				//---- list1 ----
    				list1.setForeground(Color.white);
    				panel2.add(list1);
    				list1.setBounds(10, 30, 303, 108);

    				{ // compute preferred size
    					Dimension preferredSize = new Dimension();
    					for(int i = 0; i < panel2.getComponentCount(); i++) {
    						Rectangle bounds = panel2.getComponent(i).getBounds();
    						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
    						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
    					}
    					Insets insets = panel2.getInsets();
    					preferredSize.width += insets.right;
    					preferredSize.height += insets.bottom;
    					panel2.setMinimumSize(preferredSize);
    					panel2.setPreferredSize(preferredSize);
    				}
    			}
    			tabbedPane1.addTab("Misc", panel2);

    		}
    		contentPane.add(tabbedPane1);
    		tabbedPane1.setBounds(0, 0, 485, 270);

    		//---- button3 ----
    		button3.setText("Start Script!!");
    		button3.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				button3ActionPerformed(e);
    			}
    		});
    		contentPane.add(button3);
    		button3.setBounds(180, 275, 115, button3.getPreferredSize().height);

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
    	// Generated using JFormDesigner Evaluation license - Pro Scripts
    	private JTabbedPane tabbedPane1;
    	private JPanel panel1;
    	private JPanel panel3;
    	private JLabel label1;
    	private JSpinner spinner1;
    	private JLabel label2;
    	private JComboBox comboBox1;
    	private JLabel label3;
    	private JComboBox comboBox2;
    	private JComboBox comboBox3;
    	private JComboBox comboBox4;
    	private JLabel label9;
    	private JLabel label8;
    	private JLabel label7;
    	private JSpinner spinner2;
    	private JSpinner spinner3;
    	private JLabel label4;
    	private JLabel label5;
    	private JSpinner spinner4;
    	private JSpinner spinner5;
    	private JLabel label6;
    	private JLabel label10;
    	private JComboBox comboBox5;
    	private JPanel panel2;
    	private JButton button2;
    	private JTextField textField1;
    	private JLabel label11;
    	private JButton button4;
    	private JButton button5;
    	private JList list1;
    	private JButton button3;
    	// JFormDesigner - End of variables declaration  //GEN-END:variables
    }
    private boolean contains(Rectangle rec , Point m){
		return rec.contains(m.x + rec.x, m.y + rec.y);
	}
	@Override
	public Rectangle getPhysicalMouseArea() {
		return new Rectangle(0, 391, 515, 139);
	}
	@Override
	public void physicalMouseClicked(MouseEvent e) {
		Rectangle d = new Rectangle(0, 391, 515, 139);
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
