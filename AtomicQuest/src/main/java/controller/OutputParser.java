
package controller;

/**
 * Classe che rappresenta un output da stampare a schermo e l'azione da eseguire sull'interfaccia.
 */
public class OutputParser {
    
    private String stringaDaStampare;
    private AzioneSuInterfaccia azione;

    /**
     * Costruttore di default.
     */
    public OutputParser() {
        this.azione = AzioneSuInterfaccia.NESSUNA;
    }
    
    /**
     * Costruttore con parametri.
     * @param stringaDaStampare la stringa da stampare
     * @param azione l'azione da eseguire sull'interfaccia
     */
    public OutputParser(String stringaDaStampare, AzioneSuInterfaccia azione) {
        this.stringaDaStampare = stringaDaStampare;
        this.azione = azione;
    }

    /**
     * Metodo che restituisce la stringa da stampare.
     * @return la stringa da stampare
     */
    public String getStringaDaStampare() {
        return stringaDaStampare;
    }

    /**
     * Metodo che restituisce l'azione da eseguire sull'interfaccia.
     * @return l'azione da eseguire sull'interfaccia
     */
    public AzioneSuInterfaccia getAzione() {
        return azione;
    }

    /**
     * Metodo che imposta la stringa da stampare.
     * @param stringaDaStampare la stringa da stampare
     */
    public void setStringaDaStampare(String stringaDaStampare) {
        this.stringaDaStampare = stringaDaStampare;
    }

    /**
     * Metodo che imposta l'azione da eseguire sull'interfaccia.+
     * @param azione l'azione da eseguire sull'interfaccia
     */
    public void setAzione(AzioneSuInterfaccia azione) {
        this.azione = azione;
    }
}
