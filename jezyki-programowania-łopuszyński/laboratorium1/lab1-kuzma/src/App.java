import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class App {
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].matches("\\d+")) {
                if (Integer.parseInt(args[0]) <= 3) {
                    System.out.println("Podales liczbe, ktora jest mniej lub rowna 3");
                    return;
                }
            }
            else {
                System.out.println("Podales argument, ktory nie jest liczba, lub ten argument jest mniej niż zero");
                return;
            }
        }
        else {
            System.out.println("Brak argumentow");
            return;
        }
        System.out.println("Liczba lizcb pierwszych = " + liczba(Integer.parseInt(args[0])));
    }

    public static BigInteger silnia(int x) {
        if (x==0) {
            return BigInteger.valueOf(1);
        }
        BigInteger result = BigInteger.valueOf(1);
        for (int i=1; i<=x; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static BigInteger liczba(int n) {
        BigInteger result = BigInteger.valueOf(-1);
        for (int j=3; j<=n; j++) {
            BigInteger factorial = silnia(j-2);
            BigDecimal flooredBigDecimal = new BigDecimal(factorial.divide(BigInteger.valueOf(j))).setScale(0, RoundingMode.FLOOR);
            BigInteger flooredBigInteger = flooredBigDecimal.toBigInteger();
            flooredBigInteger = flooredBigInteger.multiply(BigInteger.valueOf(j));
            result = result.add(factorial);
            result = result.subtract(flooredBigInteger);
        }
        return result;
    }
}
