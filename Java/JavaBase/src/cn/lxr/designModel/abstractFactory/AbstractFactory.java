package cn.lxr.designModel.abstractFactory;

import cn.lxr.designModel.abstractFactory.entity.Color;
import cn.lxr.designModel.abstractFactory.entity.Shape;

public abstract class AbstractFactory {

	abstract Color getColor(String color);
	abstract Shape getShape(String shape);
}
