import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	int[] id;
	int[] sz;
	int[][] grid;

	public Percolation(int n)
	{
		grid[][] = new int[n+1][n+1];
		int arrsize = (n * n)+2;
		id = new int[arrsize];

		int j = n+1;
		while(j < arrsize-2-n)
			id[j] = j++;
		while(j < arrsize-1)
			id[j++] = arrsize-1;
		id[j] = j; 

		sz = new int[arrsize];
		for(int i = 0; i < arrsize; i++)
		{
			if(i == 0 || i == arrsize-1)
				sz[i] = n;
			else
				sz[i]=1;
		}
	}

	public boolean isValid(int row, int col)
	{
		if(row > 0 && row < grid.length && col > 0 && col < grid.length)
			return true;
		else
			return false;
	}

	public void open(int row, int col)    // open site (row, col) if it is not open already
	{
		if(!isValid)
		{
			throw IllegalArgumentException;
			return;
		}
		grid[row][col] = 1;

		if(isValid(row-1, col))
		{
			
		}

	}
   

	public boolean isOpen(int row, int col)  // is site (row, col) open?
	{
		if(!isValid)
		{
			throw IllegalArgumentException;
			return;
		}
	}


	public boolean isFull(int row, int col)  // is site (row, col) full?
	{
		if(!isValid)
		{
			throw IllegalArgumentException;
			return;
		}
	}


	public int numberOfOpenSites()       // number of open sites


	public boolean percolates()              // does the system percolate?

	public static void main(String[] args)   // test client (optional)
}