package junas.robert.lagatoria.core.vydavatelstvo.spisovatelia;


import javafx.application.Platform;
import junas.robert.lagatoria.core.items.Text;
import junas.robert.lagatoria.core.utils.Observer;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.NormalnePisanie;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.Pisanie;
import junas.robert.lagatoria.core.vydavatelstvo.spisovatelia.pisanie.RychlePisanie;

import java.util.Random;

/**
 * Abstraktna trieda spisovatelov ktroy pisu knihu
 */
public abstract class Autor implements Observer {
    private String meno;
    private String prievzisko;
    private Boolean piseKnihu;
    //observer je trieda Vydavatelstvo.java
    private Observer observer;


    /**
     * Vytvori sa nova instancia autora
     * @param meno autora
     * @param prievzisko autora
     * @param observer sledovatel deja (Mal by byt vzdy vydavatel)
     */
    public Autor(String meno, String prievzisko, Observer observer){
        this.meno = meno;
        this.prievzisko = prievzisko;
        this.observer = observer;
        piseKnihu = false;
    }

    /**
     * Metoda spusti novy thread na ktorom autor vymysli a pise knihu a odosle ju do vydavatelstva
     * @param caller instnacia upozornujuca autora
     * @param msg sprava aku caller poslal autorovi
     */
    public void notify(Object caller, Object msg){
        if(piseKnihu) {
            //upozorni vydavatelstvo, ze neprijal poziadavku
            observer.notify(this, "Autor neprijal poziadavku");
            return;
        }

        //upozorni vydavatelstvo ze prijal poziadavku
        observer.notify(this,"\t" + meno + " " + prievzisko +" prijal poziadavku");
        piseKnihu = true;
        Autor autor = this;
        final Pisanie pisanie;
        //nahodne sa vyberie akou formou bude autor pisat
        if((int)(Math.random()*5) == 0){
            pisanie = new RychlePisanie();
        }else{
            pisanie = new NormalnePisanie();
        }
        Thread thread = new Thread(new Runnable() {
            String nazov = "";
            @Override
            public void run() {
                Text text = null;

                //proces vymyslania knihy
                Runnable myslienky = new Runnable() {
                    @Override
                    public void run() {
                        nazov = autor.vymysliKnihu();
                    }
                };

                //process dokoncenia knihy
                Runnable hotovo = new Runnable() {
                    @Override
                    public void run() {
                        observer.notify(autor,"Autor ["+getMeno()+" "+getPrievzisko()+"] dokoncil knihu a odoslal ju vydavatelovi");
                    }
                };

                //cakanie nez sa autor vymysliknihu
                try {
                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //lock na vypis do view
                Platform.runLater(myslienky);

                try {
                    text = autor.napisText(pisanie);
                    text.setNazov(nazov);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //autor odosle knihu vydavatelovi
                autor.odosliVydavatelovi(text);

                //lock na vypis do view
                Platform.runLater(hotovo);
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Proces vymyslenia knihy
     * @return nazov knihy/textu
     */
    private String vymysliKnihu() {
        observer.notify(this,"Autor ["+getMeno()+" "+getPrievzisko()+"] vymyslel Knihu");
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(97, 123)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * @param pisanie akou formou spisovatel pise
     * @return napisany text
     * @throws InterruptedException ak bol dej preruseny
     */
    public Text napisText(Pisanie pisanie) throws InterruptedException {
        return accept(pisanie);
    }

    /**
     * pre abstraktnu triedu Autor vracia null
     * @param pisanie akou formou spisovatel pise
     * @return napisany text
     * @throws InterruptedException ak bol dej preruseny
     */
    public Text accept(Pisanie pisanie) throws InterruptedException {
        return null;
    };

    /**
     * autor posiela svoju knihu vydavatelstvu
     * a nastavi sa na cakanie na poziadavku dalsej knihy
     * @param text odosielany text
     */
    private synchronized void odosliVydavatelovi(Text text){
        observer.notify(this,text);
        piseKnihu = false;
    }

    /**
     * @return vrati meno autora
     */
    public String getMeno() {
        return meno;
    }

    /**
     * @return vrati prievzisko autora
     */
    public String getPrievzisko() {
        return prievzisko;
    }
}
