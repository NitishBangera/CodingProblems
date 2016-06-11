import java.util.Set;
import java.util.HashSet;

public class SumPair {
    public static void main(String... args) {
        int[] arr = new int[] {1, 5, 4, 10, 8, 2, -4};
        int sum = 6;
        
        Set<Integer> check = new HashSet<Integer>();
        
        for (int val : arr) {
            Integer diff = sum - val;
            
            if (check.contains(diff)) {
                System.out.printf("(%d, %d)\n", val, diff);
            }
            
            check.add(val);
        }        
    }
}