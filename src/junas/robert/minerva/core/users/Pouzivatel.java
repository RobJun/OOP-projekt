package junas.robert.minerva.core.users;

public abstract class Pouzivatel {
    protected long id;
    protected String meno;
    private boolean close = false;
    Pouzivatel(String m, long id) {
        meno = m;
        this.id =id;
    };

    ///funkcia ktora povie programu nech hlavny loop programu ukonci
    public void exit(){
        close = true;
    }

    ///funckia ktora zistuje ci sa hlavny loop konci
    public boolean closeCallback(){
        return close;
    }
    ///vypise meno a id uzivatela
    public void vypisInfo(){
        System.out.println(meno + " ["+ id+"]");
    }
}
