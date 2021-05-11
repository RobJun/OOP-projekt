package junas.robert.lagatoria.gui.tableViews.data;

public class OdoberatelData {
    private int id;
    private String typ;
    private String nazov;

    private OdoberatelData(int id, String typ, String nazov){
        this.id = id;
        this.typ = typ;
        this.nazov = nazov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    static public OdoberatelData parseText(String data){
        String[] d = data.split(":");

        int id = Integer.parseInt(d[0]);
        String typ = d[1];
        String nazov = d[2];
        return new OdoberatelData(id,typ,nazov);
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }
}
