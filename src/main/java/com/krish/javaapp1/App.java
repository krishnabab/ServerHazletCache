package com.krish.javaapp1;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

/**
 * Hello world!
 *
 */
public class App 
{	public static String memberName;
	public final static String cacheGroupname = "AdminGating";
	public final static String cachePassword = "password";
    public static void main( String[] args )
    {	
    	try {
			memberName = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println( "Hello World!" );
        Config config = new Config();
        config.getGroupConfig().setName(cacheGroupname).setPassword(cachePassword);
        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortCount(20);
        network.setPortAutoIncrement(true);
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig().addMember("192.168.1.11").addMember("192.168.1.9").setEnabled(true);
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
        IQueue<String> queue = hzInstance.getQueue("AdminData");
        queue.add("Test");
        queue.add("Test");
        System.out.println("Q Size>>>"+queue.size());
    }
}
