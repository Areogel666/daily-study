package cn.lxr.dataStructure.linkedList;

public class Link {

	public int iData;
	public double dData;
	public Link next;
	public Link prev; //双向列表用
	
	public Link(int iData, double dData) {
		this.iData = iData;
		this.dData = dData;
	}
	
	public void displayLink() {
		System.out.println("{" + iData + "," + dData + "}");
	}
}
