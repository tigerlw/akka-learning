package com.camp.akka.cluster.actors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.routing.ConsistentHashingGroup;
import akka.routing.ConsistentHashingRouter.ConsistentHashableEnvelope;

public class EventBusActor extends UntypedActor
{
	private ActorRef actor;
	
	@Override
	public void preStart() {
		List<String> routees = new ArrayList<String>();
		String actPath = "/user/BillingServiceActor/WorkerActor*";
		routees.add(actPath);
	    boolean allowLocalRoutees = true;
	    Set<String> useRoles = new HashSet<>();
	    int totalInstances = 100;
	    
	    Iterable<String> routeesPaths = routees;
	    
	    actor = getContext().actorOf(
				  new ClusterRouterGroup(new ConsistentHashingGroup(routeesPaths),
				    new ClusterRouterGroupSettings(totalInstances, routeesPaths,
				      allowLocalRoutees, useRoles)).props(), "workerRouterbus");
	    
	    
		
	
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		// TODO Auto-generated method stub
		actor.tell(new ConsistentHashableEnvelope(msg, msg), getSelf());
		System.out.println("bus msg");
	}

}
