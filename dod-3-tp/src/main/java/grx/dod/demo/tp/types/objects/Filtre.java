package grx.dod.demo.tp.types.objects;

import java.util.List;
import java.util.stream.Collectors;

import grx.dod.demo.tp.Type;

public class Filtre implements Pipeline<Forme> {

	Type type;
	
	public Filtre(Type type) {
		this.type = type;
	}
	
	@Override
	public List<Forme> output(List<Forme> input) {
		return input.stream()
		.filter(
			forme -> {
				return (forme!=null && type.equals(forme.type));
			}
		).collect(Collectors.toList());
	}
	
	public static List<Forme> output(Type type, List<Forme> input) {
		return (new Filtre(type)).output(input);
	}

}
