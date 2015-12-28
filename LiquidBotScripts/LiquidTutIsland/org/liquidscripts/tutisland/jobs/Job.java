package org.liquidscripts.tutisland.jobs;

public abstract interface Job {

    public abstract boolean isActive();

    public abstract void run();

}
