package grx.dod.demo.ihm;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import grx.dod.demo.tp.Couleur;

public abstract class Fields {

	private Fields() {}
	
	public abstract static class Field<T> extends JPanel {

		private static final long serialVersionUID = 7743839994426027826L;
		
		String name;
		
		transient JLabel text;
		
		protected Field(String name, String text, T value) {
			super(new BorderLayout(1, 2));
			
			this.name = name;
			this.setName(this.name);
			
			this.text = new JLabel(text);
			this.text.setFont(Styles.LABEL_FONT);
			this.text.setAlignmentX(JLabel.LEFT);
			this.text.setAlignmentY(JLabel.CENTER);
			this.add(this.text, BorderLayout.CENTER);
			
			this.add(this.getField(value), BorderLayout.EAST);
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public abstract Component getField(T value);
		
		public abstract T getValue();

	}
	
	public static class NombreField extends Field<String> {

		private static final long serialVersionUID = 5852382081168176375L;

		transient JTextField field;
		
		transient String value;
		
		public NombreField(String name, String text, Number value) {
			this(name, text, String.valueOf(value));
		}
		
		public NombreField(String name, String text, String value) {
			super(name, text, value);
		}

		public JTextField getField(String value) {
			if (this.field==null) {
				this.field = new JTextField(3);
				this.field.setFont(Styles.FIELD_FONT);
			}
			if (value!=null) {
				this.value = value.toString();
				this.field.setText(this.value);
			}
			return this.field;
		}
		
		@Override
		public String getValue() {
			if (field!=null) {
				return field.getText();
			} else {
				return "";
			}
		}

	}
	
	public static class CouleurField extends Field<Couleur> {

		private static final long serialVersionUID = -7569970356637321193L;

		transient CouleurSelection combo;
		
		protected CouleurField(String name, String text, Couleur value) {
			super(name, text, value);
		}

		@Override
		public CouleurSelection getField(Couleur value) {
			if (combo==null) {
				combo = new CouleurSelection(value);
			}
			return combo;
		}

		@Override
		public Couleur getValue() {
			if (combo!=null) {
				return combo.getValue();
			} else {
				return Couleur.blue;
			}
		}

	}

}
