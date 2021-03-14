package junas.robert.lagatoria.core.items;

public class PevnaVazba extends Obalka{
    private String material;

    public PevnaVazba(String dizajn, String farba,String material){
        super(dizajn,farba);
        typObalky = "Pevna vazba";
        this.material = material;
    }

    @Override
    public void getInfo() {
        super.getInfo();
        System.out.println("\t\ttyp vazby: " + typObalky + " ["+material+"]");
    }
}
