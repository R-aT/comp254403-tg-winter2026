package linkedlists;

public class SinglyLinkedList<E> implements Cloneable {
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

	// instance variables of the SinglyLinkedList
	private Node<E> head = null;

	private Node<E> tail = null;

	private int size = 0;

	public SinglyLinkedList() {
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E first() {
		if (isEmpty()) return null;
		return head.getElement();
	}

	public E last() {
		if (isEmpty()) return null;
		return tail.getElement();
	}

	// update methods
	public void addFirst(E e) {
		head = new Node<>(e, head);
		if (size == 0)
			tail = head;
		size++;
	}

	public void addLast(E e) {
		Node<E> newest = new Node<>(e, null);
		if (isEmpty())
			head = newest;
		else
			tail.setNext(newest);
		tail = newest;
		size++;
	}


	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		SinglyLinkedList other = (SinglyLinkedList) o;
		if (size != other.size) return false;
		Node walkA = head;
		Node walkB = other.head;
		while (walkA != null) {
			if (! walkA.getElement().equals(walkB.getElement())) return false; //mismatch
			walkA = walkA.getNext();
			walkB = walkB.getNext();
		}
		return true;
	}

	@SuppressWarnings({ "unchecked" })
	public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
		// always use inherited Object.clone() to create the initial copy
		SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
		if (size > 0) {                    // we need independent chain of nodes
			other.head = new Node<>(head.getElement(), null);
			Node<E> walk = head.getNext();      // walk through remainder of original list
			Node<E> otherTail = other.head;     // remember most recently created node
			while (walk != null) {              // make a new node storing same element
				Node<E> newest = new Node<>(walk.getElement(), null);
				otherTail.setNext(newest);     // link previous node to this one
				otherTail = newest;
				walk = walk.getNext();
			}
		}
		return other;
	}

	public int hashCode() {
		int h = 0;
		for (Node walk = head; walk != null; walk = walk.getNext()) {
			h ^= walk.getElement().hashCode();      // bitwise exclusive-or with element's code
			h = (h << 5) | (h >>> 27);              // 5-bit cyclic shift of composite code
		}
		return h;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = head;
		while (walk != null) {
			sb.append(walk.getElement());
			if (walk != tail)
				sb.append(", ");
			walk = walk.getNext();
		}
		sb.append(")");
		return sb.toString();
	}

	// TG Lab Assignment #1 Exercise 2
	// Swap two nodes given reference
	public void swapNodes(Node<E> one, Node<E> two) {
		if (one == two || one == null || two == null) return;

		// Find nodes in list
		Node<E> prevNodeOne = null, prevNodeTwo = null, atNode = head;

		// Check if tail
		boolean oneWasTail = (one == tail);
		boolean twoWasTail = (two == tail);

		// Find previous nodes
		while (atNode != null && (prevNodeOne == null || prevNodeTwo == null)) {
			if (atNode.getNext() == one) {
				prevNodeOne = atNode;
			} else if (atNode.getNext() == two) {
				prevNodeTwo = atNode;
			}
			atNode = atNode.getNext();
		}

		// Check for head swaps and adjacent nodes
		// One is before two
		if (one.getNext() == two) {
			if (prevNodeOne != null) {
				prevNodeOne.setNext(two);
			} else {
				head = two;
			}
			one.setNext(two.getNext());
			two.setNext(one);

			// Two is before one
		} else if (two.getNext() == one) {
			if (prevNodeTwo != null) {
				prevNodeTwo.setNext(one);
			} else {
				head = one;
			}
			two.setNext(one.getNext());
			one.setNext(two);
		} else {
			// Not adjacent nodes
			if (prevNodeOne != null) {
				prevNodeOne.setNext(two);
			} else {
				head = two;
			}
			if (prevNodeTwo != null) {
				prevNodeTwo.setNext(one);
			} else {
				head = one;
			}
			Node<E> temp = one.getNext();
			one.setNext(two.getNext());
			two.setNext(temp);
		}

		// Update Tails
		if (oneWasTail) tail = two;
		else if (twoWasTail) tail = one;
	}

	//main method
	static void main() {

		// Test 1 middle swap:
		SinglyLinkedList<String> listOne = new SinglyLinkedList<>();
		listOne.addFirst("LAX");
		listOne.addFirst("MSP");
		listOne.addLast("ATL");
		listOne.addLast("BOS");

		System.out.println("List original: ");
		System.out.println(listOne);
		System.out.println();

		Node<String> fromOne = listOne.head.getNext();
		Node<String> toTwo = fromOne.getNext().getNext();
		listOne.swapNodes(fromOne, toTwo);
		System.out.println("List after swap LAX to BOS: ");
		System.out.println(listOne);
		System.out.println();

		// Test 2 head swap:
		fromOne = listOne.head;
		toTwo = fromOne.getNext();
		listOne.swapNodes(fromOne, toTwo);
		System.out.println("List after swap MSP to BOS: ");
		System.out.println(listOne);
		System.out.println();

		// Test 3 head to tail swap:
		fromOne = listOne.head;
		toTwo = listOne.tail;
		listOne.swapNodes(fromOne, toTwo);
		System.out.println("List after swap BOS to LAX: ");
		System.out.println(listOne);
		System.out.println();

	}
}