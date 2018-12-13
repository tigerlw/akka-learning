package com.camp.akka.cluster.actors;

import akka.actor.DeadLetter;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.UnreachableMember;

public class ClusterStateMonitorActor extends UntypedActor
{
	private Cluster cluster;

	@Override
	public void preStart() {

		cluster = Cluster.get(getContext().system());

		cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), UnreachableMember.class, MemberEvent.class);
		
		getContext().system().eventStream().subscribe(getSelf(), DeadLetter.class);
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		// TODO Auto-generated method stub

		String className = msg.getClass().getSimpleName();
		
		System.out.println("Cluster Msg:"+className);
		
	/*	if(msg instanceof DeadLetter){
			DeadLetter deadLetter=(DeadLetter)msg;
			System.out.println ( "deadLetter : "+ deadLetter.message ()+";"
			+deadLetter.sender()+";"+deadLetter.recipient());
		}*/
	}

}
