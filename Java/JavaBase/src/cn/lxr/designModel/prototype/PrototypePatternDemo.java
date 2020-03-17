package cn.lxr.designModel.prototype;

public class PrototypePatternDemo {

	public static void main(String[] args) {
//		ShapeCache.loadCache();
		Circle clonedCircle = (Circle)ShapeCache.getShape("1");
		System.out.println("Shape : " + clonedCircle.getType());

		Square clonedSquare = (Square)ShapeCache.getShape("2");
		System.out.println("Shape : " + clonedSquare.getType());
		
		Rectangle clonedRectangle = (Rectangle)ShapeCache.getShape("3");
		System.out.println("Shape : " + clonedRectangle.getType());
	}

}
