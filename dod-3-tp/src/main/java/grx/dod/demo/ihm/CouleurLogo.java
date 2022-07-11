package grx.dod.demo.ihm;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import grx.dod.demo.tp.Couleur;

public class CouleurLogo extends JLabel {

	private static final long serialVersionUID = -6045601220623576689L;

	Couleur color;
	
	public CouleurLogo(Couleur color, int size) {
		super();
		this.color = color;
		this.setOpaque(true);
		this.setBackground(color.getColor());
		this.setPreferredSize(new Dimension(size, size));
		this.setBorder(new LineBorder(Color.BLACK, 5));
	}

}
