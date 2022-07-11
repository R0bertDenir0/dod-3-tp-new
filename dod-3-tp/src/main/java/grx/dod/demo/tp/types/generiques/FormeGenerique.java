package grx.dod.demo.tp.types.generiques;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import grx.dod.demo.tp.Type;

public class FormeGenerique {

	Type type;
	
	Map<String, Object> attributes;
	
	public FormeGenerique(Type type, Map<String, Object> attributes) {
		this.type = type;
		this.attributes = new HashMap<>();
		if (attributes!=null) {
			this.attributes.putAll(attributes);
		}
	}
	
	public Type getType() {
		return type;
	}
	
	public Set<String> attributes() {
		return attributes.keySet();
	}
	
	public Object get(String attribute) {
		return attributes.get(attribute);
	}
	
	public Map<String, Object> values() {
		return attributes;
	}

}
