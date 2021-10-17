import java.util.HashMap;
import java.util.Scanner;

class Main {
    public static HashMap getMap(char[] arrs) {
        HashMap<Character, Integer> map = new HashMap();
        for (char ch: arrs) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        return map;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] arrOne = scanner.nextLine().toUpperCase().toCharArray();
        char[] arrTwo = scanner.nextLine().toUpperCase().toCharArray();

        System.out.println(getMap(arrOne).equals(getMap(arrTwo)) ? "yes" : "no");
    }
}