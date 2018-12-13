package com.camp.akka.cluster.controller;

import static akka.pattern.Patterns.ask;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camp.akka.cluster.actorsystem.ActorSystemBean;

import akka.actor.ActorRef;
import akka.cluster.Cluster;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.routing.ConsistentHashingGroup;

@RestController
public class MsgController 
{
	@Autowired
	private ActorSystemBean actorSystemBean;
	
	private ActorRef workerRouter;
	
	@RequestMapping(value = "/execlimit" ,method = RequestMethod.GET)
	public String execLimitController(@RequestParam String msg)
	{
		
		actorSystemBean.getActor().tell(msg, null);
		
		System.out.println("msg");
		return "{msg:" + msg + "}";
	}
	
	@RequestMapping(value = "/bus" ,method = RequestMethod.GET)
	public String busController(@RequestParam String msg)
	{
		
		actorSystemBean.getBusActor().tell(msg, null);
		
		System.out.println("msg");
		return "{msg:" + msg + "}";
	}
	
	@RequestMapping(value = "/down" ,method = RequestMethod.GET)
	public String downController(@RequestParam String msg)
	{
		Cluster cluster = Cluster.get(actorSystemBean.getActorSystem());
		
		cluster.leave(cluster.selfAddress());
		
		return "{msg:" + msg + "}";
	}

}
