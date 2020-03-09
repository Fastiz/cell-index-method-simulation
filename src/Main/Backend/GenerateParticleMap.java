package Main.Backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GenerateParticleMap {
    private ArrayList<Particle> particles;
    private float mapSideSize;
    private float maxRadius;
    private float minRadius;

    public GenerateParticleMap(float mapSideSize, int numberOfParticles, float minRadius, float maxRadius){
        this.mapSideSize = mapSideSize;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;

        this.particles = new ArrayList<>();

        for(int i=0; i<numberOfParticles; i++){
            while(!createParticleAndCheckForOverlap());
        }

    }

    private boolean createParticleAndCheckForOverlap(){
        float x = (float)Math.random()*mapSideSize, y = (float)Math.random()*mapSideSize, radius =
                (float)Math.random()*(maxRadius-minRadius) + minRadius;

        for(Particle particle : particles){
            float distance = (float)Math.sqrt(Math.pow(x-particle.getPos().getX(), 2) + Math.pow(y-particle.getPos().getY(), 2))-radius-particle.getRadius();

            if(distance<0)
                return false;
        }

        particles.add(new Particle(x, y, radius));

        return true;
    }

    public ArrayList<Particle> getParticles(){
        return this.particles;
    }

    public float getMapSideSize(){
        return mapSideSize;
    }

    public void writeToFile(String staticFileName, String dynamicFileName) throws IOException{
        writeStaticFile(staticFileName);
        writeDynamicFile(dynamicFileName);
    }

    private void writeStaticFile(String file) throws IOException {
        try(PrintWriter writer = new PrintWriter(file)){
            writer.println(this.particles.size());
            writer.println(this.mapSideSize);

            for(Particle particle : this.particles){
                writer.println(particle.getRadius());
            }
        }
    }

    private void writeDynamicFile(String file) throws IOException {
        try(PrintWriter writer = new PrintWriter(file)){
            writer.println("t0");

            for(Particle particle : this.particles){
                Position pos = particle.getPos();
                writer.println(pos.getX() + " " + pos.getY());
            }
        }
    }

}
