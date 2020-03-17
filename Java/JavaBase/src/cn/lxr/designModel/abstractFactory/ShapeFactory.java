package cn.lxr.designModel.abstractFactory;

import cn.lxr.designModel.abstractFactory.entity.Circle;
import cn.lxr.designModel.abstractFactory.entity.Color;
import cn.lxr.designModel.abstractFactory.entity.Rectangle;
import cn.lxr.designModel.abstractFactory.entity.Shape;

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
