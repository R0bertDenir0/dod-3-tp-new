package grx.dod.demo.tp.types.generiques;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import grx.dod.demo.tp.Type;

public class ConversionGenerique implements Function<FormeGenerique, FormeGenerique> {

	@Override
	public FormeGenerique apply(FormeGenerique forme) {
		if (forme!=null) {
			Type type = forme.getType();
			
			if (Type.CERCLE.equals(type)) {
				double rayon = (Double)forme.get("radius");
				double cx = (Double)forme.get("cx");
				double cy = (Double)forme.get("cy");
				double x = cx - rayon;
				double y = cy - rayon;
				double width = rayon * 2;
				double height = rayon * 2;
				Map<String, Object> attributes = new HashMap<>(forme.values());
				
				attributes.put("x", x);
				attributes.put("y", y);
				attributes.put("width", width);
				attributes.put("height", height);
				
				return new FormeGenerique(Type.RECTANGLE, attributes);
			} else {
				// Rien à faire
				return forme;
			}
		} else {
			// Rien
			return null;
		}
	}

}
