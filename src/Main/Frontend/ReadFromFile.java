package Main.Frontend;

import Main.Backend.Particle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {

    private ArrayList<Particle> particles;

    private float mapSideSize;

    public ReadFromFile(String staticFile, String dynamicFile) throws IOException{
        readStaticFile(staticFile);

        readDynamicFile(dynamicFile);
    }

    private void readStaticFile(String file) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            if((line = br.readLine()) != null){
                this.particles = new ArrayList<>(Integer.valueOf(line));
            };

            if((line = br.readLine()) != null){
                this.mapSideSize = Integer.valueOf(line);
            };

            while ((line = br.readLine()) != null) {
                readParticleProperties(line);
            }
        }
    }

    private void readParticleProperties(String line){
        String[] arr = line.split(" ");

        Particle newParticle = new Particle();

        newParticle.setRadius(Float.valueOf(arr[0]));

        this.particles.add(newParticle);
    }

    private void readDynamicFile(String file) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //discard t0
            br.readLine();

            String line;
            for(int i=0; (line = br.readLine()) != null && i<this.particles.size(); i++){
                readParticlePositionAndVelocity(line, i);
            }
        }
    }

    private void readParticlePositionAndVelocity(String line, int i){
        String[] arr = line.split(" ");

        Particle particle = this.particles.get(i);

        particle.setPosition(Float.valueOf(arr[0]), Float.valueOf(arr[1]));
    }

    public float getMapSideSize() {
        return mapSideSize;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }
}