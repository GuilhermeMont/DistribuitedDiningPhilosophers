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
    private InputStream inputStream	 = null;
    private ObjectInputStream objectInputStream = null;
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

            // get the input stream from the connected socket
            inputStream = socket.getInputStream();

            // create a DataInputStream so we can read data from it.
            objectInputStream = new ObjectInputStream(inputStream);


        } catch(IOException u)
        {
            System.out.println(u);
        }

        // string to read message from input
        Message m = new Message("Tem um garfin aí man ??");
        m.isForkClient(true);

        // Continuar lendo até a mensagem indicar o fim da conexão
        while (m.isTerminate())
        {
            try
            {
                objectOutputStream.writeObject(m);
                objectOutputStream.flush();
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
