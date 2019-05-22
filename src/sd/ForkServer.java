package sd;

// A Java program for a PhilosopherServer

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ForkServer implements Runnable {
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private InputStream inputStream = null;
    private ObjectInputStream objectInputStream = null;
    private OutputStream outputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    private boolean terminate = false;
    private int port;
    private Fork fork = null;

    Message m = new Message(null);

    ForkServer(int port) {
        this.port = port;
        this.fork = new Fork(port);
    }


    public void consume(Socket socket) throws IOException, ClassNotFoundException {

        // get the input stream from the connected socket
        inputStream = socket.getInputStream();

        // create a DataInputStream so we can read data from it.
        objectInputStream = new ObjectInputStream(inputStream);

        // get the output stream from the socket.
        outputStream = socket.getOutputStream();

        // create an object output stream from the output stream so we can send an object through it
        objectOutputStream = new ObjectOutputStream(outputStream);

        m = (Message) objectInputStream.readObject();


        System.out.println(m.getMessage());

        if (m.isForkClient() && !m.isReceiving()) { // Se o fork client estiver pedindo um garfo

            if (fork.isLeftFork() && !fork.isBeingUsed()) { // olha se tem garfo esquerdo
                System.out.println("Tem garfo esquerdo");
                fork.setLeftFork(false);
                m.setLeftFork(true);
                m.setReceiving(true); //esta enviando garfo

            }

            if (fork.isRightFork() && !fork.isBeingUsed()) { //olha se tem garfo direito
                System.out.println("Tem garfo direito");
                fork.setRightFork(false);
                m.setRightFork(true);
                m.setReceiving(true); // esta enviando garfo

            } else {
                System.out.println("Não tem nenhum garfo por aqui");
                m.setReceiving(false); // não esta enviando nada
                m.setTerminate(true);
            }


        } else if (m.isForkClient() && m.isReceiving()) {

            if (m.isRightFork()) {
                fork.setRightFork(true); //recebi um garfo direito
            }

            if (m.isLeftFork()) {
                fork.setLeftFork(true); //recebi um garfo esquerdo
            }
        } else if (m.isPhilosopherClient()) { // se for um cliente filosofo

            if (m.Ate()) {
                fork.setBeing_used(false);
            }
        }

        m.setTerminate(true);
        objectOutputStream.writeObject(m);
        objectOutputStream.flush();
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();

    }


    // constructor with port
    public void run() {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            fork.setLeftFork(true);
            fork.setRightFork(true);

            // reads message from client until "Over" is sent
            do {

                try {
                    Socket client = server.accept();

                    new Thread(() ->{								// Thread que recebe informa��es do cliente
                        try {
                            consume(client);
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }).start();

                } catch (IOException e) {
                    if (this.terminate) {
                        System.out.println("Server Stopped.");
                        return;
                    }
                    throw new RuntimeException(
                            "Error accepting client connection", e);
                }


            } while (!m.isTerminate());

            System.out.println("Closing connection");

            // close connection
            server.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

}

