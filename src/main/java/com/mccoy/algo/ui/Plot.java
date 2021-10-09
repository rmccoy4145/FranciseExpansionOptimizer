
package com.mccoy.algo.ui;

import com.mccoy.algo.ui.common.AbstractUIObject;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author ryanm
 */
public class Plot extends AbstractUIObject{

    
    private Color color = Color.CYAN;

    public Plot(Color color, int x, int y, boolean visible) {
        super(x, y, 25, 25, visible);
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    @Override
    public void tick() {
        //do nothing at the moment
    }

    @Override
    public void render(Graphics g) {
        g.setColor(this.color);
        g.drawRect(this.getX(), this.getY(), this.getHeight(), this.getWidth());
    }
    
}
