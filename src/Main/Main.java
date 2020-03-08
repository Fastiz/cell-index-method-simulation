package Main;

import Main.Backend.Particle;
import Main.Backend.CellMap;
import Main.Frontend.ReadFromFile;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        float actionRadius = (float)Math.sqrt(2)*1000;

        ReadFromFile readFromFile;
        try{
            readFromFile = new ReadFromFile("/home/fastiz/Desktop/staticFile",
                    "/home/fastiz/Desktop/dynamicFile");
        }catch (IOException e){
            System.err.println(e);
            return;
        }

        ArrayList<Particle> particles = readFromFile.getParticles();

        CellMap cellMap = new CellMap(particles, actionRadius, readFromFile.getMapSizeSize(), false);

        cellMap.calculateAllNeighbours();

        for(int i=0; i < particles.size(); i++){
            System.out.println(cellMap.getNeighboursOf(particles.get(i)));
        }
    }
}
