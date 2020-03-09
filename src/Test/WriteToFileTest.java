package Test;

import Main.Backend.CellMap;
import Main.Backend.Particle;
import Main.Frontend.ReadFromFile;
import Main.Frontend.WriteToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteToFileTest {
    public void run(){
        float actionRadius = (float)Math.sqrt(2)*1000;

        ReadFromFile readFromFile;
        try{
            readFromFile = new ReadFromFile("./src/Files/staticFile",
                    "./src/Files/dynamicFile");
        }catch (IOException e){
            System.err.println(e);
            return;
        }

        ArrayList<Particle> particles = readFromFile.getParticles();

        CellMap cellMap = new CellMap(particles, actionRadius, readFromFile.getMapSideSize(), false, readFromFile.getMaxRadius());

        cellMap.calculateAllNeighbors();

        ArrayList<List<Particle>> particlesNeighbors = new ArrayList<>(particles.size());

        for(int i=0; i < particles.size(); i++){
            ArrayList<Particle> newNeighbors = new ArrayList<>(cellMap.getNeighboursOf(particles.get(i)));
            particlesNeighbors.add(newNeighbors);
        }

        try{
            WriteToFile writeToFile = new WriteToFile("neighbors", particlesNeighbors);
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
