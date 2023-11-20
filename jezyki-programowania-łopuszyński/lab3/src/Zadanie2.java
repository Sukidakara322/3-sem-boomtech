import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Zadanie2 {

    public static void main(String[] args) {
        // przyklad argumentow (java src/Zadanie2.java src/imiona1.txt src/imiona2.txt src/imiona3.txt)

        if (args.length != 3) {
            System.out.println("Podales nie trzy argumenty");
            System.exit(0);
        }

        for (int plik=0; plik<3; plik++) {
            ArrayList<Imie> litery = new ArrayList<>();

            try {
                FileReader fr = new FileReader(args[plik]);
                BufferedReader br = new BufferedReader(fr);

                Imiona[] wszystkieImiona = Imiona.values();

                String str;
                while ((str = br.readLine()) != null) {
                    String[] bufor = str.split("\\s");
                    for (int i=0; i<bufor.length; i++) {

                        for (int j=0; j<wszystkieImiona.length; j++) {
                            String imieString = wszystkieImiona[j].toString();
                            if (imieString.toLowerCase().equals(bufor[i].toLowerCase())) {

                                char firstLetter = bufor[i].toLowerCase().charAt(0);

                                Imiona znalezioneImie = wszystkieImiona[j];
                                Imie noweImie = new Imie(firstLetter);
                                noweImie.dodajNoweImie(znalezioneImie);

                                boolean znaleziono = false; // zmienna zeby sprawdzac

                                if (litery.size() == 0) { // dodaje nowa litere w przypadku pustej listy czy ta litera pojawila sie juz na liscie, czy nie?
                                    litery.add(noweImie);
                                }
                                else {
                                    for (int g = 0; g < litery.size(); g++) { // sprawdzamy czy lista zawiera juz ta litere
                                        if (litery.get(g).getLitera() == firstLetter) {
                                            litery.get(g).dodajNoweImie(znalezioneImie);
                                            znaleziono = true;
                                        }
                                    }
                                    if (!znaleziono) {
                                        litery.add(noweImie);
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("\nWszystkie imiona, ktore wystepily w pliku " + "(" + args[plik] + ")");
                for (int i=0; i<litery.size(); i++) {
                    System.out.println(litery.get(i).toString());
                }
                System.out.print("\n");


            }
            catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(0);
            }
            catch (IOException e) {
                System.out.println("Problem with reading the file");
            }
        }

    }


    public enum Imiona { // ta lista zawiera wszystkie imiona ktorych szukamy
        Hanna,
        Dzmitry,
        Maria,
        Krzysztof,
        Maciej,
        Roman
    }

    public static class Imie{
        private char litera;
        private Map<Imiona, Integer> wystapienia = new HashMap<>(); // lista zawiera imiona i ilosc wystapien


        public Imie(char litera) {
            this.litera = litera;
        }


        public void dodajNoweImie(Imiona imie) { // metoda dodania nowego imia do listy
                zwiekszLicznik(imie);
                wystapienia.putIfAbsent(imie, 1);
            }

            private void zwiekszLicznik(Imiona imie) { // metoda dla zwiekszenia liczniku juz istniejacego imia
                for (Map.Entry<Imiona, Integer> wystapioneImie : wystapienia.entrySet()) {
                    if (wystapioneImie.getKey().equals(imie)) {
                        wystapienia.merge(imie, 1, Integer::sum);
                        return;
                    }
                }
            }


        public char getLitera() {
            return litera;
        }

        public void setLitera(char litera) {
            this.litera = litera;
        }

        public Map<Imiona, Integer> getWystapienia() {
            return wystapienia;
        }

        public void setWystapienia(Map<Imiona, Integer> wystapienia) {
            this.wystapienia = wystapienia;
        }


        @Override
        public String toString() {
            return litera + ": " + wystapienia;
        }
    }
}
