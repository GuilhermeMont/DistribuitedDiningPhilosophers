package sd;

// A Java program for a ForkClient

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
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
    private int innerPort;
    private int philosopherPort;
    private  boolean terminate = false;


    Message m = new Message(null);


    ForkClient(String address, int port,int innerPort) {
        this.address = address;
        this.port = port;
        this.innerPort = innerPort;
    }

    private void setTermination (boolean terminate) {
        this.terminate = terminate;
    }


    private void connect (String address, int port) throws IOException, ClassNotFoundException {

        Socket socket = new Socket(address,port);

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
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }



    private static int generateRandom(){
        int i = (new Random().nextInt(3)+1);
        switch (i){
            case 1:
                return (new Random().nextInt(5)+1)*1000;
            case 2:
                return (new Random().nextInt(5)+1)*100;
            case 3:
                return (new Random().nextInt(5)+1)*500;
            default:
                break;
        }
        return 0;
    }



    private void consumeSomething () {
        try {
            int time = generateRandom();
            Thread.sleep(time);
        }catch (InterruptedException e){
            System.err.println(e);
        }
    }


    private void checkFork () {
        if (m.isLeftFork() && m.isRightFork()) {
            m.setEating(true);
            System.out.println("O Cliente " + this.address +" : "+ this.innerPort + " esta comendo");
            consumeSomething();
            m.setReceiving(true);
            m.setEating(false);
        }
        else {
            m.setReceiving(false);
            m.setEating(false);
        }
    }


    public void run ()
    {
        // establish a connection
        try
        {

            System.out.println("O CLIENTE " + this.address + ':' + this.port + " esta conectado");
            m.writeMessage(this.address + " : " +this.innerPort);
            Thread.sleep(1000);

            while (!this.terminate)
            {

                m.setChecking(true);
                connect(address, innerPort);
//                m.checkInfo(this.innerPort);

                checkFork();

                consumeSomething();

                m.setChecking(false);
                connect(address,port);
//                m.checkInfo(this.innerPort);

                checkFork();


            }


        } catch(IOException | ClassNotFoundException | InterruptedException u)
        {
            System.out.println(u);
        }

    }

}
