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
  }

  public static void main(String... args) {
    int n = Integer.parseInt(args[0]);
    int[] persons = new int[n];

    int count = n;
    boolean kill = false;
    int round = 1;
    printSurvivors(persons);
    while(count != 1) {
      for (int i = 0; i < n; i++) {
        if (persons[i] == 1) {
          continue;
        }
        if (kill) {
          persons[i] = 1;
          kill = false;
          count--;
        } else {
          kill = true;
        }
      }

      System.out.printf("\nRound %d Survivors\n", round++);
      printSurvivors(persons);
    }
  }
}
