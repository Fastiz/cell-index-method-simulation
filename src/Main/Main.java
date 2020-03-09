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
        float actionRadius = (float)1.5;

        ReadFromFile readFromFile;
        try{
            readFromFile = new ReadFromFile("./src/Files/staticFile",
                    "./src/Files/dynamicFile");
        }catch (IOException e){
            System.err.println(e);
            return;
        }

        ArrayList<Particle> particles = readFromFile.getParticles();

        CellMap cellMap = new CellMap(particles, actionRadius, readFromFile.getMapSideSize(), true, readFromFile.getMaxRadius());

        long startTime = System.nanoTime();
        cellMap.calculateAllNeighbours();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime/10000);

        ArrayList<List<Particle>> neighbors = new ArrayList<>();
        for(int i=0; i < particles.size(); i++){
            neighbors.add(cellMap.getNeighboursOf(particles.get(i)));
        }

        try{
            WriteToFile writeToFile = new WriteToFile("neighbors", neighbors, particles);
        }catch (IOException e){
            System.err.println(e);
        }

        startTime = System.nanoTime();
        cellMap.calculateAllNeighboursv2();
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println(totalTime/10000);


        neighbors = new ArrayList<>();
        for(int i=0; i < particles.size(); i++){
            neighbors.add(cellMap.getNeighboursOf(particles.get(i)));
        }

        try{
            WriteToFile writeToFile = new WriteToFile("neighborsv2", neighbors, particles);
        }catch (IOException e){
            System.err.println(e);
        }


        startTime = System.nanoTime();
        cellMap.calculateAllNeighboursOld();
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println(totalTime/10000);

        neighbors = new ArrayList<>();
        for(int i=0; i < particles.size(); i++){
            neighbors.add(cellMap.getNeighboursOf(particles.get(i)));
        }

        try{
            WriteToFile writeToFile = new WriteToFile("neighborsOLD", neighbors, particles);
        }catch (IOException e){
            System.err.println(e);
        }

        startTime = System.nanoTime();
        cellMap.calculateAllNeighboursBruteForce();
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println(totalTime/10000);

        neighbors = new ArrayList<>();
        for(int i=0; i < particles.size(); i++){
            neighbors.add(cellMap.getNeighboursOf(particles.get(i)));
        }

        try{
            WriteToFile writeToFile = new WriteToFile("neighborsBRUTE", neighbors, particles);
        }catch (IOException e){
            System.err.println(e);
        }

    }
}
