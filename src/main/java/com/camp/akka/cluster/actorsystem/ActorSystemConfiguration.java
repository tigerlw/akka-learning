package com.camp.akka.cluster.actorsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.camp.akka.cluster.actors.BillingServiceActor;
import com.camp.akka.cluster.actors.ClusterStateMonitorActor;
import com.camp.akka.cluster.actors.EventBusActor;
import com.camp.akka.cluster.actors.WorkerActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;

@Configuration
public class ActorSystemConfiguration 
{
	@Autowired
	private Environment env;

	@Bean
	public ActorSystemBean creatActorSystem() {
		String port = env.getProperty("actorsystem.port");
		Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
			      withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
			      withFallback(ConfigFactory.load());

		// Create an Akka system
		ActorSystem system = ActorSystem.create("ClusterSystem", config);

		// Create an actor that handles cluster domain events
		ActorRef actor = system.actorOf(Props.create(BillingServiceActor.class), "BillingServiceActor");
		
		system.actorOf(Props.create(ClusterStateMonitorActor.class), "ClusterStateMonitorActor");
		
		ActorRef busActor = system.actorOf(Props.create(EventBusActor.class), "EventBusActor");
		
		//system.actorOf(Props.create(WorkerActor.class), "WorkerActor");

		ActorSystemBean actorSystemBean = new ActorSystemBean();
		actorSystemBean.setActorSystem(system);
		actorSystemBean.setActor(actor);
		actorSystemBean.setBusActor(busActor);

		return actorSystemBean;

	}

}
