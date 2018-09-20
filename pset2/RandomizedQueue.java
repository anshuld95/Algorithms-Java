import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] items;
	private int N = 0;

	public RandomizedQueue()
	{
		items = (Item[]) new Object[1];
	}

	public boolean isEmpty()                 // is the randomized queue empty?
	{
		return N == 0;
	}

	private void resize(int capacity)
	{
		Item[] copy = (Item[]) new Object[capacity];
		for(int i = 0; i < N; i++)
			copy[i] = items[i];  
		items = copy;
	}

	public int size()                        // return the number of items on the randomized queue
	{
		return N;
	}
	public void enqueue(Item item)           // add the item
	{
		if ( item == null )
			throw new IllegalArgumentException("Cannot insert null item");
		if( N == items.length )
			resize(2 * items.length);
		items[N++] = item;
	}

	public Item dequeue()                    // remove and return a random item
	{
		if ( this.isEmpty() )
			throw new java.util.NoSuchElementException("RandomizedQueue is Empty!");
		int rand_idx = StdRandom.uniform(N);
		Item removed = items[rand_idx];
		items[rand_idx] = items[--N];
		items[N] = null;

		if ( N > 0 && N == items.length / 4 )
			resize(items.length / 2);
		return removed;
	}
	public Item sample()                     // return a random item (but do not remove it)
	{
		if ( this.isEmpty() )
			throw new java.util.NoSuchElementException("RandomizedQueue is Empty!");
		int rand_idx = StdRandom.uniform(N);
		return items[rand_idx];
	}

	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		return new RQIterator();
	}

	private class RQIterator implements Iterator<Item>
	{
		private Item[] items_copy;
		int current = 0;

		public RQIterator()
		{
			items_copy = (Item[]) new Object[N];
			for(int i = 0; i < N; i++)
				items_copy[i] = items[i];
		}

		public boolean hasNext()
		{
			return current < N;
		}

		public Item next()
		{
			if ( ! this.hasNext() )
				throw new java.util.NoSuchElementException("No more items in iterator!");
			int rand_idx = StdRandom.uniform(current, N);
			Item curr = items_copy[rand_idx];
			items_copy[rand_idx] = items_copy[current];
			items_copy[current++] = curr;
			return curr;
		}

		public void remove()
		{
			throw new UnsupportedOperationException("Remove operation is not supported.");
		}
	}

	private void printDeck()
	{
		Iterator<Item> itr = this.iterator();
		while( itr.hasNext() )
		{
			System.out.print(itr.next() + " ");
		}
		System.out.println();
	}

	public static void main(String[] args)   // unit testing (optional)
	{
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		rq.enqueue(3);
		rq.enqueue(4);
		rq.enqueue(2);
		rq.enqueue(6);
		rq.enqueue(8);
		System.out.print("Print Random Order: ");
		rq.printDeck();
		System.out.print("Print Random Order: ");
		rq.printDeck();
		System.out.println("Sampling: " + rq.sample());
		System.out.println("Deleting random: " + rq.dequeue());
		System.out.print("Deleted Print Random: ");
		rq.printDeck();
	}
}