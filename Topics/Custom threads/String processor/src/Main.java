import java.util.Locale;
import java.util.Scanner;

class StringProcessor extends Thread {

    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input

    @Override
    public void run() {
        while (true) {
            String str = scanner.nextLine();
            if (str.matches("^.*[a-z].*$")) {
                str = str.toUpperCase(Locale.ROOT);
                System.out.println(str);
            } else {
                System.out.println("FINISHED");
                break;
            }
        }
    }
}