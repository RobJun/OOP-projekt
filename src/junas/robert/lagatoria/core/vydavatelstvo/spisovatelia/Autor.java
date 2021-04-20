package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia;


import javafx.application.Platform;
import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.NormalnePisanie;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.Pisanie;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.RychlePisanie;

import java.util.Random;

public abstract class Autor implements Observer {
    private String meno;
    private String prievzisko;
    private Boolean piseKnihu;
    private Observer observer;


    protected Pisanie pisanie;

    public Autor(String meno, String prievzisko, Observer observer){
        this.meno = meno;
        this.prievzisko = prievzisko;
        this.observer = observer;
        piseKnihu = false;
        pisanie = new NormalnePisanie();
    }

    /**
     * Metoda spusti novy thread na ktorom autor vymysli a pise knihu a odosle ju do vydavatelstva
     * @return ci autor prijal pozidavku
     */
    public void notify(Object o, Object s){
        if(piseKnihu) {
            observer.notify(this, "Autor neprijal poziadavku");
            return;
        }
        observer.notify(this,"\t" + meno + " " + prievzisko +" prijal poziadavku\n");

        piseKnihu = true;
        Autor autor = this;
        if((int)(Math.random()*5) == 0){
            pisanie = new RychlePisanie();
        }
        Thread thread = new Thread(new Runnable() {
            String nazov = "";
            @Override
            public void run() {
                Text text = null;

                Runnable myslienky = new Runnable() {
                    @Override
                    public void run() {
                        nazov = autor.vymysliKnihu();
                    }
                };

                Runnable hotovo = new Runnable() {
                    @Override
                    public void run() {
                        observer.notify(autor,"Autor ["+getMeno()+" "+getPrievzisko()+"] dokoncil knihu a odoslal ju vydavatelovi");
                    }
                };

                try {
                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(myslienky);

                try {
                    text = autor.napisText();
                    text.setNazov(nazov);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                autor.odosliVydavatelovi(text);
                Platform.runLater(hotovo);
                if(pisanie instanceof RychlePisanie)
                    pisanie = new NormalnePisanie();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    public String vymysliKnihu() {
        observer.notify(this,"Autor ["+getMeno()+" "+getPrievzisko()+"] vymyslel Knihu");
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

    public synchronized void odosliVydavatelovi(Text text){
        observer.notify(this,text);
        piseKnihu = false;
    }


    public String getMeno() {
        return meno;
    }

    public String getPrievzisko() {
        return prievzisko;
    }
}
