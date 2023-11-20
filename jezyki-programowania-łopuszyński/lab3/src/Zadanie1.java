import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Zadanie1 {
    static ArrayList<Artykul> artykuly = new ArrayList<>();

    public static void main(String[] args) {

        wczytajPlik();

        Map<Artykul, Integer> koszyk = new HashMap<>();

        while (true) {

            System.out.println("Menu użytkownika");
            System.out.println("0 - Zakoncz dzialanie programy");
            System.out.println("1 - Znajdz artykul w sklepie");
            System.out.println("2 - Dodaj towar do koszyku");
            System.out.println("3 - Wycenic artykuly w koszyku");
            System.out.println("4 - Zrealizowac zamowienie");
            System.out.println("5 - Wyswietlic koszyk");
            System.out.println("6 - Wyswietl wszystkie dostepne artykulu w sklepie");

            System.out.println("\nMenu modyfikacji stanu magazynu");

            System.out.println("7 - Dodac artykul do magazynu");
            System.out.println("8 - Usunac artykul z magazynu");


            try {
                System.out.print("Wpisz liczbe: ");
                Scanner scanner = new Scanner(System.in);
                int decyzja = scanner.nextInt();
                switch (decyzja) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        System.out.println("Wpisz nazwe artykulu, ktorego chcesz znalezc: ");
                        String nazwaDoZnalezenia = new Scanner(System.in).nextLine();
                        try {
                             if (znajdzArtykul(nazwaDoZnalezenia) != null) {
                                 System.out.println("Artykul zostal znalieziony");
                             }
                             else {
                                 System.out.println("Artykul nie znalieziony");
                             }
                        }
                        catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                            dodajDoKosza(koszyk);
                        break;
                    case 3:
                        wycenaZamowienie(koszyk);
                        break;
                    case 4:
                        zrealizowacZamowienie(koszyk);
                        break;
                    case 5:
                        wyswietlicKoszyk(koszyk);
                        break;
                    case 6:
                        wyswietlDostepneArtykuly();
                        break;
                    case 7:
                        System.out.println("Podaj nazwe towaru, ktorego chcesz dodac do magazynu: ");
                        String nazwaDoDodania = new Scanner(System.in).nextLine();
                        System.out.println("Podaj kod towaru, ktorego chcesz dodac do magazynu: ");
                        String kodDoDodania = new Scanner(System.in).nextLine();
                        System.out.println("Podaj cene dla tego towaru, ktorego chcesz dodac do magazynu: ");
                        double cenaDoDodania = new Scanner(System.in).nextDouble();
                        dodajArtykul(kodDoDodania, nazwaDoDodania, cenaDoDodania);
                    case 8:
                        System.out.println("Wpisz nazwe artykulu, ktorego chcesz usunac z magazynu");
                        String nazwaDoUsuniencia = new Scanner(System.in).nextLine();
                        pobierzArtykul(nazwaDoUsuniencia);
                        break;
                    default:
                        System.out.println("Wpisales niepoprawna liczbe");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wpisales cos zlego.");
            }
        }
    }

    public static void wczytajPlik() {
        try {
            FileReader fr = new FileReader("src/magazyn.txt");
            BufferedReader br = new BufferedReader(fr);

            String str;
            while ((str = br.readLine()) != null) {
                str = str.replace(",", ".");
                String[] bufor = str.split("\\s");

                if (bufor.length != 3) {
                    throw new NumberFormatException();
                }

                String kod = bufor[0];
                if (kod.length() <= 4) {
                    int kodInt = Integer.parseInt(kod);
                    if (kodInt < 0)
                        throw new NumberFormatException("Kod towaru ujemny");
                }
                else {
                    throw new NumberFormatException("Kod towaru zawiera wiecej niż 4 cyfry");
                }
                String nazwa = bufor[1];
                if (nazwa.matches("\\d+")) {
                    throw new NumberFormatException("Nazwa towaru zawiera jest nieprawidlowa (zawiera cyfry)");
                }
                double cena = Double.parseDouble(bufor[2]);
                if (cena <= 0) {
                    throw new NumberFormatException("Cena towaru ujemna");
                }
                Artykul artykul = new Artykul(kod, nazwa, cena);
                artykuly.add(artykul);
            }

            wyswietlDostepneArtykuly();

        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Problem with reading file");
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println("You have wrong data in a file");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }


    public static Artykul znajdzArtykul(String nazwa) throws NumberFormatException{

        if (nazwa.matches("\\d+")) {
            throw new NumberFormatException("Nazwa zawiera cyfry");
        }

        for (int i=0; i<artykuly.size(); i++) {
            if (artykuly.get(i).getNazwa().toLowerCase().equals(nazwa.toLowerCase())) {
                return artykuly.get(i);
            }
        }
        return null;
    }

    public static Artykul dodajDoKosza(Map<Artykul, Integer> koszyk) throws NumberFormatException{
        System.out.println("Wpisz nazwe towaru, ktorego chcesz dodac: ");
        String nazwaDoDodania = new Scanner(System.in).nextLine();
        if (nazwaDoDodania.matches("\\d+")) {
            throw new NumberFormatException("Nazwa zawiera cyfry");
        }

        Artykul artykulDoDodania;
        if ((artykulDoDodania = znajdzArtykul(nazwaDoDodania)) != null) {
            System.out.println("Wpisz ilosc tego towaru, ktory chcesz dodac: ");
            int iloscDoDodania = new Scanner(System.in).nextInt();
            Integer ilosc = Integer.valueOf(iloscDoDodania);
            koszyk.put(artykulDoDodania, ilosc);
            return artykulDoDodania;
        }
        else {
            System.out.println("Artykul nie znaleziony");
            return null;
        }
    }


    public static double wycenaZamowienie(Map<Artykul, Integer> koszyk) {
        if (koszyk.size() == 0) {
            System.out.println("Koszyk na obecny moment jest pusty");
            return 0;
        }

        double cenaZamowenia = 0;
        for (int i=0; i<artykuly.size(); i++) {
            if (koszyk.containsKey(artykuly.get(i))) {
                int pobranaIlosc = koszyk.get(artykuly.get(i));
                cenaZamowenia += artykuly.get(i).getCena() * pobranaIlosc;
            }
        }
        System.out.println("Wynikowa cena zamowienia: " + cenaZamowenia);

        return cenaZamowenia;
    }

    public static Artykul dodajArtykul(String kod, String nazwa, double cena) throws NumberFormatException{

        if (znajdzArtykul(nazwa) != null) {
            throw new NumberFormatException("Taki samy towar juz istnieje w magazynie");
        }


        if (nazwa.matches("\\d+")) {
            throw new NumberFormatException("Nazwa zawiera cyfry");
        }

        if (kod.length() <= 4) {
            int kodInt = Integer.parseInt(kod);
            if (kodInt < 0)
                throw new NumberFormatException("Kod towaru ujemny");
        }
        else {
            throw new NumberFormatException("Kod towaru zawiera wiecej niż 4 cyfry");
        }

        if (nazwa.matches("\\d+")) {
            throw new NumberFormatException("Nazwa towaru zawiera jest nieprawidlowa (zawiera cyfry)");
        }

        if (cena <= 0) {
            throw new NumberFormatException("Cena towaru ujemna");
        }


        Artykul artykulDoDodania = new Artykul(kod, nazwa, cena);
        artykuly.add(artykulDoDodania);

        try (FileWriter fw = new FileWriter("src/magazyn.txt")) {

            for (int i=0; i<artykuly.size(); i++) {
                fw.write(artykuly.get(i).getKod() + " " + artykuly.get(i).getNazwa() + " " + artykuly.get(i).getCena() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Wystapil blad podczas nadpisywania pliku");
            artykuly.remove(artykuly.size()-1);
            return null;
        }

        System.out.println("Artykul zostal dodany do sklepu");

        return artykulDoDodania;
    }


    public static Artykul pobierzArtykul(String nazwa) throws NumberFormatException{

        if (nazwa.matches("\\d+")) {
            throw new NumberFormatException("Nazwa zawiera cyfry");
        }

        Artykul artykulDoUsuniencia;
        if ((artykulDoUsuniencia = znajdzArtykul(nazwa)) != null) {
            artykuly.remove(artykulDoUsuniencia);

            try (FileWriter fw = new FileWriter("src/magazyn.txt")) {

                for (int i=0; i<artykuly.size(); i++) {
                    fw.write(artykuly.get(i).getKod() + " " + artykuly.get(i).getNazwa() + " " + artykuly.get(i).getCena() + "\n");
                }

            } catch (IOException e) {
                System.out.println("Wystapil blad podczas usuniencia artykulu z pliku");
                return null;
            }
            System.out.println("Artykul zostal usunienty");
            return artykulDoUsuniencia;
        }
        else {
            System.out.println("Takiego artykulu nie istnieje w magazynie");
            return null;
        }
    }


    public static void wyswietlDostepneArtykuly() {

        if (artykuly.size() == 0) {
            System.out.println("Obecnie nie ma artykulow w sklepie");
        }

        System.out.println("Wszystkie artykuly dostepne w sklepie");
        for (int i=0; i<artykuly.size(); i++) {
            System.out.println("Kod: " + artykuly.get(i).getKod() + " Nazwa: " + artykuly.get(i).getNazwa() + " Cena: " + artykuly.get(i).getCena());
        }
        System.out.println();
    }


    public static void wyswietlicKoszyk(Map<Artykul, Integer> koszyk) {
        if (koszyk.size() == 0) {
            System.out.println("Kosz pusty");
            return;
        }

        System.out.println("Zawartosc koszyka:");
        for (int i=0; i<artykuly.size(); i++) {
            if (koszyk.containsKey(artykuly.get(i))) {
                System.out.println("Kod: " + artykuly.get(i).getKod() + " Nazwa: " + artykuly.get(i).getNazwa() + " Cena: " + artykuly.get(i).getCena() + " Ilosc: " + koszyk.get(artykuly.get(i)));
            }
        }
    }


    public static void zrealizowacZamowienie(Map<Artykul, Integer> koszyk) {

        if (koszyk.size() == 0) {
            System.out.println("Masz pusty koszyk");
            return;
        }

        for (int i=0; i<artykuly.size(); i++) {
            if (koszyk.containsKey(artykuly.get(i))) {
                Artykul artykulDoUsuniencia = artykuly.get(i);
                pobierzArtykul(artykulDoUsuniencia.getNazwa());
            }
        }
        System.out.println("Zamowienie zostalo zrealizowane");
    }
}



class Artykul {
    private String kod;
    private String nazwa;
    private double cena;

    public Artykul(String kod, String nazwa, double cena) {
        this.kod = kod;
        this.nazwa = nazwa;
        this.cena = cena;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Artykul{" +
                "kod=" + kod +
                ", nazwa='" + nazwa + '\'' +
                ", cena=" + cena +
                '}';
    }
}