package main.java.datatype;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.lang.reflect.Array;

/**
 * Projeto AED do grupo  031
 *  
 * @author 49430 Ulisses Ferreira
 * @author 51038 Cesar Sousa
 * @author 51093 Goncalo Medeiros
 *  
 */
public class ArrayNTree<T extends Comparable<T>> implements NTree<T>, Iterable<T> {

	private T data;
	private ArrayNTree<T>[] children;	// children of the particular node
	private int nodeCap;				// capacity of node set
	
	/////////////////////////////////////

	/**
	 * Creates an empty tree 
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	@SuppressWarnings("unchecked")
	public ArrayNTree(int capacity) {
		this.nodeCap = capacity;
		this.children = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
	}

	/**
	 * Create a tree with one element
	 * @param elem     The element value
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	@SuppressWarnings("unchecked")
	public ArrayNTree(T elem, int capacity) {
		this.data = elem;
		this.nodeCap = capacity;
		this.children = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
	}

	/**
	 * Creates a tree with the elements inside the given list
	 * @param elem     The list with all the elements to insert
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	@SuppressWarnings("unchecked")
	public ArrayNTree(List<T> list, int capacity) {
		this.nodeCap = capacity;
		this.children = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
		
		// insertion of elements of list to the tree
		// TODO
		T[] listArray = (T[]) list.toArray();
		for (T elem : listArray)
			this.insert(elem);
			
	}

	/////////////////////////////////////

	/**
	 * Returns whether or not the tree is empty
	 * @return true if empty and false if not
	 * @best_case	O(1)
	 * @worst_case	O(1)
	 */
	public boolean isEmpty() {
		return this.data == null;
	}

	/////////////////////////////////////

	public boolean isLeaf() {
		// if tree is empty
		if (this.isEmpty())
			return false;
		
		// counter for the while loop that will search through the children
		int c = 0;
		
		// for each child ...
		while (this.children[c] != null)
			// ... check if empty ...
			if (this.children[c].isEmpty())
				// ... and if so then it is NOT a leave
				return false;
		
		// if neither of the previous then it is a leaf
		return true;
	}

	/////////////////////////////////////

	/**
	 * Finds the size of the tree
	 */
	public int size() {
		// the following two cases are here for performance's sake
		// thus saving the effort of calling the recursive function
		
		// if the tree is empty ...
		if (this.isEmpty())
			// ... then it's size is 0
			return 0;
		
		// if the tree is itself a leaf ...
		if (this.isLeaf())
			// ... then it's size is 1
			return 1;
		
		// if not then return the recursive function to find the size
		return sizeAux();
	}
	
	/**
	 * Recursive function to find the size of the tree
	 * @return
	 */
	private int sizeAux() {
		int size = 1;
		
		// counter for the while loop
		int c = 0;
		
		// as long as the next child isn't null ...
		while (this.children[c] != null)
			// ... count that child and add any further children, then add them
			size += this.children[c].sizeAux();
		return size;
	}

	/////////////////////////////////////

	/**
	 * 
	 */
	public int countLeaves() {
		// if this is a leaf then add up
		if (this.isLeaf())
			return 1;
		
		// initialises tree's leaves as 0
		int leaves = 0;
		
		// counter for the while loop
		int c = 0;
		
		// as long as the next child isn't null ...
		while (this.children[c] != null)
			// ... count that child's leaves and add them
			leaves += this.children[c].countLeaves();
		
		return leaves;
	}

	/////////////////////////////////////

	public int height() {
		return -1;  // TODO
	}

	/////////////////////////////////////

	public T min() {
		return this.data;
	}

	/////////////////////////////////////

	public T max() {
		// created an instance of the iterator
		Iterator<T> it = this.iterator();
		// variable to hold the value of the last element in the iterator
		T max;
		
		// while there's a next element in the iterator ...
		while (it.hasNext())
			// ... change max to such element
			max = it.next();
		
		// returns the last element of the iterator
		return max;
	}

	/////////////////////////////////////

	public boolean contains(T elem) {
		return false;  // TODO
	}

	/////////////////////////////////////

