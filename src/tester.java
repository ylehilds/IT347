import java.net.*;
import java.io.*;

public class tester {
    public static void main(String[] args) throws Exception {
        URL yahoo = new URL("http://localhost:9020");
        for (int i=0; i< 100;i++)
        {
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
        System.out.println("connection attempt i =" +i);
        }
    }
}
