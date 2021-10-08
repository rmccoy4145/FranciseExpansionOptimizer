/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.algo.ui;

import com.mccoy.algo.FranciseExpansionOptimizer;
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
    private final FranciseOptimizerVisualizer foe;
    private static UIContainer instance = null;
    
    private UIContainer() {
        this.foe = new FranciseOptimizerVisualizer(new FranciseExpansionOptimizer(), this);
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
        foe.tick();
        Iterator<AbstractUIObject> it = uiObjects.iterator();
        while(it.hasNext()) {
            it.next().tick();
        }
    }
    
    public int size(){
        return uiObjects.size();
    }
    
}
