package shadowscripts.grotworms.node;

import java.util.ArrayList;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.methods.Players;
import org.powerbot.core.script.util.Timer;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;

import shadowscripts.grotworms.Constants;

public class Abilities {

	public static void Run() {
		if (Players.getLocal().getAdrenalinePercent() == 100) {
			Keyboard.sendText(6 + "", false);
			Task.sleep(300, 600);
		} else {
			if (!Constants.ABILTY_TIMER.isRunning()) {
				int hotkey = getHotKey(Constants.HOTKEY);
				Keyboard.sendText(hotkey + "", false);
				Constants.HOTKEY.add(hotkey);
				Constants.ABILTY_TIMER = new Timer(Random.nextInt(100, 200));
			}
		}
	}

	public static int getHotKey(ArrayList<Integer> hotkeys2) {
		if (!hotkeys2.contains(1)) {
			return 1;
		} else if (!hotkeys2.contains(2)) {
			return 2;
		} else if (!hotkeys2.contains(3)) {
			return 3;
		} else if (!hotkeys2.contains(4)) {
			return 4;
		} else if (!hotkeys2.contains(5)) {
			return 5;
		}
		hotkeys2.clear();
		return 1;
	}
}
