package grx.dod.demo.tp.types.objects;

import java.util.function.Function;

public class Conversion implements Function<Forme, Forme> {

	@Override
	public Rectangle apply(Forme forme) {
		if (forme instanceof Rectangle) {
			return (Rectangle)forme;
		} else if (forme instanceof Cercle) {
			Cercle cercle = (Cercle)forme;
			double rayon = cercle.rayon;
			double x = cercle.x - rayon;
			double y = cercle.y - rayon;
			double width = rayon * 2;
			double height = rayon * 2;
			
			return new Rectangle(x, y, width, height, forme.color);
		} else {
			// Forme invalide
			return null;
		}
	}

}
