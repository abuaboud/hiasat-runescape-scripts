package org.liquidscripts.fisher;

import org.liquid.automation.osrs.api.Manifest;
import org.liquid.automation.osrs.api.RandomEventHandler;
import org.liquid.automation.osrs.api.LiquidScript;
import org.liquid.automation.osrs.api.SkillCategory;
import org.liquid.automation.osrs.api.listeners.MessageListener;
import org.liquid.automation.osrs.api.listeners.PaintListener;
import org.liquid.automation.osrs.api.methods.data.Bank;
import org.liquid.automation.osrs.api.methods.data.Game;
import org.liquid.automation.osrs.api.methods.data.Inventory;
import org.liquid.automation.osrs.api.methods.data.Skills;
import org.liquid.automation.osrs.api.methods.interactive.GameEntities;
import org.liquid.automation.osrs.api.methods.interactive.GroundItems;
import org.liquid.automation.osrs.api.methods.interactive.Players;
import org.liquid.automation.osrs.api.util.*;
import org.liquid.automation.osrs.api.wrapper.Area;
import org.liquid.automation.osrs.api.wrapper.GameObject;
import org.liquid.automation.osrs.api.wrapper.GroundItem;
import org.liquid.automation.osrs.api.wrapper.Tile;
import org.liquidscripts.fisher.jobs.Actions;
import org.liquidscripts.fisher.ui.ExtendedPaint;
import org.liquidscripts.fisher.ui.FisherGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA. User: Magorium Date: 5/3/14 Time: 5:03 PM To
 * change this template use File | Settings | File Templates.
 */

@Manifest(scriptName = "LiquidFisher", description = "Advanced Fishing Script", author = "Magorium", category = SkillCategory.FISHING)
public class LiquidFisher extends LiquidScript implements MessageListener,
        PaintListener, MouseListener {

    public static org.liquid.automation.osrs.api.util.Timer timer = new org.liquid.automation.osrs.api.util.Timer(
            60 * 25 * 1000);

    public static org.liquid.automation.osrs.api.util.Timer restTimer = new org.liquid.automation.osrs.api.util.Timer(
            0);
    private static final Area CATHEBERY_AREA = new Area(
            new Tile(2786, 3390, 0),
            new Tile(2741, 3490, 0),
            new Tile(2847, 3525, 0),
            new Tile(2892, 3435, 0),
            new Tile(2871, 3385, 0)
    );
    public static final Area DRAYNOR_AREA = new Area(
            new Tile(3066, 3268, 0),
            new Tile(3126, 3269, 0),
            new Tile(3130, 3194, 0),
            new Tile(3059, 3213, 0)
    );
    private static org.liquid.automation.osrs.api.util.Timer t = new org.liquid.automation.osrs.api.util.Timer(
            0);


    @Override
    public int operate() {

		if (Game.isLoggedIn() && !Storage.chosenLocation.equals(Storage.KARAMJA)
                && !CATHEBERY_AREA.contains(Players.getLocal().getLocation()) && !DRAYNOR_AREA.contains(Players.getLocal().getLocation())) {
			Time.sleep(500);
		} else {

			/*
			 * if (!timer.isRunning() && (!Game.isLoggedIn() ||
			 * (!Players.getLocal().isInCombat() && !Bank.isOpen()))) {
			 * RandomEventHandler.loginHandler.setEnabled(false); if
			 * (Game.isLoggedIn()) { Game.logout(); restTimer = new
			 * org.liquid.automation.osrs.api.util.Timer(Random.nextInt(45,60) * 6 *
			 * 1000); }
			 * System.out.println(Time.parse(restTimer.getRemaining())); if
			 * (!restTimer.isRunning()) {
			 * RandomEventHandler.loginHandler.setEnabled(true); timer = new
			 * org.liquid.automation.osrs.api.util.Timer(60 * 25 * 1000); } } else
			 */

        if (Game.isLoggedIn()) {
            if (Game.isLoggedIn() && ExtendedPaint.STARTXP == -1
                    && Skills.getExperience(Skills.Skill.FISHING) > 0) {
                ExtendedPaint.STARTXP = Skills
                        .getExperience(Skills.Skill.FISHING);
                ExtendedPaint.STARTLEVEL = Skills
                        .getRealLevel(Skills.Skill.FISHING);
            }

            if (!Inventory.containsAll(Storage.fishToCaught[0].getTools())) {
                Actions.run(Actions.LOSING_TOOL);
            } else if (Inventory.isFull()
                    || (Storage.chosenMode.equals(Storage.MODE.BANKING) && Bank
                    .isOpen())) {
                if (Storage.chosenMode.equals(Storage.MODE.POWERFISH)) {
                    Actions.run(Actions.DROP);
                } else if (Storage.chosenMode.equals(Storage.MODE.BANKING)) {
                    if (Storage.chosenLocation.equals(Storage.KARAMJA)) {
                        Actions.run(Actions.KARAMJA);
                    } else {
                        Actions.run(Actions.BANKING);
                    }
                }
            } else if (Players.getLocal().getAnimation() == -1) {

                Actions.run(Actions.FISHING);
            }


        }

        }
        return Random.nextInt(250, 300);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onStart() {
        Storage.started = false;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Storage.fisherGui = new FisherGUI();
                Storage.fisherGui.setVisible(true);
            }
        });
        while (!Storage.started) {
            Time.sleep(500, 1000);
        }
    }

    @Override
    public void render(Graphics graphics) {
        ExtendedPaint.paint(graphics);
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {

        if (messageEvent.getMessage().contains("You catch some")) {
            Storage.fishCought++;
            // PopLog.sendPopUp("+ " +
            // messageEvent.getMessage().replace("You catch some ",
            // "").replace(".", ""), Color.CYAN);
        }

        if (messageEvent.getMessage().contains("You catch a ")) {
            Storage.fishCought++;
            // PopLog.sendPopUp("+ " +
            // messageEvent.getMessage().replace("You catch a ",
            // "").replace(".", ""), Color.RED);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (ExtendedPaint.chatBox.contains(e.getPoint())) {
            Storage.HIDE_PAINT = !Storage.HIDE_PAINT;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // To change body of implemented methods use File | Settings | File
        // Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // To change body of implemented methods use File | Settings | File
        // Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // To change body of implemented methods use File | Settings | File
        // Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // To change body of implemented methods use File | Settings | File
        // Templates.
    }
}
