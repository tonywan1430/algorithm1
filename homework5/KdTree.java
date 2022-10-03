import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;

public class KdTree {
    private Node root = null;
    private int size = 0;
    // private variable for nearest;
    private Point2D nearPoint;
    private double nearDist;


    private class Node {
        // current point
        private Point2D p;
        private RectHV rect;
        private Node right;
        private Node left;


        // isVertical: separate by a vertical line
        private boolean isVertical;

        public Node(Point2D p, RectHV rect, boolean isVertical) {
            this.p = p;
            this.rect = rect;
            this.isVertical = isVertical;
        }

        public void draw() {
            StdDraw.setPenRadius(0.01);

            if (isVertical) {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
            } else {
                StdDraw.setPenColor(Color.blue);
                StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
            }
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(Color.black);
            p.draw();
            if (left != null) {
                left.draw();
            }
            if (right != null) {
                right.draw();
            }
        }
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        root = insert(root, null, p);
    }

    private Node insert(Node x, Node parent, Point2D p) {
        if (x == null) {
            if (parent == null) {
                size++;
                return new Node(p, new RectHV(0, 0, 1.0, 1.0), true);
            } else {
                // vertical
                double xmin = parent.rect.xmin();
                double xmax = parent.rect.xmax();
                double ymin = parent.rect.ymin();
                double ymax = parent.rect.ymax();
                if (parent.isVertical) {

                    if (p.x() < parent.p.x()) {
                        xmax = parent.p.x();
                    } else {
                        xmin = parent.p.x();
                    }

                }
                // horizon
                else {
                    if (p.y() < parent.p.y()) {
                        ymax = parent.p.y();
                    } else {
                        ymin = parent.p.y();
                    }

                }
                RectHV resRect = new RectHV(xmin, ymin, xmax, ymax);
                Node resNode = new Node(p, resRect, !parent.isVertical);
                size++;
                return resNode;
            }
        } else {
            if (x.p.equals(p)) return x;
            if (x.isVertical) {
                if (p.x() < x.p.x()) {
                    x.left = insert(x.left, x, p);
                    x.left.isVertical = false;

                } else {
                    x.right = insert(x.right, x, p);
                    x.right.isVertical = false;
                }
            } else {
                if (p.y() < x.p.y()) {
                    x.left = insert(x.left, x, p);
                    x.left.isVertical = true;

                } else {
                    x.right = insert(x.right, x, p);
                    x.right.isVertical = true;
                }
            }

        }
        return x;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        return contains(root, p) != null;
    }

    private Node contains(Node x, Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        if (x == null) return null;
        if (x.p.equals(p)) return x;
        if (x.isVertical) {
            if (p.x() < x.p.x()) return contains(x.left, p);
            else return contains(x.right, p);
        } else {
            if (p.y() < x.p.y()) return contains(x.left, p);
            else return contains(x.right, p);
        }
    }

    public void draw() {
        if (root != null) {
            root.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> insides = new ArrayList<Point2D>();
        range(root, rect, insides);
        return insides;
    }

    private void range(Node x, RectHV rect, ArrayList<Point2D> insides) {
        if (x == null) return;
        if (x.rect.intersects(rect)) {
            if (rect.contains(x.p)) {
                insides.add(x.p);
            }
            range(x.left, rect, insides);
            range(x.right, rect, insides);
        } else return;

    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        if (root == null) return null;
        nearPoint = null;
        nearDist = Double.POSITIVE_INFINITY;
        nearest(root, p);
        return nearPoint;
    }

    private void nearest(Node x, Point2D p) {
        if (x == null) return;
        if (x.rect.distanceSquaredTo(p) < nearDist) {
            if (x.p.distanceSquaredTo(p) < nearDist) {
                nearPoint = x.p;
                nearDist = nearPoint.distanceSquaredTo(p);
            }
            if (x.isVertical) {
                if (p.x() < x.p.x()) {
                    nearest(x.left, p);
                    nearest(x.right, p);
                } else {
                    nearest(x.right, p);
                    nearest(x.left, p);
                }
            } else {
                if (p.y() < x.p.y()) {
                    nearest(x.left, p);
                    nearest(x.right, p);
                } else {
                    nearest(x.right, p);
                    nearest(x.left, p);
                }
            }
        }
    }


    public static void main(String[] args) {
        // test insert and size
        KdTree mytree = new KdTree();
        // test isEmpty
        StdOut.println(mytree.isEmpty());
        mytree.insert(new Point2D(0.7, 0.2));
        // StdOut.println("rectHV is " + mytree.root.rect);
        mytree.insert(new Point2D(0.5, 0.4));
        mytree.insert(new Point2D(0.2, 0.3));
        mytree.insert(new Point2D(0.4, 0.7));
        mytree.insert(new Point2D(0.9, 0.6));
        StdOut.println("length of kdtree is " + mytree.size());

        // test isEmpty
//        StdOut.println(mytree.isEmpty());

        // test draw
        mytree.draw();
        // test contains
//        Point2D testPoint = new Point2D(0.1, 0.1);
//        StdOut.println("Point " + mytree.nearest(testPoint) + " is nearest to " + testPoint);
//        StdOut.println("point" + testPoint + " in mytree " + mytree.contains(testPoint));
//        testPoint = new Point2D(0.1, 0.1);
//        StdOut.println("point" + testPoint + " in mytree " + mytree.contains(testPoint));


        Point2D testPoint2 = new Point2D(0.036, 0.486);
//        StdOut.println("point" + testPoint2 + " in mytree " + mytree.contains(testPoint2));

        // test nearest;
        // case 1


        // case 2
        // StdOut.println(mytree.nearDist);
        StdOut.println("Point " + mytree.nearest(testPoint2) + " is nearest to " + testPoint2);
        StdOut.println(mytree.nearDist);
        // test range
//        ArrayList<Point2D> insides = new ArrayList<Point2D>();
//        insides = (ArrayList<Point2D>) mytree.range(new RectHV(0.0, 0.0, 1.0, 1.0));
//        for (Point2D p : insides) {
//            StdOut.println(p);
//
//        }
    }
}
