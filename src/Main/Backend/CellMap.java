package Main.Backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;

public class CellMap {
    private List<Particle> particles;
    private boolean[][] neighboursMatrix;
    private ParticleList[][] indexes;

    private float actionRadius, cellSideSize, mapSideSize;
    private boolean withBorders;
    private int maxIndex;

    public <T extends List<Particle> & RandomAccess> CellMap(T cells, float actionRadius, float mapSideSize, boolean withBorders, float maxRadius) {
        this(cells, actionRadius, actionRadius, mapSideSize, withBorders, maxRadius);
    }

    public <T extends List<Particle> & RandomAccess> CellMap(T cells, float actionRadius, float cellSideSize, float mapSideSize, boolean withBorders, float maxRadius) {
        this.withBorders = withBorders;
        this.actionRadius = actionRadius;
        this.cellSideSize = (2 * maxRadius) + actionRadius;
        this.mapSideSize = mapSideSize;
        this.maxIndex = (int) Math.ceil(mapSideSize / this.cellSideSize);

        int cellCount = cells.size();

        this.particles = cells;
        this.neighboursMatrix = new boolean[cellCount][cellCount];
        this.indexes = new ParticleList[maxIndex][maxIndex];
    }

    private void initializeIndexes() {
        for (int i = 0; i < maxIndex; i++) {
            for (int j = 0; j < maxIndex; j++) {
                indexes[j][i] = new ParticleList();
            }
        }
    }

    private void calculateIndexes() {
        initializeIndexes();
        this.neighboursMatrix = new boolean[particles.size()][particles.size()];
        int id = 0;
        for (Particle particle : this.particles) {
            particle.setId(id++);
            Position cellPos = particle.getPos();
            int xIndex = (int) (cellPos.getX() / cellSideSize);
            int yIndex = (int) (cellPos.getY() / cellSideSize);
            indexes[yIndex][xIndex].add(particle);
            particle.setIndex(xIndex, yIndex);
        }
    }

    private boolean checkIfNeighborIsInsideRadius(Particle p1, Particle p2){
        if(p1.IsInsideActionRadiusOf(p2, this.actionRadius)){
            return true;
        }

        if(this.withBorders){
            Position pos1 = p1.getPos(), pos2 = p2.getPos();

            Position virtualPos = new Position(pos2.getX(), pos2.getY());
            
            //check if should move x position
            if(pos2.getX() > this.mapSideSize - pos2.getX()){
                if(Math.abs(pos2.getX() - this.mapSideSize + pos1.getX()) < Math.abs(pos2.getX() - pos1.getX())){
                    //then we would get closer by sustracting mapSideSize
                    virtualPos.setX(pos2.getX()-this.mapSideSize);
                }
            }else{
                if(Math.abs(pos2.getX() + this.mapSideSize - pos1.getX()) < Math.abs(pos2.getX() - pos1.getX())){
                    //then we would get closer by adding mapSideSize
                    virtualPos.setX(pos2.getX()+this.mapSideSize);
                }
            }
            
            //check if should move y position
            if(pos2.getY() > this.mapSideSize - pos2.getY()){
                if(Math.abs(pos2.getY() - this.mapSideSize + pos1.getY()) < Math.abs(pos2.getY() - pos1.getY())){
                    //then we would get closer by sustracting mapSideSize
                    virtualPos.setY(pos2.getY()-this.mapSideSize);
                }
            }else{
                if(Math.abs(pos2.getY() + this.mapSideSize - pos1.getY()) < Math.abs(pos2.getY() - pos1.getY())){
                    //then we would get closer by adding mapSideSize
                    virtualPos.setY(pos2.getY()+this.mapSideSize);
                }
            }

            Particle virtualParticle = new Particle(virtualPos, p2.getRadius());

            return p1.IsInsideActionRadiusOf(virtualParticle, this.actionRadius);
        }else{

            return false;
        }

    }

    public void printNeighbours() {
        for (int i = 0; i < particles.size(); i++) {
            for (int j = 0; j < particles.size(); j++)
                System.out.print(neighboursMatrix[i][j] + "  ");
            System.out.println();
        }
    }

    private int correctIndex(int index) {
        if(!withBorders && (index < 0 || index >= maxIndex))
            return -1;
        if (index < 0 && withBorders)
            return maxIndex - 1;
        if (index >= maxIndex && withBorders)
            return 0;
        return index;
    }

    public void calculateAllNeighbors(){
        calculateIndexes();

        for (int xIndex = 0; xIndex < maxIndex; xIndex++) {
            for (int yIndex = 0; yIndex < maxIndex; yIndex++) {
                List<Particle> particlesInAdjacentCells = new ArrayList<>();

                for(int adjacentIndexX = xIndex; adjacentIndexX <= xIndex + 1; adjacentIndexX++){
                    for(int adjacentIndexY = yIndex-1; adjacentIndexY <= yIndex + 1; adjacentIndexY++){

                        if(adjacentIndexX != xIndex || adjacentIndexY != yIndex-1){
                            int x = correctIndex(adjacentIndexX);
                            int y = correctIndex(adjacentIndexY);

                            if(x >= 0 && y >= 0){
                                particlesInAdjacentCells.addAll(indexes[y][x].getParticles());
                            }

                        }
                    }
                }

                for(Particle particle1 : indexes[yIndex][xIndex].getParticles()){
                    for(Particle particle2 : particlesInAdjacentCells){
                        boolean result = checkIfNeighborIsInsideRadius(particle1, particle2);
                        neighboursMatrix[particle1.getId()][particle2.getId()] = result;
                        neighboursMatrix[particle2.getId()][particle1.getId()] = result;
                    }
                }
            }
        }
    }

    //Brute force method
    public void calculateAllNeighboursBruteForce() {
        for (int i = 0; i < this.particles.size() - 1; i++) {
            for (int j = i + 1; j < this.particles.size(); j++) {
                Particle particle1 = particles.get(i), particle2 = particles.get(j);
                if (checkIfNeighborIsInsideRadius(particle1, particle2)) {
                    this.neighboursMatrix[i][j] = true;
                } else {
                    this.neighboursMatrix[i][j] = false;
                }
            }
        }
    }

    public boolean areNeighbours(Particle particle1, Particle particle2) {
        int cellIndex1 = particles.indexOf(particle1), cellIndex2 = particles.indexOf(particle2);

        int max, min;
        if (cellIndex1 > cellIndex2) {
            max = cellIndex1;
            min = cellIndex2;
        } else {
            max = cellIndex2;
            min = cellIndex1;
        }

        return neighboursMatrix[min][max];
    }

    public List<Particle> getNeighboursOf(Particle particle) {
        int cellIndex = particles.indexOf(particle);

        List<Particle> neighbours = new LinkedList<>();

        for (int i = cellIndex + 1; i < particles.size(); i++) {
            if (neighboursMatrix[cellIndex][i]) {
                neighbours.add(particles.get(i));
            }
        }

        for (int i = 0; i < cellIndex; i++) {
            if (neighboursMatrix[i][cellIndex]) {
                neighbours.add(particles.get(i));
            }
        }

        return neighbours;
    }
}
