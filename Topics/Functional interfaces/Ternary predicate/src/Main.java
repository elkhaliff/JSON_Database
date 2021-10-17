
class Predicate {

    public static TernaryIntPredicate<Integer, Integer, Integer, Boolean> allValuesAreDifferentPredicate =
            (x, y, z) -> x != y && x != z && y != z;

    @FunctionalInterface
    public interface TernaryIntPredicate<A, B, C, R> {
        R test(A a, B b, C c);
    }
}