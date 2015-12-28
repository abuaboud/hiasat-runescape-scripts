package shadowscripts.grotworms;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.powerbot.core.Bot;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.randoms.Login;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;

import org.powerbot.core.script.util.Filter;
import org.powerbot.core.script.util.Timer;


import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Lobby;
import org.powerbot.game.api.methods.widget.Lobby.World;
import org.powerbot.game.api.methods.widget.WidgetCache;

import org.powerbot.game.api.util.Random;
import org.powerbot.game.client.Client;

import shadowscripts.grotworms.api.MessageListenerHandler;
import shadowscripts.grotworms.node.AttackHandler;
import shadowscripts.grotworms.node.BankHandler;
import shadowscripts.grotworms.node.FailSafe;
import shadowscripts.grotworms.node.LootHandler;
import shadowscripts.grotworms.ulits.Signature;
import shadowscripts.grotworms.ulits.Ulits;
import shadowscripts.grotworms.Constants;





@Manifest(authors = { "Shadowfiend" }, description = "Kill Grotworms , 1.3m/hour", name = "ShadowGrotworms Pro",hidden = true)
public class ShadowGrotWorms extends ActiveScript implements MessageListener,PaintListener {

	private ArrayList<Node> nodes = new ArrayList<Node>();
	private Client client = Bot.client();
	@Override
	public void onStart(){
		Mouse.setSpeed(Speed.VERY_FAST);
		addloot(Constants.ALCH_ITEM);
		addloot(Constants.HERB_ITEM);
		addloot(Constants.OTHER_LOOT);
		addloot(Constants.CRIMSON_LOOT);
		addloot(Constants.RAW_PROFIT);
		addloot(Constants.NOTED_PROFIT);
		addloot(Constants.rareTable);
		addloot(Constants.grotDrop);
		addloot(Constants.notedDrops);
		addstore(Constants.HERB_ITEM);
		addstore(Constants.RAW_PROFIT);
		nodes.add(new FailSafe());
		nodes.add(new LootHandler());
		nodes.add(new AttackHandler());
		nodes.add(new BankHandler());
		getContainer().submit(new MessageListenerHandler());
		
		getContainer().submit(new Signature());
		Constants.FIRSTINV_PROFIT = Ulits.getInventoryProfit();
		Constants.STARTXP = Skills.getExperience(Skills.RANGE)
				+ Skills.getExperience(Skills.ATTACK)
				+ Skills.getExperience(Skills.STRENGTH)
				+ Skills.getExperience(Skills.DEFENSE)
				+ Skills.getExperience(Skills.CONSTITUTION)
				+ Skills.getExperience(Skills.MAGIC);

		if (Skills.getRealLevel(Skills.SUMMONING) > 51) {
			Constants.SUMMON_ACTIVE = true;
			Constants.SUMMONAMOUNT = 1;
			Constants.POUCHAMOUNT = 3;
		} else {
			Constants.SUMMON_ACTIVE = false;
			Constants.SUMMONAMOUNT = 0;
			Constants.POUCHAMOUNT = 0;
		}
		Constants.USE_ATT = false;
		Constants.USE_DEF = false;
		Constants.USE_PRAYER = false;
		Constants.USE_RANGE = false;
		Constants.USE_STR = false;
		Constants.FIRST_INV = true;
		Constants.ALCH_LOW_PROFIT = true;
		Constants.FOODAMOUNT = 17;
		Constants.CURRENT_FOOD = Constants.SHARK;
		Constants.EAT_PERCENT = 50;
		Constants.CurrentFamiliar = Summoning.Familiar.SPIRIT_TERRORBIRD;
		Constants.STARTTIME = System.currentTimeMillis();
	}
	private void addloot(int ...id){
		for(int i = 0 ; i < id.length;i++){
			Constants.DROPS.add(id[i]);
		}
	}
	private void addstore(int ...id){
		for(int i = 0 ; i < id.length;i++){
			Constants.Store.add(id[i]);
		}
	}
	@Override
	public int loop() {
		if(Environment.getUserId() == 818877 && Game.isLoggedIn() && !Widgets.get(137,53).getText().contains("Sythe461")){
			return 50;
		}
		if (client != Bot.client()) {
			WidgetCache.purge();
			Bot.context().getEventManager().addListener(this);
			client = Bot.client();
		}else if(Lobby.isOpen() && ThereIsFavoriteWorld()){
			Environment.enableRandom(org.powerbot.core.randoms.Login.class, false);
			System.out.println("There is Favorite World entering it :)");
			Widgets.get(906,193).click(true);
			Environment.enableRandom(org.powerbot.core.randoms.Login.class, true);
		}else if (Constants.STARTCHECK) {
			for (int i = 0; i < 70; i++) {
				if (Inventory.getItems() == null
						|| ((AttackHandler.AtGrotWorm() || AttackHandler
								.AtCave()) && Inventory
								.getCount(Constants.CURRENT_FOOD) == 0)) {
					sleep(100, 150);
				} else {
					Constants.STARTTIME = System.currentTimeMillis();
					Constants.STARTCHECK = false;
					break;
				}
			}
		}else if (Game.isLoggedIn()) {
			if (Widgets.get(640, 3).visible() && !Bank.isOpen()
					&& !Widgets.get(1092).validate()) {
				Widgets.get(640, 3).click(true);
			}
			if (Widgets.get(137, 56).getText() != null
					&& !Widgets.get(137, 56).getText().contains("Press Enter")
					&& !Bank.isOpen() && !Widgets.get(1092).validate()) {
				Keyboard.sendText("1", true);
			}

			try {
				if(Constants.INV_FIRSTCHECK){
					Constants.FIRSTINV_PROFIT = Ulits.getInventoryProfit();
					Constants.INV_FIRSTCHECK = false;
				}
				for (Node job : nodes) {
					if (job != null && job.activate()) {
						job.execute();
					}
				}
				Constants.TOTAL_PROFIT = (Ulits.getInventoryProfit()
						+ Constants.LAST_PROFIT + Constants.ALCH_PROFIT + Constants.FAMILARINV)
						- Constants.FIRSTINV_PROFIT;
				if(Inventory.getItem(Constants.NATURE_RUNE) !=null && Inventory.getItem(Constants.FIRE_RUNE) !=null){
					Constants.BANK_NATURERUNE= Inventory.getItem(Constants.NATURE_RUNE).getStackSize();
					Constants.BANK_FIRERUNE= Inventory.getItem(Constants.FIRE_RUNE).getStackSize();
				}
			} catch (Exception e) {

			}
		}
		return Random.nextInt(50, 100);
	}
	private boolean ThereIsFavoriteWorld() {
		return Widgets.get(906, 193).getText() != null
				&& Widgets.get(906, 193).getText().length() > 0;
	}
	private int getRandomWorld(){
		for(int i = 0 ; i < 30 && Lobby.getWorlds() == null ;i++,sleep(100,150));
		return Constants.WORLDP2P[Random.nextInt(0, Constants.WORLDP2P.length - 1)];
	}
	
