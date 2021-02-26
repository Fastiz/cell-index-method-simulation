# Cell Index Method

## About
This project is for an assignment of the course 'Simulación de Sistemas' (Systems simulation).

Cell Index Method is a method used for efficiency of neighboring particles. A in depth explanation can be read in wikipedia page of [Cell Lists](https://en.wikipedia.org/wiki/Cell_lists).
In this project the method is implemented and then it is then analyzed the execution time varying the number of cells for a given enclosure and particle radius. Also it is then compared against a Bruteforce approach for finding neighbors.

## Results

### Visual proof that the program finds neighbors

The following images can be found [here](https://github.com/Fastiz/cell-index-method-simulation/tree/master/photos)

#### Periodic recint
By periodic is meant that the enclosure boundaries are stitched together. That is to say, if particles were to move and reach one of the four boundaries then they would continue its trajectory on the opposite boundary.

![](https://github.com/Fastiz/cell-index-method-simulation/blob/master/photos/periodic1.png)

#### Non-periodic recint

![](https://github.com/Fastiz/cell-index-method-simulation/blob/master/photos/nonPeriodic1.png)

### Comparison of performance between number of cells

<img src="https://github.com/Fastiz/cell-index-method-simulation/blob/master/photos/CIM.png" alt="" width="640"/>

The plot labels (M2, M5, M10, M20) represent the side length in terms of cells in which the enclosure is divided. For example, M2 represents 2x2 Cells.

<img src="https://github.com/Fastiz/cell-index-method-simulation/blob/master/photos/bruteforce.png" alt="" width="640"/>

Note: the error bars represent the standard deviation from multiple executions of random particle distribution.
