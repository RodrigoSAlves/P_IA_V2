package pursuitDomain;

public class TestCase {
	
	private static final TestCase instance = new TestCase();
	
	public static final int RANDOM_CONTROLLER = 1;
	public static final int ADHOC_CONTROLLER = 2;
	public static final int HOMOGEN_CONTROLLER = 3;
	public static final int HETEROGEN_CONTROLLER = 4;
	private int current;
	
	public TestCase() {
		
	}
	public int getCurrent(){
		return current;
	}
	
	public void setCurrent(int t){
		current = t;
	}
	public static TestCase getInstace(){
		return instance;
	}
}
