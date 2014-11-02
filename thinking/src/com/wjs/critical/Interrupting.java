package com.wjs.critical;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SleepBlocked implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("InterruptedException");
		}
		System.out.println("Exiting SleepBlocked.run()");
	}
	
}
class IOBlocked implements Runnable{

	private InputStream in;
	public IOBlocked(InputStream is)
	{
		in=is;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("waiting for read()");
			in.read();
		} catch (Exception e) {
			// TODO: handle exception
			if(Thread.currentThread().isInterrupted())
			{
				System.out.println("Interrupted from blocked I/O");
			}else
			{
				throw new RuntimeException(e);
			}
		}
		System.out.println("Exiting IOBlocked run()");
	}
	
}
class SynchronizedBlocked implements Runnable{

	public synchronized void f()
	{
		while(true)//never release lock
		{
			Thread.yield();
		}
	}
	public SynchronizedBlocked()
	{
		new Thread()
		{
			public void run()
			{
				f();//lock acquired by this thread
			}
		}.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Trying to call f()");
		f();
		System.out.println("Exiting SynchronizedBlocked.run()");
	}
	
}
public class Interrupting {

	private static ExecutorService exec=Executors.newCachedThreadPool();
	static void test(Runnable r) throws Exception
	{
		Future<?> f=exec.submit(r);
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("Interrupting "+r.getClass().getName());
		f.cancel(true);//Interrupts if running
		System.out.println("Interrupt send to "+r.getClass().getName());
	}
	public static void main(String[] args) throws Exception
	{
		//test(new SleepBlocked());
		//test(new IOBlocked(System.in));
		test(new SynchronizedBlocked());
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Aborting with System.exit(0)");
		System.exit(0);//since last 2 interrupts failed
	}
}
