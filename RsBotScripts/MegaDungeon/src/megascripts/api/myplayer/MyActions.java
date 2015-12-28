package megascripts.api.myplayer;


import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;
import megascripts.dungeon.boss.CurrentBoss;
import megascripts.dungeon.boss.Sagittare;
import megascripts.dungeon.boss.Stomp;
import megascripts.dungeon.boss.Wraped_Gulega;
import megascripts.dungeon.node.Enter_Dungeon;
import megascripts.dungeon.node.Loot_StartRoom;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class MyActions {

	public static boolean ThereBoss() {
		if(CurrentBoss.BOSSES[18].isValid()){
			return true;
		}
		if(CurrentBoss.BOSSES[16].isValid()){
			return true;
		}
		if(CurrentBoss.BOSSES[22].isValid()){
			return true;
		}
		SceneObject Out = SceneEntities.getNearest(Constants.ENDLADDER);
	
		return Out != null && (Calc.Reach(Out) || ulits.tileinroom(Out.getLocation()));
	}

	public static boolean ThereGroupStone() {
		GroundItem n = GroundItems.getNearest(Constants.GROUP_STONE);
		return n != null && Calc.Reach(n);
	}

	public static boolean ThereStartRoom(){
		if(!Constants.BUY_SUPPLIES){
			return true;
		}
		return (Inventory.getCount(Loot_StartRoom.FEATHER) != 0);
	}
	public static boolean ThreNPC() {
		for (NPC Monster : NPCs.getLoaded()) {
				if(Monster !=null && Calc.Reach(Monster) && Monster.getLevel() > 0  && Monster.getId() != Constants.Smuggler){
				return true;
			}
		}
		return false;
	}

	public static boolean InDungeon(){
		return !Enter_Dungeon.At_Gate() && !Enter_Dungeon.ATSurf();
	}
	public static boolean ThereKey() {
		return MyActions.GroundItem_IsValid(ulits.convertInt(Constants.ALL_KEYS)) && Calc.Reach(MyActions.Set_GroundItem(ulits.convertInt(Constants.ALL_KEYS)));
	}
	public static boolean ThereCoin(){
		if(MyActions.AtStartRoom()){
			return false;
		}
		return MyGroundItems.There(Constants.COIN);
	}
	public static boolean ThereFish() {
		return MyActions.GroundItem_IsValid(Constants.FOODTOEAT) && Calc.Reach(MyActions.Set_GroundItem(Constants.FOODTOEAT)) && Inventory.getCount() !=28;
	}
	public static boolean AtStartRoom() {
		return MyActions.NPC_IsValid(Constants.Smuggler) && Calc.Reach(MyActions.Set_NPC(Constants.Smuggler));
	}
	public static SceneObject Set_Object(Filter<SceneObject> filter) {
		return SceneEntities.getNearest(filter);
	}
	public static SceneObject Set_Object(int [] ID) {
		return SceneEntities.getNearest(ID);
	}
	public static NPC Set_NPC(int e){
		return NPCs.getNearest(e);
	}
	public static GroundItem Set_GroundItem(int e){
		return GroundItems.getNearest(e);
	}
	public static GroundItem Set_GroundItem(int []e){
		return GroundItems.getNearest(e);
	}
	public static boolean NPC_IsValid(int e){
		NPC d = NPCs.getNearest(e);
		return d!=null && d.validate();
	}
	public static boolean Object_IsValid(int e){
		SceneObject d = SceneEntities.getNearest(e);
		return d!=null && d.validate();
	}
	public static boolean GroundItem_IsValid(int e){
		GroundItem d = GroundItems.getNearest(e);
		return d!=null && d.validate();
	}
	public static boolean GroundItem_IsValid(int []e){
		GroundItem d = GroundItems.getNearest(e);
		return d!=null && d.validate();
	}
	public static void loot(int item) {
		GroundItem loot = GroundItems.getNearest(item);
		if (loot != null) {
			if (loot.isOnScreen()) {
				if (loot.getLocation().canReach()) {
					Camera.turnTo(loot);
					String name = loot.getGroundItem().getName();
					loot.interact("Take", name);
					Task.sleep(500, 650);
					if (Players.getLocal().isMoving()) {
						while (Players.getLocal().isMoving()) {
							Task.sleep(10, 30);
						}
					}
				}
			} else {
				Walking.walk(loot.getLocation());
				Camera.turnTo(loot.getLocation());
			}
		}
	}
}
