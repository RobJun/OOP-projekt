package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia;


import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.vydavatelstvo.Vydavatelstvo;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.NormalnePisanie;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.Pisanie;
import junas.robert.lagatoria.gui.Controller;

import java.util.Random;

public abstract class Autor {
    private String meno;
    private String prievzisko;
    private Boolean piseKnihu;
    private Vydavatelstvo vydavatelstvo;

    protected Pisanie pisanie;

    public Autor(String meno, String prievzisko, Vydavatelstvo vydavatelstvo){
        this.meno = meno;
        this.prievzisko = prievzisko;
        this.vydavatelstvo = vydavatelstvo;
        piseKnihu = false;
        pisanie = new NormalnePisanie();
    }

    public Boolean prijmiPoziadvku(){
        if(piseKnihu) return false;
        piseKnihu = true;
        Autor autor = this;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    String nazov = autor.vymysliKnihu();
                    Text text = autor.napisText();
                    text.setNazov(nazov);
                    autor.odosliVydavatelovi(text);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return true;
    }

    public String vymysliKnihu() {
        Controller.printline("Autor vymyslel Knihu");
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(97, 123)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Text napisText() throws InterruptedException {
        return accept(pisanie);
    }

    public Text accept(Pisanie pisanie) throws InterruptedException {
        return null;
    };

    public void odosliVydavatelovi(Text text){
        vydavatelstvo.prijmiText(text);
        piseKnihu = false;
    }


    public String getMeno() {
        return meno;
    }

    public String getPrievzisko() {
        return prievzisko;
    }
}
