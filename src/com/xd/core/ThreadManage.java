package com.xd.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManage {

	private static ThreadManage threadManage =  new ThreadManage();
	private ExecutorService threadPool;
	
	public ThreadManage(){
		threadPool = Executors.newFixedThreadPool(5);
	}
	
	public ThreadManage getInstance(){
		
		return threadManage;
	}
	
	
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}
	
	
	

}
