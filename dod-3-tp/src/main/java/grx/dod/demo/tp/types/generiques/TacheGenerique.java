package grx.dod.demo.tp.types.generiques;

import java.util.concurrent.Callable;

public class TacheGenerique implements Callable<FormeGenerique> {

	FormeGenerique forme;
	ConversionGenerique conversion;
	
	public TacheGenerique(FormeGenerique forme) {
		this.forme = forme;
	}
	
	public TacheGenerique(FormeGenerique forme, ConversionGenerique conversion) {
		this.forme = forme;
		this.conversion = conversion;
	}
	
	@Override
	public FormeGenerique call() throws Exception {
		if (conversion!=null) {
			// On applique la conversion
			return conversion.apply(forme);
		} else {
			// Sa conversion
			return forme;
		}
	}

}
