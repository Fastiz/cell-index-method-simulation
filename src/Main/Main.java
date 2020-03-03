package Main;

import Main.Backend.Cell;
import Main.Backend.CellMap;
import Test.PositionTest;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        float actionRadius = 100, mapSideSize = 1000;
        int N = 1000;

        ArrayList<Cell> cells = new ArrayList<>(N);

        for(int i=0; i<N ; i++){
            cells.add(new Cell((float)Math.random()*mapSideSize, (float)Math.random()*mapSideSize));
        }

        CellMap cellMap = new CellMap(cells, actionRadius, mapSideSize);

        List<Cell> neighbours = cellMap.getNeighboursOf(cells.get((N/2)));

        System.out.println(neighbours);
    }
}
