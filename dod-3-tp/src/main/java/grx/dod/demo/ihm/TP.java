package grx.dod.demo.ihm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import grx.dod.demo.tp.Couleur;
import grx.dod.demo.tp.Type;
import grx.dod.demo.tp.types.generiques.ConversionGenerique;
import grx.dod.demo.tp.types.generiques.EmissionGenerique;
import grx.dod.demo.tp.types.generiques.FiltreGenerique;
import grx.dod.demo.tp.types.generiques.FormeGenerique;
import grx.dod.demo.tp.types.generiques.MutationGenerique;
import grx.dod.demo.tp.types.generiques.TacheGenerique;
import grx.dod.demo.tp.types.objects.Cercle;
import grx.dod.demo.tp.types.objects.Conversion;
import grx.dod.demo.tp.types.objects.Emission;
import grx.dod.demo.tp.types.objects.Espace;
import grx.dod.demo.tp.types.objects.Filtre;
import grx.dod.demo.tp.types.objects.Forme;
import grx.dod.demo.tp.types.objects.Generation;
import grx.dod.demo.tp.types.objects.Mutation;
import grx.dod.demo.tp.types.objects.Rectangle;
import grx.dod.demo.tp.types.objects.Sauvegarde;
import grx.dod.demo.tp.types.objects.Tache;
import grx.dod.demo.tp.types.simples.ConversionSimple;
import grx.dod.demo.tp.types.simples.EmissionSimple;
import grx.dod.demo.tp.types.simples.FiltreSimple;
import grx.dod.demo.tp.types.simples.FormeSimple;
import grx.dod.demo.tp.types.simples.MutationSimple;
import grx.dod.demo.tp.types.simples.TacheSimple;

public class TP {
	
	private static final String CHEMIN = "src\\main\\resources\\espace.txt";
	
	public static Principale principale(List<Forme> formes) {
		return new Principale("Espace d'occupation des formes", formes);
	}
	
	public static void draw(List<Forme> formes) {
		Principale window = principale(formes);
		
		window.setVisible(true);
	}
	
	public static void draw(String chemin) {
		// Chargement classique
		List<Forme> formes = Objects.charger(chemin);
		
		draw(formes);
	}
	
	public static long start() {
		return System.currentTimeMillis();
	}
	
	public static long end(long start) {
		long end = System.currentTimeMillis();
		
		return (end-start);
	}
	
	public static int randomX() {
		// -10 ... +10
		return (int)((Math.random()*20)-10);
	}
	
	public static int randomY() {
		// -10 ... +10
		return (int)((Math.random()*20)-10);
	}
	
	public static int randomRadius() {
		// 0 ... 10
		return (int)(Math.random()*10);
	}
	
	public static int randomWidth() {
		// 0 ... 10 | 11
		return (int)(Math.random()*10+1);
	}
	
	public static int randomHeight() {
		// 0 ... 10 | 11
		return (int)(Math.random()*10+1);
	}
	
	public static Couleur randomColor() {
		int count = Couleur.values().length;
		int order = (int)(Math.random()*count);
		
		return Couleur.get(order);
	}

	public static void main(String[] args) throws Exception {
		List<Forme> formes = Objects.charger(CHEMIN);
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		draw(formes);
	}
	
	public static class Objects {
		
		private Objects() {}
		
		private static List<Forme> formes;
		private static Espace espace;
		
		public static List<Forme> charger(String chemin) {
			formes = (new Generation(chemin)).objects();
			return formes;
		}
		
		public static void sauvegarder(String chemin, List<Forme> formes) {
			(new Sauvegarde(chemin)).ecrire(formes);
		}
		
		public static List<Forme> formes() {
			if (formes==null) {
				formes = new ArrayList<>();
			}
			return formes;
		}
		
		public static Espace espace() {
			if (espace==null) {
				espace = espace(formes());
			}
			return espace;
		}

		public static Espace espace(List<Forme> formes) {
			if (formes!=null && !formes.isEmpty()) {
				List<Forme> rects = new ArrayList<>();
				Conversion conversion = new Conversion();
				
				for (Forme forme : formes) {
					if (forme instanceof Rectangle) {
						// Rien à faire
						rects.add(forme);
					} else if (forme instanceof Cercle) {
						// Conversion à faire
						rects.add(conversion.apply(forme));
					} else {
						// On ne sait pas faire
					}
				}
				
				Emission espace = new Emission();
				
				return (Espace)espace.output(rects).get(0);
			} else {
				// Rien
				return null;
			}
		}

