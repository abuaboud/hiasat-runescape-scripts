package megascripts.dungeon.boss;

import java.awt.Color;
import java.util.ArrayList;


import megascripts.api.Calc;
import megascripts.api.Combat;
import megascripts.api.Flood;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.myplayer.MyPrayer;
import megascripts.api.myplayer.MyPrayer.Ancient;
import megascripts.api.myplayer.MyPrayer.Modern;
import megascripts.api.plugin.Boss;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

import shadowscripts.graphic.LogHandler;

public class Riftsplitter extends Boss {

	static ArrayList<Tile> PortalTile = new ArrayList<Tile>();
	static ArrayList<Area> PortalArea = new ArrayList<Area>();
	static Timer Portal_Life = new Timer(10000);
    @Override
	public String getName() {
		return "Har'Lakk the Riftsplitter";
	}
    @Override
	public String getAuthor() {
		return "Magorium";
	}
    @Override
	public String getStatus() {
		CurrentBoss.BossName = getName();
		return "Attacking " + CurrentBoss.BossName + "...";
	}
    @Override
	public boolean isValid() {
		return MyNpc.getNearstNpc(getName()) != null
				&& Calc.Reach(MyNpc.getNearstNpc(getName()));
	}
    @Override
	public void Kill() {
		ShadowDungeon.setStatus(getStatus());
		NPC Boss = MyNpc.getNearstNpc(getName());
		MyPrayer.Effect effect = MyPrayer.Modern.PROTECT_FROM_MAGIC;
		MyPrayer.Effect effect2 = MyPrayer.Ancient.DEFLECT_MAGIC;
		if (MyPlayer.getPrayerLevel() < effect2.getRequiredLevel()) {
			MyPlayer.TurnPrayer(effect, MyPrayer.isModernSetActive());
		} else {
			MyPlayer.TurnPrayer(effect2, !MyPrayer.isModernSetActive());
		}
		if (Boss.getMessage() != null) {
			String msg = Boss.getMessage().toLowerCase();
			if (msg.contains("miasma") || msg.contains("flame portal")|| msg.contains("cut you"))
				LogHandler.Print("[" + getName() + "]: "+ "Walking Away from Portal...", Color.BLUE);
			PortalTile.add(MyPlayer.getLocation());
			PortalArea.add(generatePortalArea(PortalTile.get(0)));
			Portal_Life = new Timer(10000);
			WalktoRandomTileOnMap();
		}
		if (!Portal_Life.isRunning()) {
			PortalTile.clear();
			PortalArea.clear();
		}
		if (!MyPlayer.isMoving()) {
			if (MyPlayer.containArea(PortalArea)) {
				WalktoRandomTileOnMap();
			} else {
				MyNpc.Attack(Boss);
			}
		}
	}

	private static Area generatePortalArea(Tile t) {
		int tx = t.getX();
		int ty = t.getY();
		return new Area(new Tile(tx + 1, ty + 1, 0),
				new Tile(tx - 1, ty - 1, 0), new Tile(tx + 1, ty - 1, 0),
				new Tile(tx - 1, ty + 1, 0));
	}

	public static void WalktoRandomTileOnMap() {
		Constants.CurrentRoom = Flood.getArea();
		Tile[] tiles = Constants.CurrentRoom.getTileArray();
		Tile randTile = tiles[Random.nextInt(0, tiles.length)];
		MyPlayer.WalkTo(randTile);
	}
}