	public void insert(T elem) {
		// as long as elem doesn't already exist in the tree ...
		/*if (!this.contains(elem)) {
			
			// Rule 1: if elem is smaller than this.data (main root) ...
			if (elem.compareTo(this.root) < 0) 
				smallerThanRoot(elem);
			
			else {
				if ()
			}
		}*/
	}

	/////////////////////////////////////

	private void smallerThanRoot(T elem) {
		// TODO Auto-generated method stub
		
	}

	public void delete(T elem) {
		// TODO
	}

	/////////////////////////////////////

	/**
	 * Is this tree equal to another object?
	 * Two NTrees are equal iff they have the same values
	 */
	public boolean equals(Object other) {
		// iterator for the current instance
		Iterator<T> itThis = this.iterator();
		// iterator for Object other (cast as an NTree)
		Iterator<T> itOther = ((NTree<T>) other).iterator();
		
		// while both iterators have a next element ...
		while (itThis.hasNext() && itOther.hasNext())
			// ... check if such elements are equal ...
			if (!itThis.next().equals(itOther.next()))
				// ... and if they do then they're NOT EQUAL
				return false;
		
		// if either iterators still have a next element ...
		if (itThis.hasNext() || itOther.hasNext())
			// ... then they're NOT EQUAL
			return false;
		
		// if neither of the conditions have applied, then they're EQUAL
		return true;	// TODO
	}

	/////////////////////////////////////

	public List<T> toList() {
		// List where the elements will be stored in order
		List<T> l = new LinkedList<>();
		// Iterator for reading the tree
		Iterator<T> it = this.iterator();
		
		// while the iterator has a next element ...
		while (it.hasNext())
			// ... add its element to the list
			l.add(it.next());
		
		// return the list
		return l;
	}

	/////////////////////////////////////

	/**
	 * @returns a new tree with the same elements as the current instance
	 */
	public ArrayNTree<T> clone() {
		
		// If current instance is empty
		// use (int) constructor
		if (this.isEmpty())
			return new ArrayNTree<T>(this.nodeCap);
		
		// If current instance is a leaf
		// use (T, int) constructor
		if (this.isLeaf())
			return new ArrayNTree<T>(this.data, this.nodeCap);
		
		// TODO
		// In other cases create new
		// instance and add the elements recursively
		return null;
	}

	/////////////////////////////////////

	public String toString() {
		if (isEmpty())
			return "[]";

		if (isLeaf())
			return "["+data+"]";

		StringBuilder sb = new StringBuilder();
		sb.append("["+data+":");

		for(NTree<T> brt : children)
			if (brt!=null)
				sb.append(brt.toString());

		return sb.append("]").toString();				
	}

	// more detailed information about tree structure 
	public String info() {
		return this + ", size: " + size() + ", height: " + height() + ", nLeaves: " + countLeaves();
	}

	/////////////////////////////////////
	
	
	
	//// New ////
	
	public T getData () {
		return this.data;
	}
	
	//// End of New ////

	@Override
	public Iterator<T> iterator() {
		return new NTreeIterator(this);
	}
	
	
	/**
	 * Iterator for the ArrayNTree class
	 * 
	 *
	 */
	private class NTreeIterator implements Iterator<T> {

		private Queue<ArrayNTree<T>> deque = new ConcurrentLinkedQueue<>();
		
		private NTreeIterator(ArrayNTree<T> root) {
			queueFill(root);
		}
		
		/**
		 * Recursively adds the elements of the tree in an in-order fashion
		 * @param tree Tree from which we get the next element to add
		 */
		private void queueFill(ArrayNTree<T> tree) {
			// adds the element to the tree
			this.deque.add(tree);
			
			// as long as the next child isn't null ...
			for (int c = 0; c < children.length && tree.children[c] != null; c++)
				// ... count that child and add any further children, then add them
				queueFill(tree.children[c]);
		}
		
		public boolean hasNext() {
			// if the queue isn't empty, then it has a next element
			return !deque.isEmpty();
		}

		
		public T next() throws NoSuchElementException {
			// if there is a next element ...
			if (hasNext()) 
				// ... poll it from the 
				return this.deque.poll().data;
				
			throw new NoSuchElementException();
		}
		
	}

}
