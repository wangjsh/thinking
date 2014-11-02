package thinking.wjs.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicEvenGenerator extends IntGenerator{

	private AtomicInteger currentEvenValue=new AtomicInteger(10);
	@Override
	public int next() {
		// TODO Auto-generated method stub
		return currentEvenValue.addAndGet(2);
	}
	public static void main(String[] args)
	{
		EvenChecker.test(new AtomicEvenGenerator());
	}

}
