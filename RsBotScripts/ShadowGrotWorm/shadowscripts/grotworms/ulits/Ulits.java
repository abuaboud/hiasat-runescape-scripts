package shadowscripts.grotworms.ulits;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;

import shadowscripts.grotworms.Constants;
import shadowscripts.grotworms.api.MCamera;
import shadowscripts.grotworms.api.calc;


public class Ulits {

	public static int[] convertIntegers(ArrayList<Integer> integers) {
		int[] ret = new int[integers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = integers.get(i).intValue();
		}
		return ret;
	}

	public static int[] Reverse(int e[]) {
		int[] b = new int[e.length];
		for (int x = 0; x < e.length; x++) {
			b[e.length - 1 - x] = e[x];
		}
		return b;
	}

	public static void turnTo(Locatable loc) {
		if (loc == null || calc.isPointOnScreen(loc.getLocation().getCentralPoint()))
			return;
		MCamera.turnTo(loc, 20);
	}

	public static int getInventoryProfit() {
		int profit = 0;
		for (Item g : Inventory.getItems()) {
			if (g != null
					&& Arraycontain(g.getId(), convertIntegers(Constants.DROPS))) {
				int stacksize = g.getStackSize();
				profit = profit + getProfit(g.getId(), stacksize);
			}
		}
		return profit;
	}

	public static void SleepTillStop(int i) {
		Task.sleep(600, 900);
		int x = i * 10;
		for (int j = 0; j < x
				&& (Players.getLocal().isMoving() || Players.getLocal()
						.getAnimation() != -1); j++, Task.sleep(100, 150))
			;
	}

	public static int getProfit(int id, int stacksize) {
		if (Constants.PRICES.get(id) == null) {
			Constants.STATUS = "Grabbing non cached Price...";
			boolean b = (Arraycontain(id, Constants.NOTED_PROFIT) || Arraycontain(
					id, Constants.notedDrops)) ? true : false;
			Constants.PRICES.put(id, getPrice(id, b));
		}
		return Constants.PRICES.get(id) * stacksize;
	}

	public static boolean Arraycontain(int id, int[] id2) {
		boolean con = false;
		for (int i : id2) {
			if (i == id) {
				con = true;
				break;
			}
		}
		return con;
	}

	public static Tile[] ReversePath(Tile e[]) {
		Tile[] b = new Tile[e.length];
		for (int x = 0; x < e.length; x++) {
			b[e.length - 1 - x] = e[x];
		}
		return b;
	}

	public static int getPrice(int id, boolean noted) {
		if (Ulits.Arraycontain(id, Constants.CRIMSON_LOOT)) {
			return 1;
		}
		id = noted ? id - 1 : id;
		long MISSTIME = System.currentTimeMillis();
		try {
			String price;
			final URL url = new URL(
					"http://www.tip.it/runescape/json/ge_single_item?item="
							+ id);
			final URLConnection con = url.openConnection();
			con.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("mark_price")) {
					price = line.substring(line.indexOf("mark_price") + 13,
							line.indexOf(",\"daily_gp") - 1);
					price = price.replace(",", "");
					in.close();
					System.out.println("Loaded price of " + id + " result is "
							+ Integer.parseInt(price));
					Constants.STARTTIME = Constants.STARTTIME
							+ (System.currentTimeMillis() - MISSTIME);
					return Integer.parseInt(price);
				}
			}
		} catch (final Exception ignored) {
		}
		Constants.STARTTIME = Constants.STARTTIME
				+ (System.currentTimeMillis() - MISSTIME);
		return getPrice(id, noted);
	}
/*	public static int getPrice(int id, boolean noted) {
		if (Ulits.Arraycontain(id, Constants.CRIMSON_LOOT)) {
			return 1;
		}
		id = noted ? id - 1 : id;
		long MISSTIME = System.currentTimeMillis();
		try {
			System.out.println("Loading price " + id + "...");
			String price;
			final URL url = new URL(
					"http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item="
							+ id);
			final URLConnection con = url.openConnection();
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("price")) {
					price = line.substring(line.indexOf("price"),
							line.indexOf("}"));
					price = price.replace("k", "00").replaceAll("[\\D]", "");
					in.close();
					Constants.STARTTIME = Constants.STARTTIME + (System.currentTimeMillis() - MISSTIME);
					System.out.println("Loaded price of " + id + " result is "
							+ Integer.parseInt(price));
					return Integer.parseInt(price);
				}
			}
		} catch (final Exception ignored) {
		}
		Constants.STARTTIME = Constants.STARTTIME + (System.currentTimeMillis() - MISSTIME);
		return getPrice(id, noted);
	}*/

}
