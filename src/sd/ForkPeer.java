package sd;

public class ForkPeer {


    public static void main(String args[]){

        try {
            new Thread(StartForkServer).start();
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o servidor de Garfo");
        }

        try {
            new Thread(StartForkClient).start();
        }
        catch (Exception e) {
            System.out.println("Falha ao criar o cliente local de Garfos");
        }


    }


    private static Runnable StartForkServer = new Runnable() {
        @Override
        public void run() {
            ForkServer server = new ForkServer(5001);
        }
    };


    private static Runnable StartForkClient = new Runnable() {
        @Override
        public void run() {
            ForkClient client = new ForkClient("127.0.0.1", 5001);
        }
    };



}
