import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//this class is to help client to initialize one socket and keep track of it
public class sock { 
	String start="";
	Socket myclientSocket=null;
	int count=0;

	public sock(String ipaddress, int port) throws UnknownHostException, IOException //contructor
	{
		start="started";
		myclientSocket = new Socket (ipaddress, port);
	}
	public void setString() //setter
	{
		start = "initialized";
	}
	public String getStatus()//getter
	{
		return start;
	}
	public Socket getSocket() //returns te socket saved
	{
		return myclientSocket;
	}
	public void increaseCount() //increment count
	{
		count=1;
	}
	public int getCount() // get count
	{
		return count;
	}
}
