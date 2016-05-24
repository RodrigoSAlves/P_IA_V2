package controllers;

import pursuitDomain.Cell;

import java.util.ArrayList;
import java.util.List;

import com.sun.prism.image.CompoundTexture;

import pursuitDomain.Action;
import pursuitDomain.Agent;
import pursuitDomain.Environment;
import pursuitDomain.Perception;
import pursuitDomain.Predator;

public class AdHocController extends PerceptionBasedController{
		
	private Environment environment;
	
	public void setEnvironment(Environment environment)
	{
		this.environment = environment;
	}
	
	@Override
	public Action act() {	

		if(environment.predatorIsAdjacentToPrey(perception.getPredator()))
		{
			return Action.WAIT;
		}
		else{
		
		Cell predatorCell = perception.getPredator().getCell();
		//If no-one is closer, go to that cell;
		
		//See nearest cell (need array of cells) OK
		ArrayList<Cell> adjacentCells = getCellsAdjacentToPreyOrderedByDistance(perception.getPredator());
		//current index
		int cIndex = 0;
		
		//See if anyone is closer(need specific distance from a cell)
		List<Predator> predators = environment.getPredators();
		for(int i = 0; i < predators.size(); i++)
		{

			//If anyone is closer, choose another cell from the array
			if(adjacentCells.get(cIndex).hasAgent() ||
				environment.computeDistanceBetweenCells(perception.getPredator().getCell(), 
				adjacentCells.get(cIndex)) > 
				environment.computeDistanceBetweenCells(predators.get(i).getCell(), 
				adjacentCells.get(cIndex))) 
			{
				if(cIndex < adjacentCells.size() - 1)
				{
					cIndex ++;
					i = 0;
				}
			}
		}
		
		double distance;
		double previous = 6;
		int index = 0;
		
		Cell nextCell;
		for(int i = 0; i < availableActions.size(); i++)
		{
			nextCell = environment.getNextCell(availableActions.get(i),predatorCell);
			distance = environment.computeDistanceBetweenCells(nextCell, adjacentCells.get(cIndex));
			
			if(distance < previous)
			{
				index = i;
 				previous = distance;
			}
		}
		
		return availableActions.get(index);
		//set course for the selected cell
		}

	}
	
	public double[] getDistancesFromMultipleCellsToPrey(ArrayList<Cell> cells, Cell cell)
	{
		double[] distances = new double[cells.size()];

		for(int i = 0; i < cells.size(); i++)
		{
			distances[i] = environment.computeDistanceBetweenCells(cell, cells.get(i));
		}
		
		return distances;
	}
	
	public ArrayList<Cell> getCellsAdjacentToPreyOrderedByDistance(Predator predator) {
		//Do not change orders
		
		ArrayList<Cell> cells = environment.getFreeCellsAdjacentToPrey();
		double[] distances = getDistancesFromMultipleCellsToPrey(cells, predator.getCell());
		Cell aux;
		
		for (int i = 0; i < distances.length-1; i++) {
			for (int j = i+1; j < distances.length; j++)
			if (distances[j] <= distances[i]) {
				aux = cells.get(j);
				cells.set(j, cells.get(i));
				cells.set(i, aux);
			}
		}
				
		return cells;
	}
	


}
