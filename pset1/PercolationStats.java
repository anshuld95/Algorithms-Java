import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
	private final double[] samples;
	private static final double CONFIDENCE_CONST = 1.96;
	private final double sample_mean;
	private final double sample_stddev;


	public PercolationStats(int n, int trials)
	{
		if(n <= 0 || trials <= 0)
			throw new IllegalArgumentException("Invalid grid size or trials given");
		samples = new double[trials];
		double totalSites = n*n*1.0;

		for(int i = 0; i < trials; i++)
		{
			Percolation p = new Percolation(n);
			while(!p.percolates())
			{
				int rand_row = StdRandom.uniform(n)+1;
				int rand_col = StdRandom.uniform(n)+1;
				//System.out.println("Row: " + rand_row);
				//System.out.println("col: " + rand_col);
				p.open(rand_row, rand_col);
			}
			//System.out.println(p.numberOfOpenSites());
			samples[i] = p.numberOfOpenSites() / (totalSites);
		}
		sample_mean = StdStats.mean(samples);
		sample_stddev = StdStats.stddev(samples);
	}

	public double mean()                          // sample mean of percolation threshold
	{
		return sample_mean;
	}


	public double stddev()                        // sample standard deviation of percolation threshold
	{
		return sample_stddev;
	}
	
	public double confidenceLo()                  // low  endpoint of 95% confidence interval
	{
		return sample_mean - ((CONFIDENCE_CONST*sample_stddev) / Math.sqrt(samples.length));
	}

	public double confidenceHi()                  // high endpoint of 95% confidence interval
	{
		return sample_mean + ((CONFIDENCE_CONST*sample_stddev) / Math.sqrt(samples.length));
	}

	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out.println("Invalid number of args");
			return;
		}

		int n = 0;
		int trials = 0;
		try {
            // Parse the string argument into an integer value.
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException nfe) {
            // The first argument isn't a valid integer.  Print
            // an error message, then exit with an error code.
            System.out.println("The arguments must be integers.");
            return;
        }

        PercolationStats ps = new PercolationStats(n, trials);
        System.out.printf("%-15s %-2s %.10f%n", "mean", "=", ps.mean());
        System.out.printf("%-15s %-2s %.10f%n", "stddev", "=", ps.stddev());
        System.out.printf("%-15s %-2s [%.10f, %.10f]%n", "95% Confidence Interval", "=", ps.confidenceLo(), ps.confidenceHi());
        //System.out.printf("this: %f", 1.0/3);

	}

}