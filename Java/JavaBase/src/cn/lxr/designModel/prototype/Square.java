package cn.lxr.designModel.prototype;

public class Square extends Shape {

	public Square() {
		super.type = "Square";
	}
	
	@Override
	void draw() {
		System.out.println("Inside Square::draw() method.");
	}

}
