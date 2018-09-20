import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class FastCollinearPoints {
	private LineSegment[] lines;
	private int numLines;

	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
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

		//Arrays.sort(points);

		for(int i = 0; i < points.length - 3; i++)
		{
			int idx = 0;
			Point[] tmp = new Point[points.length-1];
			Point curr = points[i];
			for(int j = 0; j < points.length; j++)
			{
				if(j != i)
					tmp[idx++] = points[j];
			}
			

			Arrays.sort(tmp);
			Arrays.sort(tmp, curr.slopeOrder());

			Point first = tmp[0];
			int numPoints = 2;
			for(int k = 1; k < tmp.length; k++)
			{
				//System.out.println("Here");
				if(curr.slopeTo(first) == curr.slopeTo(tmp[k]))
					numPoints++;
				else
				{
					if(numPoints > 3)
					{
						Point last = tmp[k-1];
						if(curr.compareTo(first) < 0)
							lines[numLines++] = new LineSegment(curr, last);
					}
					numPoints = 2;
					first = tmp[k]; 
				}
			}
			if(numPoints > 3)
			{
				Point last = tmp[tmp.length-1];
				if(curr.compareTo(first) < 0)
					lines[numLines++] = new LineSegment(curr, last);
			}
		}

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

	public static void main(String[] args) 
	{
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}