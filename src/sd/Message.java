package sd;
import java.io.Serializable;

public class Message implements Serializable{

    private static final long serialVersionUID = -5399605122490343339L;

    private String message = null;
    private boolean ate = false;
    private boolean terminate = false;

    Message (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void Ate (boolean ate) {
        this.ate = ate;
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
}
