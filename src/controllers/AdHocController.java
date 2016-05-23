package controllers;

import pursuitDomain.Cell;

import java.util.ArrayList;
import java.util.List;

import pursuitDomain.Action;
import pursuitDomain.Agent;
import pursuitDomain.Environment;
import pursuitDomain.Perception;
import pursuitDomain.Predator;

public class AdHocController extends PerceptionBasedController{
	
	
	private Environment environment;
	
	
	public AdHocController(Environment e) {
		environment = e;
	}
	
	@Override
	public Action act() {
		Action action = null;
		//comment
		//See which cell is nearest
		ArrayList<Cell> thisNearest = environment.getNearestCellAdjacentToPrey(perception.getPredator());
		
		List<Predator> predators = environment.getPredators();
		
		
		for(Predator p:predators)
		{
			if(thisNearest == environment.getNearestCellAdjacentToPrey(p))
			{
				
			}
		}
		
		//See if anyone is closer to that cell
		//IF NOT return action to be closer to the cell
		//IF SO get next nearest cell
		//See if anyoe is closer to that cell
		
		
		
		
		
		
		
		return action;
	}

}
