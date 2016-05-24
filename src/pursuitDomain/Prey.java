package pursuitDomain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import controllers.Controller;
import controllers.RandomController;

public class Prey extends Agent {

	final private double restProbability;
	private RandomController controller;
	private Random probMoveRandom;
	private Action action;

	public Prey(Cell cell, double restProbability, long seed) {
		super(cell, Color.RED);
		this.restProbability = restProbability;
		this.controller = new RandomController(seed);
		probMoveRandom = new Random();
	}

	@Override
	public void act(Environment environment) {
		if (probMoveRandom.nextInt(100) > 10) {
			setAvailableActions(environment.getFreeSorroundingActions(cell));
			action = controller.act();

			execute(action, environment);
		}
	}
	
	public Action getAction(){
		return action;
	}

	public Controller getController() {
		return controller;
	}

	public void setAvailableActions(ArrayList<Action> actions) {
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
		} else {
			nextCell = environment.getEastCell(cell);
		}

		if (!nextCell.hasAgent()) {
			setCell(nextCell);
		}
	}

}
