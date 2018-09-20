import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] grid;
	private final WeightedQuickUnionUF wquf;
	private int numOpen;

	public Percolation(int n)
	{
		numOpen = 0;
		grid = new boolean[n+1][n+1];
		wquf = new WeightedQuickUnionUF((n*n)+2);

		for (int i = 1; i <= n; i++)
			wquf.union(0, i);
		for (int i = n*n; i > n*n-n; i--)
			wquf.union(n*n+1, i);
	}

	private boolean isValid(int row, int col)
	{
		if(row > 0 && row < this.grid.length && col > 0 && col < this.grid.length)
			return true;
		else
			return false;
	}

	private int getIdx(int row, int col)
	{
		return (row-1)*(this.grid.length-1) + col;
	}

	public void open(int row, int col)    // open site (row, col) if it is not open already
	{
		if (!isValid(row, col))
			throw new IllegalArgumentException("Invalid Row: " + row  + " and Column: " + col + " Given");
		if (this.grid[row][col] != true)
		{
			this.grid[row][col] = true;

			if(isValid(row+1, col) && isOpen(row+1, col))
				wquf.union(getIdx(row, col), getIdx(row+1, col));
			if(isValid(row-1, col) && isOpen(row-1, col))
				wquf.union(getIdx(row, col), getIdx(row-1, col));
			if(isValid(row, col+1) && isOpen(row, col+1))
				wquf.union(getIdx(row, col), getIdx(row, col+1));
			if(isValid(row, col-1) && isOpen(row, col-1))
				wquf.union(getIdx(row, col), getIdx(row, col-1));
			numOpen++;
		}
	}
   

	public boolean isOpen(int row, int col)  // is site (row, col) open?
	{
		if(!isValid(row, col))
			throw new IllegalArgumentException("Invalid Row: " + row  + " and Column: " + col + " Given");
		if(grid[row][col] == true)
			return true;
		else
			return false;
	}


	public boolean isFull(int row, int col)  // is site (row, col) full?
	{
		if(!isValid(row, col))
			throw new IllegalArgumentException("Invalid Row: " + row  + " and Column: " + col + " Given");
		if(wquf.connected(0,getIdx(row, col)) && isOpen(row, col))
			return true;
		else
			return false;
	}


	public int numberOfOpenSites()       // number of open sites
	{
		return this.numOpen;
	}

	public boolean percolates()              // does the system percolate?
	{
		int tmp = this.grid.length-1;
		return wquf.connected(0, tmp * tmp + 1);
	}

	public static void main(String[] args)   // test client (optional)
	{
		Percolation p = new Percolation(2);
		p.open(1,1);
		System.out.println("(1, 1) Open?: " + p.isOpen(1,1));
		p.open(2,2);
		System.out.println("(2, 2) Full?: " + p.isFull(2,2));
		System.out.println("Percolates?: " + p.percolates());
		//p.open(3,3);
		//System.out.println("(3, 3) Full?: " + p.isFull(2,2));
		//System.out.println("Percolates?: " + p.percolates());
		p.open(1,2);
		System.out.println("(1, 2) Full?: " + p.isFull(2,2));
		System.out.println("Percolates?: " + p.percolates());
		//p.open(3,2);
		//System.out.println("(3, 2) Full?: " + p.isFull(2,2));
		//System.out.println("Percolates?: " + p.percolates());

		System.out.println(p.numberOfOpenSites());
	}
}