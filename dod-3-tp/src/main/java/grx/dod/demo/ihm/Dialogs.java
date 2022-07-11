package grx.dod.demo.ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import grx.dod.demo.ihm.Fields.CouleurField;
import grx.dod.demo.ihm.Fields.NombreField;
import grx.dod.demo.ihm.Fields.Field;
import grx.dod.demo.tp.Couleur;

public abstract class Dialogs {

	private Dialogs() {}
	
	public abstract static class Ajout extends JDialog {

		private static final long serialVersionUID = 3835993938704576566L;

		transient EditionPanel edition;
		
		protected Ajout(Principale parent, String titre, Field<?> ... valeurs) {
			super(parent);
			this.setTitle(titre);
			this.setLayout(new BorderLayout());
			
			this.edition = new EditionPanel(this, valeurs);
			this.add(this.edition);
			
			this.setModal(true);
			this.pack();
			this.setLocationRelativeTo(null);
		}
		
		@Override
		public void setVisible(boolean visible) {
			boolean open = this.isVisible();
			
			if (!open) {
				edition.reset();
			}
			
			super.setVisible(visible);
		}
		
		public boolean isOk() {
			if (edition!=null) {
				return edition.isOk();
			} else {
				return false;
			}
		}
		
		public Object get(String name) {
			if (edition!=null) {
				return edition.get(name);
			} else {
				return null;
			}
		}
		
		public int getInt(String name) {
			Object value = get(name);
			
			if (value!=null) {
				String str = String.valueOf(value);
				
				if (str!=null && !str.isEmpty()) {
					return Integer.valueOf(str);
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		}
		
		public Couleur getCouleur(String name) {
			Object value = get(name);
			
			if (value!=null) {
				if (value instanceof Couleur) {
					return (Couleur)value;
				} else {
					String str = String.valueOf(value);
					
					return Couleur.valueOf(str);
				}
			} else {
				return Couleur.blue;
			}
		}
		
	}
	
	public static class EditionPanel extends JPanel {

		private static final long serialVersionUID = 3059724018742411169L;

		transient Map<String, Field<?>> valeurs;
		
		transient JDialog dialog;
		transient boolean valide;
		
		transient JPanel commandes;
		transient JButton ok;
		transient JButton annuler;
		
		public EditionPanel(JDialog dialog, Field<?> ... valeurs) {
			super(new GridLayout(valeurs.length+1, 0, 10, 10));
			
			this.dialog = dialog;
			
			this.setBorder(new EmptyBorder(10, 10, 10, 10));
			
			this.valeurs = new HashMap<>();
			if (valeurs!=null) {
				for (Field<?> valeur : valeurs) {
					this.valeurs.put(valeur.getName(), valeur);
					this.add(valeur);
				}
			}
			
			this.ok = new JButton("OK");
			this.ok.setFont(Styles.LABEL_FONT);
			this.ok.addActionListener(new Ok());
			
			this.annuler = new JButton("Annuler");
			this.annuler.setFont(Styles.LABEL_FONT);
			this.annuler.addActionListener(new Annuler());
			
			this.commandes = new JPanel(new FlowLayout(FlowLayout.CENTER));
			this.commandes.add(this.ok);
			this.commandes.add(this.annuler);
			this.add(this.commandes);
		}
		
		public Object get(String name) {
			Field<?> valeur = valeurs.get(name);
			
			if (valeur!=null) {
				return valeur.getValue();
			} else {
				return null;
			}
		}
		
		public boolean isOk() {
			return valide;
		}
		
		public void reset() {
			valide = false;
		}
		
		public class Ok implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (dialog!=null) {
					valide = true;
					dialog.setVisible(false);
				}
			}
			
		}
		
		public class Annuler implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (dialog!=null) {
					valide = false;
					dialog.setVisible(false);
				}
			}
			
		}
		
	}

	public static class AjoutCercle extends Ajout {

		private static final long serialVersionUID = -3931204862232441122L;

		public AjoutCercle(Principale parent) {
			super(
				parent, "Edition de cercle", 
				new NombreField("center.x", "Centre - X", TP.randomX()),
				new NombreField("center.y", "Centre - Y", TP.randomY()),
				new NombreField("radius", "Rayon", TP.randomRadius()),
				new CouleurField("color", "Couleur", TP.randomColor())
			);
		}
		
		public int getCentreX() {
			return getInt("center.x");
		}
		
		public int getCentreY() {
			return getInt("center.y");
		}
		
		public int getRayon() {
			return getInt("radius");
		}
		
		public Couleur getCouleur() {
			return getCouleur("color");
		}

	}

	public static class AjoutRectangle extends Ajout {

		private static final long serialVersionUID = -3931204862232441122L;

		public AjoutRectangle(Principale parent) {
			super(
				parent, "Edition de rectangle", 
				new NombreField("point.x", "Position - X", TP.randomX()),
				new NombreField("point.y", "Position - Y", TP.randomY()),
				new NombreField("size.width", "Largeur", TP.randomWidth()),
				new NombreField("size.height", "Heuteur", TP.randomHeight()),
				new CouleurField("color", "Couleur", TP.randomColor())
			);
		}
		
		public int getPositionX() {
			return getInt("point.x");
		}
		
		public int getPositionY() {
			return getInt("point.y");
		}
		
		public int getLargeur() {
			return getInt("size.width");
		}
		
		public int getLongeur() {
			return getInt("size.height");
		}
		
		public Couleur getCouleur() {
			return getCouleur("color");
		}

	}
}
