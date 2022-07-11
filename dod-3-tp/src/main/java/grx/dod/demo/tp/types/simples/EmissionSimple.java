package grx.dod.demo.tp.types.simples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import grx.dod.demo.tp.Type;
import grx.dod.demo.tp.types.objects.Pipeline;

public class EmissionSimple implements Pipeline<FormeSimple> {

	// Retourne le rectangle représentant l'espace
	
	@Override
	public List<FormeSimple> output(List<FormeSimple> formes) {
		List<Double> topX = new ArrayList<>();
		List<Double> topY = new ArrayList<>();
		List<Double> bottomX = new ArrayList<>();
		List<Double> bottomY = new ArrayList<>();
		
		double minTopX;
		double minTopY;
		double maxBottomX;
		double maxBottomY;
		
		double x;
		double y;
		double width;
		double height;
		Set<String> colors = new HashSet<>();
		FormeSimple espace;
		Type type;
		
		for (FormeSimple forme : formes) {
			type = forme.type;
			if (!Type.CERCLE.equals(type)) {
				topX.add(forme.rx);
				topY.add(forme.ry);
				bottomX.add(forme.rx + forme.width);
				bottomY.add(forme.ry + forme.height);
				
				if (Type.ESPACE.equals(type)) {
					colors.addAll(forme.colors);
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
			
			espace = new FormeSimple(x, y, width, height, colors);
			
			return Collections.singletonList(espace);
		} else {
			return Collections.emptyList();
		}
	}

}
