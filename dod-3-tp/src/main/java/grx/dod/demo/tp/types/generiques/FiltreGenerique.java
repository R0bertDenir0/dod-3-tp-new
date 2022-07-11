package grx.dod.demo.tp.types.generiques;

import java.util.List;
import java.util.stream.Collectors;

import grx.dod.demo.tp.Type;
import grx.dod.demo.tp.types.objects.Pipeline;

public class FiltreGenerique implements Pipeline<FormeGenerique> {

	Type type;
	
	public FiltreGenerique(Type type) {
		this.type = type;
	}
	
	@Override
	public List<FormeGenerique> output(List<FormeGenerique> input) {
		return input.stream()
		.filter(
			forme -> {
				return (forme!=null && type.equals(forme.type));
			}
		).collect(Collectors.toList());
	}
	
	public static List<FormeGenerique> output(Type type, List<FormeGenerique> input) {
		return (new FiltreGenerique(type)).output(input);
	}

}
