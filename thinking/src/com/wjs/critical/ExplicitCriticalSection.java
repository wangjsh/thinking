package com.wjs.critical;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//synchronized the entire method
class ExplicitPairManager1 extends PairManager{

	private Lock lock=new ReentrantLock();
	@Override
	public synchronized void increment() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			p.IncrementX();
			p.IncrementY();
			store(getPair());
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
		
	}
	
}
//use a critical section
class ExplicitPairManger2 extends PairManager{

	private Lock lock=new ReentrantLock();
	@Override
	public void increment() {
		// TODO Auto-generated method stub
		Pair temp = null;
		lock.lock();
		try {
			p.IncrementX();
			p.IncrementY();
			temp=getPair();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
		store(temp);
	}
	
}
public class ExplicitCriticalSection {
	
	public static void main(String[] args)
	{
		PairManager pman1=new ExplicitPairManager1(),
				pman2=new ExplicitPairManger2();
		CriticalSection.testApproaches(pman1, pman2);
	}

}
