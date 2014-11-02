package com.wjs.critical;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockedMutex{
	
	private Lock lock=new ReentrantLock();
	public BlockedMutex()
	{
		//acquire it right away to demonstrate interruption of a task blocked on a ReeentrantLock
		lock.lock();
	}
	public void f()
	{
		try {
			//this will never be available to a second task
			lock.lockInterruptibly();//special call
			System.out.println("lock acquired in f()");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Interruptted from lock acquisition in f()");
		}
	}
}
class Blocked2 implements Runnable{

	BlockedMutex blocked=new BlockedMutex();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("waiting for f() in BlockedMutex");
		blocked.f();
		System.out.println("broken out of blocked call");
	}
	
}
public class Interrupting2 {

	public static void main(String[] args) throws Exception
	{
		Thread t=new Thread(new Blocked2());
		t.start();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Issuing t.interrupt()");
		t.interrupt();
	}
}
