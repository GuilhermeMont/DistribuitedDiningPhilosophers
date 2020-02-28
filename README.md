# Distribuited Dining Philosophers

A distributed solution for the classic Dining Philosophers problem

# Introduction

The Dining Philosopher Problem – The Dining Philosopher Problem states that K philosophers seated around a circular table with one chopstick between each pair of philosophers. There is one chopstick between each philosopher. A philosopher may eat if he can pickup the two chopsticks adjacent to him. One chopstick may be picked up by any one of its adjacent followers but not both.

## How to run

First of all, you will need five diferent computers. In four of then run **ForkPeer.java**, in the last one run both **ForkPeer.java** and **LocalPhilosophersDinner.java**

And do not forget to set the right IPs here, inside LocalPhilosophersDinner.java.

`private static final String[] addresses = {
            "200.239.139.120:5000",
            "200.239.138.211:5000",
            "200.239.139.25:5000",
            "200.239.139.27:5000",
            "200.239.139.28:5000",
    };`


