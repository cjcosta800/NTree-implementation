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
	// Inicializar array vazio e definir a capacidade
	public ArrayNTree(int capacity) { 
		this.nodeCap = capacity;
		this.children = (ArrayNTree<T>[]) Array.newInstance(ArrayNTree.class, capacity);
	}

	/**
	 * Create a tree with one element
	 * @param elem     The element value
	 * @param capacity The capacity of each node, ie, the maximum number of direct successors
	 */
	// Inicializar array vazio, definir a capacidade e 
	// definir o elemento
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
	// Inicializar o array vazio, definir a capacidade e
	// para cada elemento da lista fazer um insert
	public ArrayNTree(List<T> list, int capacity) {
		this.nodeCap = capacity;
		this.children = (ArrayNTree<T>[])Array.newInstance(ArrayNTree.class, capacity);
		
		// insertion of elements of list to the tree
		// TODO
		for (T elem : list)
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
		// *NOTE*	if this.data is null it means that either the first
		//			constructor was used and no element has been added
		//			or that every element has been removed, therefore
		//			being empty
		
		// if this.data is null, then the tree is empty
		return this.data == null;
	}

	/////////////////////////////////////

	/**
	 * Returns whether or not this instance is a leaf
	 * @return true if it is a leaf and false if not
	 * @best_case	O(1), if the tree is empty or not a 
	 * @worst_case	O(n), if the
	 */
	public boolean isLeaf() {
		// if tree is empty
		if (this.isEmpty())
			return false;
		
		// *NOTE*	the children are placed left to right, therefore there's no need
		//			to be checking every child because if the first is null then the
		//			following should be as well
		
		// if the tree's first child is null then it is a leaf
		if (this.children[0] == null)
			return true; 
		
		// if neither of the previous then it is NOT a leaf
		return false;
	}

	/////////////////////////////////////

	/**
	 * Finds the size of the tree
	 */
	public int size() {
		
		// *NOTE*	the following two cases are here for performance's sake,
		//			thus saving the effort of calling the recursive function
		
		// if the tree is empty ...
		int size = 1;
		
		if (this.isEmpty())
			// ... then it's size is 0
			return 0;
		
		// if the tree is itself a leaf ...
		if (this.isLeaf())
			// ... then it's size is 1
			return 1;
		
		// if not then return the recursive function to find the size
		for (ArrayNTree<T> child : this.children) {
			size += child.size();
		}
		
		return size;
		//return sizeAux();
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
		if (this.isEmpty())
			return 0;
		
		if (this.isLeaf())
			return 1;
		
		return heightAux();
	}

	private int heightAux() {
		// TODO Auto-generated method stub
		return 0;
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
		T max = this.data;
		
		// while there's a next element in the iterator ...
		while (it.hasNext())
			// ... change max to such element
			max = it.next();
		
		// returns the last element of the iterator
		return max;
	}

	/////////////////////////////////////

	public boolean contains(T elem) {
		Iterator<T> it = this.iterator();
		
		System.out.println(it.next());
		
		while (it.hasNext())
			if (it.next().compareTo(elem) == 0)
				return true;
		
		return false;  // TODO
	}

	/////////////////////////////////////

	public void insert(T elem) {

		// if the tree already contains elem
		if (this.contains(elem))
			return;
		
		// if the tree is empty change current tree's data to elem
		else if (this.isEmpty())
			this.data = elem;
		
		// if the element is smaller than the tree's current data
		else if (elem.compareTo(this.data) < 0)
			insertUpwards(elem);
		
		// if the current tree has space in its children and
		// they don't have any offspring that exceeds elem
		else if (this.hasSpace())
			insertAsChild(elem);
		
		// if the current tree has no space in its children then
		else insertInChild(elem);

	}


	private void insertInChild(T elem) {
		for (int i = 0; i < this.children.length; i++) {
			if (children[i].isLeaf()) {
				children[i].insertAsChild(elem);
				return;
			}
				
				
			if (children[i + 1].data.compareTo(elem) > 0) {
				children[i].insert(elem);
				return;
			}
		}
		
	}

	private boolean hasSpace() {
		// for each child in this.children ...
		for (int c = 1; c < this.children.length; c++)
			// ... if any child is null ...
			if (this.children[c] == null)
				// ... then there is space
				return true;
		
		// if no child was null then there is no space
		return false;
	}

	private void insertAsChild(T elem) {
		// for each child ...
		for (int i = 0; i < this.children.length; i++)
			// check if it is
			if (elem.compareTo(this.children[i].data) > 0) {
				this.shiftChildrenOnceFrom(i+1);
				this.children[i] = new ArrayNTree<T>(elem, this.nodeCap);
				break;
			}
		
	}

	/**
	 * Shifts children right from index
	 * @param i
	 */
	private void shiftChildrenOnceFrom(int index) {
		// if the child is not null ...
		if (this.children[index].data != null)
			// ... shift the next child
			shiftChildrenOnceFrom(index + 1);
		this.children[index + 1] = this.children[index];
		
		
	}

	private void insertUpwards(T elem) {
		insertUpwardsRec(this);
		this.data = elem;
	}

	
	private void insertUpwardsRec(ArrayNTree<T> tree) {
		// if the tree is not a leaf ...
		if (!tree.isLeaf())
			// ... then call the function again, this time inserting its child bellow it
			tree.children[0].insertUpwardsRec(tree.children[0]);
		tree.children[0].data = tree.data;
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
		// iterator for the current instance
		Iterator<T> itThis = this.iterator();
		// iterator for Object other (cast as an NTree)
		Iterator<T> itOther = ((NTree<T>) other).iterator();
		
		// while both iterators have a next element ...
		while (itThis.hasNext() && itOther.hasNext())
			// ... check if such elements are equal ...
			if (!(itThis.next().compareTo(itOther.next()) == 0))
				// ... and if they do then they're NOT EQUAL
				return false;
		
		// if either iterators still have a next element ...
		if (itThis.hasNext() || itOther.hasNext())
			// ... then they're NOT EQUAL
			return false;
		
		// if neither of the conditions have applied, then they're EQUAL
		return true;
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
		return new ArrayNTree<T>(this.toList(), this.nodeCap);
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
				// ... poll it from the deque
				return this.deque.poll().data;
				
			// throws NoSuchElementException if no next element exists
			throw new NoSuchElementException();
		}
		
	}

}
