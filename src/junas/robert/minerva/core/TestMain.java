package junas.robert.minerva.core;

import junas.robert.minerva.core.items.Kniha;
import junas.robert.minerva.core.users.Skladnik;


///Tato classa je iba na testovanie funckii v knihkupectva
///Zatial je to iba automaticke vyplnenie skladu
///ma byt v UML?
public class TestMain {

    public static void main(String[] args) {
        Knihkupectvo knihk = new Knihkupectvo();
        Skladnik user =new Skladnik("Jozo", 21312321);
        user.objednajtovar(knihk.getSklad(), "./novyTovar.txt");

        knihk.getSklad().getNovyTovar().printContent();
        user.umiestniNovyT(knihk.getSklad());


        knihk.getSklad().getNovyTovar().printContent();

        while(knihk.getSklad().getNovyTovar().getZoznamKnih().size() != 0){

            Kniha k = knihk.getSklad().getNovyTovar().getZoznamKnih().get(0);
            user.odoberKnihyZRegalu(knihk.getSklad(),k,knihk.getSklad().getNovyTovar().getPocetKnih(k.getISBN()),null);

            System.out.println();
            knihk.getSklad().getNovyTovar().printContent();

            while(user.getPocetNosenych() > 0) {
                int[] miesto = knihk.getSklad().najdiNajvacsieMiesto();
                user.umiestniKnihyDoRegalu(knihk.getSklad(), miesto, miesto[2]);
            }
        }
        knihk.getSklad().getNovyTovar().vyhodPaletu();

        knihk.getSklad().printSklad();

        knihk.getSklad().printKatalog();



        Kniha ref = user.najdReferenciuNaKnihu(knihk.getSklad(),"ISBN-978-80-556-0637-8");
        user.odoberKnihyZRegalu(knihk.getSklad(), ref,100,new int[]{3,1});
        user.umiestniKnihyDoRegalu(knihk.getSklad(),new int[]{2,2},100);

        knihk.getSklad().printSklad();

        user.pridajHodinu();
        user.pridajHodinu();
        user.pridajHodinu();
        user.pridajHodinu();

        System.out.println(user.vypocitajPlat());
    }
}
