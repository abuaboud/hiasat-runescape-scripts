package org.liquidscripts.fisher.ui;

import org.liquid.automation.osrs.api.methods.data.Game;
import org.liquid.automation.osrs.api.methods.data.Skills;
import org.liquid.automation.osrs.api.methods.interactive.Players;
import org.liquid.automation.osrs.api.util.Random;
import org.liquid.automation.osrs.api.util.Time;
import org.liquid.automation.osrs.api.util.Timer;
import org.liquid.automation.osrs.api.wrapper.Player;
import org.liquidscripts.fisher.Storage;
import org.liquidscripts.fisher.wrapper.Fish;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 8/26/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedPaint {

    private static final Color color1 = new Color(255, 255, 255);

    private static final Font font1 = new Font("Verdana", 1, 10);

    private static final Image img1 = getImage("http://i61.tinypic.com/wugt38.png");

    public static final Rectangle chatBox = new Rectangle(0,340,520,143);

    private static final Color EXPCOLOR = new Color(255, 204, 42);

    private static final Font EXPFONT = new Font("Verdana", 0, 12);

    private static Point EXP_POINT;

    private static int LAST_XP;

    private static Timer EXPTIMER;

    private static Fish LAST_FISH_GAINED;
    private static int EXP;

    public static int STARTXP = -1;
    public static int STARTLEVEL = -1;
    public static long STARTTIME = System.currentTimeMillis();
    public static String STATUS = "NULL";

    public static void setTimer(long l, int exp) {
        EXPTIMER = new Timer(l);
        EXP = exp;
        Player loc = Players.getLocal();
        EXP_POINT = loc.getPointOnScreen();
    }

    private static Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
        }
        return null;
    }

    public static void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        if (Game.isLoggedIn() && Storage.fishToCaught !=null) {
            if(getXpGained() > LAST_XP && STARTXP > 0) {
                setTimer(1500,(getXpGained() - LAST_XP));
                if(Storage.fishToCaught .length > 1){
                    LAST_FISH_GAINED = Storage.fishToCaught [0].getXpPerFish() == (getXpGained() - LAST_XP) ? Storage.fishToCaught [0] :Storage.fishToCaught [1];
                  //  Storage.fishCought++;
                }
            }
            LAST_XP = getXpGained();
            if( !Storage.HIDE_PAINT){
            g.drawImage(img1, 0, 335, null);
            g.setFont(font1);
            g.setColor(color1);
            g.drawString("" + Skills.getCurrentLevel(Skills.Skill.FISHING) + " ( +" + getLevelGained() +")", 109, 430);
            g.drawString("" + Time.parse(System.currentTimeMillis() - STARTTIME), 104, 412);
            g.drawString(getFishNames() + " ", 114, 450);
            g.drawString("" + LAST_XP, 254, 412);
            g.drawString("" + getFishCaught(), 268, 432);
            g.drawString(STATUS + " (" + Storage.chosenMode.getName() + ")", 224, 449);
            g.drawString("" + perHour(LAST_XP), 374, 413);
            g.drawString("" + perHour(getFishCaught()), 395, 430);
            }
            paintExpGained(g);
        }
/*        g.setColor(Color.WHITE);
        g.drawString("Break", 690, 140);
        g.drawString("In: ", 666, 152);
        g.drawString("For: ", 666, 164);

        g.setColor(Color.ORANGE);
        g.drawString(Time.parse(LiquidFisher.timer.getRemaining()), 692, 152);
        g.drawString(Time.parse(LiquidFisher.restTimer.getRemaining()), 692, 164);*/
    }

    public static String getFishNames(){
        return Storage.fishToCaught.length > 1 ? Storage.fishToCaught [0].getName() + "," + Storage.fishToCaught [1].getName() : Storage.fishToCaught [0].getName();
    }
    public static void paintExpGained(Graphics g1) {
        if (EXPTIMER != null && EXPTIMER.isRunning() && Game.isLoggedIn()) {
            int y = (int) Math.abs((int) (EXPTIMER.getElapsed() / 32.5) * 1);
            if (EXPTIMER.getElapsed() > 750) {
                y = (int) Math.abs((int) (EXPTIMER.getElapsed() / 32.5) * 1.75);
            } else if (EXPTIMER.getElapsed() > 500) {
                y = (int) Math.abs((int) (EXPTIMER.getElapsed() / 32.5) * 1.5);
            } else if (EXPTIMER.getElapsed() > 250) {
                y = (int) Math.abs((int) (EXPTIMER.getElapsed() / 32.5) * 1.25);
            }
            g1.setFont(EXPFONT);
            g1.setColor(Color.BLACK);
            g1.drawString("+" + EXP + " XP", EXP_POINT.x - 10 - 1, (EXP_POINT.y + 1 - y));
            g1.setColor(EXPCOLOR);
            g1.drawString("+"+ EXP + " XP", EXP_POINT.x - 10, (EXP_POINT.y - y));

        }
    }

    public static int perHour(int item) {
        return (int) ((item) * 3600000D / (System.currentTimeMillis() - STARTTIME));
    }

    private static int getXpGained() {
        return Skills.getExperience(Skills.Skill.FISHING) - STARTXP;
    }

    private static int getLevelGained() {
        return Skills.getRealLevel(Skills.Skill.FISHING) - STARTLEVEL;
    }

    private static int getFishCaught() {
       return Storage.fishCought;
    }
}
