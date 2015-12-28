package org.liquidscripts.fisher.jobs;

import org.liquidscripts.fisher.ui.ExtendedPaint;

/**
 * Created with IntelliJ IDEA.
 * User: Magorium
 * Date: 5/3/14
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Action {

    private String name = "";
    private String status = "";
    private boolean start = false;

    public boolean isStarted(){
        return start;
    }

    public void setStarted(boolean start){
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        ExtendedPaint.STATUS = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void run();

    public abstract void setup();

}
