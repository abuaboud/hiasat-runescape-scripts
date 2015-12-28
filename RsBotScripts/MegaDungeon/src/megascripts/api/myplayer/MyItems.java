package megascripts.api.myplayer;


import megascripts.dungeon.Constants;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;


public class MyItems {

	public static void Interact(int ItemID,String string) {
		if (!Tabs.INVENTORY.isOpen()) {
			Tabs.INVENTORY.open();
		} else {
			final Item i = Inventory.getItem(ItemID);
			if (i != null && i.getWidgetChild().validate()) {
				int count = Inventory.getCount(ItemID);
                  i.getWidgetChild().interact(string);
                  if(string != "Use" && string != "Craft"){
      				for(int x=0;x<10 &&Inventory.getCount(ItemID) == count;x++,Task.sleep(100,150));

                  }
			}
		}
	}
	public static void Interact(int ItemID[],String string) {
		if (!Tabs.INVENTORY.isOpen()) {
			Tabs.INVENTORY.open();
		} else {
			final Item i = Inventory.getItem(ItemID);
			if (i != null && i.getWidgetChild().validate()) {
				int count = Inventory.getCount(ItemID);
                  i.getWidgetChild().interact(string);
                  if(string != "Use" && string != "Craft"){
      				for(int x=0;x<10 &&Inventory.getCount(ItemID) == count;x++,Task.sleep(100,150));

                  }
			}
		}
	}
	public static boolean contains(int[] traps){
		return Inventory.getCount(traps) !=0;
	}
	public static boolean contains(int traps){
		return Inventory.getCount(traps) !=0;
	}
	public static int getStackSize(int e){
		if(Inventory.getCount(e) == 0){
			return 0;
		}
		return Inventory.getItem(e).getStackSize();
	}
	public static int getCoinStack(){
		return getStackSize(Constants.COIN);
	}
}
