package com.wjs.cooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Car{
	private boolean waxOn=false;
	public synchronized void waxed()
	{
		waxOn=true;//ready to buff
		notifyAll();
	}
	public synchronized void buffed()
	{
		waxOn=false;//ready for another coat of wax
		notifyAll();
	}
	public synchronized void waitForWaxing() throws InterruptedException
	{
		while(waxOn==false)
		{
			wait();
		}
	}
	public synchronized void waitForBuffing() throws InterruptedException
	{
		while(waxOn==true)
		{
			wait();
		}
	}
}
class WaxOn implements Runnable{

	private Car car ;
	public WaxOn(Car c)
	{
		car=c;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted())
			{
				System.out.println("Wax On");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("exitint via interrupt");
		}
		System.out.println("ending wax on task");
	}
	
	
}
class WaxOff implements Runnable{

	private Car car;
	public WaxOff(Car c)
	{
		car=c;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted())
			{
				car.waitForWaxing();
				System.out.println("wax off");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("exiting via interrupt");
		}
		System.out.println("ending wax off task");
	}
	
}
public class WaxOMatic {

	public static void main(String[] args) throws Exception
	{
		Car car=new Car();
		ExecutorService exec=Executors.newCachedThreadPool();
		exec.execute(new WaxOff(car));
		exec.execute(new WaxOn(car));
		TimeUnit.SECONDS.sleep(5);//run for a while
		exec.shutdownNow();//interrupt all tasks
	}
}
