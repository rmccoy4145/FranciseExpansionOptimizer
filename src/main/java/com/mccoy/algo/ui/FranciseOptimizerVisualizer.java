/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mccoy.algo.ui;

import com.mccoy.algo.data.Point;
import com.mccoy.algo.data.NeighborhoodData;
import com.mccoy.algo.ui.common.AbstractUIObject;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Find number of valid plot points a franchise can build from a maximum distance from all house point within a given matrix
 * @author ryanm
 */
public class FranciseOptimizerVisualizer {
    
    private static final int PLOT_SPACING = 50;
    private static final int PLOT_CENTER_ADJUST = 50;
    private final UIContainer uiContainer;
    private final Map<Point, AbstractUIObject> uiObjectsMap = new HashMap<>();
    private int plotColIterator = 0;
    private int plotRowIterator = 0;
    private int outwardSearchIterator = 1;
    private final int[][] matrixInQuestion = NeighborhoodData.MATRIX;
    private final int maxDistance = NeighborhoodData.MAX_DISTANCE;
    
    private State state = State.START;
    
    private int numberOfValidPlots = 0;
    
    Set<Point> houseLocations = new HashSet<>();
    Set<Point> searchedMemo = new HashSet<>();
    
    public FranciseOptimizerVisualizer(UIContainer uiContainer) {
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
                    findOptimalPlotsBySearchAroundHouses();
                break;    
            case COMPLETE:
                complete();
                break;    
            default:
                
        }
        
    }
    
    private void getNextPlot() {
        int[][] matrix = matrixInQuestion;
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
 
        private void findOptimalPlotsBySearchAroundHouses() {
        int maxDistance = this.maxDistance;
        if(outwardSearchIterator > maxDistance) {
            this.state = State.COMPLETE;
            return;
        }
        
        houseLocations.stream().forEach((house) -> {
            //check left
            checkPoint(house.getRow(), house.getCol() - outwardSearchIterator, maxDistance);
            //check right
            checkPoint(house.getRow(), house.getCol() + outwardSearchIterator, maxDistance);
            //check down
            checkPoint(house.getRow() + outwardSearchIterator, house.getCol(), maxDistance);
            //check up
            checkPoint(house.getRow() - outwardSearchIterator, house.getCol(), maxDistance);
        });
        
        outwardSearchIterator++;    
    }

    private void checkPoint(int row, int col, int maxDistance) {
        int[][] matrix = matrixInQuestion;
        if(col >= 0 && col < matrix.length && row >= 0 && row < matrix.length) {
            Point point = new Point(row, col);
            if(!houseLocations.contains(point) && !searchedMemo.contains(point)) {
                var plot = (Plot) uiContainer.getUIObject(uiObjectsMap.get(point));
                plot.setColor(Color.ORANGE);
                if(isValidBuildLocation(point, maxDistance)) {
                    plot.setColor(Color.GREEN);
                    numberOfValidPlots++;
                    System.out.println(point);
                }
            }
            searchedMemo.add(point);
        }
    }
    

    
    private void generatePlots(int increment) { 
        int[][] matrix = matrixInQuestion;
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
        uiContainer.add(new UIHeader(25, 375, 50, 50, true, String.format("Complete! %d Plots Found within a Max Distance of %d", numberOfValidPlots, this.maxDistance)));
    }
    
}
