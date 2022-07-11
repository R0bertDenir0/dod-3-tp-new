package grx.dod.demo.tp.types.simples;

import java.util.List;
import java.util.stream.Collectors;

import grx.dod.demo.tp.types.objects.Pipeline;

public class MutationSimple implements Pipeline<FormeSimple> {

	// Transforme : Cercle => Rectangle
	
	ConversionSimple conversion;
	
	public MutationSimple(ConversionSimple conversion) {
		this.conversion = conversion;
	}
	
	@Override
	public List<FormeSimple> output(List<FormeSimple> input) {
		if (conversion!=null) {
			return input.stream().map(conversion)
			.collect(Collectors.toList());
		} else {
			return input;
		}
	}

}
