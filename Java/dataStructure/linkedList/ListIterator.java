package cn.lxr.dataStructure.linkedList;

public class ListIterator {

	private Link current;
	private Link previous;
	private LinkList itList;
	
	public ListIterator(LinkList linkList) {
		this.itList = linkList;
		reset();
	}
	
	//set to start of list
	public void reset() {
		current = itList.getFirst();
		previous = null;
	}
	
	//go to next link
	public void nextLink() {
		previous = current;
		current = current.next;
	}
	
	public boolean atEnd() {
		return (current.next == null);
	}
	
	public Link getCurrent() {
		return this.current;
	}
	
	public void insertAfter(int iData, double dData) {
		Link newLink = new Link(iData, dData);
		if (itList.isEmpty()) {
			itList.setFirst(newLink);
			current = newLink;
		} else {
			newLink.next = current.next;
			current.next = newLink;
			nextLink();
		}
	}
	
	public void insertBefore(int iData, double dData) {
		Link newLink = new Link(iData, dData);
		if (previous == null) {
			newLink.next = current;
			itList.setFirst(newLink);
			current = newLink;
		} else {
			newLink.next = current;
			previous.next = newLink;
			current = newLink;
		}
	}
	
	public Link deleteCurrent() {
		Link link = current;
		if (previous == null) {
			itList.setFirst(current.next);
			reset();
		} else {
			previous.next = current.next;
			if (atEnd()) {
				reset();
			} else {
				current = current.next;
			}
		}
		return link;
	}
}
