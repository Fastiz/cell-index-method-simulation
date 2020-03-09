package Main.Frontend;

import Main.Backend.Particle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WriteForOvito {
    public WriteForOvito(String file, List<Particle> particles) throws IOException {
        try(PrintWriter writer = new PrintWriter(file)){
            writer.println(particles.size());
            writer.println("");

            for(Particle part : particles){
                writer.println(part.getPos().getX() + " " + part.getPos().getY() + " " + part.getRadius());
            }
        }
    }
}
