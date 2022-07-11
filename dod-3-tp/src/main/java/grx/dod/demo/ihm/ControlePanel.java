package grx.dod.demo.ihm;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import grx.dod.demo.ihm.Dialogs.AjoutCercle;
import grx.dod.demo.ihm.Dialogs.AjoutRectangle;
import grx.dod.demo.tp.Couleur;
import grx.dod.demo.tp.ProcessingMode;
import grx.dod.demo.tp.StructureMode;
import grx.dod.demo.tp.types.objects.Cercle;
import grx.dod.demo.tp.types.objects.Rectangle;

public class ControlePanel extends JPanel {

	private static final long serialVersionUID = -1317674255545484883L;

	transient Principale principale;
	
	transient ProcessingSelection processing;
	transient StructureSelection structure;
	
	transient FormePanel ajout;
	
	public ControlePanel(Principale principale) {
		super(new GridLayout(3, 1));
		
		this.principale = principale;
		
		this.processing = new ProcessingSelection(principale);
		this.add(this.processing);
		
		this.structure = new StructureSelection(principale);
		this.add(this.structure);
		
		this.ajout = new FormePanel(this.principale);
		this.add(this.ajout);
	}
	
	public ProcessingMode getProcessing() {
		return processing.getMode();
	}
	
	public int getThreads() {
		return processing.getThreads();
	}
	
	public StructureMode getStructure() {
		return structure.getMode();
	}
	
	public class FormePanel extends JPanel {

		private static final long serialVersionUID = 8834547852756787659L;
		
		transient Principale principale;
		
		transient JButton cercle;
		transient AjoutCercle cercleDialog;
		
		transient JButton rectangle;
		transient AjoutRectangle rectangleDialog;
		
		transient JButton effacer;
		
		public FormePanel(Principale principale) {
			super(new FlowLayout(FlowLayout.CENTER));
			
			this.principale = principale;
			
			this.cercle = new JButton("+ Cercle");
			this.cercle.setFont(Styles.LABEL_FONT);
			this.cercle.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ajoutCercle();
					}
					
				}
			);
			this.add(this.cercle);
			
			this.rectangle = new JButton("+ Rectangle");
			this.rectangle.setFont(Styles.LABEL_FONT);
			this.rectangle.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ajoutRectangle();
					}
					
				}
				
			);
			this.add(this.rectangle);
			
			this.effacer = new JButton("Effacer (x)");
			this.effacer.setFont(Styles.LABEL_FONT);
			this.effacer.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						effacerTout();
					}
					
				}
			);
			this.add(this.effacer);
		}
		
		public void ajoutCercle() {
			if (cercleDialog==null) {
				cercleDialog = new AjoutCercle(principale);
			}
			cercleDialog.setVisible(true);
			
			if (cercleDialog.isOk()) {
				int x = cercleDialog.getCentreX();
				int y = cercleDialog.getCentreY();
				int rayon = cercleDialog.getRayon();
				Couleur couleur = cercleDialog.getCouleur();
				Cercle cercle = new Cercle(x, y, rayon, couleur.toString());
				
				JOptionPane.showMessageDialog(
					principale, 
					"Ajout de cercle : \n ("+x+", "+y+", "+rayon+", "+couleur+")"
				);
				
				principale.ajout(cercle);
			}
			
			// RAZ dialog
			cercleDialog = null;
		}
		
		public void ajoutRectangle() {
			if (rectangleDialog==null) {
				rectangleDialog = new AjoutRectangle(principale);
			}
			rectangleDialog.setVisible(true);
			
			if (rectangleDialog.isOk()) {
				int x = rectangleDialog.getPositionX();
				int y = rectangleDialog.getPositionY();
				int largeur = rectangleDialog.getLargeur();
				int longeur = rectangleDialog.getLongeur();
				Couleur couleur = rectangleDialog.getCouleur();
				Rectangle rectangle = new Rectangle(x, y, largeur, longeur, couleur.toString());
				
				JOptionPane.showMessageDialog(
					principale, 
					"Ajout de rectangle : \n ("+x+", "+y+", "+largeur+", "+longeur+", "+couleur+")"
				);
				
				principale.ajout(rectangle);
			}
			
			// RAZ dialog
			rectangleDialog = null;
		}

	}
	
	public void effacerTout() {
		principale.effacer();
	}

}
