package controllers;

import pursuitDomain.Action;
import pursuitDomain.Agent;
import pursuitDomain.Perception;

public abstract class PerceptionBasedController extends Controller{
	
	protected Perception perception;
	
	@Override
	public abstract Action act();	
	
	public void  setPerception(Perception p)
	{
		this.perception = p;
	}

}
