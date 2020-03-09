package Main;

import Main.Backend.GenerateParticleMap;
import Main.Backend.Particle;
import Main.Backend.CellMap;
import Main.Frontend.ReadFromFile;
import Main.Frontend.WriteForOvito;
import Main.Frontend.WriteToFile;
import Test.WriteToFileTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        float actionRadius = (float)150;

        GenerateParticleMap generateParticleMap = new GenerateParticleMap(1000, 200, 10, 15);

        ArrayList<Particle> particles = generateParticleMap.getParticles();

        CellMap cellMap = new CellMap(particles, actionRadius, generateParticleMap.getMapSideSize(), true, generateParticleMap.getMaxRadius());

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
            WriteToFile writeToFile = new WriteToFile("neighbors", neighbors);
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
            WriteToFile writeToFile = new WriteToFile("neighborsv2", neighbors);
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
            WriteToFile writeToFile = new WriteToFile("neighborsOLD", neighbors);
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
            WriteToFile writeToFile = new WriteToFile("neighbors", neighbors);
        }catch (IOException e){
            System.err.println(e);
        }

        try{
            Particle selectedParticle = particles.get(0);
            WriteForOvito writeForOvito= new WriteForOvito("ovito", particles, selectedParticle , cellMap.getNeighboursOf(selectedParticle));
        }catch (IOException e){
            System.err.println(e);
        }

    }
}
