package junas.robert.lagatoria.gui.TableViews.data;

public class TextyNaVydanieData {
        private int id;
        private String meno;
        private String kategoria;
        private String text;

        private TextyNaVydanieData(int id, String meno, String kategoria, String text){
            this.id = id;
            this.meno = meno;
            this.kategoria = kategoria;
            this.text = text;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMeno() {
            return meno;
        }

        public void setMeno(String meno) {
            this.meno = meno;
        }

        public String getKategoria() {
            return kategoria;
        }

        public void setKategoria(String kategoria) {
            this.kategoria = kategoria;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    static public TextyNaVydanieData parseText(String data){
        int id = Integer.parseInt(data.split("|")[0]);
        String meno = data.substring(data.indexOf(']')+1,data.indexOf(':'));
        String kategoria = data.substring(data.indexOf('[') + 1, data.indexOf(']'));
        String text = data.substring(data.indexOf(':') + 1, data.lastIndexOf('['));
        return new TextyNaVydanieData(id,meno,kategoria,text);
    }
}
