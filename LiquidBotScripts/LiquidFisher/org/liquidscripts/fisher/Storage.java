package org.liquidscripts.fisher;

import org.liquid.automation.osrs.api.wrapper.Area;
import org.liquid.automation.osrs.api.wrapper.Path;
import org.liquid.automation.osrs.api.wrapper.Tile;
import org.liquidscripts.fisher.ui.FisherGUI;
import org.liquidscripts.fisher.wrapper.Fish;
import org.liquidscripts.fisher.wrapper.Location;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/3/14
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Storage {

    public static final Area CATHERBY_BANK_AREA = new Area(new Tile[]{
            new Tile(2806, 3438, 0), new Tile(2806, 3445, 0),
            new Tile(2812, 3445, 0), new Tile(2812, 3438, 0)
    });
	public static final Area DRYANOR_BANK_AREA = new Area(
			new Tile(3098, 3239, 0),
			new Tile(3098, 3248, 0),
			new Tile(3087, 3248, 0),
			new Tile(3087, 3239, 0)
	);
    public static final Area KARMJA_BANK_AREA = new Area(
            new Tile(3040, 3231, 0),
            new Tile(3039, 3239, 0),
            new Tile(3052, 3240, 0),
            new Tile(3055, 3229, 0)
    );
    public static final Tile[] KARAMJA_PATH_TO_SHIP = new Tile[] {
            new Tile(2924, 3175, 0),
            new Tile(2918, 3167, 0),
            new Tile(2915, 3157, 0),
            new Tile(2923, 3151, 0),
            new Tile(2933, 3149, 0),
            new Tile(2943, 3146, 0),
            new Tile(2954, 3147, 0)
    };

    public static final Area KARAMJA_AREA = new Area(
            new Tile(2883, 3199, 0),
            new Tile(2927, 3195, 0),
            new Tile(2973, 3139, 0),
            new Tile(2917, 3121, 0),
            new Tile(2860, 3138, 0)
    );
    public static final Tile[] KARAMJA_PATH_TO_DEPOSIT_BOX = new Tile[] {
            new Tile(3029, 3218, 0),
            new Tile(3026, 3228, 0),
            new Tile(3034, 3236, 0),
            new Tile(3044, 3237, 0),
            new Tile(3050, 3236, 0)
    };


    public static final Fish SHRIMP = new Fish(317, 10, new int[]{303}, "Net", "Shrimp", new String[]{"Net", "Bait"});
    public static final Fish LOBSTER = new Fish(377, 90, new int[]{301}, "Cage", "Lobster", new String[]{"Cage"});
    public static final Fish SALMON = new Fish(331, 70, new int[]{309, 314}, "Lure", "Salmon", new String[]{"Lure"});
    public static final Fish TROUT = new Fish(335, 50, new int[]{307, 313}, "Bait", "Trout", new String[]{"Lure"});
    public static final Fish SHARK = new Fish(383, 110, new int[]{311}, "Harpoon", "Shark", new String[]{"Net", "Harpoon"});
    public static final Fish SARDINE = new Fish(345, 20, new int[]{307,313}, "Bait", "Sardine", new String[]{"Net", "Bait"});

    public static final Fish[] FISHES = {LOBSTER, SALMON, TROUT, SHARK, SHRIMP,SARDINE};

    public static final Location CATHERBY = new Location(new Path(new Tile[]{new Tile(2850, 3432)}), new Path(new Tile[]{new Tile(2809, 3439)}), CATHERBY_BANK_AREA, "Catherby", new Fish[]{LOBSTER, SHARK, SHRIMP,SARDINE});
    
    public static final Location DRAYNOR = new Location(new Path(new Tile[]{new Tile(3089,3230)}), new Path(new Tile[]{new Tile(3093,3243)}), DRYANOR_BANK_AREA, "Draynor", new Fish[]{ SHRIMP,SARDINE});

    public static final Location KARAMJA = new Location(KARMJA_BANK_AREA, "Karamja", new Fish[]{ LOBSTER,SHRIMP});

    public static final Location[] LOCATIONS = {CATHERBY,DRAYNOR,KARAMJA};

    public static final ArrayList<Integer> NPC_ARRAY_LIST = new ArrayList<Integer>();

    public static FisherGUI fisherGui;

    public static int fishCought;

    public static boolean HIDE_PAINT = false;

    public static Fish[] fishToCaught;
    public static MODE chosenMode;
    public static Location chosenLocation;

    public static boolean started;

    public static enum MODE {
        POWERFISH("PowerFish"), BANKING("Banking");

        String name;

        MODE(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
