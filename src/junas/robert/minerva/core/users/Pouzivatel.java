package junas.robert.minerva.core.users;

public abstract class Pouzivatel {
    protected long id;
    protected String meno;
    private boolean close = false;
    Pouzivatel(String m, long id) {
        meno = m;
        this.id =id;
    };

    public void exit(){
        close = true;
    }

    public boolean closeCallback(){
        return close;
    }

    public void vypisInfo(){
        System.out.println(meno + " ["+ id+"]");
    }
}
