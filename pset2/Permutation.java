import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation 
{
	public static void main(String[] args)
	{
		if ( args.length != 1 )
			throw new IllegalArgumentException("Invalid Arguments specified");
		int k = 0;
		try {
            // Parse the string argument into an integer value.
            k = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException nfe) {
            // The first argument isn't a valid integer.  Print
            // an error message, then exit with an error code.
            System.out.println("The arguments must be integers.");
            return;
        }

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while ( !StdIn.isEmpty() ) 
        {
        	String value = StdIn.readString();
        	rq.enqueue(value);
        }

        Iterator<String> itr = rq.iterator();
        int numPrinted = 0;
        while ( itr.hasNext() && numPrinted < k )
        {
        	System.out.println(itr.next());
        	numPrinted++;
        }
	}
}