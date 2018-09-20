import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
	//private MinPQ<SearchNode> heap;
	private ArrayList<Board> shortest;

	private static class SearchNode implements Comparable<SearchNode> {
		private final Board curr;
		private final int numMoves;
		private final SearchNode predecessor;
		private final int manhattan;

		public SearchNode(Board c, int moves, SearchNode p) {
			curr  = c;
			numMoves = moves;
			predecessor = p;
			manhattan = c.manhattan();

		}

		public int compareTo(SearchNode that)
		{
			return ((this.manhattan+this.numMoves) - (that.manhattan+that.numMoves));
		}
	}

	private Solver() {throw new IllegalArgumentException("Can't have null argument!"); }

	private Board goalBoard(int nrow)
	{
		int[][] goal = new int[nrow][nrow];
    	int counter = 1;
    	for(int i = 0; i < nrow; i++ )
    	{
    		for(int j = 0; j < nrow; j++)
    			goal[i][j] = counter++;
    	}
    	goal[nrow-1][nrow-1] = 0;
    	return new Board(goal);
	}

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
    	if(initial == null)
    		throw new IllegalArgumentException("Can't have null argument!");

    	SearchNode orig = new SearchNode(initial, 0, null);
    	SearchNode twin = new SearchNode(initial.twin(), 0, null);

    	//System.out.println("Original: \n" + orig.curr.toString());


    	//System.out.println("Twin: \n" + twin.curr.toString());

    	MinPQ<SearchNode> heap_orig = new MinPQ<SearchNode>();
    	MinPQ<SearchNode> heap_twin = new MinPQ<SearchNode>();

    	heap_orig.insert(orig);
    	heap_twin.insert(twin);

    	//System.out.println("Goal: \n" + goal.toString());

    	while(!heap_orig.isEmpty() && !heap_twin.isEmpty())
    	{
    		orig = heap_orig.delMin();
    		twin = heap_twin.delMin();

    		//System.out.println("Deleting mins");

    		if(orig.curr.isGoal() || twin.curr.isGoal())
    		{
    			//System.out.println("FINISHED!");
    			break;
    		}

    		//System.out.println("orig");
    		//System.out.println(orig.curr);

    		for(Board nb : orig.curr.neighbors())
    		{
    			if(orig.predecessor == null || !nb.equals(orig.predecessor.curr))
    			{
    				//System.out.println("neighbors not pred manhattan: " + nb.manhattan());
    				//System.out.println(nb);

    				heap_orig.insert(new SearchNode(nb, orig.numMoves+1, orig) );
    			}
    		}

    		for(Board nb : twin.curr.neighbors())
    		{
    			if(twin.predecessor == null ||  !nb.equals(twin.predecessor.curr))
    				heap_twin.insert(new SearchNode(nb, twin.numMoves+1, twin) );
    		}
    	}

    	if(twin.curr.isGoal())
    		shortest = null;
    	else
    	{
    		shortest = new ArrayList<Board>();
    		SearchNode c = orig;
    		while ( c != null )
    		{
    			shortest.add(0, c.curr);
    			c = c.predecessor;
    		}
    	}
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
    	if(shortest == null)
    		return false;
    	return true;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
    	if(!isSolvable())
    		return -1;
    	return shortest.size()-1;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
    	if(!isSolvable())
    		return null;
    	return shortest;
    }

    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}
}