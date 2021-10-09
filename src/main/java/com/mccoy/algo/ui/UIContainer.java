
package com.mccoy.algo.ui;

import com.mccoy.algo.utils.FranciseExpansionOptimizer;
import com.mccoy.algo.ui.common.AbstractUIObject;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ryanm
 */
public class UIContainer {
    
    final List<AbstractUIObject> uiObjects = new ArrayList<>();
    private final FranciseOptimizerVisualizer algoVisualizer;
    private static UIContainer instance = null;
    
    private UIContainer() {
        this.algoVisualizer = new FranciseOptimizerVisualizer(this);
    }
    
    public static UIContainer getInstance() {
        if(UIContainer.instance == null) {
            UIContainer.instance = new UIContainer();
            return UIContainer.instance;
        } else {
            return UIContainer.instance;
        }
    }
    
    public void add(AbstractUIObject uiObject){
        uiObjects.add(uiObject);
    }
    
    public AbstractUIObject getUIObject(AbstractUIObject uiObject){
        int idxOf = uiObjects.indexOf(uiObject);
        return uiObjects.get(idxOf);
    }
    
    public void render(Graphics g) {
        Iterator<AbstractUIObject> it = uiObjects.iterator();
        while(it.hasNext()) {
            it.next().render(g);
        }
    }
    
    public void tick() {
        algoVisualizer.tick();
        Iterator<AbstractUIObject> it = uiObjects.iterator();
        while(it.hasNext()) {
            it.next().tick();
        }
    }
    
    public int size(){
        return uiObjects.size();
    }
    
}
