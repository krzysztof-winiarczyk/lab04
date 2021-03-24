package pollub.ism.lab04;

import android.os.Parcel;
import android.os.Parcelable;

public class Plansza implements Parcelable {
    private String tab[][];
    private int wolnePola;
    private int czyKoniec; //0-jeśli gra toczy się dalej, 1-jeśli koniec

    public Plansza(){
        tab = new String[3][3];
        init();
    }

    //gettery
    public String getCellOfTab(int x, int y){
       return tab[x][y];
   }

    public int getCzyKoniec(){
        return  czyKoniec;
    }

    //funkcja inicjalizuje planszę
    public void init(){
        wolnePola = 9;
        czyKoniec = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++) {
                tab[i][j] = ".";
            }
        }
    }

    //funkcja ustawia pole planszy
    public void ustawPole(int x, int y, String znak){
        this.tab[x][y] = znak;
        wolnePola--;
    }

    //funckja sprawdzająca czy nastąpiła wygrana bądź koniec gry
    public int czyKoniec(){
        /*
            ZWRACANE WARTOŚCI:
                0 - gra nie jest skończona
                1 - remis
                2 - wygrał X
                3 - wygrał O
         */

        for (int i=0; i<3; i++){
            //poziomo
            if (tab[i][0] == tab[i][1] && tab[i][1] == tab[i][2]) {
                if (tab[i][0] == "X") {
                    czyKoniec = 1;
                    return 2;
                } else if (tab[i][0] == "O") {
                    czyKoniec = 1;
                    return 3;
                }
            }
            //pionowo
            if (tab[0][i] == tab[1][i] && tab[1][i] == tab[2][i]) {
                if (tab[0][i] == "X") {
                    czyKoniec = 1;
                    return 2;
                } else if (tab[0][i] == "O") {
                    czyKoniec = 1;
                    return 3;
                }
            }
        }

        //ukosem
        if (tab[0][0] == tab[1][1] && tab[1][1] == tab[2][2]){
            if (tab[1][1] == "X") {
                czyKoniec = 1;
                return 2;
            } else if (tab[1][1] == "O") {
                czyKoniec = 1;
                return 3;
            }
        }
        if (tab[0][2] == tab[1][1] && tab[1][1] == tab[2][0]){
            if (tab[1][1] == "X") {
                czyKoniec = 1;
                return 2;
            } else if (tab[1][1] == "O") {
                czyKoniec = 1;
                return 3;
            }
        }

        if (wolnePola == 0) {
            czyKoniec = 1;
            return 1;
        }
        else {
            return 0;
        }
    }

    //związane z interfejsem PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(tab[0]);
        dest.writeStringArray(tab[1]);
        dest.writeStringArray(tab[2]);
        dest.writeInt(wolnePola);
        dest.writeInt(czyKoniec);
    }

    protected Plansza(Parcel in) {
        wolnePola = in.readInt();
    }

    public static final Creator<Plansza> CREATOR = new Creator<Plansza>() {
        @Override
        public Plansza createFromParcel(Parcel in) {
            return new Plansza(in);
        }

        @Override
        public Plansza[] newArray(int size) {
            return new Plansza[size];
        }
    };
}
