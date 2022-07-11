package grx.dod.demo.ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grx.dod.demo.tp.StructureMode;

public class StructureSelection extends JPanel {

	private static final long serialVersionUID = 5273396991250758189L;
	
	transient Principale principale;
	
	transient JLabel label;
	transient JComboBox<StructureMode> structures;
	
	public StructureSelection(Principale principale) {
		super(new FlowLayout(FlowLayout.CENTER));
		
		this.principale = principale;
		
		this.label = new JLabel("Structure");
		this.label.setFont(Styles.LABEL_FONT);
		this.label.setAlignmentX(JLabel.CENTER);
		this.label.setAlignmentY(JLabel.CENTER);
		this.add(this.label);
		
		this.structures = new JComboBox<>(StructureMode.values());
		this.structures.setFont(Styles.LABEL_FONT);
		this.structures.setSelectedItem(StructureMode.OBJECT);
		this.structures.addActionListener(
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					refresh();
				}
			
			}
		
		);
		this.add(this.structures);
	}
	
	public StructureMode getMode() {
		return (StructureMode)structures.getSelectedItem();
	}
	
	public void refresh() {
		principale.refresh();
	}

}
