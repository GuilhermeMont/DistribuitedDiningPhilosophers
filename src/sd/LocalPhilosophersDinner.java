package sd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalPhilosophersDinner {

    static final int MAX_T = 10;


    public static void main(String args[]){

        Fork fork1 = new Fork (5001);
        Fork fork2 = new Fork (5002);
        Fork fork3 = new Fork (5003);
        Fork fork4 = new Fork (5004);
        Fork fork5 = new Fork (5005);

        fork1.setLeftFork(true);
        fork1.setRightFork(true);
        fork2.setLeftFork(true);
        fork4.setLeftFork(true);
        fork4.setRightFork(true);


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

}
