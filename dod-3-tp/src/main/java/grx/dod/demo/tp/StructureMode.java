package grx.dod.demo.tp;

public enum StructureMode {
	
	OBJECT	("orientée objet (typée)"),
	
	GENERIC	("générique"),
	
	SIMPLE	("simplifiée");
	
	private String item;
	private String label;
	
	private StructureMode(String item) {
		this.item = item;
		this.label = "structure "+item;
	}
	
	public String getItem() {
		return item;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return item;
	}

}
