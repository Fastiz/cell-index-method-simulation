package Main.Backend;

import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;

public class CellMap{
    private List<Cell> cells;
    private boolean[][] neighboursMatrix;

    private float actionRadius, cellSideSize, mapSideSize;


    public <T extends List<Cell> & RandomAccess> CellMap(T cells, float actionRadius, float mapSideSize){
        this(cells, actionRadius, actionRadius, mapSideSize);
    }

    public <T extends List<Cell> & RandomAccess> CellMap(T cells, float actionRadius, float cellSideSize, float mapSideSize){
        this.actionRadius = actionRadius;
        this.cellSideSize = cellSideSize;
        this.mapSideSize = mapSideSize;

        int cellCount = cells.size();

        this.cells = cells;
        this.neighboursMatrix = new boolean[cellCount][cellCount];

        calculateIndexes(cells);
        calculateAllNeighbours();
    }

    private void calculateIndexes(List<Cell> cells){
        for(Cell cell : cells){
            Position cellPos = cell.GetPos();
            int xIndex = (int)(cellPos.getX() / cellSideSize);
            int yIndex = (int)(cellPos.getY() / cellSideSize);
            cell.setIndex(xIndex, yIndex);
        }
    }

    private void calculateAllNeighbours(){
        for(int cellIndex1=0; cellIndex1 < cells.size(); cellIndex1++){
            for(int cellIndex2=cellIndex1+1; cellIndex2 < cells.size(); cellIndex2++){
                Cell cell1 = cells.get(cellIndex1), cell2 = cells.get(cellIndex2);
                Index index1 = cell1.getIndex(), index2 = cell2.getIndex();

                boolean flag = false;
                if(Math.abs(index1.getX() - index2.getX()) <= 1 && Math.abs(index1.getY() - index2.getY()) <= 1){
                    if(cell1.IsInsideActionRadiusOf(cell2, this.actionRadius)){
                        flag = true;
                    }
                }

                neighboursMatrix[cellIndex1][cellIndex2] = flag;

            }
        }
    }

    public boolean areNeighbours(Cell cell1, Cell cell2){
        int cellIndex1 = cells.indexOf(cell1), cellIndex2 = cells.indexOf(cell2);

        int max, min;
        if(cellIndex1 > cellIndex2){
            max=cellIndex1;
            min=cellIndex2;
        }else{
            max=cellIndex2;
            min=cellIndex1;
        }

        return neighboursMatrix[min][max];
    }

    public List<Cell> getNeighboursOf(Cell cell){
        int cellIndex = cells.indexOf(cell);

        List<Cell> neighbours = new LinkedList<>();

        for(int i=cellIndex+1; i<cells.size(); i++){
            if(neighboursMatrix[cellIndex][i]){
                neighbours.add(cells.get(i));
            }
        }

        return neighbours;
    }
}
