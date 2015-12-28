package megascripts.api;


import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import shadowscripts.graphic.LogHandler;

/**
 * 
 * @author Magorium
 * 
 */
public class Combat {

	public static void TurnRet(boolean on) {
		int x = 0;
		if (on) {
			x = 1;
		} else {
			x = 0;
		}
		WidgetChild OffRetalite = Widgets.get(464).getChild(5);
		WidgetChild Combat = Widgets.get(548).getChild(124);
		WidgetChild Inv = Widgets.get(548).getChild(120);
		if (Settings.get(462) == x) {
			if(!OffRetalite.visible()){
				Combat.click(true);
			}
			OffRetalite.click(true);
			ShadowDungeon.SleepWhile(Settings.get(462) == x);
			Inv.click(true);
		}
	}
}
