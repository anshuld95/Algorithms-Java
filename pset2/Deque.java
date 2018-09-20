import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node first, last;
	private int size; 
	
	private class Node {
		Item item;
		Node next;
		Node prev;

		public Node(Item d)
		{
			item = d;
			next = null;
			prev = null;
		}
	}

	public Deque()
	{
		first = null;
		last = null;
		size = 0;
	}

	public boolean isEmpty()
	{
		return size == 0; 
	}

	public int size()
	{
		return size;
	}

	public void addFirst(Item item)
	{
		if ( item == null )
			throw new IllegalArgumentException("Can't add null item");
		Node newFirst = new Node(item);
		if ( size == 0 )
		{
			first = newFirst;
			last = newFirst;
		}
		else
		{
			newFirst.next = first;
			first.prev = newFirst;
			first = newFirst;
		}
		size++;
	}

	public void addLast(Item item)
	{
		if ( item == null )
			throw new IllegalArgumentException("Can't add null item");
		Node newLast = new Node(item);
		if ( size == 0 )
		{
			first = newLast;
			last = newLast;
		}
		else
		{
			last.next = newLast;
			newLast.prev = last;
			last = newLast;
		}
		size++;
	}

	public Item removeFirst()
	{
		if ( this.isEmpty() )
			throw new java.util.NoSuchElementException("Nothing to remove. Deque is Empty!");
		Item item = first.item;
		if ( first == last )
			last = null;
		first = first.next;
		if ( first != null )
			first.prev = null;
		size--;
		return item;
	}

	public Item removeLast()
	{
		if ( this.isEmpty() )
			throw new java.util.NoSuchElementException("Nothing to remove. Deque is Empty!");
		Item item = last.item;
		if ( first == last )
			first = null;
		last = last.prev;
		if ( last != null )
			last.next = null;
		size--;
		return item;
	}

	public Iterator<Item> iterator()
	{
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item>
	{
		private Node current = first;

		public boolean hasNext()
		{
			return current != null;
		}

		public Item next()
		{
			if (! this.hasNext() )
				throw new java.util.NoSuchElementException("No more items in iterator!");

			Item item = current.item;
			current = current.next;
			return item;
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

	public static void main(String[] args)
	{
		Deque<Integer> d = new Deque<Integer>();
		d.addFirst(5);
		d.printDeck();
		d.addFirst(10);
		d.printDeck();
		d.addLast(5);
		d.printDeck();
		System.out.println("Removing: " + d.removeFirst());
		System.out.println("Removing: " + d.removeLast());
		d.printDeck();
	}

}