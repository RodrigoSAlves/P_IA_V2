package controllers;

import pursuitDomain.Action;
import pursuitDomain.Agent;
import pursuitDomain.Environment;
import pursuitDomain.Perception;

public class AdHocController extends PerceptionBasedController{
	
	
	private Environment environment;
	
	
	public AdHocController(Environment e) {
		environment = e;
	}
	
	@Override
	public Action act() {
		Action action = null;
		return action;
	}

}
