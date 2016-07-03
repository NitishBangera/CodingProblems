public class SpiralMatrix {
  public static void printMatrixClockwise(int m, int n) {
    System.out.println("\nClockwise");
    int top = 0;
    int bottom = m - 1;
    int left = 0;
    int right = n - 1;

    while(top <= bottom && left <= right) {
      // Print top row.
      for (int i = left; i <= right; i++) {
        System.out.printf("T(%d, %d) ", top, i);
      }
      top++;
      // Print rightmost column.
      for (int i = top; i <= bottom; i++) {
        System.out.printf("R(%d, %d) ", i, right);
      }
      right--;
      if (top <= bottom) {
        // Print bottom row.
        for (int i = right; i >= left; i--) {
          System.out.printf("B(%d, %d) ", bottom, i);
        }
        bottom--;
      }

      if (left <= right) {
        // Print leftmost column.
        for (int i = bottom; i >= top; i--) {
          System.out.printf("L(%d, %d) ", i, left);
        }
        left++;
      }
    }
  }

  public static void printMatrixAntiClockwise(int m, int n) {
    System.out.println("\nAntiClockwise");
    int top = 0;
    int bottom = m - 1;
    int left = 0;
    int right = n - 1;
    while(top <= bottom && left <= right) {
      // Print leftmost colum.
      for (int i = top; i <= bottom; i++) {
        System.out.printf("L(%d, %d) ", i, left);
      }
      left++;
      // Print bottom column.
      for (int i = left; i <= right; i++) {
        System.out.printf("B(%d, %d) ", bottom, i);
      }
      bottom--;
      if (left <= right) {
        // Print rightmost column.
        for (int i = bottom; i >= top; i--) {
          System.out.printf("R(%d, %d) ", i, right);
        }
        right--;
      }
      if (top <= bottom) {
        // Print top column.
        for (int i = right; i >= left; i--) {
          System.out.printf("T(%d, %d) ", top, i);
        }
        top++;
      }
    }
  }

  public static void main(String... args) {
    int m = Integer.parseInt(args[0]);
    int n = Integer.parseInt(args[1]);
    printMatrixClockwise(m, n);
    printMatrixAntiClockwise(m, n);
  }
}
