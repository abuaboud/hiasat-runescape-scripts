package shadowscripts.graphic;
/*
 * Copyright (c) 2012. Redex Scripting - Unauthorized use prohibited by author.
 */

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Swipe
 * Date: 7/14/12
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogEntry {
    public String text;
    public Color color;
    public long timeSent;
    public int alpha = 255;

    public LogEntry(String text, Color color) {
        this.text = text;
        this.color = color;
        timeSent = System.currentTimeMillis();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {

        this.color = color;
    }
}