# FranciseExpansionOptimizer
Algorithm for finding the number of valid plot points a franchise can build from a maximum distance from all house points within a given matrix.
Visualization was added using AWT to show the algorithm at work!

#Approach

- All positions that contain houses are found within the matrix and stored in a Set
- Every other position's distance is compared to all the house points to determine if that position is a valid build location


**EXAMPLE**

- given the following matrix with a distance of 2
- "0"s represent potiential build locations and "1"s represent houses, "X"s represent the optimal build locations.

```
---------
|0 0 0 0|
|0 0 1 0|
|1 X X 1|
---------
```

![Showcase](https://github.com/rmccoy4145/rmccoy4145/blob/ac70b128376e932bc8f1b1bcdc3d59fcf1d982ec/images/FranchiseOptimizerShowcase.gif?raw=true)
