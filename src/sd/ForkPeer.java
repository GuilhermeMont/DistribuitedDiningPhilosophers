package sd;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForkPeer {


    static final int MAX_T = 6;
    static final int port = 5000;




    public static void main(String args[]){

        try {
            boolean begin = false;
            ServerSocket server = new ServerSocket(5000);

            while(!begin){
                Socket start = server.accept();
                start.close();
                begin = true;
            }

        }catch (IOException e){
            System.out.println(e);
        }


        Fork frk1 = new Fork(1);
//        frk1.setLeftFork(true);

        //Criar Runnable do servidor de garfos
        Runnable fs1 = new ForkServer(5005,frk1);


        //Criar Runnable do cliente de garfos
        Runnable fc1 = new ForkClient("200.239.138.211", 5001,5005);

        //Criando uma thread pool
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        try {
            pool.execute(fs1); //Executando
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o servidor de Garfo");
            pool.shutdown(); // se der merda , fecha
        }

        try {
            pool.execute(fc1); //Executando
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o cliente local de Garfos");
            pool.shutdown(); // se der merda, fecha
        }

        // Fechar a pool
        pool.shutdown();


    }

}
