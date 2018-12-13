package com.camp.akka.cluster;

import com.camp.akka.cluster.actors.BillingServiceActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import static akka.pattern.Patterns.ask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppMain 
{
	public static void main(String args[])
	{
		
		//startup(new String[] { "2551", "2552", "0" });
		SpringApplication.run(AppMain.class, args);
		
	}
	
	
	public static void startup(String[] ports) {
	    for (String port : ports) {
	      // Override the configuration of the port
	      Config config = ConfigFactory.parseString(
	          "akka.remote.netty.tcp.port=" + port).withFallback(
	          ConfigFactory.load());

	      // Create an Akka system
	      ActorSystem system = ActorSystem.create("ClusterSystem", config);

	      // Create an actor that handles cluster domain events
	      ActorRef actor = system.actorOf(Props.create(BillingServiceActor.class),
	          "BillingServiceActor");
	      
	      
	      ask(actor,"msg",1000);

	    }
	  }

}
