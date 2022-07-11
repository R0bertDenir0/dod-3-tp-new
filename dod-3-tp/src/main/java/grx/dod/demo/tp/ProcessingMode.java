package grx.dod.demo.tp;

public enum ProcessingMode {

	PIPELINE ("pipeline"),
	
	PARALLEL ("parallèle") {
		
		@Override
		public boolean isThreaded() {
			return true;
		}
		
	};
	
	private String label;
	
	private ProcessingMode(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean isThreaded() {
		return false;
	}
	
	@Override
	public String toString() {
		return label;
	}

}
