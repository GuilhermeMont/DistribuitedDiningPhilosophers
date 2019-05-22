package sd;

public class Fork {


    private boolean left_fork;
    private boolean right_fork;
    private static int fork_id;
    private boolean being_used;
    private String philosopher_server;


    Fork(int id) {
        left_fork = false;
        right_fork = false;
        fork_id = id;
        being_used = false;
        philosopher_server = "localhost";
    }



    public static int getId() {
        return fork_id;
    }

    public synchronized boolean isLeftFork() {
        return left_fork;
    }

    public synchronized void setLeftFork(boolean left_fork) {
        this.left_fork = left_fork;
    }

    public synchronized boolean isRightFork() {
        return right_fork;
    }

    public synchronized void setRightFork(boolean right_fork) {
        this.right_fork = right_fork;
    }

    public synchronized boolean isBeingUsed() {
        return being_used;
    }

    public synchronized void setBeing_used(boolean being_used) {
        this.being_used = being_used;
    }

    public String getPhilosopher_server() {
        return philosopher_server;
    }

}

