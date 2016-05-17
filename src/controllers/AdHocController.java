package controllers;

import pursuitDomain.Action;
import pursuitDomain.Environment;
import pursuitDomain.Perception;

public class AdHocController extends PerceptionBasedController{
	
	
	private Environment environment;
	
	public AdHocController(Environment e) {
		environment = e;
	}
	
	@Override
	public Action act() {
		Action action = super.act();
		return action;
	}
	
	public void  setPerception(Perception p)
	{
		this.perception = p;
	}

	@Override
	public Action perceptionBasedAct() {
		Action action = null;
		
		return action;
	}
}
