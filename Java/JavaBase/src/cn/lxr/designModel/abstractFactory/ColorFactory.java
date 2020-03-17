package cn.lxr.designModel.abstractFactory;

import cn.lxr.designModel.abstractFactory.entity.Color;
import cn.lxr.designModel.abstractFactory.entity.Green;
import cn.lxr.designModel.abstractFactory.entity.Red;
import cn.lxr.designModel.abstractFactory.entity.Shape;

public class ColorFactory extends AbstractFactory {

	@Override
	Color getColor(String color) {
		if (color == null) {
			return null;
		}
		if (color.equalsIgnoreCase("RED")) {
			return new Red();
		} else if (color.equalsIgnoreCase("GREEN")) {
			return new Green();
		}
		return null;
	}

	@Override
	Shape getShape(String shapeType) {
		return null;
	}

}
