package pursuitDomain;

import controllers.RandomController;

public class RandomProblem extends Problem{

	
	public RandomProblem(long seed, int numEnvironmentRuns) {
		super(numEnvironmentRuns);
		
		
		this.environment = new Environment(environmentSize, maxIterations, probPreyRests, 
				numPredators, seed, new RandomController(seed));

	}

	@Override
	public void run() {
		for(int i = 0; i < numEnvironmentRuns; i++){
			System.out.println(i);
			environment.simulate();
		}
	}
}
