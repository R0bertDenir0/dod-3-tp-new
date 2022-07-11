package grx.dod.demo.tp.types.objects;

import java.util.StringJoiner;

import grx.dod.demo.tp.Type;

public class Rectangle extends Forme {

	double x;
	double y;
	
	double width;
	double height;
	
	public Rectangle(double x, double y, double width, double height, String color) {
		super(Type.RECTANGLE, color);
		this.x = x;
		this.y = y;
		this.width = Math.abs(width);
		this.height = Math.abs(height);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	@Override
	public String toLine() {
		StringJoiner joiner = new StringJoiner(";");
		
		joiner
		.add("R")
		.add(Integer.toString((int)x))
		.add(Integer.toString((int)y))
		.add(Integer.toString((int)width))
		.add(Integer.toString((int)height))
		.add(color);
		
		return joiner.toString();
	}
	
	@Override
	public String toString() {
		return "R (x:"+x+", y:"+y+", w:"+width+", h:"+height+", c:"+color+")";
	}

}
