package grx.dod.demo.tp.types.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import grx.dod.demo.tp.Type;

public class Generation {

	String chemin;
	
	public Generation(String chemin) {
		this.chemin = chemin;
	}
	
	public List<Forme> objects() {
		File fichier = new File(chemin);
		
		try (BufferedReader lecteur = new BufferedReader(new FileReader(fichier))) {
			return lecteur.lines().map(
				line -> {
					Forme forme;

					if (line!=null && !line.isEmpty()) {
						String[] parts = line.split(";");
						Type type = Type.getInstance(parts[0]);
						
						if (type!=null) {
							forme = type.fromLine(line);
						} else {
							forme = null;
						}
					} else {
						// Vide
						forme = null;
					}
					
					return forme;
				}
			).collect(Collectors.toList());
		} catch (Exception failure) {
			throw new UnsupportedOperationException("Impossible de charger depuis '"+chemin+"'", failure);
		}
	}
	
	public List<?> generiques() throws Exception {
		// TODO : ... formes génériques
		return Collections.emptyList();
	}
	
	public List<?> simples() throws Exception {
		// TODO : ... formes simplifiées
		return Collections.emptyList();
	}

}
