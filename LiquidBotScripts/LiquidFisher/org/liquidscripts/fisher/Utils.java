package org.liquidscripts.fisher;

import org.liquid.automation.osrs.api.methods.interactive.GameEntities;
import org.liquid.automation.osrs.api.methods.interactive.NPCs;
import org.liquid.automation.osrs.api.util.Filter;
import org.liquid.automation.osrs.api.wrapper.NPC;
import org.liquidscripts.fisher.wrapper.Fish;
import org.liquidscripts.fisher.wrapper.SpotAction;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/3/14
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static boolean atFish() {
        return getFishSpot() != null;
    }

    public static SpotAction getFishSpot() {
        if ((Storage.fishToCaught == null) || (Storage.fishToCaught.length == 0))
            return null;
        for (final Fish fish : Storage.fishToCaught) {
            Filter<NPC> npcFilter = new Filter<NPC>() {
                public boolean accept(NPC npc) {
                    return (npc != null && npc.getName() != null && npc.getName().equalsIgnoreCase("fishing spot") && npc.getActions() != null && matchActions(fish.getActions(), npc.getActions()));
                }
            };


            NPC[] npcs = NPCs.getAll(npcFilter);
            for(NPC npc : npcs){
                if(!Storage.NPC_ARRAY_LIST.contains(npc.getId())){
                    Storage.NPC_ARRAY_LIST.add(npc.getId());
                }
            }
            if(Storage.NPC_ARRAY_LIST.size() == 0)
                return null;
            int common = getMostPopularElement(Storage.NPC_ARRAY_LIST.toArray(new Integer[Storage.NPC_ARRAY_LIST.size()]));
            return new SpotAction(fish,NPCs.getNearest(common));
        }
        return null;
    }

    private static int getMostPopularElement(Integer[] a) {

        int counter = 0, curr, maxvalue, maxcounter = -1;
        maxvalue = curr = a[0];

        for (int e : a) {
            if (curr == e) {
                counter++;
            } else {
                if (counter > maxcounter) {
                    maxcounter = counter;
                    maxvalue = curr;
                }
                counter = 0;
                curr = e;
            }
        }
        if (counter > maxcounter) {
            maxvalue = curr;
        }

        return maxvalue;
    }

    private static boolean matchActions(String[] a, String[] o) {
        int found = 0;
        for (String s : a) {
            for (String x : o) {
                if (x != null && s.equalsIgnoreCase(x)) {
                    found++;
                    break;
                }
            }
        }
        return found == a.length;
    }

}
