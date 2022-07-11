package grx.dod.demo.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import grx.dod.demo.ihm.TP.Objects;
import grx.dod.demo.tp.types.objects.Espace;
import grx.dod.demo.tp.types.objects.Forme;

public class Principale extends JFrame {

	private static final long serialVersionUID = 4484671259750691865L;
	
	transient ControlePanel controle;
	
	transient ScenarioPanel scenario;
	
	transient List<Forme> formes;
	transient Espace espace;
	
	transient JFileChooser fichier;
	
	public Principale(String titre, List<Forme> formes) {
		super(titre);
		
		long start;
		long millis;
		
		this.setLayout(new BorderLayout());
		
		this.formes = formes;
		
		this.controle = new ControlePanel(this);
		this.add(this.controle, BorderLayout.NORTH);
		
		start = TP.start();
		this.espace = Objects.espace(this.formes);
		millis = TP.end(start);
		
		this.scenario = new ScenarioPanel(this.controle, this.formes, this.espace, millis);
		this.add(this.scenario, BorderLayout.CENTER);
		
		this.setJMenuBar(new Menu());
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	public void effacer() {
		if (JOptionPane.showConfirmDialog(this, "Voulez-vous tout effacer ?", "Confirmation", JOptionPane.YES_NO_OPTION)==0) {
			formes.clear();
			refresh();
		}
	}
	
	public void ajout(Forme forme) {
		if (forme!=null) {
			formes.add(forme);
			refresh();
		}
	}
	
	public void refresh() {
		scenario.refresh(formes);
		repaint();
	}
	
	public JFileChooser fichier(String titre) {
		if (fichier==null) {
			String dir = System.getProperty("user.dir");
			
			fichier = new JFileChooser();
			fichier.setCurrentDirectory(new File(dir));
		}
		fichier.setDialogTitle(titre);
		return fichier;
	}
	
	public void charger() {
		JFileChooser dialog = fichier("Chargement depuis un fichier ...");
		
		dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		dialog.setMultiSelectionEnabled(false);
		dialog.setVisible(true);
		if (dialog.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
			File fichier = dialog.getSelectedFile();
			String chemin = fichier.getPath();
			
			if (JOptionPane.showConfirmDialog(this, "Chargement depuis '"+chemin+"'", "Confirmation", JOptionPane.YES_NO_OPTION)==0) {
				formes = TP.Objects.charger(chemin);
				
				refresh();
			}
		}
	}
	
	public void sauvegarder() {
		JFileChooser dialog = fichier("Sauvegarde dans un fichier ...");
		
		dialog.setVisible(true);
		if (dialog.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) {
			File fichier = dialog.getSelectedFile();
			String chemin = fichier.getPath();
			
			if (JOptionPane.showConfirmDialog(this, "Sauvegarde dans '"+chemin+"'", "Confirmation", JOptionPane.YES_NO_OPTION)==0) {
				TP.Objects.sauvegarder(chemin, formes);
			}
		}
	}
	
	public class Menu extends JMenuBar {

		private static final long serialVersionUID = -6237953302818325923L;

		transient JMenu fichier;
		transient JMenuItem chargerFichier;
		transient JMenuItem sauvegarderFichier;
		
		Menu() {
			super();
			this.fichier = new JMenu("Fichier");
			this.fichier.setFont(Styles.LABEL_FONT);
			
			this.chargerFichier = new JMenuItem("Charger un fichier ...");
			this.chargerFichier.setFont(Styles.LABEL_FONT);
			this.chargerFichier.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						charger();
					}
					
				}
			);
			this.fichier.add(this.chargerFichier);
			
			this.sauvegarderFichier = new JMenuItem("Sauvegarder dans un fichier ...");
			this.sauvegarderFichier.setFont(Styles.LABEL_FONT);
			this.sauvegarderFichier.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						sauvegarder();
					}
					
				}
			);
			this.fichier.add(this.sauvegarderFichier);
			
			this.add(this.fichier);
		}

	}

	
}
