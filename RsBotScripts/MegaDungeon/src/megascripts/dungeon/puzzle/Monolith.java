package megascripts.dungeon.puzzle;


import megascripts.api.Calc;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;


public class Monolith extends Puzzle{

	public static boolean Solved = false;
	
	@Override
	public String getAuthor() {
		return "Arthurr";
	}
	@Override
	public String getName() {
		return "Monolith";
	}
	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}
	@Override
	public boolean isValid() {
		if (Solved) {
			return false;
		}
		NPC m = NPCs.getNearest("Monolith");
		if (m == null) {
			return false;
		}
		return ulits.tileinroom(m.getLocation());
	}
	@Override
	public boolean isSolved() {
		return !isValid();
	}
	public static Filter<NPC> mattacker() {
		return new Filter<NPC>() {
			@Override
			public boolean accept(NPC shade) {
				NPC m = NPCs.getNearest("Monolith");
				return shade.getInteracting() != null && shade.getLevel() > 0
						&& Calc.Reach(shade)
						&& !shade.getName().equals("Monolith")
						&& shade.getInteracting().equals(m);
			}
		};
	}

	public static Filter<NPC> shades() {
		return new Filter<NPC>() {
			@Override
			public boolean accept(NPC shade) {
				return shade.getInteracting() != null && shade.getLevel() > 0
						&& Calc.Reach(shade)
						&& !shade.getName().equals("Monolith");
			}
		};
	}

	@Override
	public void solve() {
		ShadowDungeon.setStatus(getStatus());
		NPC m = NPCs.getNearest("Monolith");
			if (m.getPassiveAnimation() != 13072) {
				if (MyNpc.isOnScreen(m)) {
					m.interact("Activate");
					Task.sleep(300, 600);
				} else {
					MyPlayer.WalkTo(m.getLocation());
				}
			} else {
				ShadowDungeon.log(UnderATttack() + "");
				NPC shade = NPCs.getNearest(shades());
				NPC[] shade_group = NPCs.getLoaded(shades());
				NPC matt = NPCs.getNearest(mattacker());
				if(matt !=null){
					ShadowDungeon.log("isn't null");
				}else{
	
				}
				if (shade != null) {
					if (isUnderAttack(shade_group, m)) {
						ShadowDungeon.log("Aggro");
						if (MyNpc.isOnScreen(matt)) {
							matt.interact("Attack");
							Task.sleep(400, 800);
						} else {
							MyPlayer.WalkTo(matt.getLocation());
						}
					} else {
						if (Players.getLocal().getInteracting() == null) {
							if (MyNpc.isOnScreen(shade)) {
								shade.interact("Attack");
								Task.sleep(400, 800);
							} else {
								MyPlayer.WalkTo(shade.getLocation());
							}
						}
					}
				} else if (shade == null) {
					if (!Players.getLocal().isMoving()
							&& Calculations.distanceTo(m) > 2) {
						Walking.walk(m);
					}
				}
			}
	}

	private boolean UnderATttack() {
		NPC m = NPCs.getNearest("Monolith");
		if(m == null){
			return false;
		}
		for (NPC n : NPCs.getLoaded()) {
			if (n != null && n.getInteracting().equals(m)) {
				return true;
			}
		}
		return false;
	}
	private static boolean isUnderAttack(NPC[] s, NPC m) {
		for (NPC z : s) {
			if (z != null && z.getInteracting() != null
					&& z.getInteracting().equals(m)) {
				return true;
			}
		}
		return false;
	}
}
