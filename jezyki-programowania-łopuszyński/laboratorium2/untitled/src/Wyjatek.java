import java.util.List;

public class Wyjatek extends Exception {
  public Wyjatek(List<Samochod> samochody) {
    super(String.valueOf(samochody));
  }
}