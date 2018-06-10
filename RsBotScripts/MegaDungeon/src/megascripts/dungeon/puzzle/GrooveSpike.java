package megascripts.dungeon.puzzle;

import java.util.ArrayList;


import megascripts.api.Calc;
import megascripts.api.plugin.Puzzle;
import megascripts.dungeon.Constants;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class GrooveSpike extends Puzzle{

	public static final int[] Spike_Line1 = { 49390, 49392, 49394, 49396, 49414,
			49416, 49418, 49420, 49438, 49440, 49442, 49444, 54336, 54338,
			54340, 54342 };
	public static final int[] Spike_Line2 = { 49398, 49400, 49402, 49404, 49422,
			49424, 49426, 49428, 49446, 49448, 49450, 49452, 54344, 54346,
			54348, 54350 };
	public static final int[] Spike_Line3 = { 49406, 49408, 49410, 49412, 49430,
			49432, 49434, 49436, 49454, 49456, 49458, 49460, 54352, 54354,
			54356, 54358 };
    public static final int[][] Lines = {Spike_Line1,Spike_Line2,Spike_Line3};

	@Override
	public String getName(){
		return "Groove Spikes";
	}
	@Override
	public String getStatus(){
		return "Solving " + getName()+ "...";
	}
	@Override
	public String getAuthor() {
		return "Magorium";
	}
	@Override
	public boolean isValid(){
		SceneObject mm = SceneEntities.getNearest(Lines[2]);
		SceneObject me = SceneEntities.getNearest(Lines[1]);
		SceneObject mr = SceneEntities.getNearest(Lines[0]);
		if(me == null || mr == null || mm == null){
			return false;
		}
	     int b = (int) Calculations.distanceTo(SceneEntities.getNearest(Lines[2]));
	     int a = (int)Calculations.distanceTo(SceneEntities.getNearest(Lines[1]));
	     int c = (int)Calculations.distanceTo(SceneEntities.getNearest(Lines[0]));
		return c <= 2 || a ==1 || (b == 1 && a == 1);
	}
	@Override
	public boolean isSolved(){
		return !isValid();
	}
	@Override
	public void solve() {
    	Constants.LeaveDungeon = true;
		/*MegaDungeon.setStatus(getStatus());
		if(getcurrLine() != -1){
		int[] CurrLine = Lines[getcurrLine()];
	
		SceneObject sp = SceneEntities.getNearest(CurrLine);
		if (sp != null) {
			if (sp.isOnScreen()) {
				sp.interact("Step-onto");
				MegaDungeon.SleepTillStop();
				Task.sleep(1000,1500);
				MegaDungeon.SleepTillStop();
				Task.sleep(1000,1500);
				MegaDungeon.SleepTillStop();
			} else {
				if (Players.getLocal().isMoving()) {
					Walking.walk(sp);
					Camera.turnTo(sp);
					MegaDungeon.SleepTillStop();
				}
			}
		}
		if(IsCompleted()){
			MegaDungeon.AnnoucePuzzle(getName());
		}
		}*/
	}

	private static int getcurrLine() {
		ArrayList<Boolean> Lines_Boolean = new ArrayList<Boolean>();
		Lines_Boolean.clear();
		for (int x = 0; x < Lines.length; x++) {
			Lines_Boolean.add(Calc.Reach(SceneEntities.getNearest(Lines[x])));
		}
		int a = (int) Calculations.distanceTo(SceneEntities.getNearest(Lines[0]));
		int b = (int) Calculations.distanceTo(SceneEntities.getNearest(Lines[1]));
		int c = (int) Calculations.distanceTo(SceneEntities.getNearest(Lines[2]));
		if (Lines_Boolean.get(2) &&  c<= 2) {
			return 2;
		} else if (Lines_Boolean.get(1) && b<= 2) {
			return 1;
		} else if (Lines_Boolean.get(0) && a <= 2) {
			return 0;
		}
		return -1;
	}
}
