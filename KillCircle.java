/**
* 100 people stand in a circle in order 1 to 100. No. 1 has a sword.
* He kills the next person (i.e. No. 2) and gives the sword to the next living person (i.e. No. 3).
* All people do the same until only 1 survives. Which number survives to the end?
**/
public class KillCircle {
  public static void printSurvivors(int[] persons) {
    for (int i = 0; i < persons.length; i++) {
      if (persons[i] == 0) {
        System.out.print((i + 1) + " ");
      }
    }
    System.out.println();
  }

  public static void josephus1(int n, int k) {
    System.out.println("Method 1");
    int[] persons = new int[n];

    int count = n;
    boolean kill = false;
    int round = 1;
    int step = k;
    printSurvivors(persons);
    while(count != 1) {
      for (int i = 0; i < n; i++) {
        if (persons[i] == 1) {
          continue;
        }
        if (kill && step == 1) {
          persons[i] = 1;
          kill = false;
          step = k;
          count--;
        } else {
          kill = true;
          step--;
        }
      }

      System.out.printf("\nRound %d Survivors\n", round++);
      printSurvivors(persons);
    }
  }

  public static int josRecursive(int n, int k) {
    if (n == 1) {
      return 1;
    } else {
      return (josRecursive(n - 1, k) + k-1) % n + 1;
    }
  }

  public static void josephus2(int n, int k) {
    System.out.println("Method 2");
    System.out.println("Safe position : " + josRecursive(n, k));
  }

  public static void main(String... args) {
    int n = Integer.parseInt(args[0]);
    int k = Integer.parseInt(args[1]);
    if (n < 2 || k < 2) {
      System.out.printf("Number of soldiers (n) >= 2 and Number of steps(k) >= 2");
    } else {
      josephus1(n, k);
      josephus2(n, k);
    }
  }
}
