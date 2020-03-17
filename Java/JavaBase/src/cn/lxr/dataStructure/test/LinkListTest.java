package cn.lxr.dataStructure.test;


import org.junit.Ignore;
import org.junit.Test;

import cn.lxr.dataStructure.linkedList.DoublyLink;
import cn.lxr.dataStructure.linkedList.Link;
import cn.lxr.dataStructure.linkedList.LinkList;
import cn.lxr.dataStructure.linkedList.ListIterator;
import cn.lxr.dataStructure.linkedList.SortedList;

public class LinkListTest {

	@Test
	@Ignore
	public void testLinkList() {
		LinkList list = new LinkList();
		list.insertFirst(1, 1.3);
		list.insertFirst(2, 2.4);
		list.insertFirst(5, 5.7);
		list.insertFirst(4, 4.0);
		list.displayList();
		System.out.println("=========删除第一个链接点=========");
		Link deleteLink = list.deleteFirst();
		deleteLink.displayLink();
		System.out.println("=========删除完毕=========");
		System.out.println("=========删除5=========");
		Link deleteLink2 = list.delete(5);
		deleteLink2.displayLink();
		System.out.println("=========删除完毕=========");
		System.out.println("=========查询1=========");
		Link findLink = list.find(1);
		findLink.displayLink();
		System.out.println("=========查询完毕=========");
		
		list.displayList();
	}
	
	@Test
	@Ignore
	public void testSortedList() {
		SortedList list = new SortedList();
		list.insert(1, 1.3);
		list.insert(2, 2.4);
		list.insert(5, 5.7);
		list.insert(4, 4.0);
		list.displayList();
		
	}
	
	@Test
	@Ignore
	public void testDoublyLink() {
		DoublyLink list = new DoublyLink();
		list.insertFirst(5, 5.4);
		list.insertFirst(7, 7.1);
		list.insertFirst(1, 1.2);
		list.insertLast(2, 2.9);
		list.insertAfter(7, 6, 6.6);
		System.out.println("======正序显示=====");
		list.displayForward();
		System.out.println("======倒序显示=====");
		list.displayBackward();
		System.out.println("======删除首尾和7=====");
		list.deleteFirst();
		list.deleteLast();
		list.deleteKey(7);
		list.displayForward();
	}
	
	@Test
	public void testListIterator() {
		LinkList list = new LinkList();
		ListIterator iterator = list.getIterator();
		iterator.insertBefore(5, 5.4);
		iterator.insertBefore(7, 7.1);
		iterator.insertBefore(1, 1.2);
		iterator.insertAfter(6, 6.6);
		iterator.reset();
		list.displayList();
		Link nextLink = iterator.getCurrent();
		while (!iterator.atEnd()) {
			System.out.println("iData:" + nextLink.iData + ",dData:" + nextLink.dData);
			iterator.nextLink();
			nextLink = iterator.getCurrent();
		}
		System.out.println("iData:" + nextLink.iData + ",dData:" + nextLink.dData);
	}
}
