public class App2 {
  public static void main(String[] args) {
    if (args.length > 0) {
      if (args[0].matches("\\d+")) { 
          // sprawdzenie czy argument jest liczba
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

  long n = Long.parseLong(args[0]);
    for (int i=0; i<=1000; i++) {
      if (kolejna(n) > Long.MAX_VALUE || kolejna(n) < Long.MIN_VALUE) {
        System.out.println("Przekroczenie zakresu zmiennej long");
      }
      System.out.println( n + "," + (n%2==0 ? " parzysta, " : " nieparzysta, ") + kolejna(n));
      n = kolejna(n);
    }
    System.out.println("Program znalazl n_1000");
  }

  public static long kolejna(long n) {
    if (n%2 == 0) { // parzysta
      if (n/2 == 1 ) {
        System.out.println("STOP, kolejna liczba jest równa 1");
        System.exit(0);
      }
      return n/2;
    }
    else { // nieparzysta
      return n*3 + 1; 
    }
  }
}