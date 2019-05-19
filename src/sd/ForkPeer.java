package sd;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForkPeer {


    static final int MAX_T = 6;


    public static void main(String args[]){


        //Criar Runnable do servidor de garfos
        Runnable fs1 = new ForkServer(5002);


        //Criar Runnable do cliente de garfos
        Runnable fc1 = new ForkClient("192.168.1.145", 5002);

        //Criando uma thread pool
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        try {
            pool.execute(fs1); //Executando
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o servidor de Garfo");
            pool.shutdown(); // se der merda , fecha
        }

//        try {
//            pool.execute(fc1); //Executando
//        }
//        catch (Exception e) {
//            System.out.println("Falha ao criar o cliente local de Garfos");
//            pool.shutdown(); // se der merda, fecha
//        }


        // Fechar a pool
        pool.shutdown();


    }

}
