package grx.dod.demo.tp.types.generiques;

import java.util.List;
import java.util.stream.Collectors;

import grx.dod.demo.tp.types.objects.Pipeline;

public class MutationGenerique implements Pipeline<FormeGenerique> {

	// Transforme : Cercle => Rectangle
	
	ConversionGenerique conversion;
	
	public MutationGenerique(ConversionGenerique conversion) {
		this.conversion = conversion;
	}
	
	@Override
	public List<FormeGenerique> output(List<FormeGenerique> input) {
		if (conversion!=null) {
			return input.stream().map(conversion)
			.collect(Collectors.toList());
		} else {
			return input;
		}
	}

}
