package pursuitDomain;

public class Perception {

	private Position preyPos;
	private Position[] predPositions;
	
	public Perception(Position preyPos, Position[] predPositions) {
		this.preyPos = preyPos;
		this.predPositions = predPositions;
	}

}
