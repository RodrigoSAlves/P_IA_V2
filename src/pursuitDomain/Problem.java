package pursuitDomain;

public abstract class Problem {

	protected static final int environmentSize = 10;
	protected static final int maxIterations = 20;
	protected static final float probPreyRests = (float) 0.1;
	protected static final int numPredators = 4;
		
	protected Environment environment;
	
	protected final int numEnvironmentRuns;
	protected int bestRun;
	protected int bestRunValue;
	
	public Problem(int numEnvironmentRuns) {
		this.numEnvironmentRuns = numEnvironmentRuns;
		bestRun=0;
		bestRunValue=maxIterations*2+environmentSize*numPredators;
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	public abstract void run();

	public int getBestRun() {
		return bestRun;
	}

    
}
