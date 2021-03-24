package junas.robert.lagatoria.core.knihkupectvo.storage;

import junas.robert.lagatoria.core.items.*;
import junas.robert.lagatoria.core.utils.exceptions.InvalidFormatException;
import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.core.utils.enums.Vazba;
import junas.robert.lagatoria.gui.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Scanner;

public class NoveKnihy extends Regal{
    private boolean minute;

    public NoveKnihy(String path){
        super();
        try {
            minute = !nacitajKnihy(path);
        } catch (InvalidFormatException e) {
            Controller.printline(e.getMessage());
            if(e.getLoadedRows() > 0){
                Controller.printline("podarilo sa nacitat " + e.getLoadedRows() + " riadkov");
                minute = false;
            }
            else{
                minute = true;
            }
        }
    }

    public NoveKnihy(Queue<Kniha> knihy, Queue<Integer> pocetKnih){
        super();
        minute = knihy.isEmpty();
        for (Kniha k : knihy){
            this.pridajKnihyP(k,pocetKnih.remove());
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
                Controller.printline("Paletu sme vyhodili");
                Controller.printline("");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Controller.printline("");
            }
        }else{
            Controller.printline("Na palete su stale knihy\n");
        }
    }


    public boolean nacitajKnihy(String path) throws InvalidFormatException {
        try {
            File zoznam = new File(path);
            Scanner reader = new Scanner(zoznam);
            int row = 1;
            while(reader.hasNextLine()){
                String kh = reader.nextLine();
                // vo vstupnom subore su informacie o knihe rozdelene tromi /
               String[] kniha = kh.split("/{3}");
               if(kniha.length != 11
                       || !kniha[4].matches("[0-9]+")
                       || !kniha[10].matches("[0-9]+")
                       || !kniha[3].matches("[0-9]+")
                       || !kniha[5].matches("[-+]?[0-9]+\\.?[0-9]+")) {
                   throw new InvalidFormatException("Kniha na " + row + " riadku je zadana v zlom formate",row-1);
               }
               row++;

               Text text = new Text(kniha[0],kniha[1],kniha[7],Integer.parseInt(kniha[4]), Kategoria.valueOf(kniha[6]),true);

               Obalka obalka = null;
               if(Vazba.valueOf(kniha[8]) == Vazba.BROZOVANA){
                   obalka = new BrozovanaVazba("normalny", "cervena", "pekny");
               }else if(Vazba.valueOf(kniha[8]) == Vazba.PEVNA){
                   obalka = new PevnaVazba("normalny", "cervena", "koza");
               }

               Kniha k = new Kniha(kniha[2],kniha[9],Integer.parseInt(kniha[10]),Float.parseFloat(kniha[5]));

               k.pridajSucast(text);
               k.pridajSucast(obalka);

                this.pridajKnihyP(k,Integer.parseInt(kniha[3]));
            }
            System.out.println();
            reader.close();
            return true;
        } catch (FileNotFoundException e) {
            Controller.printline("Novy tovar sa nepodarilo nacitat");
            return false;
        }
    }

    public void printContent(){
        for(int i = 0; i < zoznamKnih.size(); i++){

            Controller.printline(" [" + pocetKnih.get(zoznamKnih.get(i).getISBN()) + "]\n");
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
        Controller.printline("Na paletu sa nedaju dat knihy");
        return -1;
    }

}
