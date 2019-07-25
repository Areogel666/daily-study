package cn.lxr.abstractFactory;

import cn.lxr.abstractFactory.entity.Circle;
import cn.lxr.abstractFactory.entity.Color;
import cn.lxr.abstractFactory.entity.Rectangle;
import cn.lxr.abstractFactory.entity.Shape;

public class ShapeFactory extends AbstractFactory {

	@Override
	Color getColor(String color) {
		return null;
	}

	@Override
	Shape getShape(String shapeType) {
		if (shapeType == null) {
			return null;
		}
		if (shapeType.equalsIgnoreCase("CIRCLE")) {
			return new Circle();
		} else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
			return new Rectangle();
		}
		return null;
	}

}
