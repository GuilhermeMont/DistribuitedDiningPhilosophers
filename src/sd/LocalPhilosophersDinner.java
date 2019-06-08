package sd;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalPhilosophersDinner {

    private static final int MAX_T = 10;
    private static OutputStream outputStream  = null;
    private static ObjectOutputStream objectOutputStream = null;

    // Quando tiver rodando em distribuido, colocar corretamente os addresses
    private static final String[] addresses = {
            "200.239.139.120:5000",
            "200.239.138.211:5000",
            "200.239.139.25:5000",
            "200.239.139.27:5000",
            "200.239.139.28:5000",
    };


    private static void sendBeginMessage(String address, int port) throws IOException {
        Message m = new Message(null);
        Socket socket = new Socket(address,port);
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(m);
        objectOutputStream.flush();
        objectOutputStream.close();
        socket.close();

    }

    private static void startAllDistributed () {
        // IMPORTANTE
        // TODOS OS PEERS PRECISAM ESTAR ON !

        // este metodo ira ler do array addresses e enviar uma mensagem de begin para todas
        for (String address : addresses) {
            try{
                sendBeginMessage(address.split(":")[0],Integer.parseInt(address.split(":")[1]));
            }
            catch(IOException e){
                System.out.println(e);
            }

        }

    }

    private static void startAllLocal () {

        Fork fork1 = new Fork (5001);
        Fork fork2 = new Fork (5002);
        Fork fork3 = new Fork (5003);
        Fork fork4 = new Fork (5004);
        Fork fork5 = new Fork (5005);

        fork1.setRightFork(true);
        fork1.setLeftFork(true);
        fork4.setRightFork(true);
        fork4.setLeftFork(true);
        fork3.setLeftFork(true);


        //Criar Runnable do servidor de garfos
        Runnable fs1 = new ForkServer(5001,fork1);
        Runnable fs2 = new ForkServer(5002,fork2);
        Runnable fs3 = new ForkServer(5003,fork3);
        Runnable fs4 = new ForkServer(5004,fork4);
        Runnable fs5 = new ForkServer(5005,fork5);


        //Criar Runnable do cliente de garfos
        Runnable fc1 = new ForkClient("localhost", 5002, 5001);
        Runnable fc2 = new ForkClient("localhost", 5003, 5002);
        Runnable fc3 = new ForkClient("localhost", 5004, 5003);
        Runnable fc4 = new ForkClient("localhost", 5005, 5004);
        Runnable fc5 = new ForkClient("localhost", 5001, 5005);

        //Criando uma thread pool
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        try {
            pool.submit(fs1); //Executando
            pool.submit(fs2); //Executando
            pool.submit(fs3); //Executando
            pool.submit(fs4); //Executando
            pool.submit(fs5); //Executando
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o servidor local de Garfo");
            pool.shutdown(); // se der merda , fecha
        }

        try {
            pool.submit(fc1); //Executando
            pool.submit(fc2); //Executando
            pool.submit(fc3); //Executando
            pool.submit(fc4); //Executando
            pool.submit(fc5); //Executando
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o servidor local de Garfo");
            pool.shutdown(); // se der merda , fecha
        }

        // Fechar a pool
        pool.shutdown();

    }


    public static void main(String args[]){

        //Run distributed
        startAllDistributed();


        //Run local
//        startAllLocal();

    }

}
