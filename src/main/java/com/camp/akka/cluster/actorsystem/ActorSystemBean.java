package com.camp.akka.cluster.actorsystem;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ActorSystemBean {
	
	private ActorSystem actorSystem;
	
	private ActorRef actor;
	
	private ActorRef busActor;

	public ActorSystem getActorSystem() {
		return actorSystem;
	}

	public void setActorSystem(ActorSystem actorSystem) {
		this.actorSystem = actorSystem;
	}

	public ActorRef getActor() {
		return actor;
	}

	public void setActor(ActorRef actor) {
		this.actor = actor;
	}

	public ActorRef getBusActor() {
		return busActor;
	}

	public void setBusActor(ActorRef busActor) {
		this.busActor = busActor;
	}

}
