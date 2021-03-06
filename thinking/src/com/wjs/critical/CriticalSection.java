package com.wjs.critical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Pair{
	//not thread-safe
	private int x,y;
	public Pair(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	public Pair()
	{
		this(0,0);
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void IncrementX()
	{
		x++;
	}
	public void IncrementY()
	{
		y++;
	}
	public String toString()
	{
		return "x: "+x+",y: "+y;
	}
	public class PairValuesNotEqualException extends RuntimeException{
		
		public PairValuesNotEqualException()
		{
			super("Pair values not equal:"+Pair.this);
		}
	}
	public void checkState()
	{
		if(x!=y)
		{
			throw new PairValuesNotEqualException();
		}
	}
}
// protect a Pair inside a thread-safe class:
abstract class PairManager{
	AtomicInteger checkCounter=new AtomicInteger(0);
	protected Pair p=new Pair();
	private java.util.List<Pair> storage=Collections.synchronizedList(new ArrayList<Pair>());
	public synchronized Pair getPair()
	{
		//Make a copy to keep the original safe
		return new Pair(p.getX(),p.getY());
	}
	//assume this is a time consuming operation
	protected void store(Pair p)
	{
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public abstract void increment();
}
//synchronize the entire method:
class PairManager1 extends PairManager{

	@Override
	public synchronized void increment() {
		// TODO Auto-generated method stub
		p.IncrementX();
		p.IncrementY();
		store(getPair());
	}
	
}
class PairManager2 extends PairManager{

	@Override
	public void increment() {
		// TODO Auto-generated method stub
		Pair temp;
		synchronized(this)
		{
			p.IncrementX();
			p.IncrementY();
			temp=getPair();
		}
		store(temp);
	}
	
}
class PairManipulator implements Runnable{

	private PairManager pm;
    public PairManipulator(PairManager pm) {
		// TODO Auto-generated constructor stub
    	this.pm=pm;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			pm.increment();
		}
	}
	public String toString()
	{
		return "Pair: "+pm.getPair()+" checkCounter= "+pm.checkCounter.get();
	}
	
}
class PairChecker implements Runnable{

	private PairManager pm;
	public PairChecker(PairManager pm)
	{
		this.pm=pm;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			pm.checkCounter.incrementAndGet();
			pm.getPair().checkState();
		}
	}
	
}
public class CriticalSection {
	//test the two different approaches
	static void testApproaches(PairManager pman1,PairManager pman2)
	{
		ExecutorService exec=Executors.newCachedThreadPool();
		PairManipulator pm1=new PairManipulator(pman1),
				pm2=new PairManipulator(pman2);
		PairChecker pcheck1=new PairChecker(pman1),
				pcheck2=new PairChecker(pman2);
		exec.execute(pm1);
		exec.execute(pm2);
		exec.execute(pcheck1);
		exec.execute(pcheck2);
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Sleep interrupted");
		}
		System.out.println("pm1: "+pm1+"\npm2: "+pm2);
		System.exit(0);
	}
	public static void main(String[] args)
	{
		PairManager pman1=new PairManager1(),
				pman2=new PairManager2();
		testApproaches(pman1,pman2);
	}

}
