package com.wjs.critical;
//这两个同步是互相独立的，任何一个方法都没有因为对另一个方法的同步而被阻塞
class DualSynch{
	private Object syncObject=new Object();
	public synchronized void f()
	{
		for(int i=0;i<5;i++)
		{
			System.out.println("f()");
			Thread.yield();
		}
	}
	public void g()
	{
		synchronized(syncObject)
		{
			for(int i=0;i<5;i++)
			{
				System.out.println("g()");
				Thread.yield();
			}
		}
	}
}
public class SyncObject {
	
	public static void main(String[] args)
	{
		final DualSynch ds=new DualSynch();
		new Thread(){
			public void run()
			{
				ds.f();
			}
		}.start();
		ds.g();
	}

}
