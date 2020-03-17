package cn.lxr.dataStructure.linkedList;

/**
 * 双端链表
 * @author admin
 *
 */
public class FirstLastLink {
	
	private Link first;
	private Link last;
	
	public FirstLastLink() {
		super();
	}
	
	public boolean isEmpty() {
		return (first == null);
	}
	
	public void insertFirst(int iData, double dData) {
		Link newLink = new Link(iData, dData);
		if (isEmpty()) {
			last = newLink;
		}
		newLink.next = first;
		first = newLink;
	}
	
	public void insertLast(int iData, double dData) {
		Link newLink = new Link(iData, dData);
		if (isEmpty()) {
			first = newLink;
		} else {
			last.next = newLink;
		}
		last = newLink;
	}
	
	public Link deleteFirst() {
		Link tempLink = first;
		first = first.next;
		if (first == null) {
			last = null;
		}
		return tempLink;
	}
	
	public void displayList() {
		Link current = first;
		while (current != null) {
			current.displayLink();
			current = current.next;
		}
	}
}
