package sd;

public class PhilosopherPeer {
    public static void main(String args[]){

        try {
            new Thread(StartPhilosopherServer).start();
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o servidor de Filosofo local");
        }

        try {
            new Thread(StartPhilosopherClient).start();
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o cliente local de Filosofo");
        }


    }

    private static Runnable StartPhilosopherServer = new Runnable() {
        @Override
        public void run() {
            PhilosopherServer server = new PhilosopherServer(5002);
        }
    };


    private static Runnable StartPhilosopherClient = new Runnable() {
        @Override
        public void run() {
            PhilosopherClient client = new PhilosopherClient("127.0.0.1", 5002);
        }
    };

}