	@Override
	public void messageReceived(MessageEvent e) {
/*		String message = e.getMessage().toLowerCase();
		if(message.contains("coins have been added to your money pouch".toLowerCase())){
			Constants.ALCH_PROFIT =  Constants.ALCH_PROFIT + Math.abs(Integer.parseInt(message.split(" ")[0].replaceAll("," ,"")));
			log.info("" + Math.abs(Integer.parseInt(message.split(" ")[0].replaceAll("," ,""))));
		}
		if(message.contains("familiar cannot".toLowerCase())){
			Constants.FAMILIAR_FULL = true;
		}			
		if(message.contains("your polypore staff has run out") || message.contains("you must wield a charged polypore staff")  ){
			Constants.RENEWAL = true;
		}*/
	}
    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }

	private int getHour(int item) {
		return (int) ((item) * 3600000D / (System.currentTimeMillis() - Constants.STARTTIME));
	}
	

    private final Color color1 = new Color(51, 102, 0);
    private final Color color2 = new Color(255, 255, 255);

    private final Font font2 = new Font("Arial Black", 1, 10);
    private final Font font1 = new Font("Arial Black", 0, 11);

    private final Image img1 = getImage("http://puu.sh/2vxZU");

	@Override
	public void onRepaint(Graphics g1) {
		 Graphics2D g = (Graphics2D)g1;
		if (!Game.isLoggedIn()) {
			return;
		}
		Constants.LASTINTERFACE = System.currentTimeMillis();
/*		 if(((Ulits.getInventoryProfit()  + Constants.LAST_PROFIT + Constants.ALCH_PROFIT + Constants.FAMILARINV )- Constants.FIRSTINV_PROFIT) != Constants.TOTAL_PROFIT){
			 System.out.println(Ulits.getInventoryProfit()  + "+"+ Constants.LAST_PROFIT + "+"+Constants.ALCH_PROFIT + "+"+ Constants.FAMILARINV + "-"+ Constants.FIRSTINV_PROFIT);
		 }*/
			Point p = Mouse.getLocation();
	    	int x = p.x,y = p.y;
			g.setColor(Color.white);
			g.drawLine(x, 0, x,551 );
			g.drawLine(0, y, 760, y);
			
		    g.drawImage(img1, 0, 369, null);
		    g.setFont(font1);
		    g.setColor(color1);
		    g.drawString("" + Timer.format(System.currentTimeMillis() - Constants.STARTTIME), 89, 468);
		    g.drawString("" + Constants.STATUS, 89, 491);
		    g.drawString("" + Constants.TOTAL_PROFIT + "(" + getHour(Constants.TOTAL_PROFIT) + ")", 89, 513);
	        g.drawString("V1.29", 286, 513);
	        
	        g.setFont(font2);
	        g.setColor(color2);
	        g.drawString("Shark:" + Constants.BANK_SHARKAMOUNT, 555, 500);
	        g.drawString("Falador Teleport:"+ Constants.BANK_FALADOR_TELEPORT, 555, 485);
	        g.drawString("Pouch Amount:"+ Constants.BANK_POUCHAMOUNT, 555, 470);
	        g.drawString("Summon Potion:"+ Constants.BANK_SUMMONAMOUNT, 555, 455);
	        g.drawString("Use Summon:"+ Constants.SUMMON_ACTIVE, 555, 440);
	        g.drawString("Polypore Staff:"+ Constants.BANK_POLYPORESTAFF, 555, 425);
	        g.drawString("Nature Rune:"+ Constants.BANK_NATURERUNE, 555, 410);
	        g.drawString("Fire Rune:"+ Constants.BANK_FIRERUNE, 555, 395);
	}


}
