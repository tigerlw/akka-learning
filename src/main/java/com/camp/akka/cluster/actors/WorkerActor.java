package com.camp.akka.cluster.actors;

import akka.actor.UntypedActor;

public class WorkerActor extends UntypedActor
{
	private String workerName;
	
	public WorkerActor(String workerName)
	{
		this.workerName = workerName;
	}
	
	public WorkerActor()
	{
		
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("worker receive msg:"+msg+";worker:"+workerName);
		
		Thread.sleep(30*1000);
		
		System.out.println("worker msg:"+msg+";worker:"+workerName);
	}

}
