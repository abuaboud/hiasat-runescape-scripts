package org.liquidscripts.fisher.jobs;


/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/3/14
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Actions {

    public static final Action DROP = new Drop();
    public static final Action FISHING = new Fishing();
    public static final Action BANKING = new Banking();
    public static final Action LOSING_TOOL = new LosingTool();
    public static final Action KARAMJA = new Karamja();

    public static void run(Action action) {
        try {
            if (!action.isStarted()) {
                action.setup();
                action.setStarted(true);
            }


            action.run();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
