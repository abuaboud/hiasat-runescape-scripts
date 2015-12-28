package shadowscripts.grotworms.node;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;

public class FailSafe extends Node {

	@Override
	public boolean activate() {
		return (Widgets.get(1218, 78).validate()
				|| Widgets.get(220, 16).validate()
				|| Widgets.get(137, 4).visible() 
				|| Widgets.get(1370, 30).visible()
				|| interactWithPlayer()
				|| (Widgets.get(640, 25).getText() != null && !Widgets.get(640, 25).getText().startsWith("1")));
	}

	@Override
	public void execute() {
		if(Widgets.get(640, 25).getText() != null && !Widgets.get(640, 25).getText().startsWith("1")) {
			Widgets.get(640,23).click(true);
			for (int i = 0; i < 20 && (Widgets.get(640, 25).getText() != null && !Widgets.get(640, 25).getText().startsWith("1")); i++, Task.sleep(100, 150));
		}else if(interactWithPlayer()){
			Tile m = Players.getLocal().getLocation();
			Walking.walk(new Tile(m.getX() + 1, m.getY() + 1, 0));
		}else if(Widgets.get(1370,30).visible()){
			Widgets.get(1370,30).click(true);
			for (int i = 0; i < 20 && Widgets.get(1370,30).visible(); i++, Task.sleep(100, 150));
		}else if(Widgets.get(137, 4).visible()){
			Widgets.get(137, 4).click(true);
			for (int i = 0; i < 20 && Widgets.get(137, 4).visible(); i++, Task.sleep(100, 150));
		}else if (Widgets.get(220, 16).validate()) {
			Widgets.get(220, 16).click(true);
			for (int i = 0; i < 20 && Widgets.get(220, 16).validate(); i++, Task.sleep(100, 150));
		} else {
			Widgets.get(1218, 78).click(true);
			for (int i = 0; i < 20 && Widgets.get(1218, 78).validate(); i++, Task.sleep(100, 150));
		}
	}
	private static boolean interactWithPlayer(){
		if(Players.getLocal().getInteracting() == null)
			return false;
		for(int i = 0 ; i < Players.getLoaded().length;i++){
			if(Players.getLoaded()[i] !=null && Players.getLocal().getInteracting().equals(Players.getLoaded()[i])){
				return true;
			}
		}
		return false;
	}
}
