package org.liquidscripts.fisher.wrapper;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 8/26/13
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class Fish {

    int id;
    int xp;
    int[] tools;
    String action;
    String name;
    String[] firstAction;

    public Fish(final int id,final int xp, final int[] tools, final String action, final String name,String[] firstAction) {
        this.id = id;
        this.xp = xp;
        this.tools = tools;
        this.action = action;
        this.name = name;
        this.firstAction = firstAction;
    }

    public String getName() {
        return name;
    }

    public String[] getActions(){
        return firstAction;
    }

    public String getAction() {
        return action;
    }

    public int[] getTools() {
        return tools;
    }

    public int getId() {
        return id;
    }

    public int getXpPerFish(){
        return xp;
    }

}
