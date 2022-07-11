package grx.dod.demo.tp.types.objects;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Sauvegarde {

	String chemin;
	
	public Sauvegarde(String chemin) {
		this.chemin = chemin;
	}
	
	public void ecrire(List<Forme> formes) {
		if (formes!=null && !formes.isEmpty()) {
			File fichier = new File(chemin);

			try (FileWriter ecriture = new FileWriter(fichier, false)) {
				int size = formes.size();
				int pos = 0;
				String ligne;
				
				for (Forme forme : formes) {
					pos++;
					ligne = forme.toLine();
					if (pos==size) {
						ecriture.write(ligne);
					} else {
						ecriture.write(ligne+"\n");
					}
				}
				
				ecriture.flush();
			} catch (Exception failure) {
				throw new UnsupportedOperationException("Impossible de sauvegarder dans '"+chemin+"'", failure);
			}
		}
	}

}
