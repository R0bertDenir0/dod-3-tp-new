package grx.dod.demo.tp.types.objects;

import java.util.concurrent.Callable;

public class Tache implements Callable<Forme> {

	Forme forme;
	Conversion conversion;
	
	public Tache(Forme forme) {
		this.forme = forme;
	}
	
	public Tache(Forme forme, Conversion conversion) {
		this.forme = forme;
		this.conversion = conversion;
	}
	
	@Override
	public Forme call() throws Exception {
		if (conversion!=null) {
			// On applique la conversion
			return conversion.apply(forme);
		} else {
			// Sa conversion
			return forme;
		}
	}

}
