package com.wjs.cooperation;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Sender implements Runnable{

	private Random rand=new Random(47);
	private PipedWriter out=new PipedWriter();
	public PipedWriter getPipedWriter()
	{
		return out;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true)
			{
				for(char c='A';c<='z';c++)
				{
					out.write(c);
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sender exception");
		}
	}
	
}
class Receiver implements Runnable{

	private PipedReader in;
	public Receiver(Sender sender) throws IOException
	{
		in=new PipedReader(sender.getPipedWriter());
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true)
			{
				//blocks until characters are there
				System.out.println("Read: "+(char)in.read());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("receiver exception");
		}
	}
	
}
public class PipedIO {

	public static void main(String[] args) throws Exception
	{
		Sender sender=new Sender();
		Receiver receiver=new Receiver(sender);
		ExecutorService exec=Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		TimeUnit.SECONDS.sleep(10);
		exec.shutdownNow();
	}
}
