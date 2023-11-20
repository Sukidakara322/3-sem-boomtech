import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        if (args.length > 0) {
            if ((args[0].matches("\\d+") == false) || ((Objects.equals(args[1], "W") == false) && (Objects.equals(args[1], "R") == false))) {
                System.out.println("Podales zle argumenty");
                return;
            }
        }
        else {
            System.out.println("Brak argumentow");
            return;
        }


        ArrayList<Samochod> cars = new ArrayList<>();
        for (int i=0; i<Integer.valueOf(args[0]); i++) {
            cars.add(new Samochod());
        }
        EasyReader reader = new EasyReader();
        System.out.println("Wybierz kryterium wyboru");
        System.out.println("0 - najstarszy");
        System.out.println("1 - nie starszy niz ROK");
        System.out.println("2 - Najmlodszy");
        System.out.println("3 - Nie mlodszy niz ROK");
        System.out.println("Wybierz odpowiednia liczbe");
        int wybor = 0;
        try {
            wybor = reader.readInt();
        if (wybor > 3 || wybor < 0) {
            System.out.println("Podales zly wybor");
                return;
        }
        }
        catch (NumberFormatException e) {
            System.out.println("Wpisales nie liczbe");
            return;
        }

        int rok = 0;

        try {
            if (wybor == 1 || wybor == 3) {
            System.out.println("Podaj rok: ");
            rok = reader.readInt();
        }
        }
        catch (NumberFormatException e) {
            System.out.println("Wpisales nie liczbe");
            return;
        }

        if (rok >= 2023 || rok < 0) {
            System.out.println("Wpisales zly rok");
            return;
        }

        System.out.println(cars.toString());

        try {
        switch(wybor) {
            case 0:
                System.out.println("\n\n\nWynik\n" + Kryterium.najstarszy(cars, args[1]));
                break;
            case 1:
                System.out.println("\n\n\nWynik\n" + Kryterium.nieStarszyNiz(cars, rok, args[1]));
                break;    
            case 2:
                System.out.println("\n\n\nWynik\n" + Kryterium.najmlodszy(cars, args[1]));
                break;
            case 3:
                System.out.println("\n\n\nWynik\n" + Kryterium.nieMlodszyNiz(cars, rok, args[1]));
                break;
            default:
                System.out.println("Wpisales nie poprawna liczbe");
                System.exit(0);
                break;    
        }
    }
        catch (Wyjatek e){
            System.out.println("\n\n\nException");
            System.out.println(e.getSamochody());
        }

    }

}




enum Brand {
    POLONEZ,
    FIAT,
    SYRENA
}


 class Samochod {
    private Brand marka;
    private int cena;
    private int rok;

    public Samochod() {
        Random random = new Random(); // losowanie marki
        marka = Brand.values()[random.nextInt(Brand.values().length)];

        cena = (int)Math.floor(Math.random() * (15000 - 4000 + 1) + 4000); // losowanie ceny, cena 4000-15000

        rok = (int)Math.floor(Math.random() * (2023 - 1980 +1) + 1980); // losowanie roku, 1980-2023
    }

    public Samochod(Brand marka, int cena, int rok) {
        this.marka = marka;
        this.cena = cena;
        this.rok = rok;
    }

    public Brand getMarka() {
        return marka;
    }

    public void setMarka(Brand marka) {
        this.marka = marka;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    @Override
    public String toString() {
        return "Samochod{" +
                "marka=" + marka +
                ", cena=" + cena +
                ", rok=" + rok +
                "}\n";
    }
}


class Wyjatek extends Exception {
    private ArrayList<Samochod> samochody;
    
    public Wyjatek(ArrayList<Samochod> samochody) {
        super("EXCEPTION");
        this.samochody = samochody;
    }

    public ArrayList<Samochod> getSamochody() {
        return samochody;
    }
}



class Kryterium {
    

    public Kryterium() {
        
    }

    public static ArrayList<Samochod> najstarszy(ArrayList<Samochod> cars, String metoda) throws Wyjatek{
        ArrayList<Samochod> listaSamochodow = new ArrayList<>();
        int position = 0;
        int max = cars.get(0).getRok();
        for (int i=0; i<cars.size(); i++) {
            if (cars.get(i).getRok() < max) {
                max = cars.get(i).getRok();
                position = i;
            }
        }
        listaSamochodow.add(cars.get(position));
        if (Objects.equals(metoda, "R")) {
            return listaSamochodow;
        }
        else {
            throw new Wyjatek(listaSamochodow);
        }
    }    

    public static ArrayList<Samochod> nieStarszyNiz(ArrayList<Samochod> cars, int rok, String metoda) throws Wyjatek{
        ArrayList<Samochod> newCars = new ArrayList<>();
        for (int i=0; i<cars.size(); i++) {
            if (cars.get(i).getRok() > rok) {
                newCars.add(cars.get(i));
            }
        }
        if (Objects.equals(metoda, "R")) {
            return newCars;
        }
        else {
            throw new Wyjatek(newCars);
        }
    }
    
    public static ArrayList<Samochod> najmlodszy(ArrayList<Samochod> cars, String metoda) throws Wyjatek{
        ArrayList<Samochod> listaSamochodow = new ArrayList<>();
        int position = 0;
        int min = cars.get(0).getRok();
        for (int i=0; i<cars.size(); i++) {
            if (cars.get(i).getRok() > min) {
                min = cars.get(i).getRok();
                position = i;
            }
        }
        listaSamochodow.add(cars.get(position));
        if (Objects.equals(metoda, "R")) {
            return listaSamochodow;
        }
        else {
            throw new Wyjatek(listaSamochodow);
        }
    }

    public static ArrayList<Samochod> nieMlodszyNiz(ArrayList<Samochod> cars, int rok, String metoda) throws Wyjatek{
        ArrayList<Samochod> newCars = new ArrayList<>();
        for (int i=0; i<cars.size(); i++) {
            if (cars.get(i).getRok() < rok) {
                newCars.add(cars.get(i));
            }
        }
        if (Objects.equals(metoda, "R")) {
            return newCars;
        }
        else {
            throw new Wyjatek(newCars);
        }
    }

}
