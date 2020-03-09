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

    public <T extends List<Particle> & RandomAccess> CellMap(T cells, float actionRadius, float mapSideSize, boolean withBorders) {
        this(cells, actionRadius, actionRadius, mapSideSize, withBorders);
    }

    public <T extends List<Particle> & RandomAccess> CellMap(T cells, float actionRadius, float cellSideSize, float mapSideSize, boolean withBorders) {
        this.withBorders = withBorders;
        this.actionRadius = actionRadius;
        this.cellSideSize = Math.max(cellSideSize, actionRadius);
        this.mapSideSize = mapSideSize;
        this.maxIndex = (int) Math.ceil(mapSideSize / this.cellSideSize);

        int cellCount = cells.size();

        this.particles = cells;
        this.neighboursMatrix = new boolean[cellCount][cellCount];
        this.indexes = new ParticleList[maxIndex][maxIndex];
    }

    private void calculateIndexes() {
        int id = 0;
        for (Particle particle : this.particles) {
            particle.setId(id++);
            Position cellPos = particle.getPos();
            int xIndex = (int) (cellPos.getX() / cellSideSize);
            int yIndex = (int) (cellPos.getY() / cellSideSize);
            indexes[yIndex][xIndex] = new ParticleList();
            indexes[yIndex][xIndex].add(particle);
            particle.setIndex(xIndex, yIndex);
        }
    }

    //Are neighbours
    private boolean checkIfNeighbourIsInsideRadius(Particle particle1, Particle particle2) {
        Index index1 = particle1.getIndex(), index2 = particle2.getIndex();
        int numberOfCells = (int) (this.mapSideSize / this.cellSideSize);

        if (Math.abs(index1.getX() - index2.getX()) <= 1 && Math.abs(index1.getY() - index2.getY()) <= 1)
            return particle1.IsInsideActionRadiusOf(particle2, this.actionRadius);

        if (this.withBorders) {
            if (Math.abs(index1.getX() - index2.getX()) + 1 == numberOfCells && index1.getY() == index2.getY()) {
                if (particle1.getPos().getX() > particle2.getPos().getX())
                    return particle2.IsInsideActionRadiusOf(new Particle(particle1.getPos().getX() - this.mapSideSize, particle1.getPos().getY(), particle1.getRadius()), this.actionRadius);
                return particle1.IsInsideActionRadiusOf(new Particle(particle2.getPos().getX() - this.mapSideSize, particle2.getPos().getY(), particle2.getRadius()), this.actionRadius);
            }
            if (Math.abs(index1.getY() - index2.getY()) + 1 == numberOfCells && index1.getX() == index2.getX()) {
                if (particle1.getPos().getY() > particle2.getPos().getY())
                    return particle2.IsInsideActionRadiusOf(new Particle(particle1.getPos().getX(), particle1.getPos().getY() - this.mapSideSize, particle1.getRadius()), this.actionRadius);
                return particle1.IsInsideActionRadiusOf(new Particle(particle2.getPos().getX(), particle2.getPos().getY() - this.mapSideSize, particle2.getRadius()), this.actionRadius);
            }
        }

        return false;
    }

    public void printNeighbours() {
        for (int i = 0; i < particles.size(); i++) {
            for (int j = 0; j < particles.size(); j++)
                System.out.print(neighboursMatrix[i][j] + "  ");
            System.out.println();
        }
    }

    //Particle index method
    public void calculateAllNeighboursOld() {
        calculateIndexes();
        for (int particleIndex1 = 0; particleIndex1 < particles.size(); particleIndex1++) {
            for (int particleIndex2 = particleIndex1 + 1; particleIndex2 < particles.size(); particleIndex2++) {
                Particle particle1 = particles.get(particleIndex1), particle2 = particles.get(particleIndex2);
                neighboursMatrix[particleIndex1][particleIndex2] = checkIfNeighbourIsInsideRadius(particle1, particle2);
            }
        }
        System.out.println("OLD");
    }

    private int correctIndex(int index) {
        if(index < 0 && withBorders)
            return maxIndex - 1;
        if(index >= maxIndex && withBorders)
            return 0;
        return index;
    }

    //Particle index method
    public void calculateAllNeighbours() {
        calculateIndexes();

        for (int particleIndex1 = 0; particleIndex1 < particles.size(); particleIndex1++) {
            Particle particle1 = particles.get(particleIndex1);
            int xIndex = particle1.getIndex().getX();
            int yIndex = particle1.getIndex().getY();
            for (int x = xIndex - 1; x <= xIndex + 1; x++) {
                int xToCalculate = correctIndex(x);
                if(xToCalculate >= 0 && xToCalculate < maxIndex){
                    for (int y = yIndex - 1; y <= yIndex + 1; y++) {
                        int yToCalculate = correctIndex(y);
                        if(yToCalculate >= 0 && yToCalculate < maxIndex) {
                            if(indexes[yToCalculate][xToCalculate] != null) {
                                for (Particle particle2 : indexes[yToCalculate][xToCalculate].getParticles()) {
                                    neighboursMatrix[particleIndex1][particle2.getId()] = checkIfNeighbourIsInsideRadius(particle1, particle2);
                                }
                            }
                        }
                    }
                }
            }
        }
        printNeighbours();
    }

    //Particle index method
    public void calculateAllNeighboursv2() {
        calculateIndexes();

        for (int i = 0; i < maxIndex; i++) {
            for(int j = 0; j < maxIndex; j++) {
                int xIndex = i;
                int yIndex = j;
                if (indexes[j][i] != null) {
                    for (Particle particle1 : indexes[j][i].getParticles()) {
                        for (int x = xIndex ; x <= xIndex + 1; x++) {
                            int xToCalculate = correctIndex(x);
                            if (xToCalculate >= 0 && xToCalculate < maxIndex) {
                                for (int y = yIndex; y <= yIndex + 1; y++) {
                                    int yToCalculate = correctIndex(y);
                                    if (yToCalculate >= 0 && yToCalculate < maxIndex) {
                                        if (indexes[yToCalculate][xToCalculate] != null) {
                                            for (Particle particle2 : indexes[yToCalculate][xToCalculate].getParticles()) {
                                                neighboursMatrix[particle1.getId()][particle2.getId()] = checkIfNeighbourIsInsideRadius(particle1, particle2);
                                                neighboursMatrix[particle2.getId()][particle1.getId()] = neighboursMatrix[particle1.getId()][particle2.getId()];
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        printNeighbours();
    }

    //Brute force method
    public void calculateAllNeighboursBruteForce() {
        for (int i = 0; i < this.particles.size() - 1; i++) {
            for (int j = i + 1; j < this.particles.size(); j++) {
                Particle particle1 = particles.get(i), particle2 = particles.get(j);
                if (particle1.IsInsideActionRadiusOf(particle2, this.actionRadius)) {
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
