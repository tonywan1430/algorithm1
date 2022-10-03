import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument is null");
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        if (points.isEmpty()) return;
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("argument is null");
        SET<Point2D> insides = new SET<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) insides.add(p);
        }
        return insides;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument is null");
        if (points == null) return null;
        Point2D nearPoint = null;
        for (Point2D q : points) {
            if (nearPoint == null) {
                nearPoint = q;
                continue;
            }
            if (q.distanceSquaredTo(p) < nearPoint.distanceSquaredTo(p)) {
                nearPoint = q;
            }
        }
        return nearPoint;
    }

    public static void main(String[] args) {
        // unit test
        PointSET testSET = new PointSET();
        testSET.insert(new Point2D(0, 0));
        testSET.insert(new Point2D(0.5, 0.5));
        testSET.insert(new Point2D(0, 1));
        StdOut.println(testSET.isEmpty());
        Point2D nearPoint = testSET.nearest(new Point2D(0, 0.7));
        StdOut.println(nearPoint);
        RectHV testRect = new RectHV(0, 0, 0.6, 0.6);
        Iterable<Point2D> range = testSET.range(testRect);
        for (Point2D p : range) {
            StdOut.println(p);
        }
        testSET.draw();

    }


}
