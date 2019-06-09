package sd;

// A Java program for a PhilosopherServer

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ForkServer implements Runnable {
    //initialize socket and input stream
    private ServerSocket server = null;
    private InputStream inputStream = null;
    private ObjectInputStream objectInputStream = null;
    private OutputStream outputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    private boolean terminate = false;
    private int port;
    private Fork fork = null;
    private int numberOfRequests = 0;
    private boolean isFinishingConnection = false;


    private static final int MaxRequests = 100;

    Message m = new Message(null);

    ForkServer(int port,Fork fork) {
        this.port = port;
        this.fork = fork;
    }

    private void setTermination(boolean terminate) {
        this.terminate = terminate;
    }

    private void increaseRequest () {
        this.numberOfRequests++;
    }


    public synchronized void consume(Socket socket) throws IOException, ClassNotFoundException {

        // get the input stream from the connected socket
        inputStream = socket.getInputStream();

        // create a DataInputStream so we can read data from it.
        objectInputStream = new ObjectInputStream(inputStream);

        // get the output stream from the socket.
        outputStream = socket.getOutputStream();

        // create an object output stream from the output stream so we can send an object through it
        objectOutputStream = new ObjectOutputStream(outputStream);

        m = (Message) objectInputStream.readObject();

        if (m.isTerminate()){
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
            setTermination(m.isTerminate());
            return;
        }

        if (m.isForkClient()) {
            if(!fork.isBeingUsed() && !m.isChecking()){
                if (!m.isReceiving()) { // Se o fork client estiver pedindo um garfo

                    if (fork.isLeftFork()) { // olha se tem garfo esquerdo
                        System.out.println("Passando o garfo esquerdo para o cliente " + m.getMessage());
                        fork.setLeftFork(false);
                        m.setLeftFork(true);
                        m.setReceiving(true); //esta enviando garfo
                    }

                    if (fork.isRightFork()) { //olha se tem garfo direito
                        System.out.println("Passando o garfo direito para o cliente " + m.getMessage());
                        fork.setRightFork(false);
                        m.setRightFork(true);
                        m.setReceiving(true); // esta enviando garfo

                    } else {
                        System.out.println("Não tem nenhum garfo por aqui " + this.port + " respondendo ao cliente" + m.getMessage());
                        m.setReceiving(false); // não esta enviando nada
                    }


                } else if (m.isReceiving()) {

                    System.out.println( m.getMessage() + " esta pedindo garfo para " + this.port);

                    if (m.isRightFork()) {
                        System.out.println("Recebendo garfo direito do cliente " + m.getMessage());
                        fork.setRightFork(true); //recebi um garfo direito
                        m.setReceiving(false);

                    }

                    if (m.isLeftFork()) {
                        System.out.println("Recebendo garfo esquerdo do cliente " + m.getMessage());
                        fork.setLeftFork(true); //recebi um garfo esquerdo
                        m.setReceiving(false);
                    }

                }
            }
            else if (m.isChecking()) {
                System.out.println("O cliente " + this.port + " está verificando se possui garfo");
                m.setRightFork(fork.isRightFork());
                m.setLeftFork(fork.isLeftFork());
                fork.setBeing_used(m.isEating());
            }
        }


        if (this.isFinishingConnection) {
            m.setTerminate(true);
        }

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
            System.out.println("Servidor de Garfo : " + this.port + " esta ativo");
            // reads message from client until "Over" is sent

            while (!this.terminate) {

                try {
                    Socket client = server.accept();
                    increaseRequest();
                    new Thread(() -> {                                // Thread que recebe informa��es do cliente
                        try {
                            consume(client);
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    if(this.numberOfRequests == MaxRequests) {
                        this.isFinishingConnection = true;
                    }

                } catch (IOException e) {
                    if (this.terminate) {
                        System.out.println("Server Stopped.");
                        return;
                    }
                    throw new RuntimeException(
                            "Error accepting client connection", e);
                }
            }

            System.out.println("Closing connection");
            // close connection
            server.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

}

