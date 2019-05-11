package sd;

// A Java program for a PhilosopherServer

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ForkServer implements Runnable
{
    //initialize socket and input stream
    private Socket		 socket = null;
    private ServerSocket server = null;
    private DataInputStream in	 = null;
    private int port;

    ForkServer (int port) {
        this.port = port;
    }

    // constructor with port
    public void run()
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("PhilosopherServer started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("PhilosopherClient accepted");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

}

