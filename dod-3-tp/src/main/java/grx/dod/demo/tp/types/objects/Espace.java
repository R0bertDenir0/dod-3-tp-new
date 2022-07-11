package grx.dod.demo.tp.types.objects;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Espace extends Rectangle {

	private Set<String> colors;
	private Set<String> _colors;
	
	public Espace(double x, double y, double width, double height, Set<String> colors) {
		super(x, y, width, height, colors.iterator().next());
		
		this.colors = new HashSet<>();
		for (String color : colors) {
			this.colors.add(color);
		}
	}
	
	public Set<String> getColors() {
		if (_colors==null) {
			_colors = Collections.unmodifiableSet(colors);
		}
		return _colors;
	}
	
	@Override
	public String toString() {
		return "E (x:"+x+", y:"+y+", w:"+width+", h:"+height+", c:"+colors+")";
	}

}
