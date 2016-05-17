package controllers;

import pursuitDomain.Action;
import pursuitDomain.Perception;

public abstract class PerceptionBasedController extends Controller{
	
	protected Perception perception;
	
	@Override
	public Action act() {
		Action action = perceptionBasedAct();
		return action;
	}
	
	public abstract Action perceptionBasedAct();

}
