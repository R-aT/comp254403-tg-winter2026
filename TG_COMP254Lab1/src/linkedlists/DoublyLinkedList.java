package linkedlists;

public class DoublyLinkedList<E> {

	//---------------- nested Node class ----------------
	protected static class Node<E> {

		private final E element;

		private Node<E> prev;

		private Node<E> next;

		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}

		// public accessor methods
		public E getElement() {
			return element;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public Node<E> getNext() {
			return next;
		}

		// Update methods
		public void setPrev(Node<E> p) {
			prev = p;
		}

		public void setNext(Node<E> n) {
			next = n;
		}

	} //----------- end of nested Node class -----------

	// instance variables of the DoublyLinkedList

	public final Node<E> header;

	public final Node<E> trailer;

	public int size = 0;

	public DoublyLinkedList() {
		header = new Node<>(null, null, null);
		trailer = new Node<>(null, header, null);
		header.setNext(trailer);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E first() {
		if (isEmpty()) return null;
		return header.getNext().getElement();
	}

	public E last() {
		if (isEmpty()) return null;
		return trailer.getPrev().getElement();
	}

	public void addFirst(E e) {
		addBetween(e, header, header.getNext());    // place just after the header
	}

	public void addLast(E e) {
		addBetween(e, trailer.getPrev(), trailer);  // place just before the trailer
	}

	public E removeFirst() {
		if (isEmpty()) return null;
		return remove(header.getNext());
	}

	public E removeLast() {
		if (isEmpty()) return null;
		return remove(trailer.getPrev());
	}

	private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
		// create and link a new node
		Node<E> newest = new Node<>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
	}

	private E remove(Node<E> node) {
		Node<E> predecessor = node.getPrev();
		Node<E> successor = node.getNext();
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		return node.getElement();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = header.getNext();
		while (walk != trailer) {
			sb.append(walk.getElement());
			walk = walk.getNext();
			if (walk != trailer)
				sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}
}