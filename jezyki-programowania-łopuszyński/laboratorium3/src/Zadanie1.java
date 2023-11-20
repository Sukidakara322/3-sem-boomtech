import java.io.*;
import java.util.Scanner;

public class Zadanie1 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/magazyn.txt"));
            String str;
            dodajArtykul();
            while ((str = br.readLine()) != null) {
                System.out.println(str);
//                String[] bufor = str.split("\\s");
//                for (int i=0; i<bufor.length; i++) {
//                    System.out.println(bufor[i]);
//                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dodajArtykul() {
        try {
            FileWriter fw = new FileWriter("src/magazyn.txt", true);
            BufferedWriter bf = new BufferedWriter(fw);

            System.out.println("Wpisz artykul");
            Scanner sc = new Scanner(System.in);
            String artykul = sc.nextLine();

            bf.write(artykul);

            fw.close();
            bf.close();
        }
        catch (IOException e) {
            System.err.println("Wystapił bład podczas dodawania tekstu do pliku.");
            e.printStackTrace();
        }
    }
}