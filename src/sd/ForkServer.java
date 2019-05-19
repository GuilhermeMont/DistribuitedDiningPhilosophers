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
    private OutputStream outputStream  = null;
    private ObjectOutputStream objectOutputStream 	 = null;
    private int port;
    private Fork fork = null;

    Message m = new Message(null);

    ForkServer (int port) {
        this.port = port;
        this.fork = new Fork (port);
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

            // get the output stream from the socket.
            outputStream = socket.getOutputStream();

            // create an object output stream from the output stream so we can send an object through it
            objectOutputStream = new ObjectOutputStream(outputStream);

            fork.setLeftFork(true);
            fork.setRightFork(true);

            // reads message from client until "Over" is sent

            do {
                try
                {
                    m = (Message) objectInputStream.readObject();
                    if (m != null){
                        System.out.println(m.getMessage());

                        if (m.isForkClient() && !m.isReceiving()) {

                            if (fork.isLeftFork() && !fork.isBeingUsed()) {
//                            m.setTerminate(true);
                                System.out.println("Tem garfo sim man, toma um garfo esquerdo aí Xd");
                                fork.setLeftFork(false);
                                m.setLeftFork(true);
                                m.setReceiving(true);

                            }

                            if (fork.isRightFork() && !fork.isBeingUsed()) {
//                            m.setTerminate(true);
                                System.out.println("Tem garfo sim man, toma um garfo direito aí Xd");
                                fork.setRightFork(false);
                                m.setRightFork(true);
                                m.setReceiving(true);
                            }

                            else {
                                System.out.println("Não tem nenhum garfo por aqui");
                                m.setReceiving(false);
                            }


                        }

                        else if (m.isForkClient() && m.isReceiving()) {

                            if (m.isRightFork()) {
                                fork.setRightFork(true);
                            }

                            if (m.isLeftFork()) {
                                fork.setLeftFork(true);
                            }
                        }

                        else if (m.isPhilosopherClient()) {

                            if(m.Ate()) {
                                fork.setBeing_used(false);
                            }
                        }

                        System.out.println("Mandando o garfo aí man, Xd");

                        objectOutputStream.writeObject(m);
                    }

                }
                catch(IOException i)
                {
                    System.out.println(i);
                    socket.close();
                    server.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    socket.close();
                    server.close();
                }

            } while (m.isTerminate());

            System.out.println("Closing connection");

            // close connection
            socket.close();
            server.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

}

