package grx.dod.demo.tp.types.simples;

import java.util.Collections;
import java.util.Set;

import grx.dod.demo.tp.Type;

public class FormeSimple {

	Type type;
	
	String color;
	
	// Si cercle
	double rayon;
	double cx;
	double cy;
	
	// Si rectangle
	double width;
	double height;
	double rx;
	double ry;
	
	// Si espace d'occupation
	// + attributs d'un rectangle
	Set<String> colors;
	Set<String> _colors;
	
	public FormeSimple(double cx, double cy, double radius, String color) {
		this.type = Type.CERCLE;
		this.cx = cx;
		this.cy = cy;
		this.rayon = radius;
		this.color = color;
	}
	
	public FormeSimple(double rx, double ry, double width, double height, String color) {
		this.type = Type.RECTANGLE;
		this.rx = rx;
		this.ry = ry;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public FormeSimple(double x, double y, double width, double height, Set<String> colors) {
		this.type = Type.ESPACE;
		this.rx = x;
		this.ry = y;
		this.width = width;
		this.height = height;
		this.colors = (colors!=null ? colors : Collections.emptySet());
	}
	
	public Type getType() {
		return type;
	}

	public String getColor() {
		return color;
	}
	
	/****************************************************************************************\
	 * Si CERCLE
	\****************************************************************************************/
	public double getCx() {
		return cx;
	}

	public double getCy() {
		return cy;
	}
	
	public double getRadius() {
		return rayon;
	}
	/****************************************************************************************/
	
	/****************************************************************************************\
	 * Si RECTANGLE
	\****************************************************************************************/

	public double getRx() {
		return rx;
	}

	public double getRy() {
		return ry;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	/****************************************************************************************/
	
	
	/****************************************************************************************\
	 * Si ESPACE
	\****************************************************************************************/
	public Set<String> getColors() {
		if (_colors==null) {
			_colors = Collections.unmodifiableSet(colors);
		}
		return _colors;
	}
	/****************************************************************************************/

}
