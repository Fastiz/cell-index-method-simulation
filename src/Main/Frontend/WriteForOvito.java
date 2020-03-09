package Main.Frontend;

import Main.Backend.Particle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WriteForOvito {
    public WriteForOvito(String file, List<Particle> particles, Particle selectedParticle, List<Particle> neighbors) throws IOException {
        try(PrintWriter writer = new PrintWriter(file)){
            writer.println(particles.size());
            writer.println("");

            int R, G, B;
            for(Particle part : particles){
                if(part.equals(selectedParticle)){
                    R=235;
                    G=229;
                    B=52;
                }else if(neighbors.contains(part)){
                    R=235;
                    G=76;
                    B=52;
                }else{
                    R=52;
                    G=79;
                    B=235;
                }
                writer.println(part.getPos().getX() + " " + part.getPos().getY() + " " + part.getRadius() + " " + R +" "+" "+G+" "+B);
            }
        }
    }
}
