package Main.Backend;

import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;

public class CellMap{
    private List<Particle> particles;
    private boolean[][] neighboursMatrix;

    private float actionRadius, cellSideSize, mapSideSize;

    public <T extends List<Particle> & RandomAccess> CellMap(T cells, float actionRadius, float mapSideSize){
        this(cells, actionRadius, actionRadius, mapSideSize);
    }

    public <T extends List<Particle> & RandomAccess> CellMap(T cells, float actionRadius, float cellSideSize, float mapSideSize){
        this.actionRadius = actionRadius;
        this.cellSideSize = cellSideSize;
        this.mapSideSize = mapSideSize;

        int cellCount = cells.size();

        this.particles = cells;
        this.neighboursMatrix = new boolean[cellCount][cellCount];
    }

    private void calculateIndexes(){
        for(Particle particle : this.particles){
            Position cellPos = particle.GetPos();
            int xIndex = (int)(cellPos.getX() / cellSideSize);
            int yIndex = (int)(cellPos.getY() / cellSideSize);
            particle.setIndex(xIndex, yIndex);
        }
    }

    //Particle index method
    public void calculateAllNeighbours(){
        calculateIndexes();
        for(int cellIndex1 = 0; cellIndex1 < particles.size(); cellIndex1++){
            for(int cellIndex2 = cellIndex1+1; cellIndex2 < particles.size(); cellIndex2++){
                Particle particle1 = particles.get(cellIndex1), particle2 = particles.get(cellIndex2);
                Index index1 = particle1.getIndex(), index2 = particle2.getIndex();

                boolean flag = false;
                if(Math.abs(index1.getX() - index2.getX()) <= 1 && Math.abs(index1.getY() - index2.getY()) <= 1){
                    if(particle1.IsInsideActionRadiusOf(particle2, this.actionRadius)){
                        flag = true;
                    }
                }

                neighboursMatrix[cellIndex1][cellIndex2] = flag;

            }
        }
    }

    //Brute force method
    public void calculateAllNeighboursBruteForce(){
        for(int i = 0; i < this.particles.size()-1 ; i++){
            for(int j = i+1; j < this.particles.size() ; j++){
                Particle particle1 = particles.get(i), particle2 = particles.get(j);
                if(particle1.IsInsideActionRadiusOf(particle2, this.actionRadius)){
                    this.neighboursMatrix[i][j] = true;
                }else{
                    this.neighboursMatrix[i][j] = false;
                }
            }
        }
    }

    public boolean areNeighbours(Particle particle1, Particle particle2){
        int cellIndex1 = particles.indexOf(particle1), cellIndex2 = particles.indexOf(particle2);

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

    public List<Particle> getNeighboursOf(Particle particle){
        int cellIndex = particles.indexOf(particle);

        List<Particle> neighbours = new LinkedList<>();

        for(int i = cellIndex+1; i< particles.size(); i++){
            if(neighboursMatrix[cellIndex][i]){
                neighbours.add(particles.get(i));
            }
        }

        return neighbours;
    }
}