		public static Espace pipeline(List<Forme> formes) {
			if (formes!=null && !formes.isEmpty()) {
				Emission emission = new Emission();
				Conversion conversion = new Conversion();
				Mutation mutation = new Mutation(conversion);
				
				List<Forme> s1;
				List<Forme> s2;
				
				s1 = emission.output(mutation.output(Filtre.output(Type.CERCLE,    formes)));
				s2 = emission.output(               (Filtre.output(Type.RECTANGLE, formes)));
				
				List<Forme> sN = new ArrayList<>();
				sN.addAll(s1);
				sN.addAll(s2);
				
				return (Espace)emission.output(sN).get(0);
			} else {
				// Rien
				return null;
			}
		}

		public static Espace parallel(List<Forme> formes, int nbThreads) {
			if (formes!=null && !formes.isEmpty()) {
				Emission emission = new Emission();
				Conversion conversion = new Conversion();
				Tache mutation;
				
				ExecutorService processeur = Executors.newFixedThreadPool(nbThreads);
				
				List<Future<Forme>> taches = new ArrayList<>();
				for (Forme forme : formes) {
					if (forme instanceof Rectangle) {
						// Pas de conversion
						mutation = new Tache(forme);
					} else {
						// Avec conversion
						mutation = new Tache(forme, conversion);
					}
					taches.add(processeur.submit(mutation));
				}
				
				List<Forme> espace = new ArrayList<>();
				for (Future<Forme> tache : taches) {
					try {
						espace.add(tache.get());
					} catch (Exception failure) {
						// Erreur !
						failure.printStackTrace();
					}
				}
				
				processeur.shutdown();
				
				return (Espace)emission.output(espace).get(0);
			} else {
				// Rien
				return null;
			}
		}
		
	}
	
	public static class Generiques {
		
		private Generiques() {}
		
		public static FormeGenerique generique(Forme forme) {
			if (forme!=null) {
				Type type = forme.getType();
				Map<String, Object> attributes = new HashMap<>();
				
				switch (type) {
					case CERCLE:
						Cercle cercle = (Cercle)forme;
						
						attributes.put("cx", cercle.getX());
						attributes.put("cy", cercle.getY());
						attributes.put("radius", cercle.getRayon());
						attributes.put("color", cercle.getColor());
						return new FormeGenerique(Type.CERCLE, attributes);
						
					case RECTANGLE:
						Rectangle rect = (Rectangle)forme;
						
						attributes.put("x", rect.getX());
						attributes.put("y", rect.getY());
						attributes.put("width", rect.getWidth());
						attributes.put("height", rect.getHeight());
						attributes.put("color", rect.getColor());
						return new FormeGenerique(Type.RECTANGLE, attributes);
						
					case ESPACE:
						Espace espace = (Espace)forme;
						
						attributes.put("x", espace.getX());
						attributes.put("y", espace.getY());
						attributes.put("width", espace.getWidth());
						attributes.put("height", espace.getHeight());
						attributes.put("colors", espace.getColors());
						return new FormeGenerique(Type.ESPACE, attributes);
						
					default:
						// Type non supporté
						return null;
				}
			} else {
				return null;
			}
		}
		
		public static List<FormeGenerique> generiques(List<Forme> formes) {
			if (formes!=null) {
				return formes.stream().map(
					forme -> generique(forme)
				).collect(Collectors.toList());
			} else {
				return Collections.emptyList();
			}
		}
		
		public static Espace espace(FormeGenerique forme) {
			if (forme!=null) {
				double x = (Double)forme.get("x");
				double y = (Double)forme.get("y");
				double width = (Double)forme.get("width");
				double height = (Double)forme.get("height");
				
				@SuppressWarnings("unchecked")
				Set<String> colors = (Set<String>)forme.get("colors");
				
				return new Espace(x, y, width, height, colors);
			} else {
				// Rien
				return null;
			}
		}
		
		public static Espace parallel(List<FormeGenerique> formes, int nbThreads) {
			if (formes!=null && !formes.isEmpty()) {
				EmissionGenerique emission = new EmissionGenerique();
				ConversionGenerique conversion = new ConversionGenerique();
				TacheGenerique mutation;
				
				ExecutorService processeur = Executors.newFixedThreadPool(nbThreads);
				
				List<Future<FormeGenerique>> taches = new ArrayList<>();
				Type type;
				
				for (FormeGenerique forme : formes) {
					type = forme.getType();
					if (Type.RECTANGLE.equals(type)) {
						// Pas de conversion
						mutation = new TacheGenerique(forme);
					} else {
						// Avec conversion
						mutation = new TacheGenerique(forme, conversion);
					}
					taches.add(processeur.submit(mutation));
				}
				
				List<FormeGenerique> espace = new ArrayList<>();
				for (Future<FormeGenerique> tache : taches) {
					try {
						espace.add(tache.get());
					} catch (Exception failure) {
						// Erreur !
						failure.printStackTrace();
					}
				}
				
				processeur.shutdown();
				
				/* TODO : ...
				FormeGenerique resultat = emission.output(espace).get(0);
				
				return espace(resultat);
				 */
				
				JOptionPane.showMessageDialog(null, "Non implémentée, à faire ...");
				return null;
			} else {
				// Rien
				return null;
			}
		}
		
