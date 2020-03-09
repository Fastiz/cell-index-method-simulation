package Main;

import Main.Backend.GenerateParticleMap;
import Main.Backend.Particle;
import Main.Backend.CellMap;
import Main.Frontend.ReadFromFile;
import Main.Frontend.WriteToFile;
import Test.WriteToFileTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        float actionRadius = (float)Math.sqrt(2)*1000;

        GenerateParticleMap generateParticleMap = new GenerateParticleMap(1000, 100, 6, 15);

        try{
            generateParticleMap.writeToFile("staticFile", "dynamicFile");
        }catch (IOException e){
            System.err.println(e);
        }

        ArrayList<Particle> particles = generateParticleMap.getParticles();

        CellMap cellMap = new CellMap(particles, actionRadius, generateParticleMap.getMapSideSize(), false);

        cellMap.calculateAllNeighbours();

        ArrayList<List<Particle>> neighbors = new ArrayList<>();
        for(int i=0; i < particles.size(); i++){
            neighbors.add(cellMap.getNeighboursOf(particles.get(i)));
        }

        try{
            WriteToFile writeToFile = new WriteToFile("neighbors", neighbors, particles);
        }catch (IOException e){
            System.err.println(e);
        }

    }
}
