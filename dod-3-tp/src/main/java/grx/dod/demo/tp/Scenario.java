package grx.dod.demo.tp;

public class Scenario {

	StructureMode structure;
	
	ProcessingMode processing;
	int nbThreads;
	
	long millis;
	
	public Scenario(StructureMode structure, long millis) {
		this.structure = structure;
		this.processing = ProcessingMode.PIPELINE;
		this.millis = millis;
	}
	
	public Scenario(StructureMode structure, int nbThreads, long millis) {
		this.structure = structure;
		this.processing = ProcessingMode.PARALLEL;
		this.nbThreads = nbThreads;
		this.millis = millis;
	}

	public StructureMode getStructure() {
		return structure;
	}

	public ProcessingMode getProcessing() {
		return processing;
	}

	public int getNbThreads() {
		return nbThreads;
	}
	
	public long getMillis() {
		return millis;
	}
	
	@Override
	public String toString() {
		return "une "+structure.getLabel()+" et en mode "+processing+(processing.isThreaded() ? " ("+nbThreads+" threads"+")" : "");
	}

}
