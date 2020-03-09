package Main;

import Main.Backend.CellMap;
import Main.Backend.GenerateParticleMap;
import Main.Backend.Particle;
import Main.Frontend.ReadFromFile;
import Main.Frontend.WriteForOvito;
import Main.Frontend.WriteToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CellIndexMethodProject {

    //Load from file
    public CellIndexMethodProject(String staticFile, String dynamicFile, int numberOfCells, float searchRadius, boolean isPeriodic){

        ReadFromFile readFromFile;
        try {
            readFromFile = new ReadFromFile(staticFile, dynamicFile);
        }catch (IOException e){
            System.err.println(e);
            return;
        }

        execute(readFromFile.getParticles(), searchRadius, readFromFile.getMapSideSize(), isPeriodic, readFromFile.getMaxRadius(), numberOfCells);

    }

    //Auto generate
    public CellIndexMethodProject(int numberOfParticles, float mapSideSize, float minRadius, float maxRadius, float searchRadius, boolean isPeriodic, int numberOfCells){
        GenerateParticleMap generateParticleMap = new GenerateParticleMap(mapSideSize, numberOfParticles, minRadius, maxRadius);

        execute(generateParticleMap.getParticles(), searchRadius, mapSideSize, isPeriodic, maxRadius, numberOfCells);
    }

    private void execute(ArrayList<Particle> particles, float searchRadius, float mapSideSize, boolean isPeriodic, float maxRadius, int numberOfCells){
        long startTime = System.nanoTime();

        CellMap cellMap = new CellMap(particles, searchRadius, mapSideSize, isPeriodic, maxRadius);

        cellMap.calculateAllNeighbors();

        long endTime   = System.nanoTime();

        System.out.println("Execution time: " + (endTime-startTime)/1000000 + " ms");

        ArrayList<List<Particle>> neighbors = new ArrayList<>();
        for(int i=0; i < particles.size(); i++){
            neighbors.add(cellMap.getNeighboursOf(particles.get(i)));
        }

        try{
            WriteToFile writeToFile = new WriteToFile("output", neighbors);
        }catch (IOException e){
            System.err.println(e);
        }

        System.out.println("Neighbors output was saved in 'output' file.");

        try{
            Particle selectedParticle = particles.get(0);

            WriteForOvito writeForOvito= new WriteForOvito("ovito", particles, selectedParticle , cellMap.getNeighboursOf(selectedParticle));
        }catch (IOException e){
            System.err.println(e);
        }

        System.out.println("Particle properties were saved in 'ovito' file.");
    }
}
