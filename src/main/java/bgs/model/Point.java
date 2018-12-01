package bgs.model;
import javax.persistence.*;

@Entity
public class Point {
    @Id
    @SequenceGenerator(name = "idgen", sequenceName = "point_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "idgen")
    @Column(nullable = false)
    int id;
    @Column(nullable = false)
    double x;
    @Column(nullable = false)
    double y;
    @Column(nullable = false)
    int r;
    @Column(nullable = false)
    boolean inside;
    @Column(nullable = false)
    String session;

    public Point() {}

    public Point(double x, double y, int r, boolean inside, String session) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inside = inside;
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public boolean isInside() {
        return inside;
    }
}
