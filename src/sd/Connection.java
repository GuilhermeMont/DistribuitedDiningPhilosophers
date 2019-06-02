package sd;

public class Connection {

    private int innerPort;
    private String address;
    private String innerAddress = "localhost";
    private int port;


    Connection (int innerPort,String address, int port) {
        this.innerPort = innerPort;
        this.address = address;
        this.port = port;
    }


}
