package com.wjs.cooperation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

class LiftOffRunner implements Runnable{

	private BlockingQueue<LiftOff> rockets;
	public LiftOffRunner(BlockingQueue<LiftOff> queue)
	{
		rockets=queue;
	}
	public void add(LiftOff lo)
	{
		try {
			rockets.put(lo);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Interrupt during put()");
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.interrupted())
			{
				LiftOff rocket=rockets.take();
				rocket.run();//use this thread
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("walking from take()");
		}
		System.out.println("exiting LiftOffRunner");
	}	
	
	
}
public class TestBlockingQueues {

	static void getkey()
	{
		try {
			//compensate for window/linux difference in the length of the result produced by the enter key
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	static void getkey(String message)
	{
		System.out.println("message");
		getkey();
	}
	static void test(String msg,BlockingQueue<LiftOff> queue)
	{
		System.out.println(msg);
		LiftOffRunner runner=new LiftOffRunner(queue);
		Thread t=new Thread(runner);
		t.start();
		for(int i=0;i<5;i++)
			runner.add(new LiftOff(5));
		getkey("press 'enter' ("+msg+")");
		t.interrupt();
		System.out.println("finished"+msg+"test");
	}
	public static void main(String[] args)
	{
		test("LinkedBlockingQueue",new LinkedBlockingQueue<LiftOff>());//unlimited size
//		test("ArrayBlockingQueue",new ArrayBlockingQueue<LiftOff>(3));//fixed size
//		test("synchronousQueue",new SynchronousQueue<LiftOff>());//size of 1
	}
}
