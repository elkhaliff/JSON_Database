
class Counter {

    volatile int count = 0;

    public synchronized void inc() {
        count++;
    }
}