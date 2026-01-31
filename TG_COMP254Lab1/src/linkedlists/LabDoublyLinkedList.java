// TG Lab Assignment #1 Exercise 1
package linkedlists;

public class LabDoublyLinkedList<E> extends DoublyLinkedList<E> {

	// Concatenate two doubly linked lists via loop
	public void concatDoublyLoop(DoublyLinkedList<E> from) {
		{
			while (! from.isEmpty()) {
				this.addLast(from.removeFirst());
			}
		}
	}

	// Concatenate two doubly linked lists by reconnecting
	public void concatDoubly(DoublyLinkedList<E> from) {
		{
			if (from == this || from.isEmpty()) return;

			Node<E> thisLast = this.trailer.getPrev();
			Node<E> fromFirst = from.header.getNext();

			thisLast.setNext(fromFirst);
			fromFirst.setPrev(thisLast);

			from.trailer.getPrev().setNext(this.trailer);
			this.trailer.setPrev(from.trailer.getPrev());

			this.size = this.size + from.size;

			from.header.setNext(from.trailer);
			from.trailer.setPrev(from.header);
			from.size = 0;
		}
	}


	static void main() {
		System.out.println("Testing concatenation of doubly linked lists:");
		LabDoublyLinkedList<String> L = new LabDoublyLinkedList<String>();
		L.addFirst("MSP");
		L.addLast("ATL");
		L.addLast("BOS");
		L.addFirst("LAX");
		System.out.println("L" + L);

		LabDoublyLinkedList<String> M = new LabDoublyLinkedList<String>();
		M.addFirst("ABC");
		M.addLast("DEF");
		M.addLast("GHI");
		M.addFirst("JKL");
		System.out.println("M" + M);

		L.concatDoubly(M);
		System.out.println("M joined to L:");
		System.out.println("L" + L);
		System.out.println("M" + M);

		M.concatDoublyLoop(L);
		System.out.println("L joined to M:");
		System.out.println("L" + L);
		System.out.println("M" + M);
		System.out.println("Concluded testing concatenation of doubly linked lists.");
	}
}