package grx.dod.demo.tp.types.generiques;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import grx.dod.demo.tp.Type;
import grx.dod.demo.tp.types.objects.Pipeline;

public class EmissionGenerique implements Pipeline<FormeGenerique> {

	// Retourne le rectangle représentant l'espace
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormeGenerique> output(List<FormeGenerique> formes) {
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
		FormeGenerique espace;
		Type type;
		
		for (FormeGenerique forme : formes) {
			if (forme!=null) {
				type = forme.type;
				if (!Type.CERCLE.equals(type)) {
					x = (Double)forme.get("x");
					y = (Double)forme.get("y");
					width = (Double)forme.get("width");
					height = (Double)forme.get("height");
					topX.add(x);
					topY.add(y);
					bottomX.add(x + width);
					bottomY.add(y + height);
					
					if (Type.ESPACE.equals(type)) {
						colors.addAll((Set<String>)forme.get("colors"));
					} else {
						colors.add((String)forme.get("color"));
					}
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
			
			Map<String, Object> attributes = new HashMap<>();
			
			attributes.put("x", x);
			attributes.put("y", y);
			attributes.put("width", width);
			attributes.put("height", height);
			attributes.put("colors", colors);
			espace = new FormeGenerique(Type.ESPACE, attributes);
			
			return Collections.singletonList(espace);
		} else {
			return Collections.emptyList();
		}
	}

}
