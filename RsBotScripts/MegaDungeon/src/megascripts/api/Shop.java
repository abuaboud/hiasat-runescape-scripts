package megascripts.api;

import java.awt.Rectangle;


import megascripts.api.myplayer.MyItems;
import megascripts.api.myplayer.MyNpc;
import megascripts.api.myplayer.MyPlayer;
import megascripts.dungeon.Constants;
import megascripts.dungeon.ShadowDungeon;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


/**
 * 
 * @author Magorium
 * 
 */
public class Shop {

	public static int SHOP_WIDGET = 956;
	public static Rectangle RESOURCE_AREA = new Rectangle(28, 123, 448, 227);
	public static WidgetChild Close = Widgets.get(956, 18);

	public static boolean isOpen() {
		return Widgets.get(SHOP_WIDGET).validate();
	}

	public static void close() {
		for (int x = 0; x < 10; x++) {
			if (Shop.isOpen()) {
				Close.click(true);
				Task.sleep(400, 800);
			}
		}
	}

	public static void Open() {
		NPC Smuggler = NPCs.getNearest(11226);
		if (Smuggler != null) {
			if (MyNpc.isOnScreen(Smuggler)) {
				Smuggler.interact("Trade");
				ShadowDungeon.SleepWhile(!isOpen());
			} else {
				MyPlayer.WalkTo(Smuggler.getLocation());
			}
		}
	}
	public static void buy(int ItemId, int amount, int price,boolean r) {
		if (!MyItems.contains(ItemId)) {
		if (Shop.isOpen()) {
		 WidgetChild SCROLL = Widgets.get(956, 25).getChild(5);
			if (!MyItems.contains(ItemId)) {
				WidgetChild Item = getWidgetChild(ItemId);
				if (Item != null) {
					if (RESOURCE_AREA.contains(Item.getCentralPoint())) {
						if (amount == 1) {
							Item.interact("Buy 1");
						} else if (amount == 2) {
							Item.interact("Buy 5");
						} else if (amount == 3) {
							Item.interact("Buy 10");
						} else if (amount == 4) {
							Item.interact("Buy 50");
						} else if (amount == 5) {
							Item.interact("Buy 250");
						}
						Task.sleep(2000,2500);
					} else {
						if(SCROLL != null){
						SCROLL.click(true);
						}
					}
				}
				
			} else {
				Shop.close();
			}
		} else {
			Shop.Open();
		}
		}
	}
	public static void buy(int ItemId, int amount, int price) {
		if (!MyItems.contains(ItemId)) {
		if (Shop.isOpen()) {
		 WidgetChild SCROLL = Widgets.get(956, 25).getChild(5);
			if (!MyItems.contains(ItemId)) {
				int totalprice = getAmount(amount) * price;
				if(MyItems.getStackSize(Constants.COIN) < totalprice){
					MyItems.Interact(Constants.FOODTOEAT, "Sell 1");
					Task.sleep(2000,2500);
				}else{
				WidgetChild Item = getWidgetChild(ItemId);
				if (Item != null) {
					if (RESOURCE_AREA.contains(Item.getCentralPoint())) {
						if (amount == 1) {
							Item.interact("Buy 1");
						} else if (amount == 2) {
							Item.interact("Buy 5");
						} else if (amount == 3) {
							Item.interact("Buy 10");
						} else if (amount == 4) {
							Item.interact("Buy 50");
						} else if (amount == 5) {
							Item.interact("Buy 250");
						}
						Task.sleep(2000,2500);
					} else {
						if(SCROLL != null){
						SCROLL.click(true);
						}
					}
				}
				}
			} else {
				Shop.close();
			}
		} else {
			Shop.Open();
		}
		}
	}

	private static int getAmount(int amount) {
		if (amount == 1) {
			return 1;
		} else if (amount == 2) {
			return 5;
		} else if (amount == 3) {
			return 10;
		} else if (amount == 4) {
			return 50;
		} else if (amount == 5) {
			return 250;
		}
		return 0;
	}

	private static WidgetChild getWidgetChild(int itemId) {
		for (WidgetChild e : Widgets.get(SHOP_WIDGET).getChild(24).getChildren()) {
			if (e.getChildId() == itemId) {
				return e;
			}
		}
		return null;
	}

}
