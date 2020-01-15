package cn.lxr.dataStructure.linkedList;

/**
 * 双向链表
 * @author admin
 *
 */
public class DoublyLink {

	private Link first;
	private Link last;
	
	public boolean isEmpty() {
		return (first == null);
	}
	
	public void insertFirst(int iData, double dData) {
		Link newLink = new Link(iData, dData);
		if (isEmpty()) {
			last = newLink;
		} else {
			first.prev = newLink;
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
		newLink.prev = last;
		last = newLink;
	}
	
	public Link deleteFirst() {
		Link temp = first;
		if (first.next == null) {
			last = null;
		} else {
			first.next.prev = null;
		}
		first = first.next;
		return temp;
	}
	
	public Link deleteLast() {
		Link temp = last;
		if (last.prev == null) {
			first = null;
		} else {
			last.prev.next = null;
		}
		last = last.prev;
		return temp;
	}
	
	public boolean insertAfter(int iKey, int iData, double dData) {
		// 首先匹配key链接点
		Link current = first;
		while (current.iData != iKey) {
			current = current.next;
			if (current == null) {
				return false;
			}
		}
		// 然后插入
		Link newLink = new Link(iData, dData);
		if (current == last) {
			last = newLink;
		} else {
			newLink.next = current.next;
			current.next.prev = newLink;
		}
		newLink.prev = current;
		current.next = newLink;
		return true;
	}
	
	public Link deleteKey(int iData) {
		Link current = first;
		while (current.iData != iData) {
			current = current.next;
			if (current == null) {
				return null;
			}
		}
		if (current == first) {
			first = current.next;
		} else {
			current.prev.next = current.next;
		}
		if (current == last) {
			last = current.prev;
		} else {
			current.next.prev = current.prev;
		}
		return current;
	}
	
	public void displayForward() {
		Link current = first;
		while (current != null) {
			current.displayLink();
			current = current.next;
		}
	}
	
	public void displayBackward() {
		Link current = last;
		while (current != null) {
			current.displayLink();
			current = current.prev;
		}
	}
}
