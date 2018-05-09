package main.java.datatype;

import java.util.*;
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

	public boolean isEmpty() {
		return this.data == null;
	}

	/////////////////////////////////////

	public boolean isLeaf() {
		// if tree is empty
		if (this.isEmpty())
			return false;
		// for each child ...
		for (ArrayNTree<T> c : this.children)
			// ... check if empty
			if (!c.isEmpty())
				return false;
		
		// if neither of the previous
		// it is a leaf
		return true;
	}

	/////////////////////////////////////

	public int size() {
		return -1;  // TODO
	}

	/////////////////////////////////////

	public int countLeaves() {
		return -1;  // TODO

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
		return null;  // TODO
	}

	/////////////////////////////////////

	public boolean contains(T elem) {
		return false;  // TODO
	}

	/////////////////////////////////////

	public void insert(T elem) {
		//If element doesn't exist we can add it
		//Otherwise do nothing
		//
		if (!this.contains(elem)) {
			System.out.println("Inserting");
			
		}
	}

	/////////////////////////////////////

	public void delete(T elem) {
		// TODO
	}

	/////////////////////////////////////

	/**
	 * Is this tree equal to another object?
	 * Two NTrees are equal iff they have the same values
	 */
	public boolean equals(Object other) {
		return false;	// TODO
	}

	/////////////////////////////////////

	public List<T> toList() {
		return null;  // TODO
	}

	/////////////////////////////////////

	/**
	 * @returns a new tree with the same elements of this
	 */
	public ArrayNTree<T> clone() {
		
		// If current instance is empty
		// use (int) constructor
		if (this.isEmpty())
			return new ArrayNTree<T>(this.nodeCap);
		
		// If current instance only contains its own
		// element use (T, int) constructor
		if (this.data != null)
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

	@Override
	public Iterator<T> iterator() {
		return new NTreeIterator();  // TODO
	}
	
	
	private class NTreeIterator implements Iterator<T> {

		private T hello;
		
		private NTreeIterator() {

		}
		
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		
		public T next() {
			// TODO Auto-generated method stub
			return this.hello;
		}
		
		
	}

}
