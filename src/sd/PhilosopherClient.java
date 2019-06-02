package sd;

// A Java program for a PhilosopherClient
import java.net.*;
import java.io.*;

public class PhilosopherClient implements Runnable
{
    // initialize socket and input output streams
    private Socket socket		 = null;
    private OutputStream os  = null;
    private ObjectOutputStream oos	 = null;
    private InputStream is = null;
    private ObjectInputStream ois = null;
    private String address;
    private int port;


    Message m = new Message(null);


    // constructor to put ip address and port

    PhilosopherClient(String address, int port) {
        this.address = address;
        this.port = port;
    }


    public void connect (Socket socket) throws IOException, ClassNotFoundException {

        os = socket.getOutputStream();
        oos = new ObjectOutputStream(os);
        is = socket.getInputStream();
        ois= new ObjectInputStream(is);
        m.isPhilosopherClient(true);

        oos.writeObject(m);
        oos.flush();
        m = (Message) ois.readObject();
        System.out.println(m.getMessage());
        oos.close();
        ois.close();
        socket.close();

    }

    private void cosumeSomething () throws InterruptedException {
        Thread.sleep(1000);
    }

    public void run()
    {
        // keep reading until "Over" is input
        while (!m.isTerminate())
        {
            try
            {
                Socket client = new Socket(this.address,this.port);
                System.out.println("O FILSOFO " + this.address + ":" + this.port + " ESTA ENVIANDO MENSAGEM");
                connect(client);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
