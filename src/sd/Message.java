package sd;
import java.io.Serializable;

public class Message implements Serializable{

    private static final long serialVersionUID = -5399605122490343339L;

    private String message = null;
    private boolean eating = false;
    private boolean terminate = false;
    private boolean checking = false;
    private boolean isForkClient;
    private boolean isPhilosopherClient;
    private boolean receiving;
    private boolean rightFork;
    private boolean leftFork;



    public void checkInfo (int port) {
        System.out.println("O clinte da porta: " + port);
        System.out.println("---------------------------");
        System.out.println("Possui garfo direito ? " + this.rightFork);
        System.out.println("Possui garfo esquerdo ? " + this.leftFork);
        System.out.println("Esta comendo ? " + this.eating);
        System.out.println("Esta pedindo um garfo ? " + !this.receiving);
        System.out.println("Esta recebendo um garfo ? " + this.receiving);
        System.out.println("---------------------------");
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }

    Message (String message) {
        this.message = message;
    }

    public boolean isRightFork() {
        return rightFork;
    }



    public void setRightFork(boolean rightFork) {
        this.rightFork = rightFork;
    }

    public boolean isLeftFork() {
        return leftFork;
    }

    public void setLeftFork(boolean leftFork) {
        this.leftFork = leftFork;
    }

    public boolean isReceiving() {
        return receiving;
    }

    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }

    public String getMessage() {
        return message;
    }

    public void setEating (boolean eating) {
        this.eating = eating;
    }

    public boolean isEating () {
        return eating;
    }

    public void writeMessage (String message) {
        this.message = message;
    }

    public void setTerminate (boolean terminate){
        this.terminate = terminate;
    }

    public boolean isTerminate () {
        return terminate;
    }

    public void isPhilosopherClient (boolean isPhilosopherClient){
        this.isPhilosopherClient = isPhilosopherClient;
    }

    public void isForkClient (boolean isForkClient) {
        this.isForkClient = isForkClient;
    }

    public boolean isPhilosopherClient (){
       return this.isPhilosopherClient ;
    }

    public boolean isForkClient () {
        return this.isForkClient;
    }
}
