package sd;

// A Java program for a PhilosopherServer
import java.net.*;
import java.io.*;
import sd.Philosopher;

public class PhilosopherServer
{
    //initialize socket and input stream
    private Socket		 socket = null;
    private ServerSocket server = null;
    private DataInputStream in	 = null;

    // constructor with port
    public PhilosopherServer(int port)
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

    public static void main(String args[])
    {
        PhilosopherServer server = new PhilosopherServer(5000);
    }
}

