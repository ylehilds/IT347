import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
public class TCPClient extends JFrame implements ActionListener{
InputStreamReader inFromServer;
char [] inputStream = null;
String OldText;
String ipaddress="192.168.100.52";
int port= 9020;
private JTextField toclient = new JTextField();
public JTextArea display = new JTextArea();
public JButton send = new JButton("Send");
public JButton close = new JButton("Close");
JScrollPane scrollingArea  = new JScrollPane (display);

  sock mySock;// = new sock(Socket sock);
  public static void main (String[] args) throws UnknownHostException, IOException {
	  new TCPClient();
  }
  public String myTest()
  {
	 return "This is working";
  }
  public TCPClient () throws UnknownHostException, IOException
  {
	  //listener set up
  send.addActionListener(this);
  close.addActionListener(this);
  if(mySock==null) // checks if mySock was initialized if no initializes if yes then still keep original socket
		mySock = new sock(ipaddress, port);
  if (mySock.getCount()==0) // only runs once, in getting the welcome string server sent to client
{
	 inFromServer = new  InputStreamReader(mySock.getSocket().getInputStream());
	 inputStream = new char [1025];
       int size =  inFromServer.read(inputStream, 0, 1025);
        String out = new String (inputStream, 0, size-1);
        display.append("FROM SERVER: " + out);
       mySock.increaseCount();
}
  //GUI set up
JPanel input = new JPanel();
input.setLayout(new BorderLayout());
input.setBorder(new TitledBorder("Enter Message"));
    input.add(toclient, BorderLayout.CENTER);
    input.add(send, BorderLayout.EAST);
    input.add(close, BorderLayout.WEST);
 
    JPanel output = new JPanel();
    output.setLayout(new BorderLayout());
    output.setBorder(new TitledBorder("Conversation"));
    output.add(scrollingArea, BorderLayout.CENTER);
    
    JPanel exteriorPanel = new JPanel();
    exteriorPanel.setLayout(new GridLayout(2, 1));
    exteriorPanel.add(input);
    exteriorPanel.add(output);
 
    this.getContentPane().add(exteriorPanel, BorderLayout.NORTH);
 
    setTitle("Chat Client");
	    setSize(500, 300);
	    setVisible(true);
  }
  
  public void actionPerformed(ActionEvent e) {
	  if (e.getSource() == send){ // when button is pressed
       try {
			//ipaddress = "192.168.1.104";//args[0];
			//port = 9020;//Integer.parseInt(args[1]); 
			boolean loop= true;
			Socket clientSocket=null;
			clientSocket = mySock.getSocket();
			String clientListener;
			String sentence="";
			BufferedReader inFromUser = new  BufferedReader (new InputStreamReader(System.in));
			if (mySock.getStatus() == "started") //only set up one socket and keep a record of it
				{
			     	mySock.setString();
			    }
			//getting input from user and outputs to server
			DataOutputStream outToServer = new  DataOutputStream(mySock.getSocket().getOutputStream());
	        InputStream input =  (mySock.getSocket().getInputStream());
	        inFromServer = new  InputStreamReader(mySock.getSocket().getInputStream());
	        String inputFromUser = "";
           	sentence = toclient.getText();
        	inputFromUser = sentence;
        	inputFromUser +="\r\n";
            outToServer.writeBytes(inputFromUser);
            inputStream = new char [1025];
            int size =  inFromServer.read(inputStream, 0, 1025);
            String out = new String (inputStream, 0, size-1);
            display.append("FROM SERVER: " + out);
		  } catch (IOException ex) {
	          ex.printStackTrace();
	      }
}
else if (e.getSource() == close) // exit chat
{
	System.exit(1);
}
}
}
