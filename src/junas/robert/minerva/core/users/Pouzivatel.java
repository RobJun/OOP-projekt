package junas.robert.minerva.core.users;

abstract class Pouzivatel {
    private long id;
    private String meno;

    Pouzivatel(String m, long id) {
        meno = m;
        this.id =id;
    };
}
