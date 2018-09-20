import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[] lines;
	private int numLines;

	public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		if(points == null)
			throw new java.lang.IllegalArgumentException("Cant enter null to constructor!");

		for(int i = 0; i < points.length-1; i++)
		{
			for (int j = i + 1; j < points.length; j++)
			{
				if(points[i] == null  || points[j] == null)
					throw new java.lang.IllegalArgumentException("Cant have null points!"); 
				if( points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException("Cant have duplicate points!");
			}
		}

		lines = new LineSegment[4 * points.length];
		numLines = 0;

		for(int i = 0; i < points.length-3; i++)
		{
			for (int j = i + 1; j < points.length-2; j++)
			{
				for (int k = j + 1; k < points.length - 1; k++)
				{
					for (int l = k + 1; l < points.length; l++)
					{
						Point a = points[i];
						Point b = points[j];
						Point c = points[k];
						Point d = points[l];
						if( collinear(a, b, c, d) )
							lines[numLines++] = getSegment(a,b,c,d);
					}
				}
			}
		}


	}
	
	private boolean collinear(Point a, Point b, Point c, Point d)
	{
		if(a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(b) == a.slopeTo(d))
			return true;
		else
			return false;
	}

	private LineSegment getSegment(Point a, Point b, Point c, Point d)
	{
		Point[] tmp = new Point[4];
		tmp[0] = a;
		tmp[1] = b;
		tmp[2] = c;
		tmp[3] = d;
		Arrays.sort(tmp);
		return new LineSegment(tmp[0],tmp[3]);
	}


	public int numberOfSegments()        // the number of line segments
	{
		return numLines;
	}
	
	public LineSegment[] segments()                // the line segments
	{
		LineSegment[] newLines = new LineSegment[numLines];
		for(int i = 0; i < numLines; i++)
			newLines[i] = lines[i];
		lines = newLines;
		return lines;
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
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}