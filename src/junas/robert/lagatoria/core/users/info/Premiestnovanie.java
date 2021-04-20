package junas.robert.lagatoria.core.users.info;

import junas.robert.lagatoria.core.items.Kniha;
import junas.robert.lagatoria.core.knihkupectvo.Knihkupectvo;
import junas.robert.lagatoria.core.knihkupectvo.rooms.Miestnost;

public interface Premiestnovanie {

    default Kniha najdiReferenciuNaKnihu(Miestnost s, int i){
        return  (s.getKatalog().isEmpty() || i >= s.getKatalog().size() ) ? null : s.getKatalog().get(i);
    }

    default Kniha najdiReferenciuNaKnihu(Miestnost s, String id){
        for(Kniha kp : s.getKatalog()){
            if(kp.getISBN().toLowerCase().equals(id.toLowerCase()) || kp.getBasicInfo()[0].toLowerCase().equals(id.toLowerCase())){
                return kp;
            }
        }
        return null;
    }

    String premiestni(String[] args, Knihkupectvo kh);

}
