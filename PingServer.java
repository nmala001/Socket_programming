
import java.io.*;
import java.net.*;
import java.util.*;

class PingServer {
	private static final double LOSS_RATE = 0.25;  //loss rate in percentage
    private static final int AVERAGE_DELAY = 150;  // delay in milli seconds



//start of main method
    
    public static void main(String argv[]) throws Exception
    {
		int seed=0;     
	
	DatagramSocket sSocket;
	byte[] received = new byte[1024]; 
	byte[] sent = new byte[1024]; 
	InetAddress IPAddress;   
	String IP_ADDR, ip;  
	int cPort; 
	int delay; 
	String delays; 

	String serverSentence;  

	int port=0;

	//The conditions below checks for all the various use cases of port and seed

	
	if (argv.length < 1 || argv.length >2) { 
	   
	   System.out.println("Usage: java PingServer port [seed] "); 
	    System.exit (-1); 
	}
	
	Random random; 
	
	try
	{
	
	port = Integer.parseInt(argv[0]); 
	
	if(port<10001 || port >11000) 
	{
		System.out.println("ERR - arg 1"); 
		System.exit(0); 
	}
	}
    catch(NumberFormatException e) 
	{
		System.out.println("ERR - arg 1"); 
		System.exit(0);
	}
	catch(Exception e)  
	{
		System.out.println("ERR - arg 1");
		System.exit(0);
	}
	if (argv.length==2) 
	{
	try
	{
		seed = Integer.parseInt(argv[1]);  
	}
    catch(NumberFormatException e) 
	{
		System.out.println("ERR - arg 2"); 
		System.exit(0);
	}
	}
	if (seed == 0) {   
		random = new Random(); 
		} else {
			
		random = new Random(seed);
		}
	
	sSocket = new DatagramSocket(port); 


	//The while loop receive requests from client
	

	  
	while (true) {

	
	    DatagramPacket receivePacket = new DatagramPacket 
		(received,received.length);
	    sSocket.receive(receivePacket); 
	    
    	    String clientSentence = new String(receivePacket.getData(), 0,
    					       receivePacket.getLength()); 
	  
	    IPAddress = receivePacket.getAddress(); 
		IP_ADDR= IPAddress.toString(); 
	    ip= IP_ADDR.replace("/", "");  
	    cPort = receivePacket.getPort();
	    sent = clientSentence.getBytes();
	    DatagramPacket sendPacket = new DatagramPacket(sent, 
							   sent.length, 
							   IPAddress, 
							   cPort); 
							   if (random.nextDouble() < LOSS_RATE)
							   {
								  
								  System.out.println (ip+":"+cPort+">"+clientSentence + " ACTION: not sent");// here the packet is not sent to the client, so here we are displaying the action equal to not sent
							   }else
							   {
								   delay = (int) (random.nextDouble() * 2 * AVERAGE_DELAY); 								   delays= Integer.toString(delay);// converting the delay time to string
                                   Thread.sleep(delay);
								 
	    sSocket.send(sendPacket);
		System.out.println (ip+":"+cPort+">"+clientSentence+" ACTION: delayed "+delays+" ms");
		}
	}

    }
	
	
}