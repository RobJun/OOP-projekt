package junas.robert.lagatoria.core.knihkupectvo.storage;

import junas.robert.lagatoria.core.knihkupectvo.items.*;
import junas.robert.lagatoria.core.utils.Kategoria;
import junas.robert.lagatoria.core.utils.Vazba;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NoveKnihy extends Regal{
    private boolean minute;

    public NoveKnihy(String path){
        super();
        minute = !nacitajKnihy(path);
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

               Text text = new Text(kniha[0],kniha[1],kniha[7],Integer.parseInt(kniha[4]), Kategoria.valueOf(kniha[6]));

               Obalka obalka = null;
               if(Vazba.valueOf(kniha[8]) == Vazba.BROZOVANA){
                   obalka = new BrozovanaVazba("normalny", "cervena", "pekny");
               }else if(Vazba.valueOf(kniha[8]) == Vazba.PEVNA){
                   obalka = new PevnaVazba("normalny", "cervena", "koza");
               }

               Kniha k = new Kniha(kniha[0],kniha[1],kniha[2],kniha[9],Integer.parseInt(kniha[10]),Float.parseFloat(kniha[5]));

               k.pridajSucast(text);
               k.pridajSucast(obalka);

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

            System.out.print(" [" + pocetKnih.get(zoznamKnih.get(i).getISBN()) + "]\n");
                zoznamKnih.get(i).printContent();
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
