package grx.dod.demo.tp.types.objects;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;

import grx.dod.demo.tp.Type;

public class TP {

	public static Espace espace(List<Forme> formes) {
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
	}

	public static void tp1(List<Forme> formes) {
		Emission espace = new Emission();
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
		
		Consommation affichage = new Consommation();
		affichage.consume(espace.output(rects));
	}
	
	public static void tp2(List<Forme> formes) {
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
		
		Consommation affichage = new Consommation();
		affichage.consume(emission.output(sN));
	}
	
	public static void tp3(List<Forme> formes) throws Exception {
		Emission emission = new Emission();
		Conversion conversion = new Conversion();
		Tache mutation;
		
		int nbCoeurs = 2;
		ExecutorService processeur = Executors.newFixedThreadPool(nbCoeurs);
		
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
			espace.add(tache.get());
		}
		
		processeur.shutdown();
		
		Consommation affichage = new Consommation();
		affichage.consume(emission.output(espace));
	}
	
	public static long start() {
		return System.currentTimeMillis();
	}
	
	public static void end(long start) {
		long end = System.currentTimeMillis();
		
		System.out.println(" => "+(end-start)+" (ms)");
	}
	
	public static void draw(List<Forme> formes, Espace espace) {
		JFrame window = new JFrame("Espace d'occupation des formes");
		window.setLayout(new BorderLayout());
		window.add(new Draw(formes, espace), BorderLayout.CENTER);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public static void main(String[] args) throws Exception {
		String chemin = "src\\main\\resources\\espace.txt";
		List<Forme> formes = (new Generation(chemin)).objects();
		Consommation conso = new Consommation();
		
		System.out.println();
		System.out.println("Formes :");
		conso.consume(formes);
		System.out.println(" => "+formes.size()+" (formes)");
		
		long start;
		
		System.out.println();
		System.out.println("Espace, TP N°1 :");
		start = start();
		tp1(formes);
		end(start);
		
		System.out.println();
		System.out.println("Espace, TP N°2 :");
		start = start();
		tp2(formes);
		end(start);
		
		System.out.println();
		System.out.println("Espace, TP N°3 :");
		start = start();
		tp3(formes);
		end(start);

		draw(formes, espace(formes));
	}

}
