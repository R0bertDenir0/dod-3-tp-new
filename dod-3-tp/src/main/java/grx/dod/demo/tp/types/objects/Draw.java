package grx.dod.demo.tp.types.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import grx.dod.demo.tp.Couleur;

public class Draw extends JPanel {

	private static final long serialVersionUID = -4436025711972915033L;
	
	transient List<Forme> formes;
	transient Espace espace;
	
	transient BasicStroke stroke;
	
	public Draw(List<Forme> formes, Espace espace) {
		this.formes = formes;
		this.espace = espace;
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(500, 500));
		
		this.stroke = new BasicStroke(
			5,
			BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_ROUND, 
			1.0f, 
			new float[]{2f, 0f, 2f},
			2f
		);
	}
	
	public List<Forme> getFormes() {
		return formes;
	}
	
	public Espace getEspace() {
		return espace;
	}
	
	public void refresh(List<Forme> formes, Espace espace) {
		this.formes = formes;
		this.espace = espace;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		int dW = this.getWidth();
		int dH = this.getHeight();
		
		int zX = (dW / 2);
		int zY = (dH / 2);
		int mX = (zX * 2);
		int mY = (zY * 2);
		
		int unity = 25;
		
		// Afficher le fond
		super.paintComponent(g2d);
		
		// Afficher l'axe (en gris)
		g2d.setColor(Color.GRAY);
		g2d.draw(new Line2D.Double(0, zY, mX, zY));
		g2d.draw(new Line2D.Double(zX, 0, zX, mY));
		
		if (formes!=null) {
			Couleur couleur;
			
			for (Forme forme : formes) {
				couleur = Couleur.valueOf(forme.color);
				g2d.setColor(couleur.getColor());
				if (forme instanceof Cercle) {
					Cercle cercle = (Cercle)forme;
					g2d.fill(
						new Ellipse2D.Double(
							zX + (cercle.x-cercle.rayon)*unity, zY + (cercle.y-cercle.rayon)*unity,
							cercle.rayon*2*unity, cercle.rayon*2*unity
						)
					);
				} else if (forme instanceof Rectangle) {
					Rectangle rect = (Rectangle)forme;
					g2d.fill(
						new Rectangle2D.Double(
							zX + rect.x*unity, zY + rect.y*unity,
							rect.width*unity, rect.height*unity
						)
					);
				}
			}
		}
		
		// Afficher le contour
		if (espace!=null) {
			g2d.setColor(Color.BLACK);
			g2d.setStroke(stroke);
			g2d.draw(
				new Rectangle2D.Double(
					zX + espace.x*unity, zY + espace.y*unity,
					espace.width*unity, espace.height*unity
				)
			);
		}
	}

}
