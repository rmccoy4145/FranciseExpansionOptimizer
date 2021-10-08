
package com.mccoy.algo.ui.common;

import java.awt.Graphics;

/**
 *
 * @author ryanm
 */
public abstract class AbstractUIObject {
    
    private int x, y, height, width;
    private boolean visible;

    public abstract void tick();
    public abstract void render(Graphics g);
    
    public AbstractUIObject(int x, int y, int height, int width, boolean visible) {
        this.visible = visible;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    
}