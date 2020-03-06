package Main.Backend;

public class Particle {
    private Position pos;
    private Index index;
    private float radius;

    public Particle(Position pos){
        this.pos = pos;
    }

    public Particle(float x, float y){
        this.pos = new Position(x, y);
    }

    public boolean IsInsideActionRadiusOf(Particle otherParticle, float actionRadius){
        float squaredActionRadius = (float)Math.pow(actionRadius, 2);
        return squaredActionRadius >= otherParticle.pos.SquaredDistanceFrom(pos);
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
