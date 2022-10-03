import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static LineSegment[] lines;
    private static ArrayList<LineSegment> arraylines;
    private static Point[] sortedPoints;

    public FastCollinearPoints(Point[] points) {
        // check if points is null
        if (points == null) throw new IllegalArgumentException("null points");
        // check if each element in points is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("null Point in points");
        }
        arraylines = new ArrayList<LineSegment>();
        // copy points to sortedPoints and sort;
        sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        // check if there are repeated points
        for (int i = 1; i < points.length; i++) {
            if (sortedPoints[i - 1].compareTo(sortedPoints[i]) == 0) throw new IllegalArgumentException("There are repeated points");
        }

        int n = points.length;
        for (int i = 0; i < n - 3; i++) {
            // declare an Array slopes length = n - i - 1 (starting from n - 1, n - 2,..., 4, 3)
            double[] slopes = new double[n - i - 1];
            // calculate slope of points[i] and points[j]
            for (int j = i + 1; j < n; j++) {
                slopes[j - i - 1] = sortedPoints[i].slopeTo(sortedPoints[j]);
            }
            // sort array;
            Arrays.sort(slopes);

//            StdOut.println("When i = " + i + " Point is " + sortedPoints[i] + ", length of slopes = " + slopes.length);
//            StdOut.println("And the number of slopes are: ");
//            for (double s : slopes) {
//                StdOut.print(s + " ");
//            }
//            StdOut.println();

            ArrayList<LineSegment> arraylinesOfi = findConsecutiveSlope(i, slopes);
            if (!arraylinesOfi.isEmpty()) arraylines.addAll(arraylinesOfi);
        }
        // StdOut.print("size of arraylist = " + arraylines.size());
        lines = arraylines.toArray(new LineSegment[0]);
    }

    // find consecutive 3 values in slopes (length = n - i - 1);
    private ArrayList<LineSegment> findConsecutiveSlope(int i, double[] slopes) {
        // int i: we choose sortedPoints[i] as our origin
        if (slopes == null) throw new IllegalArgumentException("null slopes");

        ArrayList<LineSegment> arraylinesOfi = new ArrayList<LineSegment>();
        if (slopes.length <= 2) return arraylinesOfi;

        // declare two index for the start and end of repeated slopes: [start, end);
        int start = 0;
        int end = 1;
        while (start < slopes.length && end < slopes.length) {
            // case 1: consecutive repeat
            if (slopes[start] == slopes[end] && end < slopes.length - 1) {
                end++;

            }
            // case 2: consecutive slopes are different,
            else {
                if (slopes[start] == slopes[end]) end++;
                // check if there are 4 (or more) points one this line segment or end reach the last
                if (end - start >= 3) {
                    // find the last point one segement: starting with Point[i], slope with slopes[start]
                    boolean subsequence = false;
                    // check if this is a subsegment;
                    for (int k = 0; k < i; k++) {
                        if (sortedPoints[i].slopeTo(sortedPoints[k]) == slopes[start]) {
                            subsequence = true;
                            break;
                        }
                    }
                    // find the last element on this segment
                    if (!subsequence) {
                        for (int j = sortedPoints.length - 1; j > i; j--) {
                            if (sortedPoints[i].slopeTo(sortedPoints[j]) == slopes[start]) {
                                arraylinesOfi.add(new LineSegment(sortedPoints[i], sortedPoints[j]));
                                break;
                            }
                        }
                    }
                }

                // update start and end
                start = end;
                end = start + 1;
            }
        }
        return arraylinesOfi;
    }

    public int numberOfSegments() {
        return lines.length;
    }

    public LineSegment[] segments() {
        return lines.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        // StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
//        StdDraw.show();

        // print and draw the line segments
        // StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.001);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        // StdOut.println("number of segments is " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
