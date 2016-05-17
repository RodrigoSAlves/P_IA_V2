package pursuitDomain;

public abstract class Problem {

	protected static final int environmentSize = 10;
	protected static final int maxIterations = 20;
	protected static final float probPreyRests = (float) 0.1;
	protected static final int numPredators = 4;
	
	protected Environment environment;
	
	protected final int numEnvironmentRuns;
	
	public Problem(int numEnvironmentRuns) {
		this.numEnvironmentRuns = numEnvironmentRuns;
	}
	
	public Environment getEnvironment() {
		return environment;
	}
	
	public int getNumEvironmentSimulations(){
		return numEnvironmentRuns;
	}

	public abstract void run();

    
}
