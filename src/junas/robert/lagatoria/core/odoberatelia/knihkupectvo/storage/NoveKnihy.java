package junas.robert.lagatoria.core.odoberatelia.knihkupectvo.storage;

import junas.robert.lagatoria.core.items.*;
import junas.robert.lagatoria.core.users.info.RadKnih;
import junas.robert.lagatoria.core.utils.exceptions.InvalidFormatException;
import junas.robert.lagatoria.core.utils.enums.Kategoria;
import junas.robert.lagatoria.core.utils.enums.Vazba;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Nacitanie novych knih do knihkupectva
 * nacitavnie zo suboru ako aj prijmanie od vydavatela
 */
public class NoveKnihy extends Regal{
    /**
     * ci boli vsetky knihy odobrne z palety
     */
    private boolean minute;

    /**
     * Nacita nove knihy zo suboru
     * @param path cesta k suboru z ktoreho chceme nacitat knihy
     * @throws FileNotFoundException ak sa subor nenanasiel
     * @throws InvalidFormatException ak knihy v subore boli zadane v zlom formate
     */
    public NoveKnihy(String path) throws FileNotFoundException, InvalidFormatException{
        super();
        try {
            minute = !nacitajKnihy(path);
        } catch (InvalidFormatException e) {
            if(e.getLoadedRows() > 0){
                minute = false;
            }
            else{
                minute = true;
            }
            throw e;
        } catch (FileNotFoundException e){
            minute = true;
            throw e;
        }
    }

    /**
     * Dostane knihy od vydavatela/distributora
     * @param knihy rad knih ktore chceme pridat do knihkupectva
     */
    public NoveKnihy(RadKnih knihy){
        super();
        minute = knihy.isEmpty();
        for (BalikKnih k : knihy.getKnihy()){
            this.pridajKnihyP((Kniha)k.getKniha(),k.getPocet());
        }
    }

    /**
     * @return ci boli vsetky knihy z palety premiestnene do regalov
     */
    public boolean isMinute() {
        return minute;
    }

    /**
     * nastavi premenu minute podla toho ci na zozname knih zostali knihy
     */
    private void skontrolujMinutie(){
        minute = (zoznamKnih.size() == 0);
    }

    /**
     * @deprecated
     * Paleta je derivat regalu a neda sa nan ukladat
     * @return vzdy vracia -1
     */
    @Override
    public int getMiesto() {
        return -1;
    }

    /**
     * @return retazec o uspechu vyhodenia palety
     */
    public String vyhodPaletu(){
        if(isMinute()){
            try {
                return "Paletu sme vyhodili";
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else{
           return "Na palete su stale knihy\n";
        }
        return "nastal problem s vyhadzovanim";
    }


    /**
     * Nacita knihy zo suboru - vyuzite v konstruktore
     * @param path cesta k suboru
     * @return vzdy vracia true
     * @throws InvalidFormatException ak knihy v subore boli zadane v zlom formate
     * @throws FileNotFoundException ak sa subor nenanasiel
     */
    private boolean nacitajKnihy(String path) throws InvalidFormatException, FileNotFoundException {
            File zoznam = new File(path);
            Scanner reader = new Scanner(zoznam);
            int row = 1;
            while(reader.hasNextLine()){
                String kh = reader.nextLine();
                // vo vstupnom subore su informacie o knihe rozdelene tromi /
               String[] kniha = kh.split("/{3}");
               if(kniha.length != 11
                       || !kniha[4].matches("[1-9][0-9]*")
                       || !kniha[10].matches("[1-9][0-9]*")
                       || !kniha[3].matches("[1-9][0-9]*")
                       || !kniha[5].matches("[1-9][0-9]*\\.?[0-9]*")) {
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
    }

    /**
     * @return vrati retazec obsahujuci zoznam knih potrebnych na ulozenie
     */
    public String printContent(){
        String s = "Tovar na palete:\n";
        for(int i = 0; i < zoznamKnih.size(); i++){
            s +=" [" + pocetKnih.get(zoznamKnih.get(i).getISBN()) + "]\n" +
             zoznamKnih.get(i).getInfo();
        }
        return s;
    }

    /**
     * Odoberie knihu a pocet kusov z noveho tovaru
     * @param kniha odoberna kniha
     * @param pocet odoberany pocet
     * @return pocet, ktory bol realne odobrany
     */
    @Override
    public int odoberKnihy(Kniha kniha, int pocet){
        int v = odoberKnihyP(kniha,pocet);
        skontrolujMinutie();
        return v;

    }

    /**
     * @deprecated
     * @param kniha pridavana kniha
     * @param pocet odoberana kniha
     * @return pocet pridanych knih vzdy -1
     */
    @Override
    public int pridajKnihy(Kniha kniha, int pocet){
        return -1;
    }

}
