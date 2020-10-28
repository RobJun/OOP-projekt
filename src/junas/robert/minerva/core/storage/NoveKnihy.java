package junas.robert.minerva.core.storage;

import junas.robert.minerva.core.utils.Kategoria;
import junas.robert.minerva.core.items.Kniha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NoveKnihy extends Regal{
    private boolean exhausted;
    private boolean[] exhaust;

    public NoveKnihy(String path){
        super();
        if(nacitajTovar(path)) {
            exhausted = false;
            exhaust = new boolean[zoznamKnih.size()];
            for(boolean b : exhaust){
                b = false;
            }
        }else {
            exhausted = true;
        }
    }

    public boolean isExhausted() {
        return exhausted;
    }

    public void setExhaust(int index, boolean b) {
        this.exhaust[index] = b;
        if(checkExhaust()) exhausted = true;
    }

    public void VyhodPaletu(){
        if(isExhausted()){
            try {
                finalize();
                System.out.println("Paletu sme vyhodili");
                System.out.println();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                System.out.println("Paletu sa nepodarilo vyhodit");
                System.out.println();
            }
        }
    }

    private boolean checkExhaust(){
        for(boolean b : exhaust){
            if(b == false) return false;
        }
        return true;
    }

    @Override
    public int getMiesto() {
        return -1;
    }

    public boolean nacitajTovar(String path){
        try {
            File zoznam = new File(path);
            Scanner reader = new Scanner(zoznam);
            while(reader.hasNextLine()){
                String kh = reader.nextLine();
                System.out.println(kh);
                // vo vstupnom subore su informacie o knihe rozdelene tromi /
               String[] kniha = kh.split("/{3}");
               Kniha k = new Kniha(kniha[0], kniha[1],kniha[2], Integer.parseInt(kniha[4]), Float.parseFloat(kniha[5]), Kategoria.valueOf(kniha[6]));
                this.addBooks(k,Integer.parseInt(kniha[3]));
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
}
