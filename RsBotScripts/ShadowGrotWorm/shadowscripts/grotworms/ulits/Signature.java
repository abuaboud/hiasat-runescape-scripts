package shadowscripts.grotworms.ulits;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Properties;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.core.script.util.Timer;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.util.Time;

import shadowscripts.grotworms.Constants;



public class Signature extends LoopTask {

	public boolean activate() {
		return Constants.sig.getRemaining() == 0;
	}

	@Override
	public int loop() {
		try{
		if (activate()) {
			if (!Game.isLoggedIn()){
				return 50;
			}
			String user = Widgets.get(137, 53).getText()
					.replaceAll(" ", "_")
					.replace("<col=C86400>Sir_</col>", "");
			if (Environment.getDisplayName().toLowerCase().contains("shadowfiend")) {
					if (user.contains("Ahmad")) {
						Constants.sig = new Timer(900000);
					}
					if (user.contains("darklord")) {
						user = "darklord<img=3>:";
					}
					Constants.STATUS = "Updating Dynamic Signature...";
					long last = getLastUpdateTime(user);
					if (last > 0) {
						updateSiggy(user, -last);
					}
					updateSiggy(user, (getMyTime() == 0 ? 0 :getMyTime()- 3600000));
					Constants.sig = new Timer(900000);
				}
			}else if (Environment.getDisplayName().toLowerCase().contains("ch")) {
				String user = Widgets.get(137, 53).getText()
						.replaceAll(" ", "_")
						.replace("<col=C86400>Sir_</col>", "");
				save(user,getMyTime());
				Constants.sig = new Timer(300000);
			}else{
				Constants.sig = new Timer(10000000);
			}
		}catch (Exception e){
			
		}
		return 50;
	}
	private static void save(String name,long s) {
		Properties file = new Properties();
		file.setProperty(name,s+"");
		try {
			File f = new File(System.getProperty("user.home")
					+ "/AppData/Roaming/GoldFarmStatus/", name + ".ini");
			file.store(new FileWriter(f),
					"Last Settings");
		} catch (java.lang.Exception ioe) {
			System.out.println("Error occurred when saving!");
		}
	}
	public static void updateSiggy(String user,long time){
		try {
			URL submit = new URL("http://shadowscripts.hostzi.com/goldfarmsigs/submit.php?user=" + user +"&timerun=" + time);
			URLConnection con = submit.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(true);
			final BufferedReader rd = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (line.toLowerCase().contains("success")) {
					System.out.println("success "  +  user );
				} else if (line.toLowerCase().contains("fuck off")) {
					System.out.println("couldn't update Signature");
				}
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static long getMyTime(){
		Date date = new Date();
		long hours = (date.getHours()) * 3600000,min = date.getMinutes() * 60000, sec = date.getSeconds()*1000;
		return hours + min + sec;
	}
	private static long getLastUpdateTime(String user){
		try {
			final URL url = new URL("http://shadowscripts.hostzi.com/goldfarmsigs/signature.php?user=" + user);
			final URLConnection con = url.openConnection();
			con.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("success")){
					int i =  Integer.parseInt(line.split(" ")[0].replace("success", ""));
					in.close();
					return i;
				}
			}
		} catch (final Exception ignored) {
		}
		return 0;
	}

	
}
