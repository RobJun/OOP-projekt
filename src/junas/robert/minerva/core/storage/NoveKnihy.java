package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.items.Kniha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NoveKnihy extends Regal{
    private boolean minute;

    public NoveKnihy(String path){
        super();
        if(nacitajKnihy(path)) {
            minute = false;
        }else {
            minute = true;
        }
    }

    public boolean isMinute() {
        return minute;
    }
    private void skontrolujMinutie(){
        minute = (zoznamKnih.size() == 0);
    }
    @Override
    public int getMiesto() {
        return -1;
    }

    public void vyhodPaletu(){
        if(isMinute()){
            try {
                finalize();
                System.out.println("Paletu sme vyhodili");
                System.out.println();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                System.out.println();
            }
        }else{
            System.out.println("Na palete su stale knihy\n");
        }
    }


    public boolean nacitajKnihy(String path){
        try {
            File zoznam = new File(path);
            Scanner reader = new Scanner(zoznam);
            while(reader.hasNextLine()){
                String kh = reader.nextLine();
                // vo vstupnom subore su informacie o knihe rozdelene tromi /
               String[] kniha = kh.split("/{3}");
               Kniha k = new Kniha(kniha);
                this.pridajKnihyP(k,Integer.parseInt(kniha[3]));
            }
            System.out.println();
            reader.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Novy tovar sa nepodarilo nacitat");
            e.printStackTrace();
            return false;
        }
    }

    public void printContent(){
        for(int i = 0; i < zoznamKnih.size(); i++){

                zoznamKnih.get(i).printContent();
                System.out.print(" [" + pocetKnih.get(zoznamKnih.get(i).getISBN()) + "]\n");
        }
    }

    @Override
    public int odoberKnihy(Kniha k, int p){
        int v = odoberKnihyP(k,p);
        skontrolujMinutie();
        return v;

    }

    @Override
    public int pridajKnihy(Kniha k, int p){
        System.out.println("Na paletu sa nedaju dat knihy");
        return -1;
    }

}
