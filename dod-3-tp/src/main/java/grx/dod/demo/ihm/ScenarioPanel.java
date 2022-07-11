package grx.dod.demo.ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

import grx.dod.demo.tp.Couleur;
import grx.dod.demo.tp.Scenario;
import grx.dod.demo.tp.types.generiques.FormeGenerique;
import grx.dod.demo.tp.types.objects.Draw;
import grx.dod.demo.tp.types.objects.Espace;
import grx.dod.demo.tp.types.objects.Forme;
import grx.dod.demo.tp.types.simples.FormeSimple;

public class ScenarioPanel extends JPanel {

	private static final long serialVersionUID = 4186161592045375526L;
	
	transient ProcessingSelection processing;
	transient StructureSelection structure;
	
	transient Scenario scenario;
	transient long millis;
	
	transient List<Forme> formes;
	transient Espace espace;
	
	transient Draw dessin;
	
	transient Couleurs couleurs;
	transient Performance performance;
	transient Statut statut;
	
	public ScenarioPanel(ControlePanel controle, List<Forme> formes, Espace espace, long millis) {
		super(new BorderLayout());
		
		this.processing = controle.processing;
		this.structure = controle.structure;
		this.millis = millis;
		
		if (this.processing.isPipeline()) {
			this.scenario = new Scenario(this.structure.getMode(), this.millis);
		} else {
			this.scenario = new Scenario(this.structure.getMode(), this.processing.getThreads(), this.millis);
		}
		
		this.dessin = new Draw(formes, espace);
		this.formes = formes;
		this.espace = this.dessin.getEspace();
		
		this.couleurs = new Couleurs(this.espace);
		this.performance = new Performance(this.formes.size(), this.scenario, this.millis);
		this.statut = new Statut(this.couleurs, this.performance);
		
		this.add(this.statut, BorderLayout.SOUTH);
		this.add(this.dessin, BorderLayout.CENTER);
	}
	
	public List<Forme> getFormes() {
		return formes;
	}

	public Espace getEspace() {
		return espace;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public long getMillis() {
		return millis;
	}
	
	public void refresh(List<Forme> formes) {
		if (formes!=null) {
			long start;
			
			this.formes = formes;
			
			if (this.processing.isPipeline()) {
				switch (this.structure.getMode()) {
					case GENERIC:
						List<FormeGenerique> generiques = TP.Generiques.generiques(this.formes);
						
						start = TP.start();
						this.espace = TP.Generiques.pipeline(generiques);
						break;
					case SIMPLE:
						List<FormeSimple> simples = TP.Simples.simples(this.formes);
						
						start = TP.start();
						this.espace = TP.Simples.pipeline(simples);
						break;
					case OBJECT:
					default:
						start = TP.start();
						this.espace = TP.Objects.pipeline(this.formes);
						break;
				}
			} else {
				int nbThreads = this.processing.getThreads();
				
				switch (this.structure.getMode()) {
					case GENERIC:
						List<FormeGenerique> generiques = TP.Generiques.generiques(this.formes);
						
						start = TP.start();
						this.espace = TP.Generiques.parallel(generiques, nbThreads);
						break;
					case SIMPLE:
						List<FormeSimple> simples = TP.Simples.simples(this.formes);
						
						start = TP.start();
						this.espace = TP.Simples.parallel(simples, nbThreads);
						break;
					case OBJECT:
					default:
						start = TP.start();
						this.espace = TP.Objects.parallel(this.formes, nbThreads);
						break;
				}
			}
			
			this.millis = TP.end(start);
			
			this.dessin.refresh(this.formes, espace);
			this.couleurs.refresh(this.espace);
			if (this.processing.isPipeline()) {
				this.scenario = new Scenario(this.structure.getMode(), this.millis);
			} else {
				this.scenario = new Scenario(this.structure.getMode(), this.processing.getThreads(), this.millis);
			}
			this.performance.refresh(formes.size(), this.scenario, millis);
		}
	}
	
	public class Statut extends JPanel {

		private static final long serialVersionUID = -2497475860298012721L;

		transient Couleurs couleurs;
		
		transient Performance performance;
		
		public Statut(Couleurs couleurs, Performance performance) {
			super(new BorderLayout());
			this.add(couleurs, BorderLayout.NORTH);
			this.add(performance, BorderLayout.SOUTH);
		}
		
	}
	
	public class Couleurs extends JPanel {

		private static final long serialVersionUID = 2736050491880715293L;
		
		transient Set<String> colors;
		
		public Couleurs(Espace espace) {
			super(new FlowLayout(FlowLayout.CENTER));
			this.colors = espace.getColors();
			for (String color : this.colors) {
				this.add(new CouleurLogo(Couleur.valueOf(color), 25));
			}
		}
		
		public Set<String> getColors() {
			return colors;
		}
		
		public void refresh(Espace espace) {
			colors = (espace!=null ? espace.getColors() : Collections.emptySet());
			this.removeAll();
			for (String color : colors) {
				this.add(new CouleurLogo(Couleur.valueOf(color), 25));
			}
			this.invalidate();
		}

	}
	
	public class Performance extends JLabel {

		private static final long serialVersionUID = 6882052268400082452L;

		transient int nbFormes;
		transient Scenario scenario;
		
		transient long millis;
		
		public Performance(int nbFormes, Scenario scenario, long millis) {
			this.nbFormes = nbFormes;
			this.scenario = scenario;
			this.millis = millis;
			this.setText(
				this.millis+" ms pour calculer l'espace d'occupation des "+this.nbFormes+" formes avec "+
				this.scenario
			);
			this.setFont(Styles.LABEL_FONT);
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
		}
		
		public void refresh(int nbFormes, Scenario scenario, long millis) {
			this.nbFormes = nbFormes;
			this.millis = millis;
			this.scenario = scenario;
			this.setText(
				this.millis+" ms pour calculer l'espace d'occupation des "+this.nbFormes+" formes avec "+
				this.scenario
			);
		}

	}

}
