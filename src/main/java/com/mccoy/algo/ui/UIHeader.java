
package com.mccoy.algo.ui;

import com.mccoy.algo.ui.common.AbstractUIObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author ryanm
 */
public class UIHeader extends AbstractUIObject{
    
    String text;

    public UIHeader(int x, int y, int height, int width, boolean visible, String text) {       
        super(x, y, height, width, visible);
        this.text = text;
    }

    
    @Override
    public void tick() {
        //do nothing
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Serif", Font.PLAIN, 24));
        g.drawString(text, this.getX(), this.getY());
    }
    
}
