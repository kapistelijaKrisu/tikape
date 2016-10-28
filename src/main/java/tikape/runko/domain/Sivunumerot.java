package tikape.runko.domain;

public class Sivunumerot {
    private int edellinen;
    private int seuraava;
    
    public Sivunumerot(int edellinen, int seuraava) {
        this.edellinen = edellinen;
        this.seuraava = seuraava;
    }

    public int getEdellinen() {
        return edellinen;
    }

    public void setEdellinen(int edellinen) {
        this.edellinen = edellinen;
    }

    public int getSeuraava() {
        return seuraava;
    }

    public void setSeuraava(int seuraava) {
        this.seuraava = seuraava;
    }

    @Override
    public String toString() {
        return "edellinen = " + edellinen + " seuraava = " + seuraava; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
