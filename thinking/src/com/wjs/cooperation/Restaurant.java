package com.wjs.cooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Meal{
	private final int orderNum;
	public Meal(int orderNum)
	{
		this.orderNum=orderNum;
	}
	public String toString()
	{
		return "Meal "+orderNum;
	}
}
class WaitPerson implements Runnable{

	private Restaurant restaurant;
	public WaitPerson(Restaurant r)
	{
		restaurant=r;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted())
			{
				synchronized(this)
				{
					while(restaurant.meal==null)
						wait();//for the chef to produce a meal
				}
				System.out.println("waitperson got "+restaurant.meal);
				synchronized(restaurant.chef)
				{
					restaurant.meal=null;
					restaurant.chef.notifyAll();//ready for another
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("wait person interrupted");
		}
	}
	
}
class Chef implements Runnable{

	private Restaurant restaurant;
	private int count=0;
	public Chef(Restaurant r)
	{
		restaurant=r;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted())
			{
				synchronized(this)
				{
					while(restaurant.meal!=null)
						wait();//for the meal to be taken
				}
				if(++count==10)
				{
					System.out.println("out of food,closing");
					restaurant.exec.shutdownNow();
				}
				System.out.print("order up	");
				synchronized(restaurant.waitPerson)
				{
					restaurant.meal=new Meal(count);
					restaurant.waitPerson.notifyAll();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("chef interrupted");
		}
	}
	
}
public class Restaurant {

	Meal meal;
	ExecutorService exec=Executors.newCachedThreadPool();
	WaitPerson waitPerson=new WaitPerson(this);
	Chef chef =new Chef(this);
	public Restaurant()
	{
		exec.execute(chef);
		exec.execute(waitPerson);
	}
	public static void main(String[] args)
	{
		new Restaurant();
	}

}
