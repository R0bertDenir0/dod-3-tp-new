package grx.dod.demo.tp.types.simples;

import java.util.concurrent.Callable;

public class TacheSimple implements Callable<FormeSimple> {

	FormeSimple forme;
	ConversionSimple conversion;
	
	public TacheSimple(FormeSimple forme) {
		this.forme = forme;
	}
	
	public TacheSimple(FormeSimple forme, ConversionSimple conversion) {
		this.forme = forme;
		this.conversion = conversion;
	}
	
	@Override
	public FormeSimple call() throws Exception {
		if (conversion!=null) {
			// On applique la conversion
			return conversion.apply(forme);
		} else {
			// Sa conversion
			return forme;
		}
	}

}
