import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import java.util.Iterator;


public class PointSET {
	private SET<Point2D> pset;

	public PointSET()                               // construct an empty set of points
	{
		pset = new SET<Point2D>();
	}

	public boolean isEmpty()                      // is the set empty? 
	{
		return pset.isEmpty();
	}

	public int size()                         // number of points in the set
	{
		return pset.size();
	}

	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if(p == null)
			throw new IllegalArgumentException("Cannot insert null argument");
		if(!pset.contains(p))
			pset.add(p);
	}

	public boolean contains(Point2D p)            // does the set contain point p?
	{
		if(p == null)
			throw new IllegalArgumentException("Cannot insert null argument");
		return pset.contains(p);
	}

	public void draw()                         // draw all points to standard draw
	{
		Iterator<Point2D> itr = pset.iterator();
		while(itr.hasNext())
			itr.next().draw();
	}

	public Iterable<Point2D> range(RectHV rect) // all points that are inside the rectangle (or on the boundary)
	{
		if(rect == null)
			throw new IllegalArgumentException("Cannot insert null argument");
		ArrayList<Point2D> arr = new ArrayList<>();
		Iterator<Point2D> itr = pset.iterator();
		while(itr.hasNext())
		{
			Point2D curr = itr.next();
			if(rect.contains(curr))
				arr.add(curr);
		}
		return arr;
	}


	public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty
	{
		if(p == null)
			throw new IllegalArgumentException("Cannot insert null argument");
		if(this.isEmpty())
			return null;
		if(pset.contains(p))
			return p;
		Point2D floor = pset.floor(p);
		Point2D ceiling = pset.ceiling(p);
		if(p.distanceTo(floor) < p.distanceTo(ceiling))
			return floor;
		else
			return ceiling;
	}

	public static void main(String[] args)                  // unit testing of the methods (optional)
	{
		System.out.println("Compiled");

	}

}