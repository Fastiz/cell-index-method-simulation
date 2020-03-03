package Test;

import Main.Backend.Position;

public class PositionTest {
    public void run(){
        Position pos1 = new Position(10, 10);

        Position pos2 = new Position(20, 20);

        assert pos1.SquaredDistanceFrom(pos2) == 200;

        assert Position.SquaredDistanceBetween(pos1, pos2) == 200;
    }

}
