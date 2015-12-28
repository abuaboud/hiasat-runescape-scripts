package org.liquidscripts.tutisland;

import org.liquidbot.osrs.api.methods.data.movement.Camera;
import org.liquidbot.osrs.api.methods.data.movement.Walking;
import org.liquidbot.osrs.api.methods.interactive.GameEntities;
import org.liquidbot.osrs.api.methods.interactive.NPCs;
import org.liquidbot.osrs.api.methods.interactive.Players;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Condition;
import org.liquidbot.osrs.api.util.Log;
import org.liquidbot.osrs.api.util.Time;
import org.liquidbot.osrs.api.wrapper.GameObject;
import org.liquidbot.osrs.api.wrapper.NPC;
import org.liquidbot.osrs.api.wrapper.WidgetChild;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 1/5/14
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class Methods {

    private static NPC npc;
    private static GameObject gameObject;

    public static State getState() {

        if (textContains("the music player") && textContains("you can control")) {
            return State.OPEN_DOOR;
        } else if (textContains("Getting started") || (textContains("Player controls") && textContains("they will become clearer as you explore the game"))) {
            return State.TALK_TO_RUNESCAPE;
        } else if (textContains("Player controls") && textContains("icon found at the bottom")) {
            return State.OPEN_PLAYER_CONTROL;
        } else if (textContains("then click on the door") && textContains("Interacting with scenery")) {
            return State.OPEN_DOOR;
        } else if (textContains("Moving around") && textContains("Talk to the Survival Expert")) {
            return State.TALK_TO_SURVIVAL_EXPERT;
        } else if (textContains("Viewing the items that you were given") && textContains("click on the flashing backpack")) {
            return State.OPEN_INVENTORY_TAB;
        } else if (textContains("Cut down a tree") && textContains("some logs by clicking on one of the trees")) {
            return State.CUT_TREE;
        } else if (textContains("Making a fire")) {
            return State.MAKE_FIRE;
        } else if (textContains("near the inventory button") && textContains("skill stats")) {
            return State.OPEN_SKILL_TAB;
        } else if (textContains("your skill stats") && textContains("as you move your")) {
            return State.TALK_TO_SURVIVAL_EXPERT;
        } else if (textContains("catch some shrimp")) {
            return State.CATCH_SHRIMPS;
        } else if (textContains("Cooking your shrimp") || textContains("Burning your shrimp")) {
            return State.COOK_SHRIMPS;
        } else if (textContains("your first runescape meal") && textContains("Click on the gate") || textContains("Go through the gates shown by the arrow")) {
            return State.OPEN_GATE;
        } else if (textContains("Find your next instructor") && textContains("follow the path until")) {
            return State.OPEN_DOOR;
        } else if (textContains("Talk to the chef")) {
            return State.TALK_TO_CHEF;
        } else if (textContains("Making dough")) {
            return State.MAKE_DOUGH;
        } else if (textContains("Cooking dough") && !textContains("Well done")) {
            return State.COOK_DOUGH;
        } else if (textContains("Cooking dough") && textContains("Well done")) {
            return State.OPEN_MUSIC_TAB;
        } else if (textContains("Emotes") && textContains("Click on that to access your")) {
            return State.OPEN_EMOTE_TAB;
        } else if (textContains("Emotes") && textContains("try one out")) {
            return State.DO_EMOTE;
        } else if (textContains("Running") && textContains("why not try")) {
            return State.DO_RUN;
        } else if (textContains("Run to the next Guide") && textContains("run button turned")) {
            return State.DO_RUN_TO_QUEST;
        } else if (textContains("Talk with the quest guide") || textContains("Talk to the Quest Guide")) {
            return State.TALK_TO_QUEST_GUIDE;
        } else if (textContains("Click on the flashing icon next to your inventory")) {
            return State.OPEN_QUEST_TAB;
        } else if (textContains("enter some caves")) {
            return State.ENTER_CAVE;
        } else if (textContains("Speak to the Mining instructor") || (textContains("Mining and smithing") && textContains("first weapon yourself") && textContains("talk to him") && textContains("will help you")) || textContains("Talk to the Mining Instructor")) {
            return State.TALK_TO_MINING;
        } else if (textContains("you know there's tin in the grey rocks")) {
            return State.PROSPECT_COPPER;
        } else if (textContains("Prospecting") || textContains("there's copper in the brown")) {
            return State.PROSPECT_TIN;
        } else if (textContains("mine one tin ore")) {
            return State.MINING_TIN;
        } else if (textContains("some copper ore")) {
            return State.MINING_COPPER;
        } else if (textContains("smelt them to make a bronze bar")) {
            return State.SMELT_BAR;
        } else if (textContains("smithing a dagger")) {
            return State.SMITH_DAGGER;
        } else if ((textContains("combat") && (textContains("Speak to the guide")) || textContains("Speak to the combat"))) {
            return State.TALK_TO_COMBAT;
        } else if (textContains("Wielding weapons") && textContains("right of your backpack icon")) {
            return State.OPEN_EQUIPMENT_TAB;
        } else if (textContains("View equipment stats")) {
            return State.SHOW_EQUIPMENT_STATE;
        } else if (textContains("click your dagger to")) {
            return State.WEAR_DAGGER;
        } else if (textContains("Unequipping items")) {
            return State.UNQUIP_ITEMS;
        } else if (textContains("crossed swords icon to see the combat")) {
            return State.OPEN_COMBAT;
        } else if (textContains("Click on the gates indicated") && textContains("combat interface")) {
            return State.OPEN_GATE;
        } else if (textContains("Attacking") && textContains("Attack the rat")) {
            return State.ATTACK_RAT;
        } else if (textContains("Pass Through the gate and talk to the combat instructor")) {
            return State.TALK_TO_COMBAT;
        } else if (textContains("Rat ranging")) {
            return State.RANGE_RAT;
        } else if (textContains("Moving on") && textContains("ladder shown")) {
            return State.CLIMB_UP_LADDER;
        } else if (textContains("Banking")) {
            return State.TALK_TO_BANKER;
        } else if (textContains("this is your bank box")) {
            return State.OPEN_DOOR;
        } else if (textContains("Financial advice")) {
            return State.TALK_TO_ADVICE;
        } else if (textContains("Continue through the next door")) {
            return State.NEXT_DOOR;
        } else if (textContains("talk to the monk") || textContains("talk with brother brace") || textContains("this is your ignore list")) {
            return State.BROTHER_BRACE;
        } else if (textContains("to open the Prayer Menu")) {
            return State.OPEN_PRAYER_MENU;
        } else if (textContains("to open your friends list")) {
            return State.OPEN_FRIEND_TAB;
        } else if (textContains("this is your friends list")) {
            return State.OPEN_IGNORE_TAB;
        } else if (textContains("your final instructor") && !textContains("talk with the mage")) {
            return State.OPEN_DOOR;
        } else if (textContains("your final instructor") && textContains("talk with the mage")) {
            return State.TALK_TO_MAGIC;
        } else if (textContains("open up your final menu")) {
            return State.OPEN_MAGIC_TAB;
        } else if (textContains("this is your spells list") && textContains("ask the mage")) {
            return State.TALK_TO_MAGIC;
        } else if (textContains("Cast wind strike at a chicken")) {
            return State.ATTACK_CHICKEN;
        } else if (textContains("completed the tutorial")) {
            return State.TALK_TO_MAGIC;
        }
        return State.WAITING;
    }

    public static void interactObject(String name, String action) {
        gameObject = GameEntities.getNearest(name);
        if (gameObject != null) {
            if (gameObject.isOnScreen()) {
                gameObject.interact(action, gameObject.getComposite().getName());
                for (int i = 0; i < 20 && !Players.getLocal().isMoving(); i++, Time.sleep(100, 150)) ;
                for (int i = 0; i < 20 && Players.getLocal().getAnimation() != -1 || Players.getLocal().isMoving(); i++, Time.sleep(100, 150))
                    ;
            } else {
                if (gameObject.distanceTo() < 7) {
                    Camera.turnTo(gameObject);
                } else {
                    Walking.walkTo(gameObject);
                }
            }
        }
    }

    public static void interactObject(int id, String action) {
        gameObject = GameEntities.getNearest(id);
        if (gameObject != null) {
            if (gameObject.isOnScreen()) {
                gameObject.interact(action, gameObject.getComposite().getName());
                for (int i = 0; i < 20 && !Players.getLocal().isMoving(); i++, Time.sleep(100, 150)) ;
                for (int i = 0; i < 20 && Players.getLocal().getAnimation() != -1 || Players.getLocal().isMoving(); i++, Time.sleep(100, 150))
                    ;
            } else {
                if (gameObject.distanceTo() < 7) {
                    Camera.turnTo(gameObject);
                } else {
                    Walking.walkTo(gameObject);
                }
            }
        }
    }

    public static void interactNPC(String name, String action) {
        npc = NPCs.getNearest(name);
        if (npc != null) {
            if (npc.isOnScreen()) {
                npc.interact(action, npc.getName());
                for (int i = 0; i < 20 && !Players.getLocal().isMoving(); i++, Time.sleep(100, 150)) ;
                for (int i = 0; i < 20 && Players.getLocal().getAnimation() != -1 || Players.getLocal().isMoving(); i++, Time.sleep(100, 150))
                    ;
            } else {
                if (npc.distanceTo() < 7) {
                    Camera.turnTo(npc);
                } else {
                    Walking.walkTo(npc);
                }
            }
        }
    }

    public static void handleTalk(String name) {
        if (Widgets.canContinue()) {
            Widgets.clickContinue();
        } else {
            interactNPC(name, "Talk-to");
        }
    }


    public static boolean textContains(String text) {
        boolean a = false;
        if (Widgets.get(372) != null && Widgets.get(372).getChildren() != null) {
            for (WidgetChild widgetChild : Widgets.get(372).getChildren()) {
                if (widgetChild != null && widgetChild.getText() != null && widgetChild.getText().toLowerCase().contains(text.toLowerCase())) {
                    a = true;
                    break;
                }
            }
        }
        if (Widgets.get(421) != null && Widgets.get(421).getChildren() != null) {
            for (WidgetChild widgetChild : Widgets.get(421).getChildren()) {
                if (widgetChild != null && widgetChild.getText() != null && widgetChild.getText().toLowerCase().contains(text.toLowerCase())) {
                    a = true;
                    break;
                }
            }
        }
        return a;
    }

}
