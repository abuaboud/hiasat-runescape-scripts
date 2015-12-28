package shadowscripts.grotworms.node;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.methods.interactive.Players;




import shadowscripts.grotworms.Constants;
import shadowscripts.grotworms.api.MCamera;
import shadowscripts.grotworms.api.calc;
import shadowscripts.grotworms.ulits.Action;
import shadowscripts.grotworms.ulits.Ulits;

public class AttackHandler extends Node {

	@Override
	public boolean activate() {
		return !BankHandler.reqBank()&& !LootHandler.thereLoot() ;
	}

	@Override
	public void execute() {
		if(Settings.get(0) == 0){
			Action.setAutoCast();
		}else if(Constants.FIRST_INV && Summoning.isFamiliarSummoned() && Inventory.getCount(Ulits.convertIntegers(Constants.Store)) == 0 && !Constants.FAMILIAR_FULL){
			Constants.FIRST_INV = false;
			System.out.println("Disabled First Inv Checker");
		}else if(Summoning.isFamiliarSummoned() && Inventory.getCount(Ulits.convertIntegers(Constants.Store)) > 0 && !Constants.FAMILIAR_FULL){
			if (calc.distanceTo(Summoning.getFamiliar().getLocation()) > 3) {
				Summoning.select("Call Follower");
				Ulits.SleepTillStop(3);
			} else {
				int id = Inventory.getItem(
						Ulits.convertIntegers(Constants.Store)).getId();
				int count = Inventory.getCount(id);
				Inventory.getItem(id).getWidgetChild().interact("Use");
				Mouse.hop((int)Summoning.getFamiliar().getCentralPoint().getX(), (int)Summoning.getFamiliar().getCentralPoint().getY());
				Summoning.getFamiliar().interact("Use",Summoning.getFamiliar().getName());
				for (int j = 0; j < 25 && Inventory.getCount(id) == count; j++, sleep(
						100, 150))
					;
				if (Inventory.getCount(id) != count) {
					Constants.FAMILARINV = Constants.FAMILARINV
							+ Ulits.getProfit(id, 1);
					if (Constants.FIRST_INV ) {
						Constants.FIRSTINV_PROFIT = Constants.FIRSTINV_PROFIT
								 - Constants.FAMILARINV;
						Constants.FAMILARINV =0;
					}
					System.out.println("Profit is " + Ulits.getProfit(id, 1));
				}
			}
		}else if(Summoning.getPoints() <= 11&& (Summoning.getTimeLeft() < 100 || !Summoning.isFamiliarSummoned()) && Constants.SUMMON_ACTIVE){
			Constants.STATUS = "Drinking Summoning Potion....";
			for(int i = 0 ; i < Ulits.Reverse(Constants.SUMMONPOTION).length ;i++){
				if(Inventory.contains(Ulits.Reverse(Constants.SUMMONPOTION)[i])){
					Inventory.getItem(Ulits.Reverse(Constants.SUMMONPOTION)[i]).getWidgetChild().interact("Drink");
					Ulits.SleepTillStop(1);
					break;
				}
			}
		}else if(Summoning.isFamiliarSummoned() && Inventory.contains(Constants.CurrentFamiliar.getPouchId()) && Summoning.getPoints() > 10&& Summoning.getTimeLeft() < 100 && Constants.SUMMON_ACTIVE){
			Constants.STATUS = "Renewing Familiar....";
			Summoning.select("Renew Familiar");
			Ulits.SleepTillStop(5);
		}else if(Players.getLocal().getHealthPercent() < 50){
			int count = Inventory.getCount(Constants.CURRENT_FOOD);
			Inventory.getItem(Constants.CURRENT_FOOD).getWidgetChild().interact("Eat");
			for(int i = 0 ; i < 20 && Inventory.getCount(Constants.CURRENT_FOOD) == count;i++,sleep(100,150));
		}else if (Inventory.contains(Constants.NATURE_RUNE)
				&& Inventory.contains(Constants.FIRE_RUNE)
				&& Inventory.getCount(Constants.ALCH_ITEM) > 0
				&& Constants.ALCH_LOW_PROFIT) {
			Item alch = Inventory.getItem(Constants.ALCH_ITEM);
			Constants.STATUS = "Alching " + alch.getName() + " for profit...";
			if (Menu.contains("Cast")) {
				int count = Inventory.getCount(Ulits
						.convertIntegers(Constants.DROPS));
				Menu.select("Cast");
				Ulits.SleepTillStop(2);
				for (int j = 0; j < 25&& Inventory.getCount(Ulits.convertIntegers(Constants.DROPS)) == count; j++, sleep(100, 150));
			} else {
				Mouse.move(alch.getWidgetChild().getCentralPoint());
				Keyboard.sendText("9", false);
			}
		}else if(AtGrotWorm()){
			if(!Summoning.isFamiliarSummoned() && Inventory.contains(Constants.CurrentFamiliar.getPouchId()) && Summoning.getPoints() > 10 && Constants.SUMMON_ACTIVE){
				Constants.STATUS = "Summoning Familiar....";
				Inventory.getItem(Constants.CurrentFamiliar.getPouchId()).getWidgetChild().interact("Summon");
				Ulits.SleepTillStop(5);
			}else{
				if (thereNonCombatGrotWorm()) {
					Attack(NPCs.getNearest(noncombatgrotworms));
				} else {
					Attack(NPCs.getNearest(grotworms));
				}
			}
		}else if(AtCave()){
			SceneObject shortcut = SceneEntities.getNearest(70795);
			if(shortcut !=null && shortcut.isOnScreen() && calc.distanceTo(shortcut.getLocation()) < 6){
				shortcut.interact("Slide down");
				Ulits.SleepTillStop(5);
			}else{
				Walking.newTilePath(Constants.PATHTOSHORTCUT).traverse();
			}
		} else if(Action.needTeleport(Constants.PATHTOCAVE[Constants.PATHTOCAVE.length -1], Action.Teleport.Port_Sarim)){
			if((System.currentTimeMillis() - Constants.STARTTIME  < 2000)){
				return;
			}
			Action.doTeleport(Action.Teleport.Port_Sarim);
		}else{
			Constants.STATUS = "Walking to Grotworm Liar....";
			SceneObject enterance = SceneEntities.getNearest(70792);
			SceneObject obs = SceneEntities.getNearest(29953);
			if(obs !=null && calc.isPointOnScreen(obs.getCentralPoint())  && calc.distanceTo(obs.getLocation()) < 6 && Constants.SUMMON_ACTIVE && Summoning.getPoints() < Skills.getRealLevel(Skills.SUMMONING)){
				obs.interact("Renew-points");
				Ulits.SleepTillStop(5);
			}else if(enterance !=null && calc.isPointOnScreen(enterance.getCentralPoint()) && calc.distanceTo(enterance.getLocation()) < 6){
				enterance.interact("Enter");
				Ulits.SleepTillStop(5);
			}else if (Walking.newTilePath(Constants.PATHTOCAVE).getNext() !=null){
				Walking.newTilePath(Constants.PATHTOCAVE).traverse();
			}else{
				Walking.walk(enterance);
				Ulits.SleepTillStop(2);
			}
		}
	}

