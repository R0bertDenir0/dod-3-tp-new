package grx.dod.demo.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import grx.dod.demo.tp.Couleur;

public class CouleurSelection extends JComboBox<Couleur> {

	private static final long serialVersionUID = -3058962063564468702L;
	
	public CouleurSelection(Couleur couleur) {
		super(Couleur.values());
		this.setFont(Styles.LABEL_FONT);
		this.setRenderer(new Ligne());
		if (couleur!=null) {
			this.setSelectedItem(couleur);
		}
	}
	
	public Couleur getValue() {
		return (Couleur)this.getSelectedItem();
	}
	
	public static class Ligne implements ListCellRenderer<Couleur> {
		
		Ligne() {}
		
		@Override
		public Component getListCellRendererComponent(
		JList<? extends Couleur> list, Couleur value, int index,
		boolean isSelected, boolean cellHasFocus) {
			JPanel element = new JPanel(new BorderLayout());
			CouleurLogo couleur = new CouleurLogo(value, 25);
			JLabel label = new JLabel(value.toString());
			
			label.setFont(Styles.LABEL_FONT);
			
			if (isSelected) {
				element.setBackground(Color.blue);
				label.setForeground(Color.white);
			} else {
				// No color
				element.setBackground(null);
				label.setForeground(Color.black);
			}
			
			element.add(couleur, BorderLayout.EAST);
			element.add(label, BorderLayout.CENTER);
			
			return element;
		}
		
	}

}
