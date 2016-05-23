package pursuitDomain;

import java.awt.Color;
import java.util.ArrayList;

import controllers.Controller;
import controllers.PerceptionBasedController;

public class Predator extends Agent {
   
    private Controller controller;
    
    public Predator(Cell cell, Controller c) {
        super(cell, Color.BLUE);
        controller = c;
    }

    @Override
    public void act(Environment environment) {
    	setAvailableActions(environment.getFreeSorroundingCells(cell));
    	
    	if (controller instanceof PerceptionBasedController) {
			((PerceptionBasedController)controller).setPerception(buildPerception(environment));
		}
    	
        buildPerception(environment);
        Action a = decide();
        System.out.println(a.toString());
        execute(a, environment);
    }

    //Predator's coordinates relative to the Prey
    private Perception buildPerception(Environment environment) {

    	ArrayList<Predator> predators= (ArrayList<Predator>) environment.getPredators();
    	Position preyPos;
    	Position[] predPositions = new Position[predators.size()];
    	
    	for (int i = 0; i < predators.size(); i++) {
			predPositions[i] = new Position(predators.get(i).getCell().getLine(), predators.get(i).getCell().getColumn());
		}
		preyPos = new Position(environment.getPrey().getCell().getLine(), environment.getPrey().getCell().getColumn());

        Perception perception = new Perception(preyPos, predPositions, this);
        
    	return perception;

    }

    private Action decide() {
        return controller.act();
    }
    
    public void setController(Controller c){
    	this.controller = c;
    }
    
    public void setAvailableActions(ArrayList<Action> actions)
    {
    	controller.setAvailableActions(actions);
    }

    private void execute(Action action, Environment environment) {
        Cell nextCell;
        if (action == Action.NORTH) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST){
            nextCell = environment.getEastCell(cell);
        } else {
        	nextCell = cell;
        }

        if (!nextCell.hasAgent()) {
            setCell(nextCell);
        }
    }
}

   
   