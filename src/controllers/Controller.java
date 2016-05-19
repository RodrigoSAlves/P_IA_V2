package controllers;

import java.util.ArrayList;

import pursuitDomain.Action;
import pursuitDomain.Agent;

public abstract class Controller {
	
	protected ArrayList<Action> availableActions;
	
	public Controller() {
		availableActions = new ArrayList<Action> ();
	}
	
	public abstract Action act();
	
	public void setAvailableActions(ArrayList<Action> actions)
	{
		availableActions = actions;
	}

}
