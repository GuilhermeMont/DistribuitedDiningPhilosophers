package sd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PhilosopherPeer {

    static final int MAX_T = 2;

    public static void main(String args[]){

        //Criar Runnable do servidor de garfos
        Runnable ps1 = new PhilosopherServer(5001);


        //Criar Runnable do cliente de garfos
        Runnable pc1 = new PhilosopherClient("127.0.0.1", 5001);

        //Criando uma thread pool
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        try {
            pool.execute(ps1); //Executando
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o servidor de Filosofos");
            pool.shutdown(); // se der merda , fecha
        }

        try {
            pool.execute(pc1); //Executando
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o cliente local de Filosofo");
            pool.shutdown(); // se der merda, fecha
        }


        // Fechar a pool
        pool.shutdown();


    }


}
