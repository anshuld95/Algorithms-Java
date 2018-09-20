import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;

public final class Board {
    private final int[][] board;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        if(blocks == null || blocks.length < 0 || blocks[0].length != blocks.length)
            throw new IllegalArgumentException("Cant init non-square or null board");
        board = deepCopy(blocks);
    }

    private int[][] deepCopy(int[][] blocks)
    {
        int nrow = blocks.length;
        int[][] cpboard = new int[nrow][nrow];
        for(int row = 0; row < nrow; row++)
        {
            for(int col = 0; col < nrow; col++)
            {
                if(blocks[row][col] > 0 && blocks[row][col] < (nrow*nrow))
                    cpboard[row][col] = blocks[row][col];
            }
        }
        return cpboard;
    }


    public int dimension()                 // board dimension n
    {
        return board.length;
    }

    public int hamming()                   // number of blocks out of place
    {
        int nrow = board.length;
        int hamming = 0;
        for(int row = 0; row < nrow; row++)
        {
            for(int col = 0; col < nrow; col++)
            {
                if(board[row][col] == 0)
                    continue;
                int goal = ((row*nrow) + col+1);
                if(goal == (nrow*nrow))
                    goal = 0;
                if(board[row][col] != goal )
                    hamming++;
            }
        }
        return hamming;
    }


    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int nrow = board.length;
        int manhattan = 0;
        for(int row = 0; row < nrow; row++)
        {
            for(int col = 0; col < nrow; col++)
            {
                int curr = board[row][col];
                if(curr!=0)
                {
                    int goalRow = (curr-1) / nrow;
                    int goalCol = curr-1-(goalRow*nrow);
                    if(!(goalRow == row && goalCol == col))
                    {
                        int steps = (Math.abs(goalRow-row) + Math.abs(goalCol-col));
                        manhattan += steps;
                        //System.out.println("Curr: " + curr + " Goal Row: "  + goalRow + " Goal Col " + goalCol + " adding: " + steps);
                    }
                }
            }
        }
        return manhattan;
    }


    public boolean isGoal()                // is this board the goal board?
    {
        return this.hamming()==0;
    }



    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] twinboard = deepCopy(board);

        int r1 = 0;
        int c1 = 0;
        if(twinboard[r1][c1]==0)
            r1++;

        int r2 = 0;
        int c2 = 1;
        if(twinboard[r2][c2]==0)
            r2++;

        swap(twinboard, r1, c1, r2, c2);
        return new Board(twinboard);
        
    }

    private Board twinRand()
    {
        int[][] twinboard = deepCopy(board);
        int nrow = twinboard.length;
        int r1 = StdRandom.uniform(0, nrow);
        int c1 = StdRandom.uniform(0, nrow);

        while(twinboard[r1][c1]==0)
        {
            r1 = StdRandom.uniform(0, nrow);
            c1 = StdRandom.uniform(0, nrow);
        }

        int r2 = StdRandom.uniform(0, nrow);
        int c2 = StdRandom.uniform(0, nrow);

        while(twinboard[r2][c2]==0 || (r2 == r1 && c1 == c2))
        {
            r2 = StdRandom.uniform(0, nrow);
            c2 = StdRandom.uniform(0, nrow);
        }

        swap(twinboard, r1, c1, r2, c2);

        return new Board(twinboard);

    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if(y == null || !(y instanceof Board))
            return false;
        if(this == y || board == y)
            return true;
        //System.out.println("Equality Check!");
        Board cy = (Board) y;        
        int nrow = board.length;

        if(cy.dimension() != nrow)
            return false;

        //System.out.println(nrow);
        for(int i = 0; i < nrow; i++)
        {
            for (int j = 0; j < nrow; j++)
            {
                if(board[i][j] != cy.getElem(i, j))
                {
                    //System.out.println("Equality Check Failed!");
                    return false;
                }
            }
        }
        return true;
    }

    private int getElem(int row, int col)
    {
        return board[row][col];
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        ArrayList<Board> nbs = new ArrayList<Board>();
        int brow = -1;
        int bcol = -1;
        int nrow = this.dimension();
        int row = 0;
        int col = 0;
        while(row < nrow && brow == -1)
        {
            while(col < nrow)
            {
                if(board[row][col] == 0)
                {
                    brow = row;
                    bcol = col;
                }
                col++;
            }
            row++;
            col = 0;
        }

        if(brow == -1 || bcol == -1)
            return null;

        int[][] copy = deepCopy(board);
        for(int i = 0; i < 4; i++)
        {
            if(i == 2)
            {
                if((brow - 1) >= 0)
                {
                    swap(copy, brow, bcol, brow-1, bcol);
                    nbs.add(new Board(copy));
                    swap(copy, brow, bcol, brow-1, bcol);
                }
            }
            else if(i == 1)
            {
                if((bcol + 1) < nrow)
                {
                    swap(copy, brow, bcol+1, brow, bcol);
                    nbs.add(new Board(copy));
                    swap(copy, brow, bcol+1, brow, bcol);
                }

            }
            else if(i == 0)
            {
                if((brow + 1) < nrow)
                {
                    swap(copy, brow, bcol, brow+1, bcol);
                    nbs.add(new Board(copy));
                    swap(copy, brow, bcol, brow+1, bcol);
                }

            }
            else
            {
                if((bcol - 1) >= 0)
                {
                    swap(copy, brow, bcol-1, brow, bcol);
                    nbs.add(new Board(copy));
                    swap(copy, brow, bcol-1, brow, bcol);
                }
            }
        }
        return nbs;
    }

    private void swap(int[][] b, int row1, int col1, int row2, int col2)
    {
        int tmp = b[row1][col1];
        b[row1][col1] = b[row2][col2];
        b[row2][col2] = tmp;
    }


    public String toString()               // string representation of this board (in the output format specified below)
    {
        int nrow = this.dimension();
        StringBuilder sb = new StringBuilder();
        sb.append(nrow+"\n");
        for(int i = 0; i < nrow; i++)
        {
            for (int j = 0; j < nrow; j++)
            {
                sb.append(" " + board[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] myb = new int[3][3];
        myb[0][0] = 1;
        myb[0][1] = 2;
        myb[0][2] = 3;
        myb[1][0] = 0;
        myb[1][1] = 7;
        myb[1][2] = 6;
        myb[2][0] = 5;
        myb[2][1] = 4;
        myb[2][2] = 8;
        Board my_board = new Board(myb);
        
        System.out.println(my_board);
        System.out.println("Hamming: " + my_board.hamming());
        System.out.println("Manhattan: " + my_board.manhattan());

        System.out.println("Twin: \n" + my_board.twin().toString());
        System.out.println("Neighbors:");

        for(Board b : my_board.neighbors())
            System.out.println(b);
    }
}