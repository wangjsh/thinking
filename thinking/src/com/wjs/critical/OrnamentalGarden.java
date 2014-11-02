package com.wjs.critical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Count{
	private int count=0;
	//private Random rand=new Random(47);
	public synchronized int increment()
	{
		int temp=count;
//		if(rand.nextBoolean())//yield half the time
//			Thread.yield();
		return (count=++temp);
	}
	public synchronized int value()
	{
		return count;
	}
}
class Entrance implements Runnable{

	private static Count count=new Count();
	private static List<Entrance> entrances=new ArrayList<Entrance>();
	private int number=0;
	//doesn't need synchronization to read
	private  int id=0;
	private static volatile boolean cancled=false;
	//atomic operation on a volatile field
	public static void cancel()
	{
		cancled=true;
	}
	public Entrance(int id)
	{
		this.id=id;
		// keep this task in a list . also prevents garbage collection of dead tasks
		entrances.add(this);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!cancled)
		{
			synchronized(this)
			{
				++number;
			}
			System.out.println(this+" total: "+count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Stoping "+this);
	}
	public synchronized int getValue()
	{
		return number;
	}
	public String toString()
	{
		return "Entrance "+id+" : "+getValue();
	}
	public static int getTotalCount()
	{
		return count.value();
	}
	public static int sumEntrances()
	{
		int sum =0;
		for(Entrance entrance:entrances)
			sum+=entrance.getValue();
		return sum;
	}
	
}
public class OrnamentalGarden {

	public static void main(String[] args) throws InterruptedException
	{
		ExecutorService exec=Executors.newCachedThreadPool();
		for(int i=0;i<5;i++)
		{
			exec.execute(new Entrance(i));
		}
		//run for a while ,then stop and collect the data
		TimeUnit.SECONDS.sleep(3);
		Entrance.cancel();
		exec.shutdown();
		if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
		System.out.println("some tasks were not terminated!");
		System.out.println("total: "+Entrance.getTotalCount());
		System.out.println("sum of entrances: "+Entrance.sumEntrances());
	}
}
