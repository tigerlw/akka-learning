package com.camp.akka.cluster.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.routing.FromConfig;
import akka.routing.RouterConfig;
import akka.routing.ConsistentHashingGroup;
import akka.routing.ConsistentHashingRouter.ConsistentHashableEnvelope;

public class BillingServiceActor extends UntypedActor
{
	/*private ActorRef workerActor ;
	private ActorRef workerActor1 ;*/
	
	
	private ActorRef actor;
	
	@Override
	public void preStart() {
		/*workerActor = getContext().actorOf(Props.create(WorkerActor.class,"WorkerActor"),"WorkerActor");
		workerActor1 = getContext().actorOf(Props.create(WorkerActor.class,"WorkerActor1"),"WorkerActor1");*/
		List<String> routees = new ArrayList<String>();
	    String actPath = "/user/BillingServiceActor/WorkerActor";
	    boolean allowLocalRoutees = true;
	    Set<String> useRoles = new HashSet<>();
	    int totalInstances = 100;
		
		for(int i=0;i<5;i++)
		{
			getContext().actorOf(Props.create(WorkerActor.class,"WorkerActor"+i),"WorkerActor"+i);
			routees.add(actPath+i);
			
		}
		
		Iterable<String> routeesPaths = routees;
		
		//Props props = FromConfig.getInstance().props(Props.create(WorkerActor.class));
		
		//actor = getContext().actorOf(props,"WorkerRouter");
		
		actor = getContext().actorOf(
				  new ClusterRouterGroup(new ConsistentHashingGroup(routeesPaths),
				    new ClusterRouterGroupSettings(totalInstances, routeesPaths,
				      allowLocalRoutees, useRoles)).props(), "workerRouter2");
		
		
		
		
		
	}

	

	@Override
	public void onReceive(Object msg) throws Throwable {
		// TODO Auto-generated method stub
		
		actor.tell(new ConsistentHashableEnvelope(msg, msg), getSelf());
		System.out.println("receive msg");

	}

}
