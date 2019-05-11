package sd;

public class Philosopher {


    private boolean eating;
    private int timesFed;
    private int philosopherId;

    Philosopher(int id) {
        eating = false;
        timesFed = 0;
        philosopherId = id;
    }


    public boolean isEating() {
        return eating;
    }

    public void setEating(boolean eating) {
        this.eating = eating;
    }

    public int getTimesFed() {
        return timesFed;
    }

    public void increaseTimesFed() {
        this.timesFed++;
    }

    public int getPhilosopherId() {
        return philosopherId;
    }


}
