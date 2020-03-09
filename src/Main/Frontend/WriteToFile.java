package Main.Frontend;

import Main.Backend.Particle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {
    public WriteToFile(String file, ArrayList<List<Particle>> particlesNeighbors) throws IOException {
        try(PrintWriter writer = new PrintWriter(file)){
            StringBuilder line;
            for(int i = 0; i < particlesNeighbors.size() ; i++){
                line = new StringBuilder("[" + i);
                for(int j=0; j<particlesNeighbors.get(i).size(); j++){
                    line.append(" ").append(j);
                }
                line.append("]");
                writer.println(line);
            }
        }
    }
}