		public static Espace pipeline(List<FormeGenerique> formes) {
			if (formes!=null && !formes.isEmpty()) {
				EmissionGenerique emission = new EmissionGenerique();
				ConversionGenerique conversion = new ConversionGenerique();
				MutationGenerique mutation = new MutationGenerique(conversion);
				
				List<FormeGenerique> s1;
				List<FormeGenerique> s2;
				
				s1 = emission.output(mutation.output(FiltreGenerique.output(Type.CERCLE,    formes)));
				s2 = emission.output(               (FiltreGenerique.output(Type.RECTANGLE, formes)));
				
				List<FormeGenerique> sN = new ArrayList<>();
				sN.addAll(s1);
				sN.addAll(s2);
				
				/* TODO : ...
				FormeGenerique resultat = emission.output(sN).get(0);
				
				return espace(resultat);
				 */
				
				JOptionPane.showMessageDialog(null, "Non implémentée, à faire ...");
				return null;
			} else {
				// Rien
				return null;
			}
		}
		
	}
	
	public static class Simples {
		
		private Simples() {}
		
		public static FormeSimple simple(Forme forme) {
			if (forme!=null) {
				Type type = forme.getType();
				
				switch (type) {
					case CERCLE:
						Cercle cercle = (Cercle)forme;
						
						return new FormeSimple(cercle.getX(), cercle.getY(), cercle.getRayon(), cercle.getColor());
						
					case RECTANGLE:
						Rectangle rect = (Rectangle)forme;
						
						return new FormeSimple(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getColor());
						
					case ESPACE:
						Espace espace = (Espace)forme;
						
						return new FormeSimple(espace.getX(), espace.getY(), espace.getWidth(), espace.getHeight(), espace.getColors());
						
					default:
						// Type non supporté
						return null;
				}
			} else {
				return null;
			}
		}
		
		public static List<FormeSimple> simples(List<Forme> formes) {
			if (formes!=null) {
				return formes.stream().map(
					forme -> simple(forme)
				).collect(Collectors.toList());
			} else {
				return Collections.emptyList();
			}
		}
		
		public static Espace espace(FormeSimple forme) {
			if (forme!=null) {
				double x = forme.getRx();
				double y = forme.getRy();
				double width = forme.getWidth();
				double height = forme.getHeight();
				Set<String> colors = forme.getColors();
				
				return new Espace(x, y, width, height, colors);
			} else {
				return null;
			}
		}
		
		public static Espace parallel(List<FormeSimple> formes, int nbThreads) {
			if (formes!=null && !formes.isEmpty()) {
				EmissionSimple emission = new EmissionSimple();
				ConversionSimple conversion = new ConversionSimple();
				TacheSimple mutation;
				
				ExecutorService processeur = Executors.newFixedThreadPool(nbThreads);
				
				List<Future<FormeSimple>> taches = new ArrayList<>();
				Type type;
				
				for (FormeSimple forme : formes) {
					type = forme.getType();
					if (Type.RECTANGLE.equals(type)) {
						// Pas de conversion
						mutation = new TacheSimple(forme);
					} else {
						// Avec conversion
						mutation = new TacheSimple(forme, conversion);
					}
					taches.add(processeur.submit(mutation));
				}
				
				List<FormeSimple> espace = new ArrayList<>();
				for (Future<FormeSimple> tache : taches) {
					try {
						espace.add(tache.get());
					} catch (Exception failure) {
						// Erreur !
						failure.printStackTrace();
					}
				}
				
				processeur.shutdown();
				
				/* TODO : ...
				FormeSimple resultat = emission.output(espace).get(0);
				
				return espace(resultat);
				 */
				
				JOptionPane.showMessageDialog(null, "Non implémentée, à faire ...");
				return null;
			} else {
				// Rien
				return null;
			}
		}
		
		public static Espace pipeline(List<FormeSimple> formes) {
			if (formes!=null && !formes.isEmpty()) {
				EmissionSimple emission = new EmissionSimple();
				ConversionSimple conversion = new ConversionSimple();
				MutationSimple mutation = new MutationSimple(conversion);
				
				List<FormeSimple> s1;
				List<FormeSimple> s2;
				
				s1 = emission.output(mutation.output(FiltreSimple.output(Type.CERCLE,    formes)));
				s2 = emission.output(               (FiltreSimple.output(Type.RECTANGLE, formes)));
				
				List<FormeSimple> sN = new ArrayList<>();
				sN.addAll(s1);
				sN.addAll(s2);
				
				
				/* TODO : ...
				FormeSimple resultat = emission.output(sN).get(0);
				
				return espace(resultat);
				 */
				
				JOptionPane.showMessageDialog(null, "Non implémentée, à faire ...");
				return null;
			} else {
				// Rien
				return null;
			}
		}
		
	}
	
}
