package sd;

// A Java program for a ForkClient

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
    private InputStream inputStream	 = null;
    private ObjectInputStream objectInputStream = null;
    private String address;
    private int port;


    Message m = new Message("Pedindo garfo");

    ForkClient(String address, int port) {
        this.address = address;
        this.port = port;
    }


    public void connect (Socket socket) throws IOException, ClassNotFoundException {
        // get the output stream from the socket.
        outputStream = socket.getOutputStream();

        // create an object output stream from the output stream so we can send an object through it
        objectOutputStream = new ObjectOutputStream(outputStream);

        // get the input stream from the connected socket
        inputStream = socket.getInputStream();

        // create a DataInputStream so we can read data from it.
        objectInputStream = new ObjectInputStream(inputStream);

        m.isForkClient(true);

        objectOutputStream.writeObject(m);
        objectOutputStream.flush();

        m = (Message) objectInputStream.readObject();
        System.out.println(m.getMessage());

        objectOutputStream.close();
        objectInputStream.close();

    }


    public void run ()
    {
        // establish a connection
        try
        {

            while (!m.isTerminate())
            {
                socket = new Socket(address, port);
                System.out.println("O CLIENTE " + this.address + ':' + this.port + " esta conectado");
                connect(socket);

            }

        } catch(IOException | ClassNotFoundException u)
        {
            System.out.println(u);
        }

    }

}
