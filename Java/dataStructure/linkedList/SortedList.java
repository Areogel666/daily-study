package cn.lxr.dataStructure.linkedList;

/**
 * 有序链表
 * @author admin
 *
 */
public class SortedList {

	private Link first;
	
	public boolean isEmpty() {
		return (first == null);
	}
	
	public void insert(int iData, double dData) { //insert in order by iData
		Link newLink = new Link(iData, dData);
		Link previous = null;
		Link current = first;
		
		while (current != null && iData > current.iData) { // 插入项更大
			previous = current;
			current = current.next;
		}
		if (previous != null) { //非首项时与上一项链接
			previous.next = newLink;
		} else {
			first = newLink;
		}
		newLink.next = current;
	}
	
	public Link remove() { //delete first link and return it
		Link temp = first;
		first = first.next;
		return temp;
	}
	
	public void displayList() {
		Link current = first;
		while (current != null) {
			current.displayLink();
			current = current.next;
		}
	}
}
