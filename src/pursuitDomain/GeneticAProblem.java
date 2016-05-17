package pursuitDomain;

import ga.GAProblem;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GeneticAProblem extends Problem implements GAProblem<PredatorIndividual> {

    public static int NUM_PREDATOR_OUTPUTS = 2;
    
    private final int predatorsNumInputs;
    private final int predatorsNumHiddenUnits;
    private final int predatorNumOutputs;
   
    

    public GeneticAProblem(int numPredatorHiddenUnits, int numEnvironmentRuns){
    	super(numEnvironmentRuns);
    	
        this.predatorsNumInputs = 100; //THIS IS A FALSE NUMBER; PLEASE ADAPT TO YOUR CASE
        this.predatorsNumHiddenUnits = numPredatorHiddenUnits;
        this.predatorNumOutputs = NUM_PREDATOR_OUTPUTS;
        
        
        
        /*environment = new Environment(
                environmentSize,
                maxIterations,
                probPreyRests,
                numPredators);*/
    }

    @Override
    public PredatorIndividual getNewIndividual() {
        int genomeSize = 0; //CHANGE THIS
        return new PredatorIndividual(this, genomeSize /*COMPLETE?*/);
    }



    public static GeneticAProblem buildProblemFromFile(File file) throws IOException {
        java.util.Scanner f = new java.util.Scanner(file);

        List<String> lines = new LinkedList<>();

        while (f.hasNextLine()) {
            String s = f.nextLine();
            if (!s.equals("") && !s.startsWith("//")) {
                lines.add(s);
            }
        }        
        
        List<String> parametersValues = new LinkedList<>();
        for (String line : lines) {
            String[] tokens = line.split(":");
            parametersValues.add(tokens[1].trim());
        }
                
        
        int numHiddenUnits = Integer.parseInt(parametersValues.get(4));
        int numEnvironmentRuns = Integer.parseInt(parametersValues.get(5));

        return new GeneticAProblem(numHiddenUnits, numEnvironmentRuns);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of predator inputs: ");
        sb.append(predatorsNumInputs);
        sb.append("\n");
        sb.append("Number of hidden units: ");
        sb.append(predatorsNumHiddenUnits);
        sb.append("\n");
        sb.append("Number of predator outputs: ");
        sb.append(NUM_PREDATOR_OUTPUTS);
        sb.append("\n");
        sb.append("Number of environment runs: ");
        sb.append(numEnvironmentRuns);
        return sb.toString();
    }

	@Override
	public void run() {
		environment.simulate();
		
	}



}
