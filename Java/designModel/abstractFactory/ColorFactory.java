package cn.lxr.abstractFactory;

import cn.lxr.abstractFactory.entity.Circle;
import cn.lxr.abstractFactory.entity.Color;
import cn.lxr.abstractFactory.entity.Green;
import cn.lxr.abstractFactory.entity.Rectangle;
import cn.lxr.abstractFactory.entity.Red;
import cn.lxr.abstractFactory.entity.Shape;

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
