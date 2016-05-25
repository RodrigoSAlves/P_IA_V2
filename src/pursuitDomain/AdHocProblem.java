package pursuitDomain;

import controllers.AdHocController;
import controllers.RandomController;

public class AdHocProblem extends Problem{

	public AdHocProblem(long seed, int numEnvironmentRuns) {
		super(numEnvironmentRuns);
		AdHocController controller = new AdHocController();
		this.environment = new Environment(environmentSize, maxIterations, probPreyRests, 
				numPredators, seed, controller);
		controller.setEnvironment(environment);
		
	}

	@Override
	public void run() {
		for(int i = 0; i < numEnvironmentRuns; i++){
			environment.run();
			if(environment.computeFitness() < bestRunValue) {
				bestRunValue=environment.computeFitness();
				bestRun = i+1;
			}
		}
	}

}
