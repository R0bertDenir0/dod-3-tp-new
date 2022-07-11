package grx.dod.demo.tp.types.simples;

import java.util.function.Function;

import grx.dod.demo.tp.Type;

public class ConversionSimple implements Function<FormeSimple, FormeSimple> {

	@Override
	public FormeSimple apply(FormeSimple forme) {
		if (forme!=null) {
			Type type = forme.getType();
			
			if (Type.CERCLE.equals(type)) {
				double rayon = forme.rayon;
				double x = forme.cx - rayon;
				double y = forme.cy - rayon;
				double width = rayon * 2;
				double height = rayon * 2;
				
				return new FormeSimple(x, y, width, height, forme.color);
			} else {
				// Rien à faire
				return forme;
			}
		} else {
			return null;
		}
	}

}