	public static void Attack(NPC worm) {
		Constants.STATUS = "Attack Grotworm...";
		if(worm == null){
			RoamingCave();
		}
		if (Camera.getPitch() > 16){
			MCamera.setPitch(Random.nextInt(10,15), 0);
		}else if (worm != null && (Players.getLocal().getInteracting() == null )) {
			if(worm.isOnScreen() && calc.isPointOnScreen(worm.getCentralPoint())){
				Mouse.hop((int)worm.getCentralPoint().getX(),(int) worm.getCentralPoint().getY());
				worm.interact("Attack",worm.getName());
				Ulits.turnTo(worm);
				Ulits.SleepTillStop(3);
			}else{
				Walking.walk(worm);
				Ulits.turnTo(worm);
				for(int i = 0 ; i < 10 && !worm.isOnScreen() ; i++,Task.sleep(100,150));
			}
		} else if(Players.getLocal().getInteracting() != null){
			NPC worm1 = NPCs.getNearest(nextgrotworms);
			if (worm1 != null && worm1.isOnScreen()) {
				Mouse.move(worm1.getCentralPoint());
			}else if(worm1 != null){
				Constants.STATUS = "Turning To Next Target...";
				/*Ulits.turnTo(worm1);*/
				Ulits.turnTo(worm1);
				
			}
			Abilities.Run();
		} else{
			RoamingCave();
		}
	}

	public static void RoamingCave() {
	/*	Camera.setPitch(Random.nextInt(10,15));*/
		Constants.STATUS = "Roaming Cave for Grotworms...";
		Tile[] Path = Constants.ROAMCAVE ? Constants.PATHROAMINGCAVE: Ulits.ReversePath(Constants.PATHROAMINGCAVE);
		Tile next = Walking.newTilePath(Path).getNext();
		Walking.newTilePath(Path).traverse();
		Camera.turnTo(next);
	    if(calc.distanceTo(Constants.PATHROAMINGCAVE[Constants.PATHROAMINGCAVE.length -1]) < 12){
	    	Constants.ROAMCAVE = false;
	    }else if(calc.distanceTo(Constants.PATHROAMINGCAVE[0]) < 12){
	    	Constants.ROAMCAVE = true;
	    }
	    
	}

	private static Filter<NPC> grotworms = new Filter<NPC>() {
		public boolean accept(NPC n) {
/*			if (!thereNonCombatGrotWorm()) {*/
				return n != null
						&& Ulits.Arraycontain(n.getId(), Constants.GROTWORMS_ID)
					
						&& ((!n.isInCombat() && n.getHealthPercent() > 99) || (n.getInteracting().equals(
								Players.getLocal()) || n.getInteracting() == null));
/*			} else {
				return n != null
						&& Ulits.Arraycontain(n.getId(), Constants.GROTWORMS_ID)
						&& (!n.isInCombat());
			}*/
		}
	};
	private static Filter<NPC> noncombatgrotworms = new Filter<NPC>() {
		public boolean accept(NPC n) {
			return n != null
						&& Ulits.Arraycontain(n.getId(), Constants.GROTWORMS_ID)
						&& calc.distanceBetween(Players.getLocal().getLocation(), n.getLocation()) < 10
						&& !n.isInCombat() && !n.getInteracting().equals(Players.getLocal());
		}
	};
	private static Filter<NPC> nextgrotworms = new Filter<NPC>() {
		public boolean accept(NPC n) {
			return n != null && Ulits.Arraycontain(n.getId(),Constants.GROTWORMS_ID)
					&& !n.isInCombat() && !n.getInteracting().equals(Players.getLocal());
		}
	};
	public static boolean thereNonCombatGrotWorm(){
		return false;
		/*return NPCs.getNearest(noncombatgrotworms) !=null;*/
	}
	public static boolean AtCave() {
		SceneObject g = SceneEntities.getNearest(71154, 71153, 71155);
		return g != null;
	}

	public static boolean AtGrotWorm() {
		boolean at = SceneEntities.getNearest(71613, 71607, 71729, 71585) != null
				|| NPCs.getNearest(Constants.GROTWORMS_ID) != null;
		if(!at)Constants.ROAMCAVE = true;
		return at;
	}
}
