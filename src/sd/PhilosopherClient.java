package sd;

// A Java program for a PhilosopherClient
import java.net.*;
import java.io.*;

public class PhilosopherClient implements Runnable
{
    // initialize socket and input output streams
    private Socket socket		 = null;
    private OutputStream outputStream  = null;
    private ObjectOutputStream objectOutputStream 	 = null;
    private InputStream inputStream	 = null;
    private ObjectInputStream objectInputStream = null;
    private String address;
    private int port;


    // constructor to put ip address and port

    PhilosopherClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void run()
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

            // get the input stream from the connected socket
            inputStream = socket.getInputStream();

            // create a DataInputStream so we can read data from it.
            objectInputStream = new ObjectInputStream(inputStream);
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        Message m = new Message("Tem um garfin aí man ??");

        // keep reading until "Over" is input
        while (!m.isTerminate())
        {
            try
            {
                objectOutputStream.writeObject(m);
                m = (Message) objectInputStream.readObject();
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
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
