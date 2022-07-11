package grx.dod.demo.tp.types.objects;

import grx.dod.demo.tp.Type;

public abstract class Forme {
	
	Type type;
	
	String color;
	
	protected Forme(Type type, String color) {
		this.type = type;
		this.color = color;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getColor() {
		return color;
	}
	
	public abstract String toLine();

}
