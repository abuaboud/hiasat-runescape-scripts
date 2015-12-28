package megascripts.dungeon.puzzle;

import megascripts.api.Combat;
import megascripts.api.ulits;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.game.api.wrappers.interactive.NPC;



public class Mercenaryleader extends Puzzle{

	@Override
	public String getAuthor() {
		return "Magorium";
	}

	@Override
	public String getName() {
		return "Mercenary leader";
	}

	@Override
	public String getStatus() {
		return "Solving " + getName() + "...";
	}

	@Override
	public boolean isValid() {
		NPC Leader = MyNpc.getNearstNpc("Mercenary leader");
		return Leader != null && ulits.tileinroom(Leader.getLocation());
	}

	@Override
	public boolean isSolved() {
		return !isValid();
	}

	@Override
	public void solve() {
		NPC Leader = MyNpc.getNearstNpc("Mercenary leader");
		Combat.TurnRet(false);
        if(!Leader.isInCombat()){
        	Leader.interact("Attack");
        }else{
        	MyPlayer.WalkTo(Leader.getLocation());
        }
        if(isSolved()){
			ShadowDungeon.AnnoucePuzzle(getName(), getAuthor());
        }
	}
}
