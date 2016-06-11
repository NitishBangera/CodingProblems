public class SquareRoot {
    public static void binSearchSqrt(int num) {
        System.out.println("Binary Search Method");
        double start = 0.0;
        double end = num;
        double mid = (start + end)/2;
        double prevMid = 0.0;
        double diff = Math.abs(prevMid - mid);
        int count = 0;
        while (mid * mid != num && diff > 0.000001) {
            if (mid * mid > num) {
                end = mid;
            } else {
                start = mid;
            }
            prevMid = mid;
            mid = (start + end)/2;
            diff = Math.abs(prevMid - mid);
            count++;
        }
        System.out.println("Iterations : " + count);
        System.out.println("SquareRoot : " + mid);
    }
    
    public static void newtonRaphsonSqrt(int num) {
        // http://www.dsplog.com/2011/12/25/newtons-method-square-root-inverse/
        System.out.println("Newton Raphson Method");
        int count = 0;
        double x = 1, fX = 0.0, fPrimeX = 0.0, guess = 0.0, diff = 0.0;
        
        do {
            fX = (x * x) - num;
            fPrimeX = 2 * x;
            guess = x - (fX / fPrimeX);
            
            diff = Math.abs(x - guess);
            System.out.println(x + " " + guess + " " + diff);
            x = guess;
            count++;
        } while(diff > 0.000001);
        
        System.out.println("Iterations : " + count);
        System.out.println("SquareRoot : " + guess);
    }

    public static void main(String... args) {
        int num = Integer.parseInt(args[0]);
        System.out.println("Number : " + num);
        binSearchSqrt(num);
        newtonRaphsonSqrt(num);
    }
}