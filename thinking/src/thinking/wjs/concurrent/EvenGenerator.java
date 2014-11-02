package thinking.wjs.concurrent;

public class EvenGenerator extends IntGenerator {
	
	private int currentEvenValue=0;

	@Override
	public int next() {
		// TODO Auto-generated method stub
		++currentEvenValue;//danger point here
		++currentEvenValue;
		return currentEvenValue;
	}
	public static void main(String[] args)
	{
		EvenChecker.test(new EvenGenerator());
	}

}
