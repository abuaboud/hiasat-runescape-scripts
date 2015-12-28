package org.liquidscripts.tutisland;

import org.liquidbot.osrs.api.Manifest;
import org.liquidbot.osrs.api.LiquidScript;
import org.liquidbot.osrs.api.SkillCategory;
import org.liquidbot.osrs.api.listeners.PaintListener;
import org.liquidbot.osrs.api.methods.interactive.Widgets;
import org.liquidbot.osrs.api.util.Time;
import org.liquidscripts.tutisland.jobs.*;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 11/1/13
 * Time: 1:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Manifest(scriptName = "LiquidTutIsland", author = "Magorium", description = "Do Tut Island", category = SkillCategory.MISC)
public class LiquidTutIsland extends LiquidScript implements PaintListener {

    private long startTime = System.currentTimeMillis();

    @Override
    public void onStop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private boolean clickRetrive = false;

    @Override
    public int operate() {
        if(!clickRetrive && Widgets.get(548) !=null && Widgets.get(548,122) !=null && Widgets.get(548,122).isVisible() && Widgets.get(548,122).getText().contains("bronze")){
            Widgets.get(548,123).click();
            for(int i = 0 ; i < 10 ;i++,Time.sleep(100,150));
            clickRetrive = true;
        }
        return JobsHandler.run();
    }

    @Override
    public void onStart() {
        JobsHandler.addJob(new TalkToRunescapeGuide());
        JobsHandler.addJob(new OpenPlayerControl());
        JobsHandler.addJob(new OpenDoorHandler());
        JobsHandler.addJob(new TalkToSurvivalExpert());
        JobsHandler.addJob(new OpenInventoryTab());
        JobsHandler.addJob(new CutTreeHandler());
        JobsHandler.addJob(new MakeFireHandler());
        JobsHandler.addJob(new OpenSkillsTab());
        JobsHandler.addJob(new CatchShrimpsHandler());
        JobsHandler.addJob(new CookingShrimpsHandler());
        JobsHandler.addJob(new OpenGateHandler());
        JobsHandler.addJob(new TalkToChef());
        JobsHandler.addJob(new MakeDoughHandler());
        JobsHandler.addJob(new CookingDoughHandler());
        JobsHandler.addJob(new OpenMusicTab());
        JobsHandler.addJob(new OpenEmoteHandler());
        JobsHandler.addJob(new DoEmoteHandler());
        JobsHandler.addJob(new DoRunHandler());
        JobsHandler.addJob(new WalkToQuestHandler());
        JobsHandler.addJob(new TalkToQuestGuide());
        JobsHandler.addJob(new OpenQuestTab());
        JobsHandler.addJob(new ClimbDownLaderHandler());
        JobsHandler.addJob(new TalkToMiningHandler());
        JobsHandler.addJob(new ProsepectHandler());
        JobsHandler.addJob(new MiningOreHandler());
        JobsHandler.addJob(new SmeltingHandler());
        JobsHandler.addJob(new SmithingHandler());
        JobsHandler.addJob(new TalkToCombat());
        JobsHandler.addJob(new OpenEquipmentTab());
        JobsHandler.addJob(new WieldingWeaponsHandler());
        JobsHandler.addJob(new WearArmorHandler());
        JobsHandler.addJob(new OpenCombatTab());
        JobsHandler.addJob(new AttackRatHandler());
        JobsHandler.addJob(new RangingRatHandler());
        JobsHandler.addJob(new ClimbUpLadderHandler());
        JobsHandler.addJob(new BankingHandler());
        JobsHandler.addJob(new TalkToAdvice());
        JobsHandler.addJob(new TalkToBrother());
        JobsHandler.addJob(new OpenPrayerHandler());
        JobsHandler.addJob(new OpenFriendsTabHandler());
        JobsHandler.addJob(new OpenIgnoreTab());
        JobsHandler.addJob(new TalkToMagic());
        JobsHandler.addJob(new OpenMagicTabHandler());
        JobsHandler.addJob(new AttackChickdenHandler());
    }

    private final Color color1 = new Color(0, 0, 0);

    private final Font font1 = new Font("Arial", 1, 13);

    @Override
    public void render(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setFont(font1);
        g.setColor(color1);
        g.drawString("RunTime: " + Time.parse(System.currentTimeMillis() - startTime), 16, 368);
    }
    //END: Code generated using Enfilade's Easel

}
