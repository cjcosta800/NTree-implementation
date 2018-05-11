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
		
		// for each child ...
		for (ArrayNTree<T> c : this.children)
			// ... check if empty
			if (!c.isEmpty())
				return false;
		
		// if neither of the previous then it is a leaf
		return true;
	}

	/////////////////////////////////////

	public int size() {
		if (this.isEmpty())
			return 0;
		return sizeAux();
		
		/*
		int output = 1;
		for (ArrayNTree<T> child : children){
			output += child.size();
		};
		return output;  // TODO
		*/
	}
	
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
		return null;  // TODO
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
		
		Iterator<T> itThis = this.iterator();
		Iterator<T> itOther = ((NTree<T>) other).iterator();
		
		while (itThis.hasNext() && itOther.hasNext())
			if (!itThis.next().equals(itOther.next()))
				return false;
		
		return true;	// TODO
	}

	/////////////////////////////////////

	public List<T> toList() {
		//Depois de conseguirmos criar uma arvore,
		//testar
		//
		List<T> newList = null;
		newList.add(data);
		for (ArrayNTree<T> child : children){
			newList.addAll(child.toList());
		};
		return newList;  // TODO
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
		return new NTreeIterator(data, children);  // TODO
	}
	
	
	private class NTreeIterator implements Iterator<T> {

		private T dataLocal;
		private ArrayNTree<T>[] children;
		private int index;
		
		private NTreeIterator(T itData, ArrayNTree<T>[] itChildren) {
			dataLocal = itData;
			children = itChildren;
			index = -1;
		}
		
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return index == children.length;
		}

		
		public T next() {
			if (index == -1) {
				index += 1;
				return dataLocal;
			} else {
				index += 1;
				return children[index--].data;
			}
				
			
		}
		
		
	}

}
