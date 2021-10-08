
package com.mccoy.algo;

import java.util.HashSet;
import java.util.Set;

/**
 * Find number of valid plot points a franchise can build from a maximum distance from all house point within a given matrix
 * @author ryanm
 */
public class FranciseExpansionOptimizer {
      public static int[][] MATRIX = new int[][]{
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,1,1,0,0,0},
        {0,0,0,0,0,0,1,0},
        {0,0,1,0,1,0,0,0},
        {0,0,0,0,0,0,0,0}
    };
    
      public static int MAX_DISTANCE = 4;
    
    /**
     * Traverse matrix to retrieve all valid build locations
     * @param maxDistance
     * @param matrix
     * @param houseLocations
     * @return 
     */
    static int calculateNumberOfValidBuildLocations(int maxDistance, int[][] matrix, Set<Point> houseLocations) {
        int numOfValidPlots = 0;
        for(int col = 0;col < matrix[0].length;col++) {
            for(int row = 0;row < matrix.length;row++){
                Point plot = new Point(row, col);
                if(!houseLocations.contains(plot)) {
                    if(isValidBuildLocation(plot, maxDistance, matrix, houseLocations)) {
                        System.out.println(plot);
                        numOfValidPlots++;
                    }
                }
            }
        }
        return numOfValidPlots;
    }
    
    /**
     * Compares build point to distance from all houses
     * Diagonal traversal not allowed 
     * @param pointToCheck
     * @param maxDistance
     * @param matrix
     * @param houses
     * @return 
     */
    static boolean isValidBuildLocation(Point pointToCheck, int maxDistance, int[][] matrix, Set<Point> houses) {
        boolean result = true;
        
        
        for(Point house : houses) {
            int colDistance = Math.abs(pointToCheck.getCol() - house.getCol());
            int rowDistance = Math.abs(pointToCheck.getRow() - house.getRow());
              
            //have to add colDistance to take account for diagonal traversal
            if(rowDistance + colDistance > maxDistance) {
                return false;
            }
        }  
        return result;
    }
    
    /**
     * Find all house locations
     * @param matrix
     * @return 
     */
    static Set<Point> getHouseLocations(int[][] matrix) {
        Set<Point> houses = new HashSet<>();
        
        for(int col = 0;col < matrix[0].length;col++) {
            for(int row = 0;row < matrix.length;row++){
                Point maybeHouse = new Point(row, col);
                if(matrix[maybeHouse.getRow()][maybeHouse.getCol()] == 1) {
                    houses.add(maybeHouse);
                }
            }
        }
        
        return houses;
    }
    
    /**
     * Used to store Row & Column for each point on the matrix
     */
    public static class Point {
        int row;
        int col;

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + this.row;
            hash = 89 * hash + this.col;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Point other = (Point) obj;
            if (this.row != other.row) {
                return false;
            }
            if (this.col != other.col) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Point{" + "row=" + row + ", col=" + col + '}';
        }
  
    }
    
    
}
