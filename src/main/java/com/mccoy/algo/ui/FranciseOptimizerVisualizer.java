/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.algo.ui;

import com.mccoy.algo.FranciseExpansionOptimizer;
import com.mccoy.algo.FranciseExpansionOptimizer.Point;
import com.mccoy.algo.ui.common.AbstractUIObject;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ryanm
 */
public class FranciseOptimizerVisualizer {
    
    private static int PLOT_SPACING = 50;
    private static int PLOT_CENTER_ADJUST = 50;
    private final UIContainer uiContainer;
    private Map<Point, AbstractUIObject> uiObjectsMap = new HashMap<>();
    private FranciseExpansionOptimizer foe;
    private int plotColIterator = 0;
    private int plotRowIterator = 0;
    private State state = State.START;
    
    private int numberOfValidPlots = 0;
    
    Set<Point> houseLocations = new HashSet<>();
    
    public FranciseOptimizerVisualizer(FranciseExpansionOptimizer foe, UIContainer uiContainer) {
        this.foe = foe;
        this.uiContainer = uiContainer;
    }
    
    private enum State {
        START, FIND_OPT_PLOTS, COMPLETE, DONE;
    }
    
    public void tick(){
        
        switch(this.state) {
            case START:
                getNextPlot();
                break;
            case FIND_OPT_PLOTS:
                findOptimalPlots();
                break;    
            case COMPLETE:
                complete();
                break;    
            default:
                
        }
        
    }
    
    private void getNextPlot() {
        int[][] matrix = FranciseExpansionOptimizer.MATRIX;
        if(plotRowIterator >= matrix.length) {
            this.state = State.FIND_OPT_PLOTS;
            this.plotColIterator = 0;
            this.plotRowIterator = 0;
            return;
        }

        int col = plotColIterator;
        int row = plotRowIterator;
        if(col < matrix[0].length && row < matrix.length) {
            Point maybeHouse = new Point(row, col);
            Plot plot;
            if(matrix[maybeHouse.getRow()][maybeHouse.getCol()] == 1) {
                    houseLocations.add(maybeHouse);
                    plot =  new Plot(Color.MAGENTA, generatePosition(col), generatePosition(row), true);
                } else {
                    plot = new Plot(Color.GRAY, generatePosition(col), generatePosition(row), true);
                }
                uiObjectsMap.put(maybeHouse, plot);
                uiContainer.add(plot);
        }
        if(col >= matrix[0].length) {
            this.plotColIterator = 0;
            this.plotRowIterator++;
        } else {
            this. plotColIterator++;
        }     
    }

    private void findOptimalPlots() {
        int[][] matrix = FranciseExpansionOptimizer.MATRIX;
        int maxDistance = FranciseExpansionOptimizer.MAX_DISTANCE;
        if(plotRowIterator >= matrix.length) {
            this.state = State.COMPLETE;
            return;
        }

        int col = plotColIterator;
        int row = plotRowIterator;
        if(col < matrix[0].length && row < matrix.length) {
            Point maybeHouse = new Point(row, col);
            var plot = (Plot) uiContainer.getUIObject(uiObjectsMap.get(maybeHouse));
            if(!houseLocations.contains(maybeHouse)) {
                plot.setColor(Color.CYAN);
                if(isValidBuildLocation(maybeHouse, maxDistance)) {                   
                    plot.setColor(Color.GREEN);
                    System.out.println(maybeHouse);
                    numberOfValidPlots++;
                }
            }
        }
        if(col >= matrix[0].length) {
            this.plotColIterator = 0;
            this.plotRowIterator++;
        } else {
            this. plotColIterator++;
        }     
    }
    

    
    private void generatePlots(int increment) { 
        int[][] matrix = FranciseExpansionOptimizer.MATRIX;
        for(int col = 0;col < matrix[0].length;col++) {
            for(int row = 0;row < matrix.length;row++){
                Point maybeHouse = new Point(row, col);
                Plot plot;
                if(matrix[maybeHouse.getRow()][maybeHouse.getCol()] == 1) {
                    plot =  new Plot(Color.MAGENTA, generatePosition(col), generatePosition(row), true);
                } else {
                    plot = new Plot(Color.GRAY, generatePosition(col), generatePosition(row), true);
                }
                uiObjectsMap.put(maybeHouse, plot);
                uiContainer.add(plot);
            }
        }
    }
    
    private int generatePosition(int position) {
        return position * PLOT_SPACING + PLOT_CENTER_ADJUST;
    }
    
    private boolean isValidBuildLocation(Point pointToCheck, int maxDistance) {
        boolean result = true;
        
        for(Point house : this.houseLocations) {
            int colDistance = Math.abs(pointToCheck.getCol() - house.getCol());
            int rowDistance = Math.abs(pointToCheck.getRow() - house.getRow());
              
            if(rowDistance + colDistance > maxDistance) {
                return false;
            }
        }  
        return result;
    }
    
    private void complete() {
        this.state = State.DONE;
        uiContainer.add(new UIHeader(375, 375, 50, 50, true, "Complete! " + numberOfValidPlots + " Found!"));
    }
    
}
