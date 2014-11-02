package com.wjs.critical;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CloseResource {
	public static void main(String[] args) throws Exception
	{
		//Interrupting a blocked task by closing the underlying resource
		ExecutorService exec=Executors.newCachedThreadPool();
		ServerSocket server=new ServerSocket(8080);
		InputStream socketInput=new Socket("localhost",8080).getInputStream();
		exec.execute(new IOBlocked(socketInput));
		//exec.execute(new IOBlocked(System.in));
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("shutdown all threads");
		exec.shutdownNow();
		TimeUnit.SECONDS.sleep(10);
		System.out.println("closing "+socketInput.getClass().getName());
		socketInput.close();//release blocked thread
//		TimeUnit.SECONDS.sleep(1);
//		System.out.println("closing "+System.in.getClass().getName());
//		System.in.close();//release the blocked thread
	}

}
