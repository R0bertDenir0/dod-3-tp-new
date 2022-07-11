package grx.dod.demo.tp.types.simples;

import java.util.List;
import java.util.stream.Collectors;

import grx.dod.demo.tp.Type;
import grx.dod.demo.tp.types.objects.Pipeline;

public class FiltreSimple implements Pipeline<FormeSimple> {

	Type type;
	
	public FiltreSimple(Type type) {
		this.type = type;
	}
	
	@Override
	public List<FormeSimple> output(List<FormeSimple> input) {
		return input.stream()
		.filter(
			forme -> {
				return (forme!=null && type.equals(forme.type));
			}
		).collect(Collectors.toList());
	}
	
	public static List<FormeSimple> output(Type type, List<FormeSimple> input) {
		return (new FiltreSimple(type)).output(input);
	}

}
