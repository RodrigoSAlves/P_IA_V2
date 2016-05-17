package pursuitDomain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import controllers.AdHocController;
import controllers.Controller;
import controllers.RandomController;

public class Environment {

	public Random random;
	private final Cell[][] grid;
	private final List<Predator> predators;
	private final Prey prey;
	private final int maxIterations;
	private long seed;

	// MORE ATTRIBUTES?

	public Environment(int size, int maxIterations, float probPreyRests, int numPredators, long seed,
			Controller controller) {

		this.seed = seed;
		this.maxIterations = maxIterations;
		random = new Random();

		grid = new Cell[size][size];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}

		// THE PREY AND THE PREDATORS ARE CREATED BUT THEY ARE NOT PLACED IN ANY
		// CELL.
		// THEY ARE PLACED IN AN INITIAL CELL IN THE BEGINNING OF EACH
		// SIMULATION
		// (SEE METHOD initializeAgentsPositions).
		prey = new Prey(null, probPreyRests, seed);
		predators = initPredatorControllers(numPredators, controller);
		initializeAgentsPositions(seed);

	}

	public void setPredatorsWeights(double[] weights) {
		// TODO
	}

	private ArrayList<Predator> initPredatorControllers(int numPredators, Controller c) {
		ArrayList<Predator> list = new ArrayList<Predator>();

		for (int i = 0; i < numPredators; i++)
			list.add(new Predator(null, c));

		return list;
	}

	// THIS METHOD SHOULD BE CALLED RIGHT BEFORE EACH CALL TO METHOD simulate
	// (SEE BELOW).
	// THAT IS, IT MUST BE CALLED RIGHT BEFORE EACH SIMULATION (.
	public void initializeAgentsPositions(long seed) {
		// THE NEXT LINE MEANS THAT ALL INDIVIDUALS WILL BE EVALUATED WITH THE
		// SAME
		// ENVIRONEMNT STARTING POSITIONS.
		random.setSeed(seed);
		// reset cells
		prey.setCell(null);
		for (Predator predator : predators) {
			predator.setCell(null);
		}

		prey.setCell(getCell(random.nextInt(grid.length), random.nextInt(grid.length)));

		for (Predator predator : predators) {
			do {
				Cell cell = getCell(random.nextInt(grid.length), random.nextInt(grid.length));
				if (!cell.hasAgent()) {
					predator.setCell(cell);
				}
			} while (predator.getCell() == null);
		}
	}

	// MAKES A SIMULATION OF THE ENVIRONMENT. THE AGENTS START IN THE POSITIONS
	// WHERE THEY WHERE PLACED IN METHOD initializeAgentsPositions.
	public void simulate() {
		initializeAgentsPositions(seed);

		for (int i = 0; i < maxIterations; i++) {
			prey.act(this);
			for (int j = 0; j < predators.size(); j++) {
				predators.get(j).act(this);

			}
			if(preyIsCaught())
			{
				System.out.println("Win");
				return;
			}
			
			spellPositions();
		}

	}
	
	private boolean preyIsCaught() {
		Cell preyCell = prey.getCell();
		ArrayList<Action> surroundingAvailebleActions = getFreeSorroundingCells(preyCell);
		
		if(surroundingAvailebleActions.size() == 0)
			return true;
		
		return false;
	}

	private void spellPositions()
	{
		System.out.println("Prey: Line ->"+prey.getCell().getLine() + "Column -> " + prey.getCell().getColumn()+"\n");
		for(int i = 0; i < predators.size(); i ++)
		{
		System.out.println("Predator" + i + ": Line ->" + predators.get(i).getCell().getLine()+"Column -> " 
		+ predators.get(i).getCell().getColumn() + "\n");
		}
		
	}

	// COMPUTES THE SUM OF THE (SMALLEST) DISTANCES OF ALL THE PREDATORS TO THE
	// PREY.
	// IT TAKES INTO ACCOUNT THAT THE ENVIRONMENT IS TOROIDAL.
	public int computePredatorsPreyDistanceSum() {
		// TODO
		return 0;
	}

	public int getSize() {
		return grid.length;
	}

	public Prey getPrey() {
		return prey;
	}

	public List<Predator> getPredators() {
		return predators;
	}

	public final Cell getCell(int line, int column) {
		return grid[line][column];
	}

	// THIS METHOD *MAY* BE USED BY THE PREY IF YOU WANT TO SELECT THE RANDOM
	// PREY MOVEMENT JUST BETWEEN FREE SORROUNDING CELLS.
	public ArrayList<Action> getFreeSorroundingCells(Cell cell) {
		ArrayList<Action> freeCellsAction = new ArrayList<>();
		if (!getNorthCell(cell).hasAgent()) {
			freeCellsAction.add(Action.NORTH);
		}
		if (!getSouthCell(cell).hasAgent()) {
			freeCellsAction.add(Action.SOUTH);
		}
		if (!getEastCell(cell).hasAgent()) {
			freeCellsAction.add(Action.EAST);
		}
		if (!getWestCell(cell).hasAgent()) {
			freeCellsAction.add(Action.WEST);
		}
		return freeCellsAction;
	}

	public Color getCellColor(int line, int column) {
		return grid[line][column].getColor();
	}

	public Cell getNorthCell(Cell cell) {
		return cell.getLine() > 0 ? grid[cell.getLine() - 1][cell.getColumn()]
				: grid[grid.length - 1][cell.getColumn()];
	}

	public Cell getSouthCell(Cell cell) {
		return cell.getLine() < grid.length - 1 ? grid[cell.getLine() + 1][cell.getColumn()]
				: grid[0][cell.getColumn()];
	}

	public Cell getWestCell(Cell cell) {
		return cell.getColumn() > 0 ? grid[cell.getLine()][cell.getColumn() - 1]
				: grid[cell.getLine()][grid.length - 1];
	}

	public Cell getEastCell(Cell cell) {
		return cell.getColumn() < grid.length - 1 ? grid[cell.getLine()][cell.getColumn() + 1]
				: grid[cell.getLine()][0];
	}

	// listeners
	private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

	public synchronized void addEnvironmentListener(EnvironmentListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	public synchronized void removeEnvironmentListener(EnvironmentListener l) {
		listeners.remove(l);
	}

	public void fireUpdatedEnvironment() {
		for (EnvironmentListener listener : listeners) {
			listener.environmentUpdated();
		}
	}
}
