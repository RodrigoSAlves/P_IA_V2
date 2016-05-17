package controllers;

import pursuitDomain.Action;
import pursuitDomain.Environment;
import pursuitDomain.Perception;

public class AdHocController extends Controller{
	
	private Perception perception;
	private Environment environment;
	
	public AdHocController(Environment e) {
		environment = e;
	}
	
	@Override
	public Action act() {
		
		return null;
	}
	
	public void  setPerception(Perception p)
	{
		this.perception = p;
	}
}
