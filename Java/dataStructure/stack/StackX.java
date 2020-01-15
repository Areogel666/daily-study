package cn.lxr.dataStructure.stack;

public class StackX {

	private int maxSize;
	private int top; //队头下标，同时是size - 1
	private char[] arr;

	public StackX(int maxSize) {
		this.maxSize = maxSize;
		this.top = -1;
		this.arr = new char[maxSize];
	}

	/**
	 * 放入栈顶
	 */
	public void push(char val) {
		arr[++top] = val;
	}
	
	/**
	 * 取出栈顶
	 */
	public char pop() {
		return arr[top--];
	}
	
	/**
	 * 查看栈顶
	 */
	public char peek() {
		return arr[top];
	}
	
	/**
	 * 查看是否为空
	 */
	public boolean isEmpty() {
		return (-1 == top);
	}
	
	/**
	 * 查看是否已满
	 */
	public boolean isFull() {
		return (maxSize - 1 == top);
	}
}
