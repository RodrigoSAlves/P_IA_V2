package ga.selectionMethods;

import ga.Individual;
import ga.Population;
import ga.GAProblem;

public abstract class SelectionMethod <I extends Individual, P extends GAProblem<I>>{

    protected int popSize;
    
    public SelectionMethod(int popSize){
        this.popSize = popSize;
    }

    public abstract Population<I, P> run(Population<I, P> original);
}