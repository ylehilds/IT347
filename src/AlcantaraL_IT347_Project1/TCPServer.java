import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {
    public static void main (String argv[]) throws Exception
    {
    	String welcome = "Welcome to Lehi's Chat Server";
    	int count=0;
        String word="";
       String listString="";
        String temp="";
        String name="";
        String tempTest="";
        List tempList = new ArrayList();
        List list = new ArrayList();
        String help = "help<cr><lf>\r\n test: words<cr><lf>\r\n name: <chatname><cr><lf>\r\n get<cr><lf>\r\n push: <stuff><cr><lf>\r\n getrange <startline> <endline><cr><lf>\r\n adios<cr><lf>\r\n";
        String [] user_input_command_line;
        String copy = "";
        String clientSentence;
        String capitalizedSentence;
        //creation of a new socket
        ServerSocket welcomeSocket = new ServerSocket(9020);
        Socket connectionSocket = welcomeSocket.accept();
        BufferedReader inFromClient = new  BufferedReader (new InputStreamReader(connectionSocket.getInputStream()));
        PrintWriter  outToClient = new  PrintWriter (connectionSocket.getOutputStream(), true);
            while (true) { // loop through all different options
            	
            	if (count==0) // runs only once to welcome the user
            	{
            		count++;
            		outToClient.println("welcome to Lehi's Chatroom and ready for input command line"+"\r\n");
            		continue;
            	}
            	else
            	{
            	//set up to read client input
            	clientSentence="";
                clientSentence = inFromClient.readLine();
                capitalizedSentence = clientSentence.toUpperCase() + "\r\n";
                user_input_command_line = clientSentence.split(" ");
                
                if (clientSentence.startsWith("test:")) //test case
                {
                    tempTest="";
                    for (int i=0;i<user_input_command_line.length;i++)
                    {
                        if (i==0)
                        {
                            continue;
                        }
                        if (i>1)
                        {
                            tempTest+=" ";
                        }
                        tempTest+=user_input_command_line[i];            
                    }
                outToClient.println(tempTest + "\r\n");
                continue;
                }
                
                if (clientSentence.startsWith("name:")) // name case with feature to let user know if no name was input while using name command
                {
                	if (user_input_command_line.length==1)
                	{
                		 outToClient.println("No Name inputted, input a Name \r\n");
                         continue;
                	}
                    name="";
                    for (int i=0;i<user_input_command_line.length;i++)
                    {
                        if (i==0)
                        {
                            continue;
                        }
                        if (i>1)
                        {
                            name+=" ";
                        }
                        name+=user_input_command_line[i];            
                    }
                outToClient.println("OK \r\n");
                continue;
                }
                
                if (clientSentence.startsWith("help")) // help case
                {
                outToClient.println(help+"\r\n");
                continue;
                }
                
                if (clientSentence.startsWith("get")) // get case
                {
                    if (user_input_command_line[0].equals("get"))
                        outToClient.println(listString);
                }
                if (clientSentence.startsWith("push")) //push case add input to a list
                {
                    copy="";
                    for (int i=0;i<user_input_command_line.length;i++)
                    {
                        if (i==0)
                            continue;
                        copy+=user_input_command_line[i];
                    }
                    list.add("\n"+name + ":" + copy +"\n");
                    listString="";
                    for (int i=0;i<list.size();i++)
                    {
                    listString += list.get(i); 
                    }
                   outToClient.println("OK \r\n");
                   continue;
                }
                
                if (clientSentence.startsWith("getrange")) // getrange case with check feature if out of range 
                {
                	
                    Integer startLine = new Integer ( user_input_command_line[1]);
                    Integer endLine = new Integer (user_input_command_line[2]);
                    tempList.clear();
                    try {
                    for(int i=startLine; i<=endLine;i++)
                    {
                        tempList.add(list.get(i));
                    }
                  }catch (IndexOutOfBoundsException e) {
                		 outToClient.println("Out of Bounds range try again" + "\r\n");
                		 continue;
                    }
                    word="";
                    for (int i=0; i<tempList.size();i++)
                    {
                    	word+= tempList.get(i)+" ";
                    }
                     outToClient.println(word + "\r\n");
                     continue;
                }
                if (clientSentence.startsWith("welcome")) // welcome server to client
                {
                    outToClient.println("welcome to Lehi's Chatroom and ready for input command line"+"\r\n");
                    continue;
                    
                }
                if (clientSentence.startsWith("get")) // just to check in the case user only input get, if input is getrange then it skips this condition
                {
                	continue;
                }
                if (clientSentence.equals("")) // added feature to check if no input entered, just enter
                {
                	 outToClient.println("No commands type, input a command"+"\r\n");
                     continue;
                }
                if (clientSentence.startsWith("adios")) //close connection
                {
                	connectionSocket.close();
                }
                if (clientSentence.startsWith("swearWord"))
                {
                	outToClient.println("No SWEAR word is allowed in this Chat Server"+"\r\n");
                    continue;
                }
                else // send what user inputted if no command is utilized
                {
                    outToClient.println(capitalizedSentence);
                    continue;
                }          	
            } 
         }
        
        }
}

