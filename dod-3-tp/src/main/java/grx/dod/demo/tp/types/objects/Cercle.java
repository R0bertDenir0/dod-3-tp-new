package grx.dod.demo.tp.types.objects;

import java.util.StringJoiner;

import grx.dod.demo.tp.Type;

public class Cercle extends Forme {

	double x;
	double y;
	
	double rayon;
	
	public Cercle(double x, double y, double rayon, String color) {
		super(Type.CERCLE, color);
		this.x = x;
		this.y = y;
		this.rayon = rayon;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRayon() {
		return rayon;
	}

	@Override
	public String toLine() {
		StringJoiner joiner = new StringJoiner(";");
		
		joiner
		.add(type.getCode())
		.add(Integer.toString((int)x))
		.add(Integer.toString((int)y))
		.add(Integer.toString((int)rayon))
		.add(color);
		
		return joiner.toString();
	}
	
	@Override
	public String toString() {
		return type.getCode()+" (x:"+x+", y:"+y+", r:"+rayon+", c:"+color+")";
	}

}
