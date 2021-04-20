package junas.robert.lagatoria.gui.TableViews.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KatalogData {
    private int id;
    private String nazov;
    private String autor;
    private String isbn;
    private String vydavatelstvo;

    private KatalogData(int id, String nazov, String autor, String isbn, String vydavatelstvo){
        this.id = id;
        this.nazov = nazov;
        this.autor = autor;
        this.isbn = isbn;
        this.vydavatelstvo = vydavatelstvo;
    }

    public static KatalogData parseText(String s){
        if(s.isEmpty()) return null;
        int id = Integer.parseInt(s.split("<>")[0]);
        String[] data = s.split("<>");
        return new KatalogData(id,data[1],data[2], data[3], data[4]);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getVydavatelstvo() {
        return vydavatelstvo;
    }

    public void setVydavatelstvo(String vydavatelstvo) {
        this.vydavatelstvo = vydavatelstvo;
    }
}
