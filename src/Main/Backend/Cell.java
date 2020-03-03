package Main.Backend;

public class Cell {
    private Position pos;
    private Index index;

    public Cell(Position pos){
        this.pos = pos;
    }

    public Cell(float x, float y){
        this.pos = new Position(x, y);
    }

    public boolean IsInsideActionRadiusOf(Cell otherCell, float actionRadius){
        float squaredActionRadius = (float)Math.pow(actionRadius, 2);
        return squaredActionRadius >= otherCell.pos.SquaredDistanceFrom(pos);
    }

    public Position GetPos(){
        return pos;
    }

    public void setIndex(int x, int y){
        this.index = new Index(x, y);
    }

    public Index getIndex(){
        return this.index;
    }
}
