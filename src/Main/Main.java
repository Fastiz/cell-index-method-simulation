package Main;

import Main.Backend.Particle;
import Main.Backend.CellMap;
import Main.Frontend.ReadFromFile;
import Main.Frontend.WriteToFile;
import Test.WriteToFileTest;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        float actionRadius = (float)100.0;

        ReadFromFile readFromFile;
        try{
            readFromFile = new ReadFromFile("./src/Files/staticFile",
                    "./src/Files/dynamicFile");
        }catch (IOException e){
            System.err.println(e);
            return;
        }

        ArrayList<Particle> particles = readFromFile.getParticles();

        CellMap cellMap = new CellMap(particles, actionRadius, readFromFile.getMapSizeSize(), false);

        long startTime = System.nanoTime();
        cellMap.calculateAllNeighbours();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime/10000);


        startTime = System.nanoTime();
        cellMap.calculateAllNeighboursv2();
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println(totalTime/10000);


        startTime = System.nanoTime();
        cellMap.calculateAllNeighboursOld();
        endTime   = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println(totalTime/10000);

        for(int i=0; i < particles.size(); i++){
            //System.out.println(cellMap.getNeighboursOf(particles.get(i)));
        }
    }
}
