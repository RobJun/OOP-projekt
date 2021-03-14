package junas.robert.lagatoria.core.items;

public class BrozovanaVazba extends Obalka{
    private String obrazok;

    public BrozovanaVazba(String dizajn, String farba,String obrazok){
        super(dizajn,farba);
        typObalky = "Brozovana vazba";
        this.obrazok = obrazok;
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.println("\t\ttyp vazby: " + typObalky + " ["+obrazok+"]");
    }
}
