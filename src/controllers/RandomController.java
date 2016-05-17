/* Rodrigo Alves
 * André Roque
 */

package controllers;


import java.util.ArrayList;
import java.util.Random;

import pursuitDomain.Action;

public class RandomController extends Controller {
	
	protected Random random;
	
	public RandomController(long seed) {
		super();
		random = new Random(seed);
	}
	
	public Action act()
	{	
		int index = random.nextInt(availableActions.size());
		return availableActions.get(index);
	}
	
}
