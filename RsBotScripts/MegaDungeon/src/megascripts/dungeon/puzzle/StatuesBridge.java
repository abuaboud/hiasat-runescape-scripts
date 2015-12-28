package megascripts.dungeon.puzzle;

import java.awt.Color;

import megascripts.api.ulits;
import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyObjects;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import shadowscripts.graphic.LogHandler;


public class StatuesBridge extends Puzzle{

	public static int[] Broken_Statue = { 40159,40157,40158, 54617 };
	public static int[] MINING_ROCKS = { 39980, 54613,39989 ,39988};
	public static int Stone = 19877;
	public static int PresurePad[] = { 54616, 40107,40150 ,40145};
	public static int Broken_Bridge[] = {40004, 40002,40003, 39991 };
	public static WidgetChild ERROR = Widgets.get(1186, 1);

	@Override
	public String getName() {
		return "Statues Bridge";
	}
	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public boolean isValid() {
		return SceneEntities.getNearest(Broken_Bridge) != null
				&& ulits.tileinroom(SceneEntities.getNearest(Broken_Bridge)
						.getLocation());
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		NPC statue = MyNpc.getNearstNpc("Statue");
		if (statue != null) {
			if (MyNpc.isOnScreen(statue)) {
				if (statue.interact("Push")) {
					Task.sleep(1000, 1500);
					ShadowDungeon.SleepTillStop();
					ShadowDungeon.SleepTillStop();
					ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
					Task.sleep(1000, 1500);
					ShadowDungeon.SleepTillStop();
					ShadowDungeon.SleepTillStop();
				}
			} else {
				MyPlayer.WalkTo(statue.getLocation());
			}
		} else {
			if (MyObjects.There(Broken_Statue)) {
				if (MyItems.contains(Stone)) {
					SceneObject n = SceneEntities.getNearest(Broken_Statue);
					if (n != null && n.validate()) {
						if (MyObjects.isOnScreen(n)) {
							n.interact("Repair");
							Task.sleep(1000, 1500);
						} else {
							MyPlayer.WalkTo(n.getLocation());
						}
					}
				} else {
					SceneObject n = SceneEntities.getNearest(MINING_ROCKS);
					if (n != null && n.validate()) {
						if (MyObjects.isOnScreen(n)) {
							n.interact("Mine");
							Task.sleep(1000, 1500);
							if (ERROR.validate()) {
								if (ERROR.getText().contains("You require")) {
									Constants.Break_Puzzle = true;
									LogHandler
											.Print(ERROR.getText(), Color.red);
								}
							}
						} else {
							MyPlayer.WalkTo(n.getLocation());
						}
					}
				}
			}
		}
	}
}
