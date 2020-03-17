package cn.lxr.designModel.prototype;

public class Circle extends Shape {

	public Circle() {
		super.type = "Circle";
	}
	
	@Override
	void draw() {
		System.out.println("Inside Circle::draw() method.");
	}

}
