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
		environment.simulate();
	}

}
