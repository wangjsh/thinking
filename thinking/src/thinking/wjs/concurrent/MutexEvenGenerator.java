package thinking.wjs.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {
	
	private int currentEvenValue=0;
	private Lock lock=new ReentrantLock();

	@Override
	public int next() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			++currentEvenValue;
			Thread.yield();//cause failure faster
			++currentEvenValue;
			return currentEvenValue;
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
		return 0;
	}
	public static void main(String[] args)
	{
		EvenChecker.test(new MutexEvenGenerator());
	}

}
