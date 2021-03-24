package pollub.ism.lab04;


import android.os.Parcel;
import android.os.Parcelable;

public class Licznik implements Parcelable {

    private int zwyciestwaX, zwyciestwaO, remisy;

    public Licznik(){
        remisy = 0;
        zwyciestwaX = 0;
        zwyciestwaO = 0;
    }

    protected Licznik(Parcel in) {
        zwyciestwaX = in.readInt();
        zwyciestwaO = in.readInt();
        remisy = in.readInt();
    }

    public int getZwyciestwaX() {
        return zwyciestwaX;
    }

    public int getZwyciestwaO() {
        return zwyciestwaO;
    }

    public int getRemisy() {
        return remisy;
    }

    public void incrementZwyciestwaX(){
        zwyciestwaX++;
    }

    public void incrementZwyciestwaO(){
        zwyciestwaO++;
    }

    public void incrementRemisy(){
        remisy++;
    }

    public void zeruj(){
        remisy = 0;
        zwyciestwaX = 0;
        zwyciestwaO = 0;
    }

    //związane z interfacem PARCELABLE
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(zwyciestwaX);
        dest.writeInt(zwyciestwaO);
        dest.writeInt(remisy);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Licznik> CREATOR = new Creator<Licznik>() {
        @Override
        public Licznik createFromParcel(Parcel in) {
            return new Licznik(in);
        }

        @Override
        public Licznik[] newArray(int size) {
            return new Licznik[size];
        }
    };
}
