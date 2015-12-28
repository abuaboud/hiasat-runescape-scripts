package shadowscripts.grotworms.ulits;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import shadowscripts.grotworms.api.calc;
import shadowscripts.grotworms.Constants;
public class Action {

	public static void doTeleport(Teleport t) {
		Constants.STATUS = "Teleporting Via Port Sarim lodstone...";
		if (!Widgets.get(1092).validate()) {
			WidgetChild e = Widgets.get(640, 67);
			e.interact("Cast");
			for (int i = 0; i < 50 && !Widgets.get(1092).validate(); i++, Task
					.sleep(100, 150))
				;
		} else {
			if (Mouse.click(Widgets.get(1092, t.getComponent()).getCentralPoint(), true)) {
				for (int i = 0; i < 10 && Widgets.get(1092).validate(); i++, Task
						.sleep(100, 150))
					;
				Task.sleep(2000, 2500);
				for (int i = 0; i < 80
						&& Players.getLocal().getAnimation() != -1; i++, Task
						.sleep(100, 150))
					;
			}
		}
	}

	public static void setAutoCast(){
		WidgetChild g = Widgets.get(640, 64);
		g.interact("Auto-cast");
		for(int i = 0 ; i < 25 && Settings.get(0) == 0; i++,Task.sleep(100,150));
	}
	public static boolean needTeleport(Tile t, Teleport r) {
		int minDist = (int) calc.distanceBetween(r.getLocation(), t);
		int mydist = calc.distanceTo(t) - 15;
		return (minDist + 15) < mydist;
	}

	public enum Teleport {
		AL_Kharid(new Tile(3296, 3184, 0), 40, "AL Kharid"), Ardougne(new Tile(
				2633, 3348, 0), 41, "Ardougne"), Burthorpe(new Tile(2898, 3544,
				0), 42, "Burthorpe"), Catherby(new Tile(2830, 3451, 0), 43,
				"Catherby"), Draynor(new Tile(3104, 3298, 0), 44, "Draynor"), Edgeville(
				new Tile(3066, 3505, 0), 45, "Edgeville"), Falador(new Tile(
				2966, 3403, 0), 46, "Falador"), Lumbridge(new Tile(3232, 3221,
				0), 47, "Lumbridge"), Port_Sarim(new Tile(3010, 3215, 0), 48,
				"Port Sarim"), Seers_Village(new Tile(2688, 3482, 0), 49,
				"Seers Village"), Taverly(new Tile(2877, 3442, 0), 50,
				"Taverly"), Varrock(new Tile(3213, 3376, 0), 51, "Varrock"), Yanille(
				new Tile(2528, 3095, 0), 52, "Yanille");

		private int comp;
		private Tile tile;

		Teleport(final Tile tile, final int comp, final String name) {
			this.tile = tile;
			this.comp = comp;
		}

		private int getComponent() {
			return this.comp;
		}

		private Tile getLocation() {
			return this.tile;
		}

	}
}
