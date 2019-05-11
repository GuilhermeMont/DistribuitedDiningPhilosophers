package sd;
import java.io.Serializable;

public class Message {
    private static final long serialVersionUID = -5399605122490343339L;

    private String message;


    Message (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
