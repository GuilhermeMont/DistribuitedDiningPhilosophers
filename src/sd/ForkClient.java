package sd;

// A Java program for a PhilosopherClient

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ForkClient implements  Runnable
{
    // initialize socket and input output streams
    private Socket socket		 = null;
    private OutputStream outputStream  = null;
    private ObjectOutputStream objectOutputStream 	 = null;
    private String address;
    private int port;

    ForkClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    // constructor to put ip address and port
    public void run ()
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // get the output stream from the socket.
            outputStream = socket.getOutputStream();
            // create an object output stream from the output stream so we can send an object through it
            objectOutputStream = new ObjectOutputStream(outputStream);

        } catch(IOException u)
        {
            System.out.println(u);
        }

        // string to read message from input
        Message m = new Message("Tem um garfin aí man ??");

        // keep reading until "Over" is input
        while (!m.isTerminate())
        {
            try
            {
                objectOutputStream.writeObject(m);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

}
