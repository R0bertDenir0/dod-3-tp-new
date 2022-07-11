package grx.dod.demo.ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import grx.dod.demo.tp.ProcessingMode;

public class ProcessingSelection extends JPanel {

	private static final long serialVersionUID = -9167681239181820256L;
	
	transient Principale principale;
	
	transient ButtonGroup choix;
	transient ProcessingMode mode;
	
	transient JRadioButton pipeline;
	transient JRadioButton parallel;
	
	transient int nbThreads;
	transient int nbMax;
	transient JTextField nbField;
	transient JLabel nbLabel;
	
	public ProcessingSelection(Principale principale) {
		super(new FlowLayout(FlowLayout.CENTER));
		this.setFont(Styles.LABEL_FONT);
		
		this.principale = principale;
		
		this.choix = new ButtonGroup();
		
		this.pipeline = new JRadioButton("Pipeline", true);
		this.pipeline.setFont(Styles.LABEL_FONT);
		this.add(this.pipeline);
		this.choix.add(this.pipeline);

		this.parallel = new JRadioButton("Parallélisme");
		this.parallel.setFont(Styles.LABEL_FONT);
		this.add(this.parallel);
		this.choix.add(this.parallel);
		this.nbThreads = 2;
		this.nbMax = Runtime.getRuntime().availableProcessors()*2;
		this.nbField = new JTextField(String.valueOf(this.nbThreads));
		this.nbField.setFont(Styles.FIELD_FONT);
		this.nbField.setColumns(3);
		this.nbField.addFocusListener(
			new FocusListener() {
				
				@Override
				public void focusGained(FocusEvent e) {
					// Rien
				}
				
				@Override
				public void focusLost(FocusEvent e) {
					threads();
				}
				
			}
		);
		this.add(this.nbField);
		this.nbLabel = new JLabel(" thread(s)");
		this.nbLabel.setFont(Styles.LABEL_FONT);
		this.add(this.nbLabel);
		
		this.pipeline.addActionListener(
				
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					refresh();
				}
			
			}
			
		);
		this.parallel.addActionListener(
				
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					refresh();
				}
			
			}
			
		);
	}
	
	public ProcessingMode getMode() {
		if (pipeline.isSelected()) {
			mode = ProcessingMode.PIPELINE;
		} else {
			mode = ProcessingMode.PARALLEL;
		}
		return mode;
	}
	
	public boolean isPipeline() {
		return pipeline.isSelected();
	}
	
	public boolean isParallel() {
		return parallel.isSelected();
	}
	
	public int getThreads() {
		String nbExpr = nbField.getText();
		
		if (!nbExpr.isEmpty()) {
			nbThreads = Integer.valueOf(nbExpr);
		}
		
		return nbThreads;
	}
	
	public void threads() {
		String nbExpr = nbField.getText();
		
		if (nbExpr!=null && !nbExpr.isEmpty()) {
			nbThreads = Integer.valueOf(nbExpr);
		} else {
			nbField.setText(String.valueOf(nbThreads));
		}
		
		if (parallel.isSelected()) {
			refresh();
		}
	}
	
	public void refresh() {
		if (parallel.isSelected() && (nbThreads<0 || nbMax<nbThreads)) {
			if (nbThreads<0) {
				nbThreads = 2;
			} else {
				nbThreads = nbMax;
			}
			nbField.setText(String.valueOf(nbThreads));
		}
		principale.refresh();
	}

}
