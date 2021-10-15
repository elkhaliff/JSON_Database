import java.util.*;
import java.util.stream.Collectors;

class Main {
    private static ArrayList<Integer> readArrayList(Scanner scanner) {
        return Arrays
                .stream(scanner.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void main(String[] args) {
        List<Integer> numbers =  readArrayList(new Scanner(System.in));
        for (int i = numbers.size() - 1; i >= 0 ; i--) {
            if (i % 2 == 0) {
                numbers.remove(i);
            } else {
                System.out.print(numbers.get(i));
                System.out.print(" ");
            }
        }
    }
}