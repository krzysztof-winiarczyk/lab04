package pollub.ism.lab04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String KEY_PLANSZA = "Plansza",
                            KEY_LICZNIK = "Licznik",
                            KEY_KOLEJKA = "Kolejka";

    private boolean czyKrzyzyki = true;
    private Plansza plansza;
    private Licznik licznik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plansza = new Plansza();
        licznik = new Licznik();
        aktualizujLiczniki();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_PLANSZA, plansza);
        outState.putParcelable(KEY_LICZNIK, licznik);
        outState.putBoolean(KEY_KOLEJKA, czyKrzyzyki);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        plansza = savedInstanceState.getParcelable(KEY_PLANSZA);
        przywrocPlansze();

        licznik = savedInstanceState.getParcelable(KEY_LICZNIK);
        aktualizujLiczniki();

        czyKrzyzyki = savedInstanceState.getBoolean(KEY_KOLEJKA);
    }

    public void koniecGry(){
        //funckcja blokuje klikalność przycisków planszy
        Button przycisk = null;

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                String nazwaPrzycisku = "button" + "_" + String.valueOf(i) + "_" + String.valueOf(j);
                int idPrzycisku = getResources().getIdentifier(nazwaPrzycisku, "id", getPackageName());
                przycisk = findViewById(idPrzycisku);
                przycisk.setClickable(false);
            }
        }
    }

    public void kliknieciePrzycisku(View view){
        //funkcja obsługuje klinięcie pola na planszy
        String znak =  kolkoCzyKrzyzyk();

        Button przycisk = (Button) findViewById(view.getId());
        przycisk.setText(znak);
        przycisk.setClickable(false);

        //wyłuskaj współżędne z nazwy przycisku
        String nazwaPrzycisku = view.getResources().getResourceEntryName(view.getId());
        String zNazwyPrzycisku[] = nazwaPrzycisku.split("_");
        int x = Integer.parseInt(zNazwyPrzycisku[1]);
        int y = Integer.parseInt(zNazwyPrzycisku[2]);
        //ustaw znak w odpowiedniej komórce tablicy
        plansza.ustawPole(x,y,znak);

        int kod = plansza.czyKoniec();
        obsluzStatusGry(kod);
    }

    public void obsluzStatusGry(int kod){
        //funckja wyświetla odpowiedni tost w razie wygranej
        switch (kod){
            case 0: break;
            case 1:
                //remis
                Toast.makeText(this,"REMIS", Toast.LENGTH_LONG).show();
                koniecGry();
                licznik.incrementRemisy();
                aktualizujLiczniki();
                break;
            case 2:
                //wygrana X
                Toast.makeText(this,"Wygrały X", Toast.LENGTH_LONG).show();
                koniecGry();
                licznik.incrementZwyciestwaX();
                aktualizujLiczniki();
                break;
            case 3:
                //wygrana O
                Toast.makeText(this,"Wygrały O", Toast.LENGTH_LONG).show();
                koniecGry();
                licznik.incrementZwyciestwaO();
                aktualizujLiczniki();
                break;
            default: break;
        }
    }

    public String kolkoCzyKrzyzyk(){
        if (czyKrzyzyki){
            czyKrzyzyki = !czyKrzyzyki;
            return "X";
        }
        else {
            czyKrzyzyki = !czyKrzyzyki;
            return "O";
        }
    }

    public void nowaGra(View view){
        Button przycisk = null;

        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                String nazwaPrzycisku = "button" + "_" + String.valueOf(i) + "_" + String.valueOf(j);
                int idPrzycisku = getResources().getIdentifier(nazwaPrzycisku, "id", getPackageName());

                przycisk = findViewById(idPrzycisku);
                //przywróć przyciskowi stan poczatkowy
                przycisk.setText("");
                przycisk.setClickable(true);
            }
        }

        //przywróć stan początkowy tablicy
        plansza.init();
    }

    public void wyzerujLicznik(View view){
        licznik.zeruj();
        aktualizujLiczniki();
    }

    public void aktualizujLiczniki(){
        //funckcja aktualizuje liczniki (pola wyświetlane w aplikacji)
        int idLicznikX = getResources().getIdentifier("textXPoints", "id", getPackageName());
        int idLicznikO = getResources().getIdentifier("textOPoints", "id", getPackageName());
        int idLicznikRemisow = getResources().getIdentifier("textDrawPoints", "id", getPackageName());

        TextView licznikX = findViewById(idLicznikX);
        TextView licznikO = findViewById(idLicznikO);
        TextView licznikRemisow = findViewById(idLicznikRemisow);

        licznikX.setText(String.valueOf(licznik.getZwyciestwaX()));
        licznikO.setText(String.valueOf(licznik.getZwyciestwaO()));
        licznikRemisow.setText(String.valueOf(licznik.getRemisy()));
    }

    public void przywrocPlansze(){
        Button przycisk = null;

        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                String nazwaPrzycisku = "button" + "_" + String.valueOf(i) + "_" + String.valueOf(j);
                int idPrzycisku = getResources().getIdentifier(nazwaPrzycisku, "id", getPackageName());

                przycisk = findViewById(idPrzycisku);
                //przywróć przyciskowi stan poczatkowy
                if (plansza.getCellOfTab(i,j) == "."){
                    przycisk.setText("");
                    if (plansza.getCzyKoniec() == 1){
                        przycisk.setClickable(false);
                    }
                    else{
                        przycisk.setClickable(true);
                    }

                }
                else if (plansza.getCellOfTab(i,j) == "X" || plansza.getCellOfTab(i,j) == "O") {
                   przycisk.setText(plansza.getCellOfTab(i,j));
                   przycisk.setClickable(false);
                }
            }
        }
    }
}