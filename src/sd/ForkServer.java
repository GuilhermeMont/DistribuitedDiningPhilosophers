package sd;

// A Java program for a PhilosopherServer

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ForkServer implements Runnable
{
    //initialize socket and input stream
    private Socket		 socket = null;
    private ServerSocket server = null;
    private InputStream inputStream	 = null;
    private ObjectInputStream objectInputStream = null;
    private int port;

    Message m = new Message(null);

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
            System.out.println("Fork Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Fork client accepted");

            // get the input stream from the connected socket
            inputStream = socket.getInputStream();

            // create a DataInputStream so we can read data from it.
            objectInputStream = new ObjectInputStream(inputStream);



            // reads message from client until "Over" is sent
            while (!m.isTerminate())
            {
                try
                {
                    m = (Message) objectInputStream.readObject();
                    System.out.println(m.getMessage());

                }
                catch(IOException i)
                {
                    System.out.println(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Closing connection");

            // close connection
            server.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

}

