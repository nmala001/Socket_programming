//importing the required packages
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DecimalFormat;

class PingClient {
	
	public static long Minimum(long[] INArray){ 
    long min = INArray[0]; 
    for(int i=1;i<INArray.length;i++) 
	{ 
      if(INArray[i] < min){  
        min = INArray[i]; 
      } 
    } 
    return min; // returns the minimum value
  } 
  
   public static long Maximim(long[] INArray){ 
    long Max = INArray[0]; 
    for(int i=1;i < INArray.length;i++) 
	{ 
      if(INArray[i] > Max){ // finds the maximum value
         Max = INArray[i]; 
      } 
    } 
    return Max; // returns the maximum value
  }
  
  // Avg function is used for calculating the average value of the RTT's 
  public static String Avg(long[] INArray){
	  long s=0; 
	  double sm; 
	  for(int i=0;i<INArray.length;i++)
	  {
		  s=s+INArray[i]; 
	  }
	  sm=(double)s; 
	  double length=(double)INArray.length; 
	  double avgV = sm/length; 
	  DecimalFormat df = new DecimalFormat("0.00"); 
	  
	 String averageO=df.format(avgV); 
	  return averageO; 
  }
 
    public static void main(String argv[]) throws Exception // msin class
    {
	
	DatagramSocket cSocket; // declaring the datagram socket
	// declaring the datagram packets
	DatagramPacket sPacket; 
	DatagramPacket rPacket;
	// declaring the Byte variables
	byte[] rData = new byte[1024];
	byte[] sData = new byte[1024];
	// declaring the IPaddres variable
		InetAddress IPaddr;
	
	
	long start=0;
	long a[]=new long[10];
	long end=0;
	
	String cSentence="";  
	String	sSentence=""; 
	

	// command-line arguments
	int port=0; 
	String server;
	int i=0,j=0,k=0,l=0;  

	// process command-line arguments and printing the errors if it not given in right format
	 
		if(argv.length==1) 
		{
			System.out.println("Usage: java PingClient hostname port"); 
			System.exit(0);
		}
		if(argv.length==0)
		{
			System.out.println("Usage: java PingClient hostname port "); 
			System.exit(0);
		}
		if(argv.length>2)
		{
			System.out.println("Usage: java PingClient hostname port "); 
			System.exit(0);
		}
	    

	
	server = argv[0];
	try // checking the argument ,whether it is given in the right format
	{
	port = Integer.parseInt(argv[1]);
	if(port<10001 || port >11000) // Checking the port range
	{
		System.out.println("ERR - arg 2"); 
		System.exit(0); // exiting from the program
	}
	}
	catch(NumberFormatException e)
	{
		System.out.println("ERR - arg 2"); 
		System.exit(0);// exting from the program
	}
	
       // inFromUser = new BufferedReader(new InputStreamReader(System.in));  
      
	
	cSocket = new DatagramSocket();// Create client socket to destination
	
	IPaddr = InetAddress.getByName (server); 
	
	
	
	
	
		
			
			Date now = new Date(); // declaring the date class object
			

		while(i<10) // while loop is used to sent the 10 packets continuously as we took 10 to compare with i
		{
			
			String str= Integer.toString(i); // converting the i to string
	
	long msSend = now.getTime();// getting the current system time 
		cSentence = "PING "+str+" "+Long.toString(msSend) ; // storing the specipied format message in the string variable                     
	sData = cSentence.getBytes(); // getting the message data into bytes and storing it into byte variable
		
		start= System.currentTimeMillis(); // getting the system current time in milliseconds
	// Create packet and send to server
	sPacket = new DatagramPacket(sData, sData.length,  
					IPaddr, port);
					
	cSocket.send(sPacket); // sending the packet to the server
	try
	{
		l=0; // setting the flat to zero
		cSocket.setSoTimeout(2000); // timeout is set to 2secs
	// Create receiving packet and receive from server
	rPacket = new DatagramPacket(rData,
					   rData.length); 
					   now = new Date();
				long msReceived = now.getTime(); 
	cSocket.receive(rPacket); 
	end=System.currentTimeMillis();
	sSentence = new String(rPacket.getData(), 0,
				    rPacket.getLength()); 
					if(l==0) 
					{
						
    a[k]=end-start; 
	
					
	
	System.out.println(sSentence + " RTT :" + Long.toString(a[k])+ "ms"); 
	k++; // incrementing the k
					}
		}
		catch(IOException e) 
		{
			System.out.println(cSentence + " RTT : " +"*"); 
			j++; 
			
			l=1; // setting the flag to one
		}
		i++;// incrementing the i
		
		
		}
		long temp[]=new long[i-j]; // declaring an array to store the RTTS which is further used to calculate the min/avg/max
		for(int t=0;t<i-j;t++) 
		{
			temp[t]=a[t]; // storing the RTT values into a array
			
		}
		float percentage= (float)j/i*100; // calculating the percentage of packet loss
		System.out.println("---- PING Statistics ----"); // printing the ping statistics
		System.out.println("10 packets transmitted, "+Integer.toString(i-j)+" packets received, "+Integer.toString((int)percentage)+ "% packet loss");// printing the ping statistics in specified format
		System.out.println("round-trip (ms) min/avg/max = "+ Minimum(temp)+"/"+Avg(temp)+"/"+Maximim(temp)); //printing the min/avg/max values

	cSocket.close();

    } // end main

} // end class