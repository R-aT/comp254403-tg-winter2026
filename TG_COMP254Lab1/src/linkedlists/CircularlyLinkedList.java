package linkedlists;

public class CircularlyLinkedList<E> implements Cloneable {
	//---------------- nested Node class ----------------

	private static class Node<E> {

		private final E element;

		private Node<E> next;

		public Node(E e, Node<E> n) {
			element = e;
			next = n;
		}

		// Accessor methods
		public E getElement() {
			return element;
		}

		public Node<E> getNext() {
			return next;
		}

		// Modifier methods
		public void setNext(Node<E> n) {
			next = n;
		}
	} //----------- end of nested Node class -----------

	// instance variables of the CircularlyLinkedList
	private Node<E> tail = null;

	private int size = 0;

	public CircularlyLinkedList() {
	}

	// access methods
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E first() {
		if (isEmpty()) return null;
		return tail.getNext().getElement();
	}

	public E last() {
		if (isEmpty()) return null;
		return tail.getElement();
	}


	public void addFirst(E e) {
		if (size == 0) {
			tail = new Node<>(e, null);
			tail.setNext(tail);
		} else {
			Node<E> newest = new Node<>(e, tail.getNext());
			tail.setNext(newest);
		}
		size++;
	}

	public void addLast(E e) {
		addFirst(e);
		tail = tail.getNext();
	}


	public String toString() {
		if (tail == null) return "()";
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = tail;
		do {
			walk = walk.getNext();
			sb.append(walk.getElement());
			if (walk != tail)
				sb.append(", ");
		} while (walk != tail);
		sb.append(")");
		return sb.toString();
	}


	// TG Lab Assignment #1 Exercise 3
	// Clone method
	public CircularlyLinkedList<E> clone() throws CloneNotSupportedException {
		CircularlyLinkedList<E> newClone = (CircularlyLinkedList<E>) super.clone();
		if (size > 0) {
			Node<E> originalHead = tail.getNext();
			Node<E> walk = originalHead;
			Node<E> clonedHead = new Node<>(walk.getElement(), null);
			Node<E> cloneWalk = clonedHead;
			walk = walk.getNext();
			while (walk != originalHead) {
				Node<E> clonedNode = new Node<>(walk.getElement(), null);
				cloneWalk.setNext(clonedNode);
				cloneWalk = clonedNode;
				walk = walk.getNext();
			}
			cloneWalk.setNext(clonedHead); // Close the circular chain
			newClone.tail = cloneWalk;
		}
		return newClone;
	}

	// Test method for Exercise 3
	static void main() throws CloneNotSupportedException {

		CircularlyLinkedList<String> circularList = new CircularlyLinkedList<String>();
		circularList.addFirst("LAX");
		circularList.addLast("MSP");
		circularList.addLast("ATL");
		circularList.addLast("BOS");
		System.out.println("Original List");
		System.out.println(circularList);

		CircularlyLinkedList<String> cloneList = circularList.clone();
		System.out.println("Cloned List");
		System.out.println(cloneList);

	}
}