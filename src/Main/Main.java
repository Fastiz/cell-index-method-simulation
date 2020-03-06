package Main;

import Main.Backend.Particle;
import Main.Backend.CellMap;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        float actionRadius = 100, mapSideSize = 1000;
        int N = 1000;

        ArrayList<Particle> particles = new ArrayList<>(N);

        for(int i=0; i<N ; i++){
            particles.add(new Particle((float)Math.random()*mapSideSize, (float)Math.random()*mapSideSize));
        }

        CellMap cellMap = new CellMap(particles, actionRadius, mapSideSize);

        cellMap.calculateAllNeighbours();

        List<Particle> neighbours = cellMap.getNeighboursOf(particles.get((N/2)));

        System.out.println(neighbours);
    }
}
