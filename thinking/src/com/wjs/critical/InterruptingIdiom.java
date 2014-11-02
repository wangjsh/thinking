package com.wjs.critical;

import java.util.concurrent.TimeUnit;

class NeedsCleanup{
	private final int id;
	public NeedsCleanup(int ident)
	{
		id=ident;
		System.out.println("NeedsCleanup "+id);
	}
	public void cleanup()
	{
		System.out.println("Cleanup "+id);
	}
}
class Blocked3 implements Runnable{

	private volatile double d=0.0;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted())
			{
				//point1
				NeedsCleanup n1=new NeedsCleanup(1);
				//start try-finally immediately after definition of n1, to guarantee proper cleanup of n1
				try {
					System.out.println("sleeping");
					TimeUnit.SECONDS.sleep(1);
					//point2
					NeedsCleanup n2=new NeedsCleanup(2);
					//guarantee proper cleanup of n2
					try {
						System.out.println("calculating");
						//a time-consuming,non-blocking operation
						for(int i=1;i<2500000;i++)
							d=d+(Math.PI+Math.E)/d;
						System.out.println("finish time-consuming operation");
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("在数学计算中");
					}finally{
						n2.cleanup();
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("在sleep中");
				}finally{
					n1.cleanup();
				}
			}
			System.out.println("Exiting via while() test");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exiting via InterruptedException");
		}
	}
	
}
public class InterruptingIdiom {

	public static void main(String[] args) throws Exception
	{
		if(args.length!=1)
		{
			System.out.println("usage: java InterruptingIdom delay-in-ms");
			System.exit(1);
		}
		Thread t=new Thread(new Blocked3());
		t.start();
		TimeUnit.MILLISECONDS.sleep(new Integer(args[0]));
		t.interrupt();
	}
}
