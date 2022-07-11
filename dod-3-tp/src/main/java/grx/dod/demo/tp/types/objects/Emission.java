package grx.dod.demo.tp.types.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Emission implements Pipeline<Forme> {

	// Retourne le rectangle représentant l'espace
	
	@Override
	public List<Forme> output(List<Forme> formes) {
		List<Double> topX = new ArrayList<>();
		List<Double> topY = new ArrayList<>();
		List<Double> bottomX = new ArrayList<>();
		List<Double> bottomY = new ArrayList<>();
		
		double minTopX;
		double minTopY;
		double maxBottomX;
		double maxBottomY;
		Rectangle rectangle;
		
		double x;
		double y;
		double width;
		double height;
		Set<String> colors = new HashSet<>();
		Espace espace;
		
		for (Forme forme : formes) {
			if (forme instanceof Rectangle) {
				rectangle = (Rectangle)forme;
				topX.add(rectangle.x);
				topY.add(rectangle.y);
				bottomX.add(rectangle.x + rectangle.width);
				bottomY.add(rectangle.y + rectangle.height);
				
				if (rectangle instanceof Espace) {
					colors.addAll(((Espace)rectangle).getColors());
				} else {
					colors.add(forme.color);
				}
			}
		}
		
		if (!topX.isEmpty() && !topY.isEmpty()) {
			minTopX = topX.stream().min((x1, x2) -> x1.compareTo(x2)).get();
			minTopY = topY.stream().min((y1, y2) -> y1.compareTo(y2)).get();
			maxBottomX = bottomX.stream().max((x1, x2) -> x1.compareTo(x2)).get();
			maxBottomY = bottomY.stream().max((y1, y2) -> y1.compareTo(y2)).get();
			
			x = minTopX;
			y = minTopY;
			width = Math.abs(maxBottomX - minTopX);
			height = Math.abs(maxBottomY - minTopY);
			
			espace = new Espace(x, y, width, height, colors);
			
			return Collections.singletonList(espace);
		} else {
			return Collections.emptyList();
		}
	}

}
