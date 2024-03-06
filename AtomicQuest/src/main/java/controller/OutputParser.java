
package controller;


public class OutputParser {
    
    private String stringaDaStampare;
    private AzioneSuInterfaccia azione;

    public OutputParser() {
        this.azione = AzioneSuInterfaccia.NESSUNA;
    }
    
    public OutputParser(String stringaDaStampare, AzioneSuInterfaccia azione) {
        this.stringaDaStampare = stringaDaStampare;
        this.azione = azione;
    }

    public String getStringaDaStampare() {
        return stringaDaStampare;
    }

    public AzioneSuInterfaccia getAzione() {
        return azione;
    }

    public void setStringaDaStampare(String stringaDaStampare) {
        this.stringaDaStampare = stringaDaStampare;
    }

    public void setAzione(AzioneSuInterfaccia azione) {
        this.azione = azione;
    }
}
