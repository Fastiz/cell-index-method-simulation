package Test;

import Main.Frontend.ReadFromFile;

import java.io.IOException;

public class ReadTest {
    public void run(){

        ReadFromFile readFromFile;
        try{
            readFromFile = new ReadFromFile("/home/fastiz/Desktop/staticFile",
                    "/home/fastiz/Desktop/dynamicFile");
        }catch (IOException e){
            System.err.println(e);
            return;
        }


        assert readFromFile.getMapSideSize() == 1000;

        //System.out.println(readFromFile.getParticles());

        assert readFromFile.getParticles().get(0).getRadius() == 5;

        assert readFromFile.getParticles().get(0).getPos().getX() == 500;

        assert readFromFile.getParticles().get(0).getPos().getX() == 510;

        assert readFromFile.getParticles().size() == 10;
    }
}
