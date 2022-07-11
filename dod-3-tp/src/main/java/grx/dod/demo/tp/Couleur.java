package grx.dod.demo.tp;

import java.awt.Color;

public enum Couleur {

	green	(Color.GREEN),
	blue	(Color.BLUE),
	red		(Color.RED),
	black	(Color.BLACK),
	magenta	(Color.MAGENTA),
	pink	(Color.PINK),
	yellow	(Color.YELLOW);
	
	private Color color;
	
	private Couleur(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	private static final Couleur[] values = values();
	private static final int size = values.length;
	public static int size() {
		return size;
	}
	
	public static Couleur get(int range) {
		if (0<=range && range<size) {
			for (Couleur couleur : values) {
				if (couleur.ordinal()==range) {
					return couleur;
				}
			}
			// Last
			return yellow;
		} else {
			// First
			return green;
		}
	}

}
