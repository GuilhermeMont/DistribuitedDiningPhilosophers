package sd;

// A Java program for a PhilosopherServer

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import sd.Fork;

public class ForkServer
{
    //initialize socket and input stream
    private Socket		 socket = null;
    private ServerSocket server = null;
    private DataInputStream in	 = null;
    private boolean working = false;
    private boolean isWaitingConnection = false;

    public boolean isWorking() {
        return working;
    }

    public boolean isWaitingConnection() {
        return isWaitingConnection;
    }

    // constructor with port
    public ForkServer(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("PhilosopherServer started");

            System.out.println("Waiting for a client ...");
            this.working = true;



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

