package grx.dod.demo.tp;

import java.util.NoSuchElementException;

import grx.dod.demo.tp.types.objects.Cercle;
import grx.dod.demo.tp.types.objects.Espace;
import grx.dod.demo.tp.types.objects.Forme;
import grx.dod.demo.tp.types.objects.Rectangle;

public enum Type {

	CERCLE		("C") {
		
		@Override
		public Cercle fromLine(String line) {
			if (line!=null && !line.isEmpty()) {
				String[] parts = line.split(";");
				
				return new Cercle(
					Double.valueOf(parts[1]), Double.valueOf(parts[2]), 
					Double.valueOf(parts[3]), 
					parts[4]
				);
			} else {
				throw new NoSuchElementException("Cercle vide");
			}
		}
		
	},
	
	RECTANGLE	("R") {
		
		@Override
		public Rectangle fromLine(String line) {
			if (line!=null && !line.isEmpty()) {
				String[] parts = line.split(";");
				
				return new Rectangle(
					Double.valueOf(parts[1]), Double.valueOf(parts[2]), 
					Double.valueOf(parts[3]), Double.valueOf(parts[4]),
					parts[5]
				);
			} else {
				throw new NoSuchElementException("Rectangle vide");
			}
		}
		
	},
	
	ESPACE		("E") {
		
		@Override
		public Espace fromLine(String line) {
			throw new UnsupportedOperationException("Fonction non active pour les formes");
		}
		
	};
	
	private String code;
	
	private Type(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public abstract Forme fromLine(String line);
	
	@Override
	public String toString() {
		return code;
	}
	
	private static final Type[] values = values();
	
	public static Type getInstance(String code) {
		if (code!=null && !code.isEmpty()) {
			for (Type type : values) {
				if (type.code.equalsIgnoreCase(code) || type.name().equalsIgnoreCase(code)) {
					return type;
				}
			}
		}
		
		// Sinon, rien
		return null;
	}

}
