package pursuitDomain;

public class Perception {
	
	private Predator predator;
	private Position preyPos;
	private Position[] predPositions;
	
	public Perception(Position preyPos, Position[] predPositions, Predator p) {
		this.preyPos = preyPos;
		this.predPositions = predPositions;
		this.predator = p;
		
	}
	
	public Predator getPredator() {
		return predator;
	}
	

}
