package thinking.wjs.concurrent;

public class SynchronizedEvenGenerator extends IntGenerator{
	
	private int currentEvenValue=0;

	@Override
	public synchronized int next() {
		// TODO Auto-generated method stub
		++currentEvenValue;
		Thread.yield();//cause failure faster ---����еĻ�
		++currentEvenValue;
		return currentEvenValue;
	}
	public static void main(String[] args)
	{
		EvenChecker.test(new SynchronizedEvenGenerator());
	}

}
