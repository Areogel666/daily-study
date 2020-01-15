package cn.lxr.dataStructure.linkedList;

/**
 * 单链表
 * @author admin
 *
 */
public class LinkList {

	private Link first;

	public LinkList() {
		first = null;
	}

	public boolean isEmpty() {
		return (first == null);
	}

	/**
	 * 表头插入新节点
	 */
	public void insertFirst(int iData, double dData) {
		Link newLink = new Link(iData, dData);
		newLink.next = first;
		first = newLink;
	}
	
	/**
	 * 表头插入新节点
	 */
	public void insertFirst(Link newLink) {
		newLink.next = first;
		first = newLink;
	}

	/**
	 * 删除链接点
	 */
	public Link deleteFirst() {
		Link temp = first;
		first = first.next;
		return temp;
	}

	/**
	 * 显示链表
	 */
	public void displayList() {
		Link current = first;
		while (current != null) {
			current.displayLink();
			current = current.next;
		}
	}

	/**
	 * 根据key查询链接点
	 */
	public Link find(int key) {
		Link current = first;
		while (current != null) {
			if (current.iData == key){
				return current;
			}
			current = current.next;
		}
		return null;
	}
	
	/**
	 * 根据key删除链接点
	 */
	public Link delete(int key) {
		Link current = first;
		Link previous = null;
		while (current != null) {
			if (current.iData == key){
				if (current == first) {
					first = first.next;
				} else {
					previous.next = current.next;
				}
				return current;
			}
			previous = current;
			current = current.next;
		}
		return null;
	}
	
	/**
	 * 首节点
	 */
	public Link getFirst() {
		return this.first;
	}
	
	/**
	 * 设置首节点
	 * @param newLink
	 */
	public void setFirst(Link newLink) {
		this.first = newLink;
	}
	
	/**
	 * 得到迭代器
	 */
	public ListIterator getIterator() {
		return new ListIterator(this);
	}
}
