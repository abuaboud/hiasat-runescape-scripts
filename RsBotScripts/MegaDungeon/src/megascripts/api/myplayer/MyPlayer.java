package megascripts.api.myplayer;

import java.util.ArrayList;
import java.util.Collections;


import megascripts.api.myplayer.MyPrayer.Modern;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Character;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.CombatStatus;
import org.powerbot.game.client.CombatStatusData;
import org.powerbot.game.client.LinkedList;
import org.powerbot.game.client.LinkedListNode;
import org.powerbot.game.client.RSCharacter;


/**
 * 
 * @author Hyprion,Magorium
 * 
 */
public class MyPlayer {

	public static Character getInteracting() {
		return Players.getLocal().getInteracting();
	}

	public static boolean isPoisoned(){
		return Widgets.get(748,4).getTextureId() == 1801;
	}
	public static Tile getLocation() {
		return Players.getLocal().getLocation();
	}

	public static int getAnimation(){
		return Players.getLocal().getAnimation();
	}
	public static boolean isMoving() {
		return Players.getLocal().isMoving();
	}

	public static int getPrayerLevel() {
		return Constants.PRAYER_LEVEL;
	}

	public static void WalkTo(Tile e) {
			Walking.walk(e);
			Camera.turnTo(e);
			ShadowDungeon.SleepWhile(MyPlayer.isMoving());
	}
	public static void WalkTo(Tile e,boolean camera) {
		Walking.walk(e);
		ShadowDungeon.SleepWhile(MyPlayer.isMoving());
}
	public static void WalkToNearstTile(Tile[] d) {
		Tile e = getNearstTile(d);
			Walking.walk(e);
			Walking.walk(e);
			ShadowDungeon.SleepTillStop();
	}

	private static Tile getNearstTile(Tile[] d) {
		ArrayList<Integer> Dist = new ArrayList<Integer>();
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		Dist.clear();
		tiles.clear();
		for (Tile x : d) {
			int dist = (int) Calculations.distanceTo(x);
			Dist.add(dist);
			tiles.add(x);
		}
		int index = Dist.indexOf(Collections.min(Dist));
		return d[index];
	}

	public static void TurnPrayer(MyPrayer.Effect eff, boolean e) {
	/*	if (MyPlayer.getPrayerLevel() >= eff.getRequiredLevel()) {
			//if (e) {
			MegaDungeons.log("Prayer");
				if (MyPrayer.getRemainingPoints() > 0) {
					if (!MyPrayer.isActive(eff)) {
					
						Tabs.PRAYER.open();
						MyPrayer.setActivated(eff, true);
						Task.sleep(90, 110);
						Tabs.INVENTORY.open();
					}
				}
			
		}*/
	}

	public static CombatStatusData getAdrenalineBar(final RSCharacter accessor) {
		LinkedListNode sentinel = (LinkedListNode) ((LinkedList) accessor
				.getCombatStatusList()).getTail();
		LinkedListNode current = (LinkedListNode) sentinel.getNext();
		if (!sentinel.equals(current) && !sentinel.equals(current.getNext())) {
			sentinel = ((LinkedListNode) ((LinkedList) ((CombatStatus) current)
					.getData()).getTail());
			if (!sentinel.equals(sentinel.getNext())) {
				final CombatStatusData adrenalineBar = (CombatStatusData) sentinel
						.getNext();
				if (adrenalineBar != null) {
					return adrenalineBar;
				}
			}
		}
		return null;
	}

	public static CombatStatusData getHealthBar(final RSCharacter accessor) {
		LinkedListNode sentinel = (LinkedListNode) ((LinkedList) accessor
				.getCombatStatusList()).getTail();
		LinkedListNode current = (LinkedListNode) sentinel.getNext();
		if (!sentinel.equals(current)) {
			if (!sentinel.equals(current.getNext())) {
				current = (LinkedListNode) current.getNext();
			}
			sentinel = ((LinkedListNode) ((LinkedList) ((CombatStatus) current)
					.getData()).getTail());
			if (!sentinel.equals(sentinel.getNext())) {
				final CombatStatusData healthBar = (CombatStatusData) sentinel
						.getNext();
				if (healthBar != null) {
					return healthBar;
				}
			}
		}
		return null;
	}

	public static int getAdrenalinePercent(final RSCharacter accessor) {
		final CombatStatusData adrenalineBar = getAdrenalineBar(accessor);
		return adrenalineBar != null ? toPercent(adrenalineBar.getHPRatio()
				* Context.multipliers().CHARACTER_HPRATIO) : 0;
	}

	public static int getHealthPercent() {
		final CombatStatusData healthBar = getHealthBar(Players.getLocal()
				.get());
		return healthBar != null ? toPercent(healthBar.getHPRatio()
				* Context.multipliers().CHARACTER_HPRATIO) : 100;
	}

	public static int toPercent(final int ratio) {
		return (int) Math.ceil((ratio * 100) / 0xFF);
	}
	public static boolean containArea(ArrayList<Area> a){
		for(Area e : a){
			if(e.contains(MyPlayer.getLocation())){
				return true;
			}
		}
		return false;
	}
	public static void RESET_LEVEL(){
		Constants.PRAYER_LEVEL = 0;
		Constants.DUNGEONEERING_LEVEL = 0;
		Constants.COOKING_LEVEL =0;
		Constants.ATTACK_LEVEL = 0;
		Constants.DEFENCE_LEVEL =0;
		Constants.STRENGTH_LEVEL = 0;
	}
}
