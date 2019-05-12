package sd;
import java.io.Serializable;

public class Message implements Serializable{

    private static final long serialVersionUID = -5399605122490343339L;

    private String message = null;
    private boolean ate = false;
    private boolean terminate = false;
    private boolean isForkClient;
    private boolean isPhilosopherClient;
    private boolean receiving;
    private boolean rightFork;
    private boolean leftFork;



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

    public void Ate (boolean ate) {
        this.ate = ate;
    }

    public boolean Ate () {
        return ate;
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
