import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        final int count = 3;
        IntStream.range(0, count).mapToObj(i -> new Thread(new RunnableWorker(), "worker-" + i)).forEach(Thread::start);
    }
}

// Don't change the code below       
class RunnableWorker implements Runnable {

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        if (name.startsWith("worker-")) {
            System.out.println("too hard calculations...");
        } else {
            return;
        }
    }
}