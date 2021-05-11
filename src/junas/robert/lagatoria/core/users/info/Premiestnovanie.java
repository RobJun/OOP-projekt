package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.odoberatelia.knihkupectvo.rooms.Miestnost;

/**
 * Vyhladavanie referencii na knihy v katalogoch a
 * metoda na premiestnovanie
 */
public interface Premiestnovanie {

    /**
     * @param miestnost sklad alebo predajna, podla toho v akom katalogu hladame
     * @param i katalogove cislo
     * @return referenciu na knihu ulozenu v katalogu
     */
    default Kniha najdiReferenciuNaKnihu(Miestnost miestnost, int i){
        return  (miestnost.getKatalog().isEmpty() || i >= miestnost.getKatalog().size() ) ? null : miestnost.getKatalog().get(i);
    }

    /**
     * @param miestnost sklad alebo predajna, podla toho v akom katalogu hladame
     * @param id ISBN hodnota knihy , ktoru chceme najst
     * @return referenciu na knihu ulozenu v katalogu
     */
    default Kniha najdiReferenciuNaKnihu(Miestnost miestnost, String id){
        for(Kniha kp : miestnost.getKatalog()){
            if(kp.getISBN().toLowerCase().equals(id.toLowerCase())
                    || kp.getBasicInfo()[0].toLowerCase().equals(id.toLowerCase())){
                return kp;
            }
        }
        return null;
    }

    /**
     * @param args argumenty na premiestnenie
     * @param kh instancia knihkupectva v ktorej premiestnujeme knihy
     * @return Retazec v ktorom su ulozeny vystup.
     */
    String premiestni(String[] args, Knihkupectvo kh);

}


